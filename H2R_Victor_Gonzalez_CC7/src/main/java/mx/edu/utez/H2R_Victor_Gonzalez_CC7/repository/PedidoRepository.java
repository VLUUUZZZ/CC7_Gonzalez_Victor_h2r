package mx.edu.utez.H2R_Victor_Gonzalez_CC7.repository;

import mx.edu.utez.H2R_Victor_Gonzalez_CC7.model.EstadoPedido;
import mx.edu.utez.H2R_Victor_Gonzalez_CC7.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    List<Pedido> findAllByEstado(EstadoPedido estado);
    List<Pedido> findAllByProductoIdAndEstado(Long idProducto, EstadoPedido estado);
    boolean existsByProductoIdAndEstado(Long idProducto, EstadoPedido estado);
}
