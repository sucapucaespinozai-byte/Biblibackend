package pe.upeu.edu.biblibackend.service.service;

import pe.upeu.edu.biblibackend.dto.CursoRequestDTO;
import pe.upeu.edu.biblibackend.entity.Curso;

import java.util.List;

public interface CursoService {
    List<Curso> listarCursos();
    Curso buscarPorId(Long id);
    Curso crearCurso(CursoRequestDTO dto);
    Curso actualizarCurso(Long id, CursoRequestDTO dto);
    void eliminarCurso(Long id);
    List<Curso> buscarCursosPersonalizado(Long carreraId, Integer ciclo, Boolean conVacantes, String orden, String dir);
}