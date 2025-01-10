package model;

import java.util.ArrayList;
import java.util.List;

public class Grille {

    // Attributs de la classe
    private int score;
    private ArrayList<Personnage> personnages;
    private Quartier[] quartiers;
    private ListeRessources[] listeRessources;
    private ListeBonhommes[] listeBonhommes;

    public Grille() {
        score = 0;
        personnages = new ArrayList<Personnage>();
        quartiers = new Quartier[3];
        listeRessources = new ListeRessources[3];
        listeBonhommes = new ListeBonhommes[3];

        initializeQuartier();

        listeRessources[0] = new ListeRessources(Constante.COMMUNICATION);
        listeRessources[1] = new ListeRessources(Constante.ECTS);
        listeRessources[2] = new ListeRessources(Constante.SAVOIR);

        listeBonhommes[0] = new ListeBonhommes(Constante.ROUGE);
        listeBonhommes[1] = new ListeBonhommes(Constante.JAUNE);
        listeBonhommes[2] = new ListeBonhommes(Constante.BLANC);

        ajouterBonhomme(new Bonhomme(Constante.BLANC,1));
        ajouterBonhomme(new Bonhomme(Constante.JAUNE,1));
        ajouterBonhomme(new Bonhomme(Constante.ROUGE,1));

    }

    public ListeRessources getRessourceByType(String type) {
        return switch (type){
            case Constante.COMMUNICATION -> listeRessources[0];
            case Constante.ECTS -> listeRessources[1];
            case Constante.SAVOIR -> listeRessources[2];
            default -> null;
        };
    }

    /**
     * Ajouter une ressource.
     *
     * @param r La ressource à ajouter.
     */
    public boolean ajouterRessource(Ressources r) {
        /*
         * if (r == null) {
         * throw new IllegalArgumentException("La ressource ne peut pas être nulle.");
         * }
         */

        int index = getRessourcesIndex(r.getType());
        if (index != -1) {
            listeRessources[index].addRessource(r);
            Bonhomme bonus = listeRessources[index].isBonus(); // Toutes les 6 ressources on gagne un bonhomme
            if (bonus != null) {
                ajouterBonhomme(bonus);
            }
            return true;
        } else {
            System.out.println("Type de ressource non pris en charge : " + r.getType());
            return false;
        }
    }

    /**
     * Dépenser une ressource.
     *
     * @param r La ressource à dépenser.
     * @return true si la ressource a été dépensée avec succès, false sinon.
     */
    public boolean depenserRessource(Ressources r) {
        /*
         * if (r == null) {
         * throw new IllegalArgumentException("La ressource ne peut pas être nulle.");
         * }
         */

        int index = getRessourcesIndex(r.getType());
        if (index != -1) {
            System.out.println("Type de ressource non pris en charge : " + r.getType() + " " + r.getNombre());
            return listeRessources[index].RemoveRessource(r);
        } else {
            System.out.println("Type de ressource non pris en charge : " + r.getType());
            return false;
        }
    }

    /**
     * Ajouter un bonhomme à la bonne liste de bonhomme.
     *
     * @param b L'objet bonhomme à ajouter.
     */
    public void ajouterBonhomme(Bonhomme b) {
        int index = getBonhommeIndex(b.getType());
        if (index != -1) {
            listeBonhommes[index].addBonhomme(b);
        } else {
            //throw new IllegalArgumentException("Type de bonhomme non pris en charge : " + b.getType());
            System.out.println("Type de bonhomme non pris en charge : " + b.getType());
        }

    }

    /**
     * Ajouter un personnage (qui a un multiplicateur de points) dans la liste
     * personnages
     *
     * @param p Le personnage à ajouter
     */
    public void ajouterPersonne(Personnage p) {
        personnages.add(p);
        if (personnages.size() <= 2) {
            p.setMultiplicateur(1);
        } else if (personnages.size() <= 4) {
            p.setMultiplicateur(2);
        } else {
            p.setMultiplicateur(3);
        }
    }

    /**
     * Avoir le bon quartier depuis le type
     *
     * @param p Le type du quartier
     * @return model.Quartier le bon quartier
     */
    public Quartier choisirQuartier(String p) {
        return switch (p) {
            case Constante.DIRECTION -> quartiers[0];
            case Constante.ETUDIANT -> quartiers[1];
            case Constante.PROFESSEUR -> quartiers[2];
            default -> null;
        };
    }


