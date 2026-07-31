package ec.edu.espe.agrosmart.repository;

import ec.edu.espe.agrosmart.entity.ProductoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<ProductoEntity, Long> {
    List<ProductoEntity> findByCategoriaIgnoreCase(String categoria);
}