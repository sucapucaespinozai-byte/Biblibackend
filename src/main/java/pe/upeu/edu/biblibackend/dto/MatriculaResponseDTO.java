package pe.upeu.edu.biblibackend.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MatriculaResponseDTO {
    private Long id;
    private LocalDateTime fecha;
    private String periodo;
    private String estado;
    private Integer totalCreditos;
    private BigDecimal montoTotal;
    private String estudianteNombreCompleto;
    private String estudianteCodigo;
    private List<DetalleMatriculaResponseDTO> detalles;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DetalleMatriculaResponseDTO {
        private Long id;
        private String cursoCodigo;
        private String cursoNombre;
        private Integer creditos;
        private BigDecimal costo;
    }
}