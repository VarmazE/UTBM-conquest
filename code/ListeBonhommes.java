public class ListeBonhommes {

	private int nombre;

	private String type;

	public ListeBonhommes(String type) {
		this.type = type;
		this.nombre = 0;
	}

	public void addBonhomme(Bonhomme b) {
		// define Recompense.getNombre()
		nombre += b.getNombre();
	}

	public String getType() {
		return type;
	}

	public int getNombre() {
		return nombre;
	}

}
