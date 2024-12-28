package model;

public class Recompenses {

	private String type;
	private int nombre;

	/**
	 * Constructeur avec paramètres.
	 *
	 * @param type Le type de récompense qui permettra de les différencier avec autres choses que leurs instances
	 * @param nombre Le coût associé à la zone.
	 */
	public Recompenses(String type, int nombre) {
		this.type = type;
		this.nombre = nombre;
	}

	// getters
	/**
	 * Obtient le type de la récompense.
	 *
	 * @return le type de la récompense
	 */
	public String getType() {
		return type;
	}

	/**
	 * Obtient le montant associées à la récompense.
	 *
	 * @return le montant
	 */
	public int getNombre() {
		return nombre;
	}

	/**
	 * Permet de multiplier le montant de la récompense
	 *
	 * @param multiplicateur le nombre par lequelle on multiplie
	 */
	public void setMultiplicateur(int multiplicateur) {
		this.nombre *= multiplicateur;
	}

}
