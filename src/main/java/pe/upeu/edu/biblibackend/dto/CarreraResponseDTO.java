package pe.upeu.edu.biblibackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarreraResponseDTO {
    private Long id;
    private String nombre;
    private String descripcion;
    private Integer estado;
    private LocalDateTime fechaCreacion;
}