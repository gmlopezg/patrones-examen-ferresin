package examen.descuentos;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import cl.patrones.examen.descuentos.DescuentoEmpleado;
import cl.patrones.examen.descuentos.TipoUsuario;
import cl.patrones.examen.productos.domain.Producto;

class DescuentoEmpleadoTest {

    private final DescuentoEmpleado descuento = new DescuentoEmpleado();

    @Test
    void empleadoRecibeCincoPorciento() {
        // Producto puede ser null porque DescuentoEmpleado no lo usa realmente
        Producto producto = null;

        BigDecimal porcentaje = descuento.calcularPorcentaje(
                producto,
                TipoUsuario.EMPLEADO,
                LocalDate.now()
        );

        assertEquals(new BigDecimal("0.05"), porcentaje);
    }

    @Test
    void clienteNoRecibeDescuento() {
        Producto producto = null;

        BigDecimal porcentaje = descuento.calcularPorcentaje(
                producto,
                TipoUsuario.CLIENTE,
                LocalDate.now()
        );

        assertEquals(BigDecimal.ZERO, porcentaje);
    }
}
