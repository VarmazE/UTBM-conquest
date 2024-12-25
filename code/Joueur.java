import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Scanner;
public class Joueur
{

	private String nom;
	private Grille grille;

	Joueur(String nom)
	{
		this.nom = nom;
		grille = new Grille();
	}
	public void jouerTour(PlateauJeu p)
	{

	}


	public int choisirZone(int index, Ressources r)
	{
		// Vérifie que l'index est valide
		if (index < 0 || index >= 4)
		{
			System.out.println("Erreur : L'index de la zone doit être compris entre 0 et 3.");
			return -1;
		}

		if (checkRessources(r.getType(), r.getNombre()))
		{
			System.out.println("Zone sélectionnée avec succès à l'index " + index + ".");
			return index;
		}
		else
		{
			System.out.println("Pas assez de ressources pour accéder à cette zone.");
			return -1; //
		}
	}

	public Grille getGrille()
	{
		return grille;
	}
	public String getNom()
	{
		return nom;
	}
	public ArrayList<De> incrementerDes(int index, ArrayList<De> listeDes) {
		// Récupérer le dé correspondant
		De de = listeDes.get(index);

		// Vérifie si le joueur a assez de ressources
		if (checkRessources("Rouge", 1)) {
			// Incrémente la valeur du dé directement
			de.incrementer();
			System.out.println("Le dé à l'index " + index + " a été incrémenté avec succès.");

			// Crée un objet Ressources pour dépenser les ressources nécessaires
			Ressources r1 = new Ressources();
			r1.setType("Rouge");
			r1.setNombre(1);
			grille.depenserRessource(r1); // Déduit les ressources
		} else {
			System.out.println("Pas assez de ressources pour incrémenter le dé.");
		}

		// Retourne la liste des dés mise à jour
		return listeDes;
	}




	public ArrayList<De> decrementerDes(int index, ArrayList<De> listeDes)
	{
		// Récupérer le dé correspondant
		De de = listeDes.get(index);

		// Vérifie si le joueur a assez de ressources
		if (checkRessources("Rouge", 1)) {
			// Vérifie si le dé peut être décrémenté
			if (de.getValeurDe() > 1) {
				de.decrementer(); // Décrémente la valeur du dé
				System.out.println("Le dé à l'index " + index + " a été décrémenté avec succès.");

				// Crée un objet Ressources pour dépenser les ressources nécessaires
				Ressources r1 = new Ressources();
				r1.setType("Rouge");
				r1.setNombre(1);
				grille.depenserRessource(r1); // Déduit les ressources
			} else {
				System.out.println("Impossible : le dé est déjà à sa valeur minimale.");
			}
		} else {
			System.out.println("Pas assez de ressources pour décrémenter le dé.");
		}

		// Retourne la liste des dés mise à jour
		return listeDes;
	}



	public ArrayList<Zone> changerCouleurJeton(int index, ArrayList<Zone> listeZone)
	{
			Zone zone = listeZone.get(index);
			zone.getJeton().inverserCouleur();
			return listeZone;
	}

	public boolean checkRessources(String type, int nombre)
	{
		switch (type)
		{
			case "Rouge":
				return grille.getRessourceByType(type).calculerRessource() >= nombre;
			case "Jaune":
				return grille.getRessourceByType(type).calculerRessource() >= nombre;
			case "Gris":
				return grille.getRessourceByType(type).calculerRessource() >= nombre;
			default:
				return false; // Si le type n'est pas reconnu, on retourne false.
		}
	}

	public void choisirAction(int index, ArrayList<De> listeDes, ArrayList<Zone> zones, PlateauJeu p)
	{
		Scanner scanner = new Scanner(System.in);

		System.out.print("Voulez-vous incrémenter le dé ? (oui/non) : ");
		String reponse = scanner.next().toLowerCase();
		if (reponse.equals("oui"))
		{
			incrementerDes(index, listeDes);
		}

		System.out.print("Voulez-vous décrémenter le dé ? (oui/non) : ");
		reponse = scanner.next().toLowerCase();
		if (reponse.equals("oui"))
		{
			decrementerDes(index, listeDes);
		}

		System.out.print("Voulez-vous changer la couleur d’un jeton ? (oui/non) : ");
		reponse = scanner.next().toLowerCase();
		if (reponse.equals("oui"))
		{
			changerCouleurJeton(index, zones);
		}

		System.out.print("Voulez-vous construire un bâtiment ? (oui/non) : ");
		reponse = scanner.next().toLowerCase();
		if (reponse.equals("oui"))
		{
			System.out.print("Quel type de bâtiment voulez-vous construire ? : ");
			String typeBatiment = scanner.next();
			grille.construireBatiment(p, index, typeBatiment);
		}

		System.out.println("Vos actions pour ce tour sont terminées !");
	}


}
