package examen.descuentos;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import cl.patrones.examen.descuentos.CalculadoraDescuentos;
import cl.patrones.examen.descuentos.DescuentoStrategy;
import cl.patrones.examen.descuentos.TipoUsuario;
import cl.patrones.examen.productos.domain.Producto;

class CalculadoraDescuentosTest {

    @Mock
    private Producto productoMock;

    @Mock
    private DescuentoStrategy estrategia1;

    @Mock
    private DescuentoStrategy estrategia2;

    private CalculadoraDescuentos calculadora;

    @BeforeEach
    void setUp() {
        // Inicializa los mocks de Mockito
        MockitoAnnotations.openMocks(this);

        // La calculadora va a usar estas dos estrategias
        calculadora = new CalculadoraDescuentos(Arrays.asList(estrategia1, estrategia2));

        // Precio de lista de ejemplo: $1000
        when(productoMock.getPrecioLista()).thenReturn(1000L);
    }

    @Test
    void cuandoVariasEstrategiasAplicanSeTomaLaDeMayorPorcentaje() {
        LocalDate hoy = LocalDate.now();

        // Estrategia 1 aplica y devuelve 5%
        when(estrategia1.aplica(productoMock, TipoUsuario.EMPLEADO, hoy)).thenReturn(true);
        when(estrategia1.calcularPorcentaje(productoMock, TipoUsuario.EMPLEADO, hoy))
                .thenReturn(new BigDecimal("0.05"));

        // Estrategia 2 aplica y devuelve 10%
        when(estrategia2.aplica(productoMock, TipoUsuario.EMPLEADO, hoy)).thenReturn(true);
        when(estrategia2.calcularPorcentaje(productoMock, TipoUsuario.EMPLEADO, hoy))
                .thenReturn(new BigDecimal("0.10"));

        // Act: calculamos el monto de descuento y el precio final
        Long descuento = calculadora.calcularMontoDescuento(productoMock, TipoUsuario.EMPLEADO, hoy);
        Long precioFinal = calculadora.calcularPrecioFinal(productoMock, TipoUsuario.EMPLEADO, hoy);

        // Assert: se debe usar el descuento del 10% (no el 5%)
        assertEquals(100L, descuento);   // 10% de 1000 = 100
        assertEquals(900L, precioFinal); // 1000 - 100 = 900
    }

    @Test
    void cuandoSoloUnaEstrategiaAplicaSeUsaSuDescuento() {
        LocalDate hoy = LocalDate.now();

        // Estrategia 1 sí aplica y da 10%
        when(estrategia1.aplica(productoMock, TipoUsuario.CLIENTE, hoy)).thenReturn(true);
        when(estrategia1.calcularPorcentaje(productoMock, TipoUsuario.CLIENTE, hoy))
                .thenReturn(new BigDecimal("0.10"));

        // Estrategia 2 NO aplica
        when(estrategia2.aplica(productoMock, TipoUsuario.CLIENTE, hoy)).thenReturn(false);
        when(estrategia2.calcularPorcentaje(productoMock, TipoUsuario.CLIENTE, hoy))
                .thenReturn(BigDecimal.ZERO);

        Long descuento = calculadora.calcularMontoDescuento(productoMock, TipoUsuario.CLIENTE, hoy);
        Long precioFinal = calculadora.calcularPrecioFinal(productoMock, TipoUsuario.CLIENTE, hoy);

        // 10% de 1000 = 100
        assertEquals(100L, descuento);
        assertEquals(900L, precioFinal);
    }

    @Test
    void cuandoNingunaEstrategiaAplicaNoHayDescuento() {
        LocalDate hoy = LocalDate.now();

        // Ninguna estrategia aplica
        when(estrategia1.aplica(productoMock, TipoUsuario.CLIENTE, hoy)).thenReturn(false);
        when(estrategia2.aplica(productoMock, TipoUsuario.CLIENTE, hoy)).thenReturn(false);

        Long descuento = calculadora.calcularMontoDescuento(productoMock, TipoUsuario.CLIENTE, hoy);
        Long precioFinal = calculadora.calcularPrecioFinal(productoMock, TipoUsuario.CLIENTE, hoy);

        assertEquals(0L, descuento);
        assertEquals(1000L, precioFinal); // precio sin descuento
    }
}
