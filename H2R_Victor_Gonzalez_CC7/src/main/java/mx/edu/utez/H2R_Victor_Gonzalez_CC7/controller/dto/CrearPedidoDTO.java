package mx.edu.utez.H2R_Victor_Gonzalez_CC7.controller.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CrearPedidoDTO {
    @NotNull(message = "El id del producto es obligatorio")
    private Long idProducto;
    @NotBlank(message = "El nombre del cliente es obligatorio")
    private String nombreCliente;
    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 1, message = "La cantidad debe ser mayor a cero")
    private Integer cantidad;
}
