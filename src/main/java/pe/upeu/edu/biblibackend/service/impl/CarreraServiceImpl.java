package pe.upeu.edu.biblibackend.service.impl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.upeu.edu.biblibackend.dto.CarreraRequestDTO;
import pe.upeu.edu.biblibackend.dto.CarreraResponseDTO;
import pe.upeu.edu.biblibackend.entity.Carrera;
import pe.upeu.edu.biblibackend.exception.RecursoNoEncontradoException;
import pe.upeu.edu.biblibackend.exception.ReglaNegocioException;
import pe.upeu.edu.biblibackend.repository.CarreraRepository;
import pe.upeu.edu.biblibackend.service.service.CarreraService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CarreraServiceImpl implements  CarreraService  {
    private final CarreraRepository carreraRepository;

    @Override
    @Transactional(readOnly = true)
    public List<CarreraResponseDTO> listarCarreras() {
        return carreraRepository.findAll().stream()
                .map(this::mapearADto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public CarreraResponseDTO buscarPorId(Long id) {
        Carrera carrera = carreraRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Carrera no encontrada con ID: " + id));
        return mapearADto(carrera);
    }

    @Override
    @Transactional
    public CarreraResponseDTO crearCarrera(CarreraRequestDTO dto) {
        if (carreraRepository.findByNombre(dto.getNombre()).isPresent()) {
            throw new ReglaNegocioException("Ya existe una carrera con ese nombre");
        }
        Carrera carrera = new Carrera();
        carrera.setNombre(dto.getNombre());
        carrera.setDescripcion(dto.getDescripcion());
        carrera.setEstado(1);

        Carrera guardada = carreraRepository.save(carrera);
        return mapearADto(guardada);
    }

    @Override
    @Transactional
    public CarreraResponseDTO actualizarCarrera(Long id, CarreraRequestDTO dto) {
        Carrera carrera = carreraRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Carrera no encontrada con ID: " + id));

        carrera.setNombre(dto.getNombre());
        carrera.setDescripcion(dto.getDescripcion());

        Carrera actualizada = carreraRepository.save(carrera);
        return mapearADto(actualizada);
    }

    @Override
    @Transactional
    public void eliminarCarrera(Long id) {
        Carrera carrera = carreraRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Carrera no encontrada con ID: " + id));
        carreraRepository.delete(carrera);
    }

    private CarreraResponseDTO mapearADto(Carrera carrera) {
        return new CarreraResponseDTO(
                carrera.getId(),
                carrera.getNombre(),
                carrera.getDescripcion(),
                carrera.getEstado(),
                carrera.getFechaCreacion()
        );
    }
}
