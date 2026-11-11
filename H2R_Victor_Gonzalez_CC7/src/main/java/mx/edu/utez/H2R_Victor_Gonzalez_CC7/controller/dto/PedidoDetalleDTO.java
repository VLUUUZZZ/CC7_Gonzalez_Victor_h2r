package mx.edu.utez.H2R_Victor_Gonzalez_CC7.controller.dto;

import mx.edu.utez.H2R_Victor_Gonzalez_CC7.model.EstadoPedido;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PedidoDetalleDTO {
    private Long id;
    private String nombreCliente;
    private Integer cantidad;
    private Double precioUnitario;
    private Double total;
    private EstadoPedido estado;
    private Long idProducto;
    private String nombreProducto;
    private String categoriaProducto;
}
