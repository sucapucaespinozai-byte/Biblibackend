package pe.upeu.edu.biblibackend.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MatriculadosPorCursoDTO {
    private String cursoCodigo;
    private String cursoNombre;
    private Long totalMatriculados;
}