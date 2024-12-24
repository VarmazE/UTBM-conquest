import java.util.Random;

public class De {

	private int valeurDe;

	private int idDé; // je comprends pas le but d'un id
	
	private static int compteur = 0;
	
	public De(){
		this.valeurDe=0;
		this.idDé=0;
	}
	
	public De(int x){
		this.valeurDe=x;
		this.idDé=++compteur;
	}
	public int getIdDe() {
		return idDé;
	}
	
	public int getValeurDe() {
		return valeurDe;
	}
	
	public void setValeurDe(int x) {
		this.valeurDe=x;
	}
	
	public void lancerDe() {
		this.valeurDe = (int) (Math.random() * 6) + 1;
	}
	
	// ici jsp si on décremente tout le temps de 1 ou on décremente d'une certaine valeur souhaité donc j'ai fais les deux
	public void décrémenter() {
		if(valeurDe < 2) {
			System.out.println("Impossible de décrementer plus"); //message d'erreur qu'on peut supprimer plus tard
		}else {
			valeurDe--;
		}

	}
	public void décrémenter(int x) {
		if(valeurDe-x < 1) {
			System.out.println("Impossible de décrementer la totalité");//message d'erreur qu'on peut supprimer plus tard
			while(valeurDe >1) {
				valeurDe--;
			}
		}else {
			valeurDe=valeurDe-x;
		}

	}

	public void incrémenter() {
		if(valeurDe > 5) {
			System.out.println("Impossible d'incrémenter plus");//message d'erreur qu'on peut supprimer plus tard
		}else {
			valeurDe++;
		}

	}
	public void incrémenter(int x) {
		if(valeurDe+x > 6) {
			System.out.println("Impossible d'augmenter la totalité");//message d'erreur qu'on peut supprimer plus tard
			while(valeurDe < 6) {
				valeurDe++;
			}
		}else {
			valeurDe=valeurDe+x;
		}

	}

}
