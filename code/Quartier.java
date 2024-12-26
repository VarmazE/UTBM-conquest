/**
 * La classe Quartier représente un quartier composé de deux lignes :
 * une ligne de prestige et une ligne de fonction.
 */
public class Quartier {

	private String couleurQuartier;
	private Ligne[] lignes = new Ligne[2];

	/**
	 * Constructeur de la classe Quartier.
	 *
	 * @param couleurQuartier La couleur du quartier.
	 * @param lignePrestige   La ligne de prestige.
	 * @param ligneFonction   La ligne de fonction.
	 * @throws IllegalArgumentException Si l'une des lignes est null.
	 */
	public Quartier(String couleurQuartier, Ligne lignePrestige, Ligne ligneFonction) {
		/*if (lignePrestige == null || ligneFonction == null) {
			throw new IllegalArgumentException("Les lignes ne peuvent pas être null.");
		}*/
		this.couleurQuartier = couleurQuartier;
		this.lignes[0] = lignePrestige;
		this.lignes[1] = ligneFonction;
	}

	/**
	 * Obtient la couleur du quartier.
	 *
	 * @return La couleur du quartier.
	 */
	public String getCouleurQuartier() {
		return couleurQuartier;
	}

	/**
	 * Définit une nouvelle couleur pour le quartier.
	 *
	 * @param couleurQuartier La nouvelle couleur du quartier.
	 */
	public void setCouleurQuartier(String couleurQuartier) {
		this.couleurQuartier = couleurQuartier;
	}

	/**
	 * Choisit une ligne du quartier en fonction de son type.
	 *
	 * @param typeLigne Le type de la ligne ("prestige" ou "fonction").
	 * @return La ligne correspondante ou null si le type est invalide.
	 */
	public Ligne choisirLigne(String typeLigne) {
		return switch (typeLigne.toLowerCase()) {
			case "prestige" -> lignes[0];
			case "fonction" -> lignes[1];
			default -> null;
		};
	}

	/**
	 * Calcule le score du quartier en fonction des ligne
	 *
	 * @return Le score total de la ligne.
	 */
	public int calculerScoreQuartier() {
		return lignes[0].calculerScoreLigne() + lignes[1].calculerScoreLigne();
	}
}
