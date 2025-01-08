package model;

public class De {

	private int valeurDe;
	private int id;
	private static int compteur = 0;

	/**
	 * Constructeur par défaut : initialise le dé avec une valeur entre 1 et 6.
	 */
	public De() {
		this.valeurDe = 0;
		this.id = ++compteur;
	}

	public De(De de){
		this.valeurDe = de.valeurDe;
		this.id = de.id;
	}

	/**
	 * Getter pour obtenir la valeur actuelle du dé.
	 *
	 * @return La valeur actuelle du dé
	 */
	public int getValeurDe() {
		return valeurDe;
	}

	/**
	 * Setter pour définir une valeur spécifique pour le dé.
	 * Valide que la valeur est comprise entre 1 et 6.
	 *
	 * @param x La nouvelle valeur du dé
	 */
	public void setValeurDe(int x) {
		/*if (x < 1 || x > 6) {
			throw new IllegalArgumentException("La valeur du dé doit être comprise entre 1 et 6.");
		}*/
		this.valeurDe = x;
	}

	/**
	 * Lance le dé et lui attribue une nouvelle valeur aléatoire entre 1 et 6.
	 */
	public void lancerDe() {
		this.valeurDe = (int) (Math.random() * 6) + 1;
	}

	/**
	 * Décrémente la valeur du dé d'une unité, si possible.
	 */
	public boolean decrementer() {
		if (valeurDe <= 1) {
			//throw new IllegalStateException("Impossible de décrémenter : valeur minimale atteinte.");
			System.out.println("Impossible de décrémenter : valeur minimale atteinte.");
			return false;
		} else {
			valeurDe--;
			return true;
		}
	}

	/**
	 * Incrémente la valeur du dé d'une unité, si possible.
	 */
	public boolean incrementer() {
		if (valeurDe >= 6) {
			//throw new IllegalStateException("Impossible d'incrémenter : valeur maximale atteinte.");
			System.out.println("Impossible d'incrémenter : valeur maximale atteinte.");
			return false;
		} else {
			valeurDe++;
			return true;

		}
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		De other = (De) obj;
		return this.id == other.id;
	}
}
