public class Main {

    public static void main(String[] args) {
        Joueur j1 = new Joueur("Joueur 1");
        Joueur j2 = new Joueur("Joueur 2");

        Jeu jeu = new Jeu(j1,j2);
        jeu.jouer();
    }

}
