package cl.patrones.examen.menu;

import java.util.List;

import cl.patrones.examen.menu.util.Slugger;

public class ItemMenuImpl implements ItemMenu {

	private String texto;
	private String enlace;	
	
	public ItemMenuImpl(String texto, String enlace) {
		super();
		this.texto = texto;
		this.enlace = enlace;
	}

	@Override
	public String getTexto() {
		return texto;
	}

	@Override
	public String getSlug() {
		return Slugger.toSlug(texto);
	}

	@Override
	public String getEnlace() {
		return enlace;
	}

	@Override
	public boolean tieneHijos() {
		return false;
	}

	@Override
	public List<? extends ItemMenu> getHijos() {
		return null;
	}

}
