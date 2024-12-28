package model;

/**
 * La classe model.ListeRessources représente une collection de ressources liées
 * à un type spécifique et gère leurs états ainsi que les bonus associés.
 */
public class ListeRessources {

	private static final char AVAILABLE = 'a';
	private static final char OWNED = 'o';
	private static final char USED = 'u';

	private static final int RESSOURCES_SIZE = 18;

	private char[] ressources;
	private String type;
	private Bonhomme[] bonhommesBonus;

	/**
	 * Constructeur de la classe model.ListeRessources.
	 *
	 * @param type Le type de ressource (par exemple, Communication, ECTS, Savoir).
	 */
	public ListeRessources(String type) {
		this.type = type;
		this.ressources = new char[RESSOURCES_SIZE];
		this.bonhommesBonus = new Bonhomme[RESSOURCES_SIZE];

		initializeRessources();
		initializeBonhommesBonus();
	}

	/**
	 * Initialise les ressources avec des états par défaut (AVAILABLE ou OWNED).
	 */
	private void initializeRessources() {
		for (int i = 0; i < RESSOURCES_SIZE; i++) {
			ressources[i] = (i < 3) ? OWNED : AVAILABLE;
		}
	}

	/**
	 * Initialise les bonhommes bonus en fonction du type de ressource.
	 */
	private void initializeBonhommesBonus() {
		String couleur = switch (type) {
			case Constante.COMMUNICATION -> Constante.ROUGE;
			case Constante.ECTS -> Constante.JAUNE;
			case Constante.SAVOIR -> Constante.BLANC;
			default -> null;
		};

		for (int i = 0; i < RESSOURCES_SIZE; i++) {
			bonhommesBonus[i] = null;
		}

		bonhommesBonus[5] = new Bonhomme(couleur, 1);
		bonhommesBonus[11] = new Bonhomme(couleur, 1);
		bonhommesBonus[17] = new Bonhomme(couleur, 1);
	}

	/**
	 * Obtient le type de ressource.
	 *
	 * @return Le type de ressource.
	 */
	public String getType() {
		return this.type;
	}

	/**
	 * Vérifie s'il existe un bonus disponible parmi les ressources possédées.
	 *
	 * @return Le bonhomme bonus disponible ou null s'il n'y en a pas.
	 */
	public Bonhomme isBonus() {
		for (int i = 0; i < RESSOURCES_SIZE; i++) {
			if ((ressources[i] == OWNED || ressources[i] == USED) && bonhommesBonus[i] != null) {
				return bonhommesBonus[i];
			}
		}
		return null;
	}

	/**
	 * Calcule le nombre total de ressources possédées.
	 *
	 * @return Le nombre de ressources possédées.
	 */
	public int calculerRessource() {
		int count = 0;
		for (char c : ressources) {
			if (c == OWNED) {
				count++;
			}
		}
		return count;
	}

	/**
	 * Ajoute des ressources à la liste.
	 *
	 * @param r Les ressources à ajouter.
	 */
	public void addRessource(Ressources r) {
		/*if (r == null || r.getNombre() <= 0) {
			throw new IllegalArgumentException("Les ressources à ajouter doivent être valides.");
		}*/

		int toAdd = r.getNombre();
		for (int i = 0; i < RESSOURCES_SIZE; i++) {
			if (ressources[i] == AVAILABLE) {
				ressources[i] = OWNED;
				toAdd--;

				if (toAdd == 0) {
					break;
				}
			}
		}
	}

	/**
	 * Supprime des ressources de la liste.
	 *
	 * @param r Les ressources à supprimer.
	 * @return true si les ressources ont été supprimées avec succès, false sinon.
	 */
	public boolean RemoveRessource(Ressources r) {
		/*if (r == null || r.getNombre() <= 0) {
			throw new IllegalArgumentException("Les ressources à retirer doivent être valides.");
		}*/

		int toRemove = r.getNombre();
		int currentOwned = calculerRessource();

		if (toRemove > currentOwned) {
			return false;
		}

		for (int i = 0; i < RESSOURCES_SIZE; i++) {
			if (ressources[i] == OWNED) {
				ressources[i] = USED;
				toRemove--;

				if (toRemove == 0) {
					break;
				}
			}
		}

		return true;
	}
	/**
	 * Calcule les points associés aux ressources possédées.
	 * Chaque groupe de 2 ressources donne 1 point.
	 *
	 * @return Les points calculés.
	 */
	public int calulerPoint(){
		return calculerRessource() / 2;
	}
}
