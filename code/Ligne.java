import java.util.List;

public class Ligne {

	private String typeLigne;
	private Personnage personnageAssocie;
	private Batiment[] batiments;
	private List<Bonus> bonus;

	public Ligne(String typeLigne, Batiment[] batiments, List<Bonus> bonus) {
		this.typeLigne = typeLigne;
		this.batiments = batiments;
		this.bonus = bonus;
	}

	public String getTypeLigne() {
		return typeLigne;
	}

	public Batiment[] getBatiments() {
		return batiments;
	}




	// Retourner un batiment par indice :
	public Batiment getBatiment(int colonne) {
		if (colonne < 0 || colonne >= batiments.length) {
			throw new IllegalArgumentException("Indice invalide. Doit être entre 0 et 5.");
		}
		return batiments[colonne];
	}

	// Ajouter un bonus à la ligne
	public void ajouterBonus(Bonus nouveauBonus) {
		bonus.add(nouveauBonus);
	}

	// Méthode checkBonus
	public Bonus checkBonus() {
		for (Bonus b : bonus) {
			List<Integer> batimentsRequis = b.getBatimentsRequis();

			// Vérifie si tous les bâtiments requis sont construits
			boolean tousConstruits = true;
			for (int index : batimentsRequis) {
				if (index < 0 || index >= batiments.length || !batiments[index].getConstruit()) {
					tousConstruits = false;
					break;
				}
			}

			// Si les bâtiments requis sont construits et le bonus n'est pas encore utilisé
			if (tousConstruits && !b.isEstUtilise()) {
				b.setEstUtilise(true); // Marque le bonus comme utilisé
				return b;              // Retourne le bonus débloqué
			}
		}
		return null; // Aucun bonus débloqué
	}

	public Personnage getPersonnageAssocie() {
		return personnageAssocie;
	}

	public List<Bonus> getBonus() {
		return bonus;
	}
}
