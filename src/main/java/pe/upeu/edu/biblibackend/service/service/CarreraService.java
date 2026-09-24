package pe.upeu.edu.biblibackend.service.service;

import pe.upeu.edu.biblibackend.dto.CarreraRequestDTO;
import pe.upeu.edu.biblibackend.dto.CarreraResponseDTO;

import java.util.List;

public interface CarreraService {
    List<CarreraResponseDTO> listarCarreras();
    CarreraResponseDTO buscarPorId(Long id);
    CarreraResponseDTO crearCarrera(CarreraRequestDTO dto);
    CarreraResponseDTO actualizarCarrera(Long id, CarreraRequestDTO dto);
    void eliminarCarrera(Long id);
}