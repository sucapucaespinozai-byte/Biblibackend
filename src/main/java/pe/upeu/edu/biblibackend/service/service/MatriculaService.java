package pe.upeu.edu.biblibackend.service.service;

import pe.upeu.edu.biblibackend.dto.MatriculadosPorCursoDTO;
import pe.upeu.edu.biblibackend.dto.MatriculaRequestDTO;
import pe.upeu.edu.biblibackend.dto.MatriculaResponseDTO;

import java.util.List;

public interface MatriculaService {
    MatriculaResponseDTO registrarMatricula(MatriculaRequestDTO dto);
    List<MatriculaResponseDTO> listarMatriculas();
    MatriculaResponseDTO buscarPorId(Long id);
    void anularMatricula(Long id);
    List<MatriculadosPorCursoDTO> obtenerMatriculadosPorCurso();
}