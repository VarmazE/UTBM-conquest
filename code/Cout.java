import java.util.ArrayList;

public class Cout {

	private int montant;
	private ArrayList<Ressources> listRessources;
	
	public Cout() {
		this.montant =0;
		this.listRessources= new List<Ressources>;
	}
	
	public Cout(int x, ArrayList<Ressources> lr) {
		this.montant =x;
		this.listRessources= lr;
	}
	
	
	public int getMontant() {
		return this.montant;
	}
	public void setMontant(int x) {
		this.montant=x;
	}


}
