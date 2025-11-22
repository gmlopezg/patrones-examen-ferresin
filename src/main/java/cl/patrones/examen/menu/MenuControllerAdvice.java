package cl.patrones.examen.menu;

import java.util.List;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class MenuControllerAdvice {

	@ModelAttribute("menu")
	public List<ItemMenu> menu() {
		return List.of(
			new ItemMenuImpl("Inicio", "/"),
			new ItemMenuImpl("Compresores", "/categoria/compresores-de-aire"),
			new ItemMenuImpl("Esmeriles", "/categoria/esmeriles-angulares"),
			new ItemMenuImpl("Taladros", "/categoria/taladros-percutores")
		);
	}
	
}
