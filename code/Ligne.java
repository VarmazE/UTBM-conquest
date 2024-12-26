import java.util.List;

/**
 * La classe Ligne représente une ligne dans un quartier, contenant des bâtiments,
 * un personnage associé et éventuellement des bonus.
 */
public class Ligne {

	private String typeLigne;
	private Personnage personnageAssocie;
	private Batiment[] batiments;
	private List<Bonus> bonus;

	/**
	 * Constructeur de la classe Ligne.
	 *
	 * @param typeLigne          Le type de la ligne.
	 * @param batiments          Les bâtiments de la ligne.
	 * @param bonus              Les bonus associés à la ligne.
	 * @param personnageAssocie  Le personnage associé à la ligne.
	 * @throws IllegalArgumentException Si les bâtiments ou la liste des bonus sont null.
	 */
	public Ligne(String typeLigne, Batiment[] batiments, List<Bonus> bonus, Personnage personnageAssocie) {
		/*if (batiments == null || bonus == null) {
			throw new IllegalArgumentException("Les bâtiments et la liste des bonus ne peuvent pas être null.");
		}*/
		this.typeLigne = typeLigne;
		this.batiments = batiments;
		this.bonus = bonus;
		this.personnageAssocie = personnageAssocie;
	}

	/**
	 * Obtient le type de la ligne.
	 *
	 * @return Le type de la ligne.
	 */
	public String getTypeLigne() {
		return typeLigne;
	}

	/**
	 * Obtient les bâtiments de la ligne.
	 *
	 * @return Un tableau de bâtiments.
	 */
	public Batiment[] getBatiments() {
		return batiments;
	}

	/**
	 * Obtient un bâtiment spécifique de la ligne par son index.
	 *
	 * @param colonne L'index du bâtiment (colonne).
	 * @return Le bâtiment correspondant.
	 * @throws IllegalArgumentException Si l'index est invalide.
	 */
	public Batiment getBatiment(int colonne) {
		/*if (colonne < 0 || colonne >= batiments.length) {
			throw new IllegalArgumentException("Indice invalide. Doit être entre 0 et " + (batiments.length - 1) + ".");
		}*/
		return batiments[colonne];
	}

	/**
	 * Ajoute un nouveau bonus à la ligne.
	 *
	 * @param nouveauBonus Le bonus à ajouter.
	 * @throws IllegalArgumentException Si le bonus est null.
	 */
	public void ajouterBonus(Bonus nouveauBonus) {
		/*if (nouveauBonus == null) {
			throw new IllegalArgumentException("Le bonus ne peut pas être null.");
		}*/
		bonus.add(nouveauBonus);
	}

	/**
	 * Vérifie si un bonus est disponible parmi les bonus associés à la ligne.
	 *
	 * @return Le premier bonus disponible ou null s'il n'y en a pas.
	 */
	public Bonus checkBonus() {
		for (Bonus b : bonus) {
			if (b.isBonusAvailable()) {
				return b;
			}
		}
		return null;
	}

	/**
	 * Obtient le personnage associé à la ligne.
	 *
	 * @return Le personnage associé ou null s'il n'y en a pas.
	 */
	public Personnage getPersonnageAssocie() {
		return personnageAssocie;
	}

	/**
	 * Obtient la liste des bonus associés à la ligne.
	 *
	 * @return La liste des bonus.
	 */
	public List<Bonus> getBonus() {
		return bonus;
	}

	/**
	 * Calcule le score de la ligne en fonction des bâtiments construits
	 * et du multiplicateur du personnage associé.
	 *
	 * @return Le score total de la ligne.
	 */
	public int calculerScoreLigne() {
		int score = 0;
		for (Batiment batiment : batiments) {
			if (batiment.isConstruit()) {
				score++;
			}
		}
		score *= personnageAssocie.getMultiplicateur();
		return score;
	}
}
