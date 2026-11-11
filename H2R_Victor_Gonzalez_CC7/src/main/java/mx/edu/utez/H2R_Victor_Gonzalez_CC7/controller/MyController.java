package mx.edu.utez.H2R_Victor_Gonzalez_CC7.controller;

import mx.edu.utez.H2R_Victor_Gonzalez_CC7.controller.dto.CrearPedidoDTO;
import mx.edu.utez.H2R_Victor_Gonzalez_CC7.controller.dto.PedidoDetalleDTO;
import mx.edu.utez.H2R_Victor_Gonzalez_CC7.controller.dto.ProductoDTO;
import mx.edu.utez.H2R_Victor_Gonzalez_CC7.model.EstadoPedido;
import mx.edu.utez.H2R_Victor_Gonzalez_CC7.model.Pedido;
import mx.edu.utez.H2R_Victor_Gonzalez_CC7.model.Producto;
import mx.edu.utez.H2R_Victor_Gonzalez_CC7.service.MyService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/controlador")
@CrossOrigin("*")
public class MyController {
    private final MyService service;

    public MyController(MyService service) {
        this.service = service;
    }


    @PostMapping("/crear-pedido")
    public ResponseEntity<Pedido> crearPedido
            (@RequestBody @Valid CrearPedidoDTO payload){
        return ResponseEntity
                .status(201)
                .body(service.crearPedido(payload));
    }

    @PatchMapping("/cancelar-pedido/{id}")
    public ResponseEntity<Pedido> cancelarPedido
            (@PathVariable Long id){
        return ResponseEntity.status(200)
                .body(service.cancelarPedido(id));
    }

    @PatchMapping("/procesar-pedido/{id}")
    public ResponseEntity<Pedido> procesarPedido
            (@PathVariable Long id){
        return ResponseEntity.status(200)
                .body(service.procesarPedido(id));
    }

    @PatchMapping("/desactivar-producto/{id}")
    public ResponseEntity<Producto> desactivarProducto
            (@PathVariable Long id){
        return ResponseEntity.status(200)
                .body(service.desactivarProducto(id));
    }

    @PatchMapping("/cancelar-pedidos-producto/{idProducto}")
    public ResponseEntity<Object> cancelarPedidosPendientes
            (@PathVariable Long idProducto){
        service.cancelarPedidosPendientes(idProducto);
        return ResponseEntity.status(200).body("Pedidos cancelados correctamente");
    }

    @GetMapping("/pedidos/{estado}")
    public ResponseEntity<List<Pedido>> pedidosPorEstado
            (@PathVariable EstadoPedido estado){
        return ResponseEntity.status(200)
                .body(service.pedidosPorEstado(estado));
    }

    @GetMapping("/pedido/{id}")
    public ResponseEntity<PedidoDetalleDTO> pedidoPorId
            (@PathVariable Long id){
        return ResponseEntity.status(200)
                .body(service.pedidoPorId(id));
    }

    @GetMapping("/productos/categoria/{categoria}")
    public ResponseEntity<List<ProductoDTO>> productosPorCategoria
            (@PathVariable String categoria){
        return ResponseEntity.status(200)
                .body(service.productosPorCategoria(categoria));
    }

    @GetMapping("/productos")
    public ResponseEntity<List<ProductoDTO>> todosLosProductos(){
        return ResponseEntity.status(200)
                .body(service.todosLosProductos());
    }

    @GetMapping("/productos-activos")
    public ResponseEntity<List<ProductoDTO>> productosActivos(){
        return ResponseEntity.status(200)
                .body(service.productosActivos());
    }
}
