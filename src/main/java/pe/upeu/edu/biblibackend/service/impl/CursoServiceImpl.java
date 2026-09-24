package pe.upeu.edu.biblibackend.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.upeu.edu.biblibackend.dto.CursoRequestDTO;
import pe.upeu.edu.biblibackend.entity.Carrera;
import pe.upeu.edu.biblibackend.entity.Curso;
import pe.upeu.edu.biblibackend.exception.RecursoNoEncontradoException;
import pe.upeu.edu.biblibackend.exception.ReglaNegocioException;
import pe.upeu.edu.biblibackend.repository.CarreraRepository;
import pe.upeu.edu.biblibackend.repository.CursoRepository;
import pe.upeu.edu.biblibackend.service.service.CursoService;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CursoServiceImpl implements  CursoService {
    private final CursoRepository cursoRepository;
    private final CarreraRepository carreraRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Curso> listarCursos() {
        return cursoRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Curso buscarPorId(Long id) {
        return cursoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Curso no encontrado con ID: " + id));
    }

    @Override
    @Transactional
    public Curso crearCurso(CursoRequestDTO dto) {
        if (cursoRepository.findByCodigo(dto.getCodigo()).isPresent()) {
            throw new ReglaNegocioException("Ya existe un curso con el código: " + dto.getCodigo());
        }
        Carrera carrera = carreraRepository.findById(dto.getCarreraId())
                .orElseThrow(() -> new RecursoNoEncontradoException("Carrera no encontrada con ID: " + dto.getCarreraId()));

        Curso curso = new Curso();
        curso.setCodigo(dto.getCodigo());
        curso.setNombre(dto.getNombre());
        curso.setCreditos(dto.getCreditos());
        curso.setCiclo(dto.getCiclo());
        curso.setVacantes(dto.getVacantes());
        curso.setEstado(1);
        curso.setCarrera(carrera);

        return cursoRepository.save(curso);
    }

    @Override
    @Transactional
    public Curso actualizarCurso(Long id, CursoRequestDTO dto) {
        Curso curso = buscarPorId(id);
        Carrera carrera = carreraRepository.findById(dto.getCarreraId())
                .orElseThrow(() -> new RecursoNoEncontradoException("Carrera no encontrada con ID: " + dto.getCarreraId()));

        curso.setCodigo(dto.getCodigo());
        curso.setNombre(dto.getNombre());
        curso.setCreditos(dto.getCreditos());
        curso.setCiclo(dto.getCiclo());
        curso.setVacantes(dto.getVacantes());
        curso.setCarrera(carrera);

        return cursoRepository.save(curso);
    }

    @Override
    @Transactional
    public void eliminarCurso(Long id) {
        Curso curso = buscarPorId(id);
        cursoRepository.delete(curso);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Curso> buscarCursosPersonalizado(Long carreraId, Integer ciclo, Boolean conVacantes, String orden, String dir) {
        List<Curso> cursos = cursoRepository.findAll().stream()
                .filter(c -> c.getCarrera() != null && c.getCarrera().getId().equals(carreraId))
                .filter(c -> c.getCiclo().equals(ciclo))
                .filter(c -> !conVacantes || c.getVacantes() > 0)
                .collect(Collectors.toList());

        Comparator<Curso> comparador = null;
        if ("vacantes".equalsIgnoreCase(orden)) {
            comparador = Comparator.comparing(Curso::getVacantes);
        } else if ("nombre".equalsIgnoreCase(orden)) {
            comparador = Comparator.comparing(Curso::getNombre);
        }

        if (comparador != null) {
            if ("desc".equalsIgnoreCase(dir)) {
                comparador = comparador.reversed();
            }
            cursos.sort(comparador);
        }

        return cursos;
    }
}