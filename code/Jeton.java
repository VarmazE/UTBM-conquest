public class Jeton {
	
	private int idJeton;
	
	private String couleurRecto;

	private String couleurVerso;

	private String couleurCourante;
	
	private static int compteur = 0;
	
	private boolean isCouleurValide(String couleur) {
        return couleur.equals("Direction") || couleur.equals("Professeur") || couleur.equals("Etudiant");
    }
	
	public Jeton(){
		this.idJeton=++compteur;
		
		int x = (int) (Math.random() * 3); // Nombre aléatoire entre 0 et 2
		switch (x) {
		    case 0:
		        this.couleurRecto = "Direction";
		        this.couleurVerso = "Etudiant";
		        break;
		    case 1:
		        this.couleurRecto = "Professeur";
		        this.couleurVerso = "Direction";
		        break; 
		    case 2:
		        this.couleurRecto = "Etudiant";
		        this.couleurVerso = "Professeur";
		        break; 
		}
		
		this.couleurCourante=couleurRecto;
	}
	
	public Jeton(String c1, String c2) {
		
        if (!isCouleurValide(c1) || !isCouleurValide(c2)) {
            System.out.println("Les couleurs doivent être : Direction, Professeur, ou Etudiant.");
            this.idJeton = ++compteur;
            int x = (int) (Math.random() * 3); // jsp si c'est possible d'apeller le constructeur par défaut ici mais ça fait la meme chose que Jeton()
            switch (x) {
                case 0:
                    this.couleurRecto = "Direction";
                    this.couleurVerso = "Etudiant";
                    break;
                case 1:
                    this.couleurRecto = "Professeur";
                    this.couleurVerso = "Direction";
                    break;
                case 2:
                    this.couleurRecto = "Etudiant";
                    this.couleurVerso = "Professeur";
                    break;
            }
            this.couleurCourante = couleurRecto;
        } else {
            this.idJeton = ++compteur; 
            this.couleurRecto = c1;
            this.couleurVerso = c2;
            this.couleurCourante = couleurRecto;
        }
	}
	
	public void inverserCouleur() {
		if(this.couleurCourante.equals(this.couleurRecto) ) {
			this.couleurCourante=this.couleurVerso;
		}else {
			this.couleurCourante = this.couleurRecto;
		}
	}
	
	public String getCouleur() {
		return this.couleurCourante;
	}
	
	public void setCouleur(String s) {
		if(!isCouleurValide(s)){
			System.out.println("Les couleurs ne peuvent être que : Direction, Professeur,Etudiant. ");
		}else {
			this.couleurCourante=s;
		}
	}
	
	public int getId() {
		return this.idJeton;
	}
}
