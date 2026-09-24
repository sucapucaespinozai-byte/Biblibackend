package pe.upeu.edu.biblibackend.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetalleMatriculaRequestDTO {

    @NotNull(message = "El ID del curso es obligatorio")
    private Long cursoId;
}
