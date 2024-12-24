public class Zone {

	private int idZone;
	private Jeton jeton;
	private Cout cout;
	private boolean etat;
	
	private static int compteur = 0;
	
	public Zone(){
		this.idZone=++compteur;
		this.jeton = new Jeton();
		this.cout = new Cout();
		this.etat=true;
	}
	
	public Zone(Jeton j, Cout c, boolean b){
		this.idZone=++compteur;
		this.jeton = j;
		this.cout = c;
		this.etat=b;
	}
	
	public void isActif() {
		if (this.etat==false) {
			this.etat=true;
		}else {
			this.etat=false;
		}
	}
	
	public boolean getEtat() {
		return this.etat;
	}
	public void setEtat(boolean b) {
		this.etat=b;
	}
	// METHODES JETON
	
	public String getCouleur() {
		return jeton.getCouleur();
	}
	public void setCouleur(String s) {
		jeton.setCouleur(s);
	}
	public int getIdJeton() {
		return jeton.getId();
	}
	public void inverserCouleur() {
		jeton.inverserCouleur();
	}
	
	// METHODES COUT
	public int getMontant() {
		return cout.getMontant();
	}
	
	public void setMontant(int x) {
		cout.setMontant(x);
	}

}
