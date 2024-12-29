package model;

public class DeNoir extends De {

	private boolean etat;

	/**
	 * Constructeur par défaut : initialise le dé noir avec un état inactif.
	 */
	public DeNoir() {
		super();
		this.etat = false;
	}

	public DeNoir(De noir){
		super(noir);
		this.etat = ((DeNoir) noir).etat;
	}

	/**
	 * Vérifie si le dé noir est actif.
	 *
	 * @return true si le dé est actif, false sinon
	 */
	public boolean isActif() {
		return this.etat;
	}

	/**
	 * Définit l'état du dé à actif
	 */
	public void setActif() {
		this.etat = true;
	}


}
