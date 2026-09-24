package pe.upeu.edu.biblibackend.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MatriculaRequestDTO {

    @NotNull(message = "El ID del estudiante es obligatorio")
    private Long estudianteId;

    @NotBlank(message = "El periodo es obligatorio")
    private String periodo;

    @NotEmpty(message = "La matrícula debe contener al menos un curso")
    private List<DetalleMatriculaRequestDTO> detalles;
}