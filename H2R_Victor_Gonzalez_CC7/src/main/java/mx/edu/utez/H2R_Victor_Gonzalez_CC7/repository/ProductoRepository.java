package mx.edu.utez.H2R_Victor_Gonzalez_CC7.repository;

import mx.edu.utez.H2R_Victor_Gonzalez_CC7.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    List<Producto> findAllByCategoria(String categoria);
    List<Producto> findAllByActivoTrue();
}
