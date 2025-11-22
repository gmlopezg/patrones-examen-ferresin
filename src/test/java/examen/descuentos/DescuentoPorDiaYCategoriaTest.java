package examen.descuentos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import cl.patrones.examen.descuentos.DescuentoPorDiaYCategoria;
import cl.patrones.examen.descuentos.TipoUsuario;
import cl.patrones.examen.productos.domain.Categoria;
import cl.patrones.examen.productos.domain.Producto;

class DescuentoPorDiaYCategoriaTest {

    @Test
    void lunesCategoriaCompresorRecibeDescuentoNoCero() {
        // Arrange: preparamos mocks
        Producto producto = Mockito.mock(Producto.class);
        Categoria categoria = Mockito.mock(Categoria.class);

        // Ajustamos este texto al NOMBRE REAL de la categoría compresores
        Mockito.when(categoria.getNombre()).thenReturn("COMPRESORES DE AIRE");
        Mockito.when(producto.getCategoria()).thenReturn(categoria);

        // Buscamos un lunes (hoy o el próximo)
        LocalDate lunes = LocalDate.now()
                .with(TemporalAdjusters.nextOrSame(DayOfWeek.MONDAY));

        DescuentoPorDiaYCategoria descuento = new DescuentoPorDiaYCategoria();

        // Act
        BigDecimal porcentaje = descuento.calcularPorcentaje(
                producto,
                TipoUsuario.CLIENTE,
                lunes
        );

        // Assert
        assertTrue(porcentaje.compareTo(BigDecimal.ZERO) > 0,
                "El descuento para lunes y categoría compresor debe ser mayor que 0");
    }

    @Test
    void juevesNoTieneDescuentoAunqueLaCategoriaSeaCompresor() {
        // Arrange: mismos mocks
        Producto producto = Mockito.mock(Producto.class);
        Categoria categoria = Mockito.mock(Categoria.class);

        // Ajustar al nombre real de la categoría
        Mockito.when(categoria.getNombre()).thenReturn("COMPRESORES DE AIRE");
        Mockito.when(producto.getCategoria()).thenReturn(categoria);

        // Se busca un jueves (hoy o el próximo)
        LocalDate jueves = LocalDate.now()
                .with(TemporalAdjusters.nextOrSame(DayOfWeek.THURSDAY));

        DescuentoPorDiaYCategoria descuento = new DescuentoPorDiaYCategoria();

        // Act
        BigDecimal porcentaje = descuento.calcularPorcentaje(
                producto,
                TipoUsuario.CLIENTE,
                jueves
        );

        // Assert
        assertEquals(BigDecimal.ZERO, porcentaje,
                "El descuento para jueves debe ser 0, aunque la categoría sea compresor");
    }
}
