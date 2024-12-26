/**
 * La classe ListeBonhommes représente une liste de bonhommes associée à un type spécifique.
 */
public class ListeBonhommes {

	private int nombre; // Nombre total de bonhommes dans la liste
	private String type; // Type de bonhommes (ex. Rouge, Jaune, Blanc)

	/**
	 * Constructeur de la classe ListeBonhommes.
	 *
	 * @param type Le type de bonhommes.
	 */
	public ListeBonhommes(String type) {
		this.type = type;
		this.nombre = 0;
	}

	/**
	 * Ajoute un bonhomme à la liste et met à jour le total.
	 *
	 * @param b Le bonhomme à ajouter.
	 * @throws IllegalArgumentException Si le bonhomme est null ou si son type ne correspond pas à celui de la liste.
	 */
	public void addBonhomme(Bonhomme b) {
		/*if (b == null) {
			throw new IllegalArgumentException("Le bonhomme ne peut pas être null.");
		}
		if (!b.getType().equals(type)) {
			throw new IllegalArgumentException("Le type du bonhomme ne correspond pas à celui de la liste.");
		}*/
		nombre += b.getNombre();
	}

	/**
	 * Obtient le type de bonhommes de la liste.
	 *
	 * @return Le type de bonhommes.
	 */
	public String getType() {
		return type;
	}

	/**
	 * Obtient le nombre total de bonhommes dans la liste.
	 *
	 * @return Le nombre total de bonhommes.
	 */
	public int getNombre() {
		return nombre;
	}
}
