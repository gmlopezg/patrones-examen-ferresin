package cl.patrones.examen.descuentos;

import java.math.BigDecimal;
import java.time.LocalDate;

import cl.patrones.examen.productos.domain.Producto;

public class DescuentoEmpleado implements DescuentoStrategy {

    private static final BigDecimal CINCO_PORCIENTO = new BigDecimal("0.05");

    @Override
    public boolean aplica(Producto producto, TipoUsuario tipoUsuario, LocalDate fecha) {
        return tipoUsuario == TipoUsuario.EMPLEADO;
    }

    @Override
    public BigDecimal calcularPorcentaje(Producto producto, TipoUsuario tipoUsuario, LocalDate fecha) {
        return aplica(producto, tipoUsuario, fecha) ? CINCO_PORCIENTO : BigDecimal.ZERO;
    }
}
