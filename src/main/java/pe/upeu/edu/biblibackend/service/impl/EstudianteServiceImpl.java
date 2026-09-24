package pe.upeu.edu.biblibackend.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.upeu.edu.biblibackend.entity.Estudiante;
import pe.upeu.edu.biblibackend.exception.RecursoNoEncontradoException;
import pe.upeu.edu.biblibackend.repository.EstudianteRepository;
import pe.upeu.edu.biblibackend.service.service.EstudianteService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EstudianteServiceImpl implements EstudianteService{
    private final EstudianteRepository estudianteRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Estudiante> listar() {
        return estudianteRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Estudiante buscarPorId(Long id) {
        return estudianteRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Estudiante no encontrado con ID: " + id));
    }

    @Override
    @Transactional
    public Estudiante guardar(Estudiante estudiante) {
        return estudianteRepository.save(estudiante);
    }

    @Override
    @Transactional
    public Estudiante actualizar(Long id, Estudiante estudiante) {
        Estudiante existente = buscarPorId(id);
        existente.setCodigo(estudiante.getCodigo());
        existente.setNombres(estudiante.getNombres());
        existente.setApellidos(estudiante.getApellidos());
        existente.setEmail(estudiante.getEmail());
        existente.setEstado(estudiante.getEstado());
        return estudianteRepository.save(existente);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        Estudiante estudiante = buscarPorId(id);
        estudianteRepository.delete(estudiante);
    }
}
