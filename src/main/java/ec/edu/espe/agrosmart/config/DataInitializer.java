package ec.edu.espe.agrosmart.config;

import ec.edu.espe.agrosmart.entity.ProductoEntity;
import ec.edu.espe.agrosmart.repository.ProductoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final ProductoRepository repository;

    public DataInitializer(ProductoRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) {
        if (repository.count() == 0) {
            List<ProductoEntity> semillero = List.of(
                    new ProductoEntity(null, "Banano Cavendish Orgánico", "Banano", new BigDecimal("1.50"), 100, "Orgánico"),
                    new ProductoEntity(null, "Banano Orito Premium", "Banano", new BigDecimal("1.20"), 80, "Premium"),
                    new ProductoEntity(null, "Banano Rojo de Exportación", "Banano", new BigDecimal("2.00"), 50, "Exportación"),
                    new ProductoEntity(null, "Banano Plátano Verde Descarte", "Banano", new BigDecimal("0.80"), 200, "Descarte"),
                    new ProductoEntity(null, "Banano Macho Comercial", "Banano", new BigDecimal("1.00"), 150, "Comercial")
            );
            repository.saveAll(semillero);
        }
    }
}