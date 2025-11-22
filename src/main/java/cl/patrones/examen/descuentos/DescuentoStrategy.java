package cl.patrones.examen.descuentos;

import java.math.BigDecimal;
import java.time.LocalDate;

import cl.patrones.examen.productos.domain.Producto;

public interface DescuentoStrategy {

    /**
     * Indica si este descuento aplica o no para el producto, el tipo de usuario y la fecha.
     */
    boolean aplica(Producto producto, TipoUsuario tipoUsuario, LocalDate fecha);

    /**
     * Devuelve el porcentaje de descuento entre 0 y 1.
     * Ejemplo: 0.06 = 6% de descuento.
     */
    BigDecimal calcularPorcentaje(Producto producto, TipoUsuario tipoUsuario, LocalDate fecha);
}
