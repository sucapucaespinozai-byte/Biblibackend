package pe.upeu.edu.biblibackend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.upeu.edu.biblibackend.dto.MatriculadosPorCursoDTO;
import pe.upeu.edu.biblibackend.service.service.MatriculaService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reportes")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ReporteController {

    private final MatriculaService matriculaService;

    @GetMapping("/matriculados-por-curso")
    public ResponseEntity<List<MatriculadosPorCursoDTO>> obtenerMatriculadosPorCurso() {
        return ResponseEntity.ok(matriculaService.obtenerMatriculadosPorCurso());
    }
}