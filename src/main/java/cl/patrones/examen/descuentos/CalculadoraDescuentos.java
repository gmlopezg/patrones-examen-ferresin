package cl.patrones.examen.descuentos;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

import cl.patrones.examen.productos.domain.Producto;

public class CalculadoraDescuentos {

    private final List<DescuentoStrategy> estrategias;

    public CalculadoraDescuentos(List<DescuentoStrategy> estrategias) {
        this.estrategias = estrategias;
    }

    /**
     * Devuelve el porcentaje máximo de descuento entre todas las estrategias.
     */
    public BigDecimal calcularPorcentajeMaximo(Producto producto, TipoUsuario tipoUsuario, LocalDate fecha) {
        BigDecimal max = BigDecimal.ZERO;

        for (DescuentoStrategy estrategia : estrategias) {
            if (estrategia.aplica(producto, tipoUsuario, fecha)) {
                BigDecimal porcentaje = estrategia.calcularPorcentaje(producto, tipoUsuario, fecha);
                if (porcentaje.compareTo(max) > 0) {
                    max = porcentaje;
                }
            }
        }

        return max;
    }

    /**
     * Calcula el monto de descuento en dinero (Long), basado en el precio de lista.
     */
    public Long calcularMontoDescuento(Producto producto, TipoUsuario tipoUsuario, LocalDate fecha) {
        Long precioLista = producto.getPrecioLista();
        if (precioLista == null) {
            return 0L;
        }

        BigDecimal porcentaje = calcularPorcentajeMaximo(producto, tipoUsuario, fecha);
        BigDecimal base = BigDecimal.valueOf(precioLista);

        BigDecimal monto = base.multiply(porcentaje);  // precioLista * porcentaje
        return monto.setScale(0, RoundingMode.HALF_UP).longValue();
    }

    /**
     * Calcula el precio final = precioLista - descuento.
     */
    public Long calcularPrecioFinal(Producto producto, TipoUsuario tipoUsuario, LocalDate fecha) {
        Long precioLista = producto.getPrecioLista();
        if (precioLista == null) {
            return 0L;
        }

        Long descuento = calcularMontoDescuento(producto, tipoUsuario, fecha);
        return precioLista - descuento;
    }
}
