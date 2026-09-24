package pe.upeu.edu.biblibackend.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.upeu.edu.biblibackend.dto.*;
import pe.upeu.edu.biblibackend.entity.*;
import pe.upeu.edu.biblibackend.exception.RecursoNoEncontradoException;
import pe.upeu.edu.biblibackend.exception.ReglaNegocioException;
import pe.upeu.edu.biblibackend.repository.*;
import pe.upeu.edu.biblibackend.service.service.MatriculaService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MatriculaServiceImpl implements  MatriculaService {
    private final MatriculaRepository matriculaRepository;
    private final EstudianteRepository estudianteRepository;
    private final CursoRepository cursoRepository;

    @Override
    @Transactional
    public MatriculaResponseDTO registrarMatricula(MatriculaRequestDTO dto) {
        Estudiante estudiante = estudianteRepository.findById(dto.getEstudianteId())
                .orElseThrow(() -> new RecursoNoEncontradoException("Estudiante no encontrado con ID: " + dto.getEstudianteId()));

        // Validación: Estudiante inactivo (RN-01)
        if (estudiante.getEstado() == 0) {
            throw new ReglaNegocioException("El estudiante se encuentra inactivo y no puede matricularse");
        }

        // Validación: Ya matriculado en el mismo periodo (RN-03)
        boolean yaMatriculado = matriculaRepository.findAll().stream()
                .anyMatch(m -> m.getEstudiante().getId().equals(dto.getEstudianteId())
                        && m.getPeriodo().equals(dto.getPeriodo())
                        && !"ANULADA".equals(m.getEstado()));
        if (yaMatriculado) {
            throw new ReglaNegocioException("El estudiante ya se encuentra matriculado en este periodo");
        }

        Matricula matricula = new Matricula();
        matricula.setEstudiante(estudiante);
        matricula.setPeriodo(dto.getPeriodo());
        matricula.setEstado("REGISTRADA");

        int totalCreditos = 0;
        BigDecimal montoTotal = BigDecimal.ZERO;
        BigDecimal costoPorCredito = new BigDecimal("50.00");

        List<DetalleMatricula> detalles = new ArrayList<>();

        for (DetalleMatriculaRequestDTO detDto : dto.getDetalles()) {
            Curso curso = cursoRepository.findById(detDto.getCursoId())
                    .orElseThrow(() -> new RecursoNoEncontradoException("Curso no encontrado con ID: " + detDto.getCursoId()));

            // Validación: Vacantes disponibles (RN-02)
            if (curso.getVacantes() <= 0) {
                throw new ReglaNegocioException("El curso " + curso.getNombre() + " no tiene vacantes disponibles");
            }

            curso.setVacantes(curso.getVacantes() - 1);
            cursoRepository.save(curso);

            DetalleMatricula detalle = new DetalleMatricula();
            detalle.setMatricula(matricula);
            detalle.setCurso(curso);
            detalle.setCreditos(curso.getCreditos());

            BigDecimal costoCurso = costoPorCredito.multiply(new BigDecimal(curso.getCreditos()));
            detalle.setCosto(costoCurso);

            detalles.add(detalle);

            totalCreditos += curso.getCreditos();
            montoTotal = montoTotal.add(costoCurso);
        }

        if (totalCreditos > 20) {
            throw new ReglaNegocioException("El total de créditos excede el límite permitido por periodo");
        }

        matricula.setTotalCreditos(totalCreditos);
        matricula.setMontoTotal(montoTotal);
        matricula.setDetalles(detalles);

        Matricula guardada = matriculaRepository.save(matricula);
        return mapearAMatriculaResponseDTO(guardada);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MatriculaResponseDTO> listarMatriculas() {
        return matriculaRepository.findAll().stream()
                .map(this::mapearAMatriculaResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public MatriculaResponseDTO buscarPorId(Long id) {
        Matricula matricula = matriculaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Matrícula no encontrada con ID: " + id));
        return mapearAMatriculaResponseDTO(matricula);
    }

    @Override
    @Transactional
    public void anularMatricula(Long id) {
        Matricula matricula = matriculaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Matrícula no encontrada con ID: " + id));

        if ("ANULADA".equals(matricula.getEstado())) {
            throw new ReglaNegocioException("La matrícula ya se encuentra anulada");
        }

        matricula.setEstado("ANULADA");

        for (DetalleMatricula detalle : matricula.getDetalles()) {
            Curso curso = detalle.getCurso();
            curso.setVacantes(curso.getVacantes() + 1);
            cursoRepository.save(curso);
        }

        matriculaRepository.save(matricula);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MatriculadosPorCursoDTO> obtenerMatriculadosPorCurso() {
        return matriculaRepository.obtenerMatriculadosPorCurso();
    }

    private MatriculaResponseDTO mapearAMatriculaResponseDTO(Matricula m) {
        List<MatriculaResponseDTO.DetalleMatriculaResponseDTO> listaDetalles = m.getDetalles().stream()
                .map(d -> new MatriculaResponseDTO.DetalleMatriculaResponseDTO(
                        d.getId(),
                        d.getCurso().getCodigo(),
                        d.getCurso().getNombre(),
                        d.getCreditos(),
                        d.getCosto()
                )).collect(Collectors.toList());

        return new MatriculaResponseDTO(
                m.getId(),
                m.getFecha(),
                m.getPeriodo(),
                m.getEstado(),
                m.getTotalCreditos(),
                m.getMontoTotal(),
                m.getEstudiante().getNombres() + " " + m.getEstudiante().getApellidos(),
                m.getEstudiante().getCodigo(),
                listaDetalles
        );
    }
}