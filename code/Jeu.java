import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * La classe Jeu gère le flux principal du jeu, y compris l'initialisation, les tours,
 * les semestres, et la fin du jeu.
 */
public class Jeu {

	// Attributs
	private int toursTotal;
	private int tourCourant;
	private String semestre;
	private PlateauJeu plateauJeu;
	private ArrayList<Joueur> joueurs;

	/**
	 * Constructeur de la classe Jeu.
	 *
	 * @param players Les joueurs participant au jeu.
	 */
	public Jeu(Joueur... players) {
		/*if (players.length < 1) {
			throw new IllegalArgumentException("Il doit y avoir au moins un joueur.");
		}*/

		joueurs = new ArrayList<>();
		Collections.addAll(joueurs, players);

		initialiserJeu();
	}

	/**
	 * Initialise le jeu : plateau, tours et semestre.
	 */
	private void initialiserJeu() {
		System.out.println("Initialisation du jeu...");

		plateauJeu = new PlateauJeu();
		tourCourant = 0;
		toursTotal = 8;
		semestre = Constante.AUTOMNE;

		System.out.println("Jeu initialisé !");
	}

	/**
	 * Passe au semestre suivant, tourne le plateau et met à jour le tour courant si nécessaire.
	 */
	private void passerSemestre() {
		if (tourCourant >= toursTotal) {
			System.out.println("Le jeu est terminé.");
			return;
		}

		if (semestre.equalsIgnoreCase(Constante.AUTOMNE)) {
			semestre = Constante.PRINTEMPS;
		} else {
			semestre = Constante.AUTOMNE;
			tourCourant++;
			plateauJeu.tournerPlateau();
			System.out.println("Tour complet terminé. Tour courant : " + tourCourant);
		}
	}

	/**
	 * Affiche les scores des joueurs.
	 */
	private void afficherScores() {
		System.out.println("Scores des joueurs :");
		for (Joueur j : joueurs) {
			System.out.println("Joueur : " + j.getNom() + " - Score : " + j.getScore());
		}
	}

	/**
	 * Termine le jeu et détermine le gagnant.
	 */
	private void terminerJeu() {
		System.out.println("Le jeu est terminé !");
		System.out.println("Voici les résultats finaux :");

		afficherScores();

		joueurs.sort((j1, j2) -> Integer.compare(j2.getScore(), j1.getScore())); // ordre décroissant j2 est mis en premier
		Joueur gagnant = joueurs.get(0);

		System.out.println("Le gagnant est : " + gagnant.getNom() + " avec " + gagnant.getGrille().calculerScore() + " points !");
	}

	/**
	 * Exécute le flux principal du jeu.
	 */
	public void jouer() {
		System.out.println("Début du jeu !");
		while (tourCourant < toursTotal) {
			jouerTour();
		}
		terminerJeu();
	}

	/**
	 * Gère un tour complet pour tous les joueurs.
	 */
	private void jouerTour() {
		System.out.println("\n=== Tour " + (tourCourant + 1) + " ===");

		plateauJeu.lancerDes();

		for (Joueur joueur : joueurs) {
			System.out.println("C'est au tour de " + joueur.getNom());
			joueur.jouerTour(plateauJeu, semestre);
		}

		passerSemestre();
	}
}
