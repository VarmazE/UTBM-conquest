package model;

public class Jeton {

	private String couleurRecto;
	private String couleurVerso;
	private String couleurCourante;

	/**
	 * Constructeur pour initialiser un jeton avec deux couleurs.
	 * Si les couleurs ne sont pas valides, des couleurs par défaut sont choisies.
	 *
	 * @param c1 La couleur du recto.
	 * @param c2 La couleur du verso.
	 */
	public Jeton(String c1, String c2) {
		if (!isCouleurValide(c1) || !isCouleurValide(c2)) {
			System.out.println("Les couleurs doivent être : Direction, Professeur, ou Etudiant.");
			int x = (int) (Math.random() * 3); // Choisir des couleurs par défaut aléatoires
			switch (x) {
				case 0 -> {
					this.couleurRecto = Constante.DIRECTION;
					this.couleurVerso = Constante.ETUDIANT;
				}
				case 1 -> {
					this.couleurRecto = Constante.PROFESSEUR;
					this.couleurVerso = Constante.DIRECTION;
				}
				case 2 -> {
					this.couleurRecto = Constante.ETUDIANT;
					this.couleurVerso = Constante.PROFESSEUR;
				}
			}
			this.couleurCourante = couleurRecto;
		} else {
			this.couleurRecto = c1;
			this.couleurVerso = c2;
			this.couleurCourante = couleurRecto;
		}
	}

	public Jeton(Jeton j){
		this.couleurRecto = j.couleurRecto;
		this.couleurVerso = j.couleurVerso;
		this.couleurCourante = j.couleurCourante;
	}

	/**
	 * Vérifie si une couleur est valide.
	 *
	 * @param couleur La couleur à vérifier.
	 * @return true si la couleur est valide, false sinon.
	 */
	private boolean isCouleurValide(String couleur) {
		return couleur.equals(Constante.DIRECTION)
				|| couleur.equals(Constante.PROFESSEUR)
				|| couleur.equals(Constante.ETUDIANT);
	}

	/**
	 * Inverse la couleur courante entre le recto et le verso.
	 */
	public void inverserCouleur() {
		if (this.couleurCourante.equals(this.couleurRecto)) {
			this.couleurCourante = this.couleurVerso;
		} else {
			this.couleurCourante = this.couleurRecto;
		}
	}

	/**
	 * Obtient la couleur courante du jeton.
	 *
	 * @return La couleur courante.
	 */
	public String getCouleur() {
		return this.couleurCourante;
	}

	/**
	 * Obtient la couleur opposée à la couleur courante du jeton.
	 *
	 * @return La couleur opposée (si la couleur courante est le recto, retourne le verso et vice-versa).
	 */
	public String getSwapCouleur() {
		return this.couleurCourante.equals(this.couleurRecto) ? this.couleurVerso : this.couleurRecto;
	}


	/**
	 * Définit une nouvelle couleur courante pour le jeton.
	 * La couleur doit être valide.
	 *
	 * @param s La nouvelle couleur à définir.
	 */
	public void setCouleur(String s) {
		if (!isCouleurValide(s)) {
			System.out.println("Les couleurs ne peuvent être que : Direction, Professeur, Etudiant.");
		} else {
			this.couleurCourante = s;
		}
	}
}
