package mx.edu.utez.H2R_Victor_Gonzalez_CC7.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductoDTO {
    private Long id;
    private String nombre;
    private String categoria;
    private Double precio;
    private Integer stock;
    private boolean activo;
}
