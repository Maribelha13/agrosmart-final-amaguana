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
                    new ProductoEntity(null, "Banano Cavendish Orgánico", "Banano", new BigDecimal("12.50"), 150, true),
                    new ProductoEntity(null, "Banano Orito Premium", "Banano", new BigDecimal("8.75"), 200, true),
                    new ProductoEntity(null, "Banano Rojo de Exportación", "Banano", new BigDecimal("15.00"), 80, true),
                    new ProductoEntity(null, "Banano Plátano Verde Descarte", "Banano", new BigDecimal("4.20"), 0, false),
                    new ProductoEntity(null, "Banano Macho Comercial", "Banano", new BigDecimal("9.90"), 120, true)
            );
            repository.saveAll(semillero);
        }
    }
}