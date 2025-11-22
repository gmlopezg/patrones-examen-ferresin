package cl.patrones.examen.productos.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import cl.patrones.examen.descuentos.CalculadoraDescuentos;
import cl.patrones.examen.descuentos.ProductoConDescuento;
import cl.patrones.examen.descuentos.TipoUsuario;
import cl.patrones.examen.productos.domain.Producto;
import cl.patrones.examen.productos.service.ProductoService;

@Controller
public class AppController {

    private final ProductoService productoService;
    private final CalculadoraDescuentos calculadoraDescuentos;

    public AppController(ProductoService productoService,
                         CalculadoraDescuentos calculadoraDescuentos) {
        this.productoService = productoService;
        this.calculadoraDescuentos = calculadoraDescuentos;
    }

    @GetMapping("/")
    String inicio(Model model, Authentication authentication) {

        // 1) Obtener los productos base desde el servicio del JAR
        List<? extends Producto> productosBase = productoService.getProductos();

        // 2) Determinar el tipo de usuario (CLIENTE o EMPLEADO)
        TipoUsuario tipoUsuario = TipoUsuario.CLIENTE;

        if (authentication != null && authentication.isAuthenticated()) {
            // Aquí asumimos que el rol de empleado es ROLE_EMPLEADO o ROLE_ADMIN.
            boolean esEmpleado = authentication.getAuthorities().stream()
                    .anyMatch(a -> "ROLE_EMPLEADO".equals(a.getAuthority())
                            || "ROLE_ADMIN".equals(a.getAuthority()));

            if (esEmpleado) {
                tipoUsuario = TipoUsuario.EMPLEADO;
            }
        }
        final TipoUsuario tipoUsuarioFinal = tipoUsuario;

        // 3) Decorar cada producto con ProductoConDescuento
        List<Producto> productosConDescuento = productosBase.stream()
                .map(p -> (Producto) new ProductoConDescuento(p, calculadoraDescuentos, tipoUsuarioFinal))
                .collect(Collectors.toList());

        // 4) Pasar la lista decorada a la vista
        model.addAttribute("productos", productosConDescuento);
        return "inicio";
    }
}
