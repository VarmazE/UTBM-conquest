public class Personnage {

	private String nom;

	private int multiplicateur;

	public Personnage(String nom) {
		this.nom = nom;
		this.multiplicateur = 0;
	}

	public String getNom() {
		return nom;
	}

	public int getMultiplicateur() {
		return multiplicateur;
	}

	public void setMultiplicateur(int multiplicateur) {
		this.multiplicateur = multiplicateur;
	}
}
