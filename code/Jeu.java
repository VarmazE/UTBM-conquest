import java.util.ArrayList;
import java.util.List;

public class Jeu
{

	private int toursTotal;

	private int tourCourant;

	private String semestre;

	private PlateauJeu plateauJeu;

	private ArrayList<Joueur> joueurs;

	public void initialiserJeu()
	{
		System.out.println("Initialisation du jeu...");

		// Étape 1 : Initialiser les joueurs
		joueurs = new ArrayList<>();
		Joueur j1 = new Joueur("Joueur 1");
		Joueur j2 = new Joueur("Joueur 2");

		// Ajouter les joueurs à la liste
		joueurs.add(j1);
		joueurs.add(j2);

		// Étape 2 : Initialiser le plateau
		plateauJeu = new PlateauJeu();

		// Étape 3 : Configurer les tours et le semestre
		tourCourant = 0;
		toursTotal =8;
		semestre = "Semestre pair";

		System.out.println("Jeu initialisé !");
	}


	public void passerSemestre()
	{
		if (tourCourant >= toursTotal)
		{
			System.out.println("Le jeu est terminé");
		}

		if (semestre.equals("Semestre pair"))
		{
			semestre = "Semestre impair";
		}
		else
		{
			semestre = "Semestre pair";

			tourCourant++;
			System.out.println("Tour Complet terminé. Tour courant : " + tourCourant);
		}

		/**Verfier si la methode  semestre suivant existe dans plateau**/
		plateauJeu.semestreSuivante();

	}

	public void afficherScore()
	{
		System.out.println("Scores des joueurs :");

		for (Joueur j : joueurs) {
			System.out.println("Joueur nom : " + j.getNom() + " - Score : " + j.getGrille().calculerScore());
		}
	}


	public void terminerJeu() {
		if (tourCourant == toursTotal) {
			System.out.println("Le jeu est terminé !");
			System.out.println("Voici les résultats finaux :");

			// Étape 1 : Afficher les scores
			afficherScore();

			Joueur gagnant = null;
			int scoreMax = Integer.MIN_VALUE;

			// Étape 2 : Déterminer le gagnant
			for (Joueur j : joueurs) {
				int score = j.getGrille().getScore();

				if (score > scoreMax) {
					scoreMax = score;
					gagnant = j;
				} else if (score == scoreMax) {
					gagnant = null; // En cas d'égalité, aucun gagnant unique
				}
			}

			// Étape 3 : Afficher le gagnant ou une égalité
			if (gagnant != null) {
				System.out.println("Le gagnant est : " + gagnant.getNom() + " avec " + scoreMax + " points !");
			} else {
				System.out.println("Il y a une égalité entre les joueurs !");
			}
		} else {
			// Si la méthode est appelée alors que le jeu n’est pas terminé
			System.out.println("Le jeu n’est pas encore terminé. Tours restants : " + (toursTotal - tourCourant));
		}
	}


	public void jouer()
	{
		initialiserJeu();


		while (tourCourant < toursTotal)
		{
			System.out.println("\n=== Tour " + (tourCourant + 1) + " ===");

			plateauJeu.lancerDes();

			for (Joueur joueur : joueurs)
			{
				System.out.println("C'est au tour de " + joueur.getNom());
				joueur.jouerTour(plateauJeu);
			}

			passerSemestre();
		}
		terminerJeu();
	}

}
