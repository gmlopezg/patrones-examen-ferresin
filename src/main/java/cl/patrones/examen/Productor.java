package cl.patrones.examen;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cl.patrones.examen.descuentos.CalculadoraDescuentos;
import cl.patrones.examen.descuentos.DescuentoEmpleado;
import cl.patrones.examen.descuentos.DescuentoPorDiaYCategoria;
import cl.patrones.examen.descuentos.DescuentoStrategy;
import cl.patrones.examen.productos.service.ProductoServiceImpl;

@Configuration
public class Productor {

    @Bean
    ProductoServiceImpl productoServiceImpl() {
        return new ProductoServiceImpl();
    }

    // === DESCUENTOS ===

    @Bean
    public DescuentoStrategy descuentoPorDiaYCategoria() {
        return new DescuentoPorDiaYCategoria();
    }

    @Bean
    public DescuentoStrategy descuentoEmpleado() {
        return new DescuentoEmpleado();
    }

    @Bean
    public CalculadoraDescuentos calculadoraDescuentos(List<DescuentoStrategy> estrategias) {
        // Spring inyecta automáticamente todas las estrategias definidas como beans
        return new CalculadoraDescuentos(estrategias);
    }
}