    /**
     * Processus de création d'un batiment
     * @param typeBatiment afin de construire soit un batiment spécial ou non
     *
     * @param zonesActives  Liste des 4 zones
     * @param des Liste des 4 dés
     * @param index le bonne index pour la zone et le bon dé
     * @return true si la construction a été effectué
     */
    public boolean construireBatiment(String typeBatiment, List<Zone> zonesActives, List<De> des, int index) {
        Zone zone = zonesActives.get(index);
        De de = des.get(index);

        Quartier quartier = choisirQuartier(zone.getJeton().getCouleur());
        if (quartier == null) {
            return false;
        }

        Ligne ligne = quartier.choisirLigne(typeBatiment);
        Batiment batiment = ligne.getBatiment(de.getValeurDe() - 1).construire();

        if (batiment != null) {
            traiterRecompensesEtBonus(batiment, ligne);
            checkBatimentPrestige(index, batiment, zonesActives, des);
            Ligne l = quartiers[0].choisirLigne(typeBatiment);
            Ligne l2 = quartiers[1].choisirLigne(typeBatiment);
            Ligne l3 = quartiers[2].choisirLigne(typeBatiment);

            for(Batiment bat : l.getBatiments()) {
                System.out.println(bat.isConstruit());
            }

            for(Batiment bat : l2.getBatiments()) {
                System.out.println(bat.isConstruit());
            }

            for(Batiment bat : l3.getBatiments()) {
                System.out.println(bat.isConstruit());
            }
            return true;
        }


        return false;
    }


    /**
     * Vérifie et applique les effets spécifiques des bâtiments de prestige.
     *
     * @param index    L'index du dé/zone sur le plateau.
     * @param b        Le bâtiment à vérifier.
     * @param zonesActives Les 4 zones actives.
     * @param des Listes des 4 dés
     */
    public void checkBatimentPrestige(int index, Batiment b, List<Zone> zonesActives, List<De> des) {
        if (b instanceof TourPenchee) {
            gererTourPenchee(des.get(index).getValeurDe() - 1);
        } else if (b instanceof BureauAssociation) {
            gererBureauAssociation((BureauAssociation) b, zonesActives, des);
        } else if (b instanceof SalleProfesseurs) {
            gererSalleProfesseurs((SalleProfesseurs) b);
        }
    }

    public void detruireColonne(String couleurQuartier, int colonne) {
        Quartier quartier = choisirQuartier(couleurQuartier);
        // change here
        quartier.choisirLigne(Constante.FONCTION).getBatiment(colonne).detruire();
        System.out.println(quartier + " detruirement");

        quartier.choisirLigne(Constante.PRESTIGE).getBatiment(colonne).detruire();
        System.out.println(quartier + " detruirements");
    }

    public void protegerColonne(int colonne) {
        for (Quartier quartier : quartiers) {
            // change here
            quartier.choisirLigne("Prestige").getBatiment(colonne).setProtege();
            quartier.choisirLigne("Fonction").getBatiment(colonne).setProtege();
        }

    }


    /**
     * Calcule le score total de la grille en fonction des quartiers,
     * ressources et bonhommes.
     *
     * @return Le score total.
     */
    public int calculerScore() {
        this.score = quartiers[0].calculerScoreQuartier()
                + quartiers[1].calculerScoreQuartier()
                + quartiers[2].calculerScoreQuartier()
                + listeRessources[0].calculerRessource()/2
                + listeRessources[1].calculerRessource()/2
                + listeRessources[2].calculerRessource()/2
                + listeBonhommes[0].getNombre()
                + listeBonhommes[1].getNombre()
                + listeBonhommes[2].getNombre();
        return score;
    }

    /**
     * Méthode utilitaire pour obtenir l'index de la ressource en fonction de son
     * type.
     *
     * @param type Le type de la ressource.
     * @return L'index correspondant ou -1 si le type est inconnu.
     */
    private int getRessourcesIndex(String type) {
        return switch (type) {
            case Constante.COMMUNICATION -> 0;
            case Constante.ECTS -> 1;
            case Constante.SAVOIR -> 2;
            default -> -1;
        };
    }

    private int getBonhommeIndex(String type) {
        return switch (type) {
            case Constante.ROUGE -> 0;
            case Constante.JAUNE -> 1;
            case Constante.BLANC -> 2;
            default -> -1;
        };
    }

    private void traiterRecompensesEtBonus(Batiment batiment, Ligne ligne) {
        if (batiment.getRecompense() != null) {
            ajouterBonhomme(batiment.getRecompense());
        }

        Bonus bonus = ligne.checkBonus();
        if (bonus != null) {
            Recompenses recompense = bonus.getRecompenses();
            appliquerRecompenses(recompense);
        }
    }

