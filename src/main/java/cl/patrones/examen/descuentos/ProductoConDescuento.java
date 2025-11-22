package cl.patrones.examen.descuentos;

import java.time.LocalDate;

import cl.patrones.examen.productos.domain.Categoria;
import cl.patrones.examen.productos.domain.Producto;

public class ProductoConDescuento implements Producto {

    private final Producto productoBase;
    private final CalculadoraDescuentos calculadoraDescuentos;
    private final TipoUsuario tipoUsuario;

    public ProductoConDescuento(Producto productoBase,
                                CalculadoraDescuentos calculadoraDescuentos,
                                TipoUsuario tipoUsuario) {
        this.productoBase = productoBase;
        this.calculadoraDescuentos = calculadoraDescuentos;
        this.tipoUsuario = tipoUsuario;
    }

    @Override
    public String getSku() {
        return productoBase.getSku();
    }

    @Override
    public String getNombre() {
        return productoBase.getNombre();
    }

    @Override
    public String getImagen() {
        return productoBase.getImagen();
    }

    @Override
    public Long getCosto() {
        return productoBase.getCosto();
    }

    @Override
    public Long getPrecioLista() {
        return productoBase.getPrecioLista();
    }

    @Override
    public Long getDescuento() {
        // Descuento calculado dinámicamente según usuario y día actual
        LocalDate hoy = LocalDate.now();
        return calculadoraDescuentos.calcularMontoDescuento(productoBase, tipoUsuario, hoy);
    }

    @Override
    public Long getPrecioFinal() {
        // Precio final calculado dinámicamente según usuario y día actual
        LocalDate hoy = LocalDate.now();
        return calculadoraDescuentos.calcularPrecioFinal(productoBase, tipoUsuario, hoy);
    }

    @Override
    public Categoria getCategoria() {
        return productoBase.getCategoria();
    }
}
