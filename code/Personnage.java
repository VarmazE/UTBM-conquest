/**
 * La classe Personnage représente un personnage associé à chaque ligne qui aura
 * un impact sur le score de la ligne notamment avec son multiplicateur
 */
public class Personnage {

	private String nom;
	private int multiplicateur;

	/**
	 * Constructeur de la classe Personnage.
	 *
	 * @param nom Le nom du personnage.
	 * @throws IllegalArgumentException Si le nom est null ou vide.
	 */
	public Personnage(String nom) {
		/*if (nom == null || nom.trim().isEmpty()) {
			throw new IllegalArgumentException("Le nom du personnage ne peut pas être null ou vide.");
		}*/
		this.nom = nom;
		this.multiplicateur = 0;
	}

	/**
	 * Obtient le nom du personnage.
	 *
	 * @return Le nom du personnage.
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * Obtient le multiplicateur de points du personnage.
	 *
	 * @return Le multiplicateur de points.
	 */
	public int getMultiplicateur() {
		return multiplicateur;
	}

	/**
	 * Définit un nouveau multiplicateur de points pour le personnage.
	 *
	 * @param multiplicateur Le nouveau multiplicateur.
	 * @throws IllegalArgumentException Si le multiplicateur est négatif.
	 */
	public void setMultiplicateur(int multiplicateur) {
		/*if (multiplicateur < 0) {
			throw new IllegalArgumentException("Le multiplicateur ne peut pas être négatif.");
		}*/
		this.multiplicateur = multiplicateur;
	}
}
