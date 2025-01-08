package model;

/**
 * La classe model.BureauAssociation représente un type spécifique de bâtiment
 * qui permet d'avoir un certains nombre de récompense en fonction du nombre dés
 * sur la couleur de l'attribut couleurJeton
 */
public class BureauAssociation extends Batiment {

	private String couleurJeton;
	private Recompenses recompenseCase;
	/**
	 * Constructeur de la classe model.BureauAssociation.
	 *
	 * @param couleurJeton La couleur du jeton associée.
	 * @param recompenseBonhomme La récompense de type model.Bonhomme associée au bâtiment.
	 * @param recompenseCase La récompense spécifique de la case.
	 */
	public BureauAssociation(String couleurJeton, Bonhomme recompenseBonhomme, Recompenses recompenseCase) {
		super(recompenseBonhomme);
		this.couleurJeton = couleurJeton;
		this.recompenseCase = recompenseCase;
	}

	/**
	 * Obtient la couleur du jeton associée.
	 *
	 * @return La couleur du jeton.
	 */
	public String getCouleurJeton() {
		return couleurJeton;
	}

	/**
	 * Obtient la récompense spécifique de la case.
	 *
	 * @return La récompense de la case.
	 */
	public Recompenses getRecompenseCase() {
		return recompenseCase;
	}

}
