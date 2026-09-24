package pe.upeu.edu.biblibackend.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.upeu.edu.biblibackend.dto.MatriculaRequestDTO;
import pe.upeu.edu.biblibackend.dto.MatriculaResponseDTO;
import pe.upeu.edu.biblibackend.dto.MatriculadosPorCursoDTO;
import pe.upeu.edu.biblibackend.service.service.MatriculaService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/matriculas")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")

public class MatriculaController {

    private final MatriculaService matriculaService;

    @PostMapping
    public ResponseEntity<MatriculaResponseDTO> registrar(@RequestBody MatriculaRequestDTO dto) {
        return new ResponseEntity<>(matriculaService.registrarMatricula(dto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<MatriculaResponseDTO>> listar() {
        return ResponseEntity.ok(matriculaService.listarMatriculas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MatriculaResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(matriculaService.buscarPorId(id));
    }

    @PatchMapping("/{id}/anular")
    public ResponseEntity<Void> anular(@PathVariable Long id) {
        matriculaService.anularMatricula(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/reporte/matriculados-por-curso")
    public ResponseEntity<List<MatriculadosPorCursoDTO>> obtenerMatriculadosPorCurso() {
        return ResponseEntity.ok(matriculaService.obtenerMatriculadosPorCurso());
    }
}