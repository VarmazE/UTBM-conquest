package model;

public class Batiment {

	private boolean construit;
	private boolean protege;
	private boolean detruit;
	private final Bonhomme recompense;

	/**
	 * Constructeur pour créer un bâtiment avec une récompense donnée.
	 *
	 * @param recompense Récompense associée au bâtiment
	 */
	public Batiment(Bonhomme recompense) {
		this.construit = false;
		this.protege = false;
		this.detruit = false;
		this.recompense = recompense;
	}

	/**
	 * Construit le bâtiment si ce n'est pas déjà fait et s'il n'est pas détruit.
	 *
	 * @return Le bâtiment construit ou null si la construction a échoué
	 */
	public Batiment construire() {
		if (!this.construit && !this.detruit) {
			this.construit = true;
			return this;
		}
		return null;
	}

	/**
	 * Détruit le bâtiment si ce dernier n'est pas protégé et s'il n'est pas construit.
	 */
	public void detruire() {
		if (!this.construit && !this.protege) {
			this.detruit = true;
		}
	}

	// Getters et Setters

	public boolean isConstruit() {
		return construit;
	}

	public boolean isProtege() {
		return protege;
	}

	public void setProtege() {
		this.protege = true;
	}

	public boolean isDetruit() {
		return detruit;
	}

	public Bonhomme getRecompense() {
		return recompense;
	}
}
