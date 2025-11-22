package cl.patrones.examen.descuentos;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;

import cl.patrones.examen.productos.domain.Categoria;
import cl.patrones.examen.productos.domain.Producto;

public class DescuentoPorDiaYCategoria implements DescuentoStrategy {

    private static final BigDecimal SEIS_PORCIENTO = new BigDecimal("0.06");
    private static final BigDecimal OCHO_PORCIENTO = new BigDecimal("0.08");
    private static final BigDecimal DIEZ_PORCIENTO = new BigDecimal("0.10");

    // Ajusta estos nombres a los que realmente usas en la BD o servicio
    private static final String NOMBRE_COMPRESOR = "COMPRESORES DE AIRE";
    private static final String NOMBRE_ESMERIL = "ESMERILES ANGULARES";
    private static final String NOMBRE_TALADRO = "TALADROS PERCUTORES";

    @Override
    public boolean aplica(Producto producto, TipoUsuario tipoUsuario, LocalDate fecha) {
        return calcularPorcentaje(producto, tipoUsuario, fecha)
                .compareTo(BigDecimal.ZERO) > 0;
    }

    @Override
    public BigDecimal calcularPorcentaje(Producto producto, TipoUsuario tipoUsuario, LocalDate fecha) {
        Categoria categoria = producto.getCategoria();
        if (categoria == null || categoria.getNombre() == null) {
            return BigDecimal.ZERO;
        }

        String nombreCategoria = categoria.getNombre().toUpperCase();
        DayOfWeek dia = fecha.getDayOfWeek();

        switch (dia) {
            case MONDAY:
                return nombreCategoria.equals(NOMBRE_COMPRESOR)
                        ? SEIS_PORCIENTO
                        : BigDecimal.ZERO;
            case TUESDAY:
                return nombreCategoria.equals(NOMBRE_ESMERIL)
                        ? OCHO_PORCIENTO
                        : BigDecimal.ZERO;
            case WEDNESDAY:
                return nombreCategoria.equals(NOMBRE_TALADRO)
                        ? DIEZ_PORCIENTO
                        : BigDecimal.ZERO;
            default:
                return BigDecimal.ZERO;
        }
    }
}
