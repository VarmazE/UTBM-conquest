public class Quartier {

	private String couleurQuartier;
	// Tableau de 2 lignes
	private Ligne[] lignes = new Ligne[2];

	// Constructeur
	public Quartier(String couleurQuartier, Ligne lignePrestige, Ligne ligneFonction) {
		this.couleurQuartier = couleurQuartier;
		this.lignes[0] = lignePrestige;
		this.lignes[1] = ligneFonction;
	}

	// Getter et Setter

	public String getCouleurQuartier() {
		return couleurQuartier;
	}

	public void setCouleurQuartier(String couleurQuartier) {
		this.couleurQuartier = couleurQuartier;
	}

	// Méthode choisir Ligne
	// Retourner une ligne par indice :
	public Ligne choisirLigne(int index) {
		if (index < 0 || index >= lignes.length) {
			throw new IllegalArgumentException("Indice invalide. Doit être 0 ou 1.");
		}
		return lignes[index];
	}

}
