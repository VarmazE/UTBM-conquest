import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PlateauJeu {
	ArrayList<De> listeDes; // liste de 4 dés
	ArrayList<Cout> listeCout; // liste des 9 couts de chaque zone
	ArrayList<Zone> listeZone; // liste des 9 zones
	
 	

	public void tournerPlateau() {
		Zone temp=listeZone.get(0);
		listeZone.remove(0); 
		listeZone.add(temp); // on supprime le premier element et on l'ajoute a la fin
		affecterCoutZone(); // potentiellement si on veut pas le réécrire a chaque fois qu'on torune le plateau.
	}

	public void lancerDes() {
		
		for (De d: listeDes) {
			d.lancerDe();
		}

	}

	public void trierDes() {
	    Collections.sort(listeDes, (d1, d2) -> Integer.compare(d1.getValeurDe(), d2.getValeurDe())); // absolument pas sur
	}

	public void affecterCoutZone() {
			for(int i=0; i<9 ; i++) {
				this.listeZone.get(i).setMontant(this.listeCout.get(i).getMontant()); // faudra aussi mettre les types de ressources possible ?
			}
	}

	public ArrayList<Zone> getZone(String semestre) {
		ArrayList<Zone> list = new ArrayList<Zone>();
		if(semestre.equals("Automne")) {
			
			for(int i=1; i<5; i++) {
				list.add(this.listeZone.get(i));
			}
		}else if(semestre.equals("Printemps")) {
			for(int i=5; i<9; i++) {
				list.add(this.listeZone.get(i));
			}
		}
		return list;
	}
	public void ajoutDe(De de) {
		if(listeDes.size() < 4) {
			listeDes.add(de);
		}

	}
	public void ajoutCout(Cout cout) {
		if(listeCout.size() < 9) {
			listeCout.add(cout);
		}
	}
	public void ajoutZone(Zone zone) {
		if(listeZone.size() < 9) {
			listeZone.add(zone);
		}
	}
	
	public ArrayList<De> getDes(){
		return listeDes;
	}
	public ArrayList<Cout> getCout(){
		return listeCout;
	}
	public ArrayList<Zone> getZone(){
		return listeZone;
	}
	

}
