package pe.upeu.edu.biblibackend.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.upeu.edu.biblibackend.dto.CarreraRequestDTO;
import pe.upeu.edu.biblibackend.dto.CarreraResponseDTO;
import pe.upeu.edu.biblibackend.service.service.CarreraService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/carreras")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CarreraController {

    private final CarreraService carreraService;

    @GetMapping
    public ResponseEntity<List<CarreraResponseDTO>> listar() {
        return ResponseEntity.ok(carreraService.listarCarreras());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarreraResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(carreraService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<CarreraResponseDTO> crear(@RequestBody CarreraRequestDTO dto) {
        return new ResponseEntity<>(carreraService.crearCarrera(dto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CarreraResponseDTO> actualizar(@PathVariable Long id, @RequestBody CarreraRequestDTO dto) {
        return ResponseEntity.ok(carreraService.actualizarCarrera(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        carreraService.eliminarCarrera(id);
        return ResponseEntity.noContent().build();
    }
}