    /**
     * Gère les effets spécifiques du bâtiment model.TourPenchee.
     *
     * @param valeurDe la valeur du dé
     */
    private void gererTourPenchee(int valeurDe) {
        protegerColonne(valeurDe);
    }

    /**
     * Gère les effets spécifiques du bâtiment model.BureauAssociation.
     *
     * @param b          Le bâtiment model.BureauAssociation.
     * @param zonesActives   Liste des 4 zones actives
     * @param des Liste des 4 dés
     */
    private void gererBureauAssociation(BureauAssociation b, List<Zone> zonesActives, List<De> des) {
        String couleur = b.getCouleurJeton();
        Recompenses recompenses = b.getRecompenseCase();

        int multiplicateur = 0;
        for (int i = 0; i < zonesActives.size(); i++) {
            if (!(des.get(i) instanceof DeNoir) && zonesActives.get(i).getJeton().getCouleur().equals(couleur)) {
                System.out.println("Incrémenté !");
                multiplicateur++;
            }
        }

        if (recompenses != null) {
            recompenses.setMultiplicateur(multiplicateur);
            System.out.println("recompenses nombre : " +  recompenses.getNombre());
            appliquerRecompenses(recompenses);
        }
    }

    /**
     * Gère les effets spécifiques du bâtiment model.SalleProfesseurs.
     *
     * @param b Le bâtiment model.SalleProfesseurs.
     */
    private void gererSalleProfesseurs(SalleProfesseurs b) {
        Personnage personnage = b.getPersonnage();
        if (personnage != null) {
            ajouterPersonne(personnage);
        }
    }

    /**
     * Applique les récompenses associées, qu'elles soient des bonhommes ou des ressources.
     *
     * @param recompenses La récompense à appliquer.
     */
    private void appliquerRecompenses(Recompenses recompenses) {
        if (recompenses instanceof Bonhomme) {
            System.out.println("Bonhomme : " + recompenses.getClass() + " " + recompenses.getNombre());
            ajouterBonhomme((Bonhomme) recompenses);
        } else if (recompenses instanceof Ressources) {
            System.out.println("Ressources : " + recompenses.getClass() + " " + recompenses.getNombre());
            ajouterRessource((Ressources) recompenses);
        }
    }

