package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Joueur {

    private String nom;
    private Grille grille;
    int score;
    private List<Zone> zonesJoueur;
    private List<De> des;


    public Joueur(String nom) {
        this.nom = nom;
        this.grille = new Grille();
    }

    /**
     * Gère le tour de jeu pour ce joueur.
     *
     * @param p        Le plateau de jeu.
     * @param semestre Le semestre courant.
    public void jouerTour(PlateauJeu p, String semestre) {
        List<De> des = p.getDes();
        List<Zone> zones = p.getActiveZone(semestre);
        Scanner scanner = new Scanner(System.in);

        int index = choisirIndex(scanner, "Sélectionnez un index de zone (0 à " + (zones.size() - 1) + "): ", 0, zones.size() - 1);

        // Méthode provisoire plus tard faudra choisir les ressources à utiliser etc
        if (choisirZone(index, new Ressources(Constante.COMMUNICATION, 1), zones, des)) {
            if (confirmationUtilisateur(scanner, "Voulez-vous incrémenter le dé ? (oui/non)")) {
                incrementerDes(des.get(index));
            }

            if (confirmationUtilisateur(scanner, "Voulez-vous décrémenter le dé ? (oui/non)")) {
                decrementerDes(des.get(index));
            }

            if (confirmationUtilisateur(scanner, "Voulez-vous changer la couleur d’un jeton ? (oui/non)")) {
                changerCouleurJeton(zones.get(index));
            }

            if (confirmationUtilisateur(scanner, "Voulez-vous construire un bâtiment ? (oui/non)")) {
                System.out.print("Quel type de bâtiment voulez-vous construire ? : ");
                String typeBatiment = scanner.next();
                grille.construireBatiment(typeBatiment,zones,des,index);
            } else{
                grille.ajouterRessource(new Ressources(zones.get(index).getJeton().getCouleur(),des.get(index).getValeurDe()));
            }
        }

        System.out.println("Vos actions pour ce tour sont terminées !");
    }*/

    /**
     * Permet au joueur de choisir une zone et de vérifier les conditions nécessaires.
     *
     * @param index L'index de la zone.
     * @param r     Les ressources nécessaires.
     * @param zones La liste des zones.
     * @param des   La liste des dés.
     * @return true si la zone est sélectionnée avec succès, false sinon.
     */
    public boolean choisirZone(int index, Ressources r, List<Zone> zones, List<De> des) {
        if (index < 0 || index >= zones.size()) {
            System.out.println("Erreur : L'index de la zone est invalide.");
            return false;
        }

        Cout cout = zones.get(index).getCout();
        System.out.println(cout);
        System.out.println(zones.get(index));
        if (!cout.canPayWithRessource(r)) {
            System.out.println("Pas la bonne type de ressource.");
            return false;
        }

        if (!(des.get(index) instanceof DeNoir) && checkAndDepenseRessource(r.getType(), r.getNombre())) {
            System.out.println("model.Zone sélectionnée avec succès à l'index " + index + ".");
            return true;
        }

        System.out.println("Conditions non remplies pour accéder à cette zone.");
        return false;
    }

    public Grille getGrille() {
        return grille;
    }

    public boolean ajouterRessource(String type, int nombre) {
        return grille.ajouterRessource(new Ressources(type, nombre));
    }

    public String getNom() {
        return nom;
    }

    private boolean checkAndDepenseRessource(String type, int nombre) {
        if (grille.getRessourceByType(type).calculerRessource() >= nombre) {
            grille.depenserRessource(new Ressources(type, nombre));
            return true;
        }
        return false;
    }

    public void setZonesJoueur(List<Zone> zones) {
        this.zonesJoueur = new ArrayList<>();
        for (Zone z : zones) {
            this.zonesJoueur.add(new Zone(z)); // Copie indépendante
        }
    }

    public void setDes(List<De> des) {
        this.des = new ArrayList<>();
        for (De de : des) {
            if(de instanceof DeNoir) {
                this.des.add(new DeNoir(de));
            } else {
                this.des.add(new De(de));

            }
        }
    }

    public List<De> getDes() {
        return des;
    }

    public List<Zone> getZonesJoueur() {
        return zonesJoueur;
    }

    public boolean incrementerDes(De d) {
        De de = getRightDe(d);
        if(de == null) return false;
        if (de.incrementer() && checkAndDepenseRessource(Constante.COMMUNICATION, 1)) {
            System.out.println("Le dé a été incrémenté avec succès.");
            return true;
        } else {
            System.out.println("Pas assez de ressources pour incrémenter le dé.");
            return false;
        }
    }

    public boolean decrementerDes(De d) {
        De de = getRightDe(d);
        if(de == null) return false;
        if (de.decrementer() && checkAndDepenseRessource(Constante.COMMUNICATION, 1)) {
            System.out.println("Le dé a été décrémenté.");
            return true;
        } else {
            System.out.println("Pas assez de ressources pour décrémenter le dé.");
            return false;
        }
    }

    public De getRightDe(De de) {
        for (De d : des) {
            if (d.equals(de)) {
                return d;
            }
        }
        return null;
    }

    public boolean changerCouleurJeton(Zone zone) {
        if (zone == null) {
            System.out.println("Zone invalide.");
            return false;
        }

        Zone zoneCible = zonesJoueur.stream()
                .filter(z -> z.getId() == zone.getId())
                .findFirst()
                .orElse(null);

        if (zoneCible == null) {
            System.out.println("La zone spécifiée n'appartient pas au joueur.");
            return false;
        }

        if (!checkAndDepenseRessource(Constante.SAVOIR, 2)) {
            System.out.println("Pas assez de ressources pour changer la couleur du jeton.");
            return false;
        }

        // Changer la couleur du jeton
        zoneCible.getJeton().inverserCouleur();
        System.out.println("La couleur du jeton a été changée avec succès.");
        return true;
    }


    // Méthodes utilitaires

    private int choisirIndex(Scanner scanner, String message, int min, int max) {
        int index;
        while (true) {
            System.out.print(message);
            try {
                index = Integer.parseInt(scanner.next());
                if (index >= min && index <= max) {
                    return index;
                }
            } catch (NumberFormatException e) {
                System.out.println("Veuillez entrer un nombre valide.");
            }
        }
    }

    private boolean confirmationUtilisateur(Scanner scanner, String message) {
        System.out.print(message + " ");
        String reponse = scanner.next().toLowerCase();
        return reponse.equals("oui");
    }

    public int getScore(){
        this.score = grille.calculerScore();
        return score;
    }
}
