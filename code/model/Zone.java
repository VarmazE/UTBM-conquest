package model;

public class Zone {

	private Jeton jeton;
	private Cout cout;
	private boolean etat;

	/**
	 * Constructeur par défaut.
	 * Initialise une zone avec un état actif, sans jeton ni coût.
	 */
	public Zone() {
		this.jeton = null;
		this.cout = null;
		this.etat = true;
	}

	/**
	 * Constructeur avec paramètres.
	 *
	 * @param j Le jeton associé à la zone.
	 */
	public Zone(Jeton j) {
		this.jeton = j;
		this.cout = null;
		this.etat = true;
	}


	public Zone(Zone z) {
		this.jeton = new Jeton(z.jeton);
		this.cout = z.cout;
		this.etat = true;
	}

	/**
	 * Obtient le jeton associé à la zone.
	 *
	 * @return Le jeton de la zone.
	 */
	public Jeton getJeton() {
		return jeton;
	}

	/**
	 * Définit un nouveau jeton pour la zone.
	 *
	 * @param j Le nouveau jeton à associer.
	 */
	public void setJeton(Jeton j) {
		this.jeton = j;
	}

	/**
	 * Obtient le coût associé à la zone.
	 *
	 * @return Le coût de la zone.
	 */
	public Cout getCout() {
		return cout;
	}

	/**
	 * Définit un nouveau coût pour la zone.
	 *
	 * @param c Le nouveau coût à associer.
	 */
	public void setCout(Cout c) {
		this.cout = c;
	}

	/**
	 * Obtient l'état actuel de la zone.
	 *
	 * @return true si la zone est active, false sinon.
	 */
	public boolean isActive() {
		return this.etat;
	}

	/**
	 * Définit l'état de la zone.
	 *
	 * @param b true pour activer la zone, false pour la désactiver.
	 */
	public void setEtat(boolean b) {
		this.etat = b;
	}
}
