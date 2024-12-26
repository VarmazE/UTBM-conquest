import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PlateauJeu {
	private List<De> listeDes;
	private Cout[] listeCout; // Tableau fixe des coûts
	private List<Zone> listeZone;

	/**
	 * Constructeur par défaut.
	 * Initialise les dés, les coûts, et les zones du plateau.
	 */
	public PlateauJeu() {
		listeDes = new ArrayList<>();
		listeCout = new Cout[9];
		listeZone = new ArrayList<>();

		// Initialisation des dés
		listeDes.add(new De());
		listeDes.add(new De());
		listeDes.add(new De());
		listeDes.add(new DeNoir());

		// Initialisation des coûts
		listeCout[0] = new Cout();
		listeCout[1] = new Cout(getArrayListRessources(new Ressources(Constante.ECTS, 0)));
		listeCout[2] = new Cout(getArrayListRessources(new Ressources(Constante.ECTS, 1), new Ressources(Constante.COMMUNICATION, 1), new Ressources(Constante.SAVOIR, 1)));
		listeCout[3] = new Cout(getArrayListRessources(new Ressources(Constante.ECTS, 1)));
		listeCout[4] = new Cout(getArrayListRessources(new Ressources(Constante.ECTS, 2)));
		listeCout[5] = new Cout(getArrayListRessources(new Ressources(Constante.ECTS, 0)));
		listeCout[6] = new Cout(getArrayListRessources(new Ressources(Constante.ECTS, 1), new Ressources(Constante.COMMUNICATION, 1), new Ressources(Constante.SAVOIR, 1)));
		listeCout[7] = new Cout(getArrayListRessources(new Ressources(Constante.ECTS, 1)));
		listeCout[8] = new Cout(getArrayListRessources(new Ressources(Constante.ECTS, 2)));

		// Initialisation des zones
		listeZone.add(new Zone(new Jeton(Constante.PROFESSEUR, Constante.DIRECTION)));
		listeZone.add(new Zone(new Jeton(Constante.ETUDIANT, Constante.DIRECTION)));
		listeZone.add(new Zone(new Jeton(Constante.DIRECTION, Constante.ETUDIANT)));
		listeZone.add(new Zone(new Jeton(Constante.DIRECTION, Constante.PROFESSEUR)));
		listeZone.add(new Zone(new Jeton(Constante.ETUDIANT, Constante.PROFESSEUR)));
		listeZone.add(new Zone(new Jeton(Constante.ETUDIANT, Constante.DIRECTION)));
		listeZone.add(new Zone(new Jeton(Constante.DIRECTION, Constante.ETUDIANT)));
		listeZone.add(new Zone(new Jeton(Constante.ETUDIANT, Constante.PROFESSEUR)));
		listeZone.add(new Zone(new Jeton(Constante.PROFESSEUR, Constante.ETUDIANT)));

		affecterCoutZone();
	}

	/**
	 * Crée une liste de ressources à partir d'une série d'arguments.
	 *
	 * @param r Ressources à inclure dans la liste.
	 * @return Une liste contenant les ressources spécifiées.
	 */
	public ArrayList<Ressources> getArrayListRessources(Ressources... r) {
		ArrayList<Ressources> ressources = new ArrayList<>();
		Collections.addAll(ressources, r);
		return ressources;
	}

	/**
	 * Tourne le plateau, déplaçant les zones d'une position.
	 */
	public void tournerPlateau() {
		Collections.rotate(listeZone, -1); // Permet de décaler la liste vers la gauche
		affecterCoutZone();
	}

	/**
	 * Affecte les coûts aux zones en fonction de leur position.
	 */
	public void affecterCoutZone() {
		int i = 0;
		for (Zone z : listeZone) {
			z.setCout(listeCout[i]);
			i++;
		}
	}

	/**
	 * Lance tous les dés du plateau.
	 */
	public void lancerDes() {
		for (De d : listeDes) {
			d.lancerDe();
		}
	}

	/**
	 * Trie les dés en fonction de leur valeur.
	 */
	public void trierDes() {
		listeDes.sort((de1, de2) -> Integer.compare(de1.getValeurDe(), de2.getValeurDe()));
	}

	/**
	 * Obtient les zones actives en fonction du semestre.
	 *
	 * @param semestre Le semestre ("Automne" ou "Printemps").
	 * @return Une liste des zones actives.
	 */
	public ArrayList<Zone> getActiveZone(String semestre) {
		ArrayList<Zone> list = new ArrayList<>();
		ArrayList<Zone> activeZones = new ArrayList<>();

		if ("Automne".equalsIgnoreCase(semestre)) {
			list.addAll(listeZone.subList(1, 5));
		} else if ("Printemps".equalsIgnoreCase(semestre)) {
			list.addAll(listeZone.subList(5, 9));
		}

		for (Zone z : list) {
			activeZones.add(new Zone(z));
		}

		return list;
	}

	/**
	 * Obtient tous les dés du plateau.
	 *
	 * @return La liste des dés.
	 */
	public List<De> getDes() {
		List<De> copieDes = new ArrayList<>();
		for (De de : listeDes) {
			copieDes.add(new De(de));
		}
		return copieDes;
	}

	/**
	 * Obtient tous les coûts.
	 *
	 * @return Le tableau des coûts.
	 */
	public Cout[] getCout() {
		return listeCout;
	}

	/**
	 * Obtient toutes les zones du plateau.
	 *
	 * @return La liste des zones.
	 */
	public List<Zone> getAllZone() {
		return listeZone;
	}

	/**
	 * Obtient une zone par son index.
	 *
	 * @param i L'index de la zone.
	 * @return La zone correspondante ou null si l'index est invalide.
	 */
	public Zone getZone(int i) {
		if (i >= 0 && i < listeZone.size()) {
			return listeZone.get(i);
		}
		return null;
	}

	/**
	 * Obtient un dé par son index.
	 *
	 * @param i L'index du dé.
	 * @return Le dé correspondant ou null si l'index est invalide.
	 */
	public De getDe(int i) {
		if (i >= 0 && i < listeDes.size()) {
			return listeDes.get(i);
		}
		return null;
	}
}