    // Refaire méthode à la fin pour les plus motivés
    public void initializeQuartier() {
        // model.Quartier Direction
        List<Personnage> personnages = List.of(new Personnage("M. Gechter"),
                new Personnage("M. Montavon"),
                new Personnage("M. Brunoud"),
                new Personnage("Mme El-Qoraychy"),
                new Personnage("M. Frossard"),
                new Personnage("M. Chah")

        );


        List<Bonhomme> bon1 = List.of(new Bonhomme(Constante.ROUGE, 1), new Bonhomme(Constante.ROUGE, 1), new Bonhomme(Constante.JAUNE, 1), new Bonhomme(Constante.JAUNE, 1), new Bonhomme(Constante.BLANC, 1), new Bonhomme(Constante.BLANC, 1));
        List<Bonhomme> bon2 = List.of(new Bonhomme(Constante.ROUGE, 2), new Bonhomme(Constante.ROUGE, 2), new Bonhomme(Constante.ROUGE, 2), new Bonhomme(Constante.ROUGE, 2), new Bonhomme(Constante.ROUGE, 2), new Bonhomme(Constante.ROUGE, 2));
        List<Bonhomme> bon3 = List.of(new Bonhomme(Constante.JAUNE, 0), new Bonhomme(Constante.JAUNE, 0), new Bonhomme(Constante.JAUNE, 0), new Bonhomme(Constante.JAUNE, 0), new Bonhomme(Constante.JAUNE, 0), new Bonhomme(Constante.JAUNE, 0));
        List<Bonhomme> bon4 = List.of(new Bonhomme(Constante.JAUNE, 2), new Bonhomme(Constante.JAUNE, 2), new Bonhomme(Constante.JAUNE, 2), new Bonhomme(Constante.JAUNE, 2), new Bonhomme(Constante.JAUNE, 2), new Bonhomme(Constante.JAUNE, 2));
        List<Bonhomme> bon5 = List.of(new Bonhomme(Constante.BLANC, 0), new Bonhomme(Constante.BLANC, 0), new Bonhomme(Constante.BLANC, 0), new Bonhomme(Constante.BLANC, 0), new Bonhomme(Constante.BLANC, 0), new Bonhomme(Constante.BLANC, 0));
        List<Bonhomme> bon6 = List.of(new Bonhomme(Constante.BLANC, 2), new Bonhomme(Constante.BLANC, 2), new Bonhomme(Constante.BLANC, 2), new Bonhomme(Constante.BLANC, 2), new Bonhomme(Constante.BLANC, 2), new Bonhomme(Constante.BLANC, 2));

        Batiment[] bat1 = new Batiment[6];
        Batiment[][] bat246 = new Batiment[3][6];
        Batiment[] bat5 = new Batiment[6];

        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 6; j++) {
                switch (i){
                    case 0:
                        bat246[i][j] = new Batiment(bon2.get(j));
                        break;
                    case 1:
                        bat246[i][j] = new Batiment(bon4.get(j));
                        break;
                    case 2:
                        bat246[i][j] = new Batiment(bon6.get(j));
                        break;
                    default:
                        break;

                }
            }
        }

        for(int i = 0; i < 6; i++) {
            bat1[i] = new TourPenchee(bon1.get(i));
        }

        BureauAssociation bureauAsso1 = new BureauAssociation(Constante.DIRECTION,bon3.get(0),new Ressources(Constante.COMMUNICATION, 3));
        BureauAssociation bureauAsso2 = new BureauAssociation(Constante.DIRECTION,bon3.get(1),new Bonhomme(Constante.ROUGE, 2));
        BureauAssociation bureauAsso3 = new BureauAssociation(Constante.ETUDIANT,bon3.get(2),new Ressources(Constante.ECTS, 3));
        BureauAssociation bureauAsso4 = new BureauAssociation(Constante.ETUDIANT,bon3.get(3),new Bonhomme(Constante.JAUNE, 2));
        BureauAssociation bureauAsso5 = new BureauAssociation(Constante.PROFESSEUR,bon3.get(4),new Ressources(Constante.SAVOIR, 3));
        BureauAssociation bureauAsso6 = new BureauAssociation(Constante.PROFESSEUR,bon3.get(5),new Bonhomme(Constante.BLANC, 2));

        Batiment[] bat3 = {bureauAsso1,bureauAsso2,bureauAsso3,bureauAsso4,bureauAsso5,bureauAsso6};

        for(int i = 0; i < 6; i++) {
            bat5[i] = new SalleProfesseurs(bon5.get(i),personnages.get(i));
        }

        List<Bonus> lb1 = List.of(new Bonus(new Bonhomme(Constante.ROUGE, 1),bat1[0],bat1[1]),
                new Bonus(new Bonhomme(Constante.JAUNE, 1),bat1[2],bat1[3]),
                new Bonus(new Bonhomme(Constante.BLANC, 1),bat1[4],bat1[5])
        );
        List<Bonus> lb2 = List.of(new Bonus(new Bonhomme(Constante.ROUGE, 2),bat246[0][0],bat246[0][1]),
                new Bonus(new Ressources(Constante.COMMUNICATION, 3),bat246[0][4],bat246[0][5])
                );
        List<Bonus> lb3 = new ArrayList<>();

        List<Bonus> lb4 = List.of(new Bonus(new Ressources(Constante.ECTS, 3),bat246[1][0],bat246[1][1]),
                new Bonus(new Bonhomme(Constante.JAUNE, 2),bat246[1][2],bat246[1][3])
        );
        List<Bonus> lb5 = new ArrayList<>();
        List<Bonus> lb6 = List.of(new Bonus(new Ressources(Constante.SAVOIR, 3),bat246[2][2],bat246[2][3]),
                new Bonus(new Bonhomme(Constante.BLANC, 2),bat246[2][4],bat246[2][5]));


        Ligne l1 = new Ligne("Prestige",bat1,lb1,personnages.get(0));
        Ligne l2 = new Ligne("Fonction",bat246[0],lb2,personnages.get(1));
        Ligne l3 = new Ligne("Prestige", bat3, lb3,personnages.get(2));
        Ligne l4 = new Ligne("Fonction",bat246[1],lb4,personnages.get(3));
        Ligne l5 = new Ligne("Prestige",bat5,lb5,personnages.get(4));
        Ligne l6 = new Ligne("Fonction",bat246[2],lb6,personnages.get(5));


        quartiers[0] = new Quartier(Constante.DIRECTION,l1,l2);


        // model.Quartier Etudiant
        quartiers[1] = new Quartier(Constante.ETUDIANT,l3,l4);

        // model.Quartier professeur
        quartiers[2] = new Quartier(Constante.PROFESSEUR, l5,l6);
    }

    public Quartier[] getQuartier(){
        return quartiers;
    }

    public ListeRessources[] getListeRessources() {
        return this.listeRessources;
    }

    public ListeBonhommes[] getListeBonhommes(){
        return this.listeBonhommes;
    }

}
