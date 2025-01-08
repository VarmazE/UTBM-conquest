package model;

/**
 * La classe model.SalleProfesseurs représente un bâtiment spécifique
 * qui contient un personnage associé.
 */
public class SalleProfesseurs extends Batiment {

	private Personnage personnage; // model.Personnage associé à la salle des professeurs

	/**
	 * Constructeur de la classe model.SalleProfesseurs.
	 *
	 * @param recompense La récompense associée à la construction de la salle.
	 * @param personnage Le personnage associé à la salle.
	 * @throws IllegalArgumentException Si le personnage est null.
	 */
	public SalleProfesseurs(Bonhomme recompense, Personnage personnage) {
		super(recompense);
		/*if (personnage == null) {
			throw new IllegalArgumentException("Le personnage ne peut pas être null.");
		}*/
		this.personnage = personnage;
	}

	/**
	 * Obtient le personnage associé à la salle des professeurs.
	 *
	 * @return Le personnage de la salle.
	 */
	public Personnage getPersonnage() {
		return personnage;
	}
}
