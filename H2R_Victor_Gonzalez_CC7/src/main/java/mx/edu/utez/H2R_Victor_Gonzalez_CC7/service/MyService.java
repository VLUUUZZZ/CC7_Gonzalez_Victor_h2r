package mx.edu.utez.H2R_Victor_Gonzalez_CC7.service;

import mx.edu.utez.H2R_Victor_Gonzalez_CC7.controller.dto.CrearPedidoDTO;
import mx.edu.utez.H2R_Victor_Gonzalez_CC7.controller.dto.PedidoDetalleDTO;
import mx.edu.utez.H2R_Victor_Gonzalez_CC7.controller.dto.ProductoDTO;
import mx.edu.utez.H2R_Victor_Gonzalez_CC7.exception.CustomException;
import mx.edu.utez.H2R_Victor_Gonzalez_CC7.model.EstadoPedido;
import mx.edu.utez.H2R_Victor_Gonzalez_CC7.model.Pedido;
import mx.edu.utez.H2R_Victor_Gonzalez_CC7.model.Producto;
import mx.edu.utez.H2R_Victor_Gonzalez_CC7.repository.PedidoRepository;
import mx.edu.utez.H2R_Victor_Gonzalez_CC7.repository.ProductoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class MyService {
    private final PedidoRepository pedidoRepository;
    private final ProductoRepository productoRepository;

    public MyService(PedidoRepository pedidoRepository, ProductoRepository productoRepository) {
        this.pedidoRepository = pedidoRepository;
        this.productoRepository = productoRepository;
    }


    @Transactional
    public Pedido crearPedido(CrearPedidoDTO payload){
        Producto producto = productoRepository.findById(payload.getIdProducto())
                .orElseThrow(
                        () -> new CustomException("El producto no existe", HttpStatus.NOT_FOUND)
                );

        if (!producto.isActivo()){
            throw new CustomException("El producto no esta activo");
        }

        if (producto.getStock() < payload.getCantidad()){
            throw new CustomException("No hay stock suficiente");
        }

        Pedido pedido = new Pedido();
        pedido.setNombreCliente(payload.getNombreCliente());
        pedido.setCantidad(payload.getCantidad());
        pedido.setPrecioUnitario(producto.getPrecio());
        pedido.setTotal(producto.getPrecio() * payload.getCantidad());
        pedido.setEstado(EstadoPedido.PENDIENTE);
        pedido.setProducto(producto);

        producto.setStock(producto.getStock() - payload.getCantidad());
        productoRepository.save(producto);

        return pedidoRepository.save(pedido);
    }


    @Transactional
    public Pedido cancelarPedido(Long id){
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(
                        () -> new CustomException("El pedido no existe", HttpStatus.NOT_FOUND)
                );

        if (pedido.getEstado() != EstadoPedido.PENDIENTE){
            throw new CustomException("Solo se pueden cancelar pedidos pendientes");
        }

        pedido.setEstado(EstadoPedido.CANCELADO);

        Producto producto = pedido.getProducto();
        producto.setStock(producto.getStock() + pedido.getCantidad());
        productoRepository.save(producto);

        return pedidoRepository.save(pedido);
    }


    @Transactional
    public Pedido procesarPedido(Long id){
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(
                        () -> new CustomException("El pedido no existe", HttpStatus.NOT_FOUND)
                );

        if (pedido.getEstado() != EstadoPedido.PENDIENTE){
            throw new CustomException("Solo se pueden procesar pedidos pendientes");
        }

        pedido.setEstado(EstadoPedido.PROCESADO);

        return pedidoRepository.save(pedido);
    }


    @Transactional
    public Producto desactivarProducto(Long id){
        Producto producto = productoRepository.findById(id)
                .orElseThrow(
                        () -> new CustomException("El producto no existe", HttpStatus.NOT_FOUND)
                );

        if (pedidoRepository.existsByProductoIdAndEstado(id, EstadoPedido.PENDIENTE)){
            throw new CustomException("No se puede desactivar un producto con pedidos pendientes", HttpStatus.CONFLICT);
        }

        producto.setActivo(false);

        return productoRepository.save(producto);
    }


    @Transactional
    public void cancelarPedidosPendientes(Long idProducto){
        Producto producto = productoRepository.findById(idProducto)
                .orElseThrow(
                        () -> new CustomException("El producto no existe", HttpStatus.NOT_FOUND)
                );

        List<Pedido> pedidos = pedidoRepository
                .findAllByProductoIdAndEstado(idProducto, EstadoPedido.PENDIENTE);

        for (int i = 0; i < pedidos.size(); i++) {
            Pedido pedido = pedidos.get(i);

            pedido.setEstado(EstadoPedido.CANCELADO);
            producto.setStock(producto.getStock() + pedido.getCantidad());

            pedidoRepository.save(pedido);
        }

        productoRepository.save(producto);
    }


    public List<Pedido> pedidosPorEstado(EstadoPedido estado){
        return pedidoRepository.findAllByEstado(estado);
    }


    public PedidoDetalleDTO pedidoPorId(Long id){
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(
                        () -> new CustomException("El pedido no existe", HttpStatus.NOT_FOUND)
                );

        Producto producto = pedido.getProducto();

        return new PedidoDetalleDTO(
                pedido.getId(),
                pedido.getNombreCliente(),
                pedido.getCantidad(),
                pedido.getPrecioUnitario(),
                pedido.getTotal(),
                pedido.getEstado(),
                producto.getId(),
                producto.getNombre(),
                producto.getCategoria()
        );
    }


    public List<ProductoDTO> productosPorCategoria(String categoria){
        return convertirProductos(productoRepository.findAllByCategoria(categoria));
    }


    public List<ProductoDTO> todosLosProductos(){
        return convertirProductos(productoRepository.findAll());
    }


    public List<ProductoDTO> productosActivos(){
        return convertirProductos(productoRepository.findAllByActivoTrue());
    }


    private List<ProductoDTO> convertirProductos(List<Producto> productos){
        List<ProductoDTO> resultado = new ArrayList<>();

        for (int i = 0; i < productos.size(); i++) {
            Producto producto = productos.get(i);

            resultado.add(new ProductoDTO(
                    producto.getId(),
                    producto.getNombre(),
                    producto.getCategoria(),
                    producto.getPrecio(),
                    producto.getStock(),
                    producto.isActivo()
            ));
        }

        return resultado;
    }
}
