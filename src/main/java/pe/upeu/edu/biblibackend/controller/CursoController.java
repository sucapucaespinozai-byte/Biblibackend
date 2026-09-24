package pe.upeu.edu.biblibackend.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.upeu.edu.biblibackend.dto.CursoRequestDTO;
import pe.upeu.edu.biblibackend.entity.Curso;
import pe.upeu.edu.biblibackend.service.service.CursoService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cursos")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CursoController {

    private final CursoService cursoService;

    @GetMapping
    public ResponseEntity<List<Curso>> listar() {
        return ResponseEntity.ok(cursoService.listarCursos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Curso> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(cursoService.buscarPorId(id));
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<Curso>> buscarCursos(
            @RequestParam Long carreraId,
            @RequestParam Integer ciclo,
            @RequestParam Boolean conVacantes,
            @RequestParam String orden,
            @RequestParam String dir) {

        List<Curso> resultados = cursoService.buscarCursosPersonalizado(carreraId, ciclo, conVacantes, orden, dir);
        return ResponseEntity.ok(resultados);
    }

    @PostMapping
    public ResponseEntity<Curso> crear(@Valid @RequestBody CursoRequestDTO dto) {
        return new ResponseEntity<>(cursoService.crearCurso(dto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Curso> actualizar(@PathVariable Long id, @Valid @RequestBody CursoRequestDTO dto) {
        return ResponseEntity.ok(cursoService.actualizarCurso(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        cursoService.eliminarCurso(id);
        return ResponseEntity.noContent().build();
    }
}