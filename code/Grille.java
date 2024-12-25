import java.util.ArrayList;

public class Grille {
    // Constantes pour les quartiers
    private static final String DIRECTION = "Direction";
    private static final String ETUDIANT = "Etudiant";
    private static final String PROFESSEUR = "Professeur";

    // Constantes pour les ressources
    private static final String COMMUNICATION = "Communication";
    private static final String ECTS = "ECTS";
    private static final String SAVOIR = "Savoir";

    // Constantes pour les bonhommes
    private static final String ROUGE = "Rouge";
    private static final String JAUNE = "Jaune";
    private static final String BLANC = "Blanc";

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

        // Penser à faire les quartiers
        quartiers[0] = new Quartier(DIRECTION);
        quartiers[1] = new Quartier(ETUDIANT);
        quartiers[2] = new Quartier(PROFESSEUR);

        listeRessources[0] = new ListeRessources(COMMUNICATION);
        listeRessources[1] = new ListeRessources(ECTS);
        listeRessources[2] = new ListeRessources(SAVOIR);

        listeBonhommes[0] = new ListeBonhommes(ROUGE);
        listeBonhommes[1] = new ListeBonhommes(JAUNE);
        listeBonhommes[2] = new ListeBonhommes(BLANC);
    }

    /**
     * Méthode utilitaire pour obtenir l'index de la ressource en fonction de son
     * type.
     *
     * @param type Le type de la ressource.
     * @return L'index correspondant ou -1 si le type est inconnu.
     */
    private int getRessourcesIndex(String type) {
        switch (type) {
            case COMMUNICATION:
                return 0;
            case ECTS:
                return 1;
            case SAVOIR:
                return 2;
            default:
                return -1;
        }
    }

    /**
     * Ajouter une ressource.
     *
     * @param r La ressource à ajouter.
     */
    public void ajouterRessource(Ressources r) {
        /*
         * if (r == null) {
         * throw new IllegalArgumentException("La ressource ne peut pas être nulle.");
         * }
         */

        int index = getRessourcesIndex(r.getType());
        if (index != -1) {
            listeRessources[index].addRessource(r);
        } else {
            System.out.println("Type de ressource non pris en charge : " + r.getType());
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
        switch (b.getType()) {
            case ROUGE:
                listeBonhommes[0].addBonhomme(b);
                break;
            case JAUNE:
                listeBonhommes[1].addBonhomme(b);
                break;
            case BLANC:
                listeBonhommes[2].addBonhomme(b);
                break;
            default:
                System.out.println("Impossible d'ajouter le bonhomme");
                break;
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
    }

    /**
     * Avoir le bon quartier depuis le type
     *
     * @param p Le type du quartier
     * @return Quartier le bon quartier
     */
    public Quartier choisirQuartier(String p) {
        return switch (p) {
            case DIRECTION -> quartiers[0];
            case ETUDIANT -> quartiers[1];
            case PROFESSEUR -> quartiers[2];
            default -> null;
        };
    }


    /**
     * Processus de création d'un batiment
     *
     * @param p            plateau de jeu afin d'accéder aux zones et aux dés
     * @param index        index correspondant à celui payer afin de récupérer le
     *                     dés et la zone correspondant à l'index
     * @param typeBatiment afin de construire soit un batiment spécial ou non
     * @return true si la construction a été effectué
     */
    public boolean construireBatiment(PlateauJeu p, int index, String typeBatiment) {
        Zone zone = p.getOneZone(index);
        Dé de = p.getDe(index);

        String couleurQuartier = zone.getJeton().getCouleurCourante();
        Quartier quartier = choisirQuartier(couleurQuartier);
        Ligne ligne = quartier.choisirLigne(typeBatiment);

        Batiment batiment = ligne.getBatiment(de.getValeurDe()).construire();
        // if batiment isn't null mean that we built the batiment so we need to take the recompense, check if the line got bonus and check if we built a batiment with specificities
        if(batiment != null) {
            if(batiment.getRecompense() != null) {
                ajouterBonhomme(batiment.getRecompense());
            }
            Bonus bonus= ligne.checkBonus();
            if (bonus != null) {
                Recompenses recompenses = bonus.getRecompenses();
                if(recompenses instanceof Bonhomme){
                    ajouterBonhomme((Bonhomme) recompenses);
                } else if(recompenses instanceof Ressources) {
                    ajouterRessource((Ressources) recompenses);
                }
            }
            checkBatimentPrestige(p,index,batiment);
            return true;
        }
        return false;
    }


    public void checkBatimentPrestige(PlateauJeu p, int index , Batiment b) {
        if(b instanceof TourPenchee){
            Dé de = p.getDe(index);
            int valeur = de.getValeurDe();
            protegerColonne(valeur);

        } else if(b instanceof BureauAssociation){
            // do at the end, take we the black dice ?
            String couleur = ((BureauAssociation) b).getCouleurJeton();
            Recompenses recompenses = ((BureauAssociation) b).getRecompense();

            int multiplicateur = 0;




        } else if(b instanceof SalleProfesseurs){
            Personnage personnage = ((SalleProfesseurs) b).getPersonnage();
            ajouterPersonne(personnage);
        }
    }

    public void detruireColonne(String couleurQuartier, int colonne) {
        Quartier quartier = choisirQuartier(couleurQuartier);
        // change here
        quartier.choisirLigne("PRESTIGE").getBatiment(colonne).detruire();
        quartier.choisirLigne("FONCTION").getBatiment(colonne).detruire();
    }

    public void protegerColonne(int colonne) {
        for(Quartier quartier : quartiers){
            // change here
            quartier.choisirLigne("PRESTIGE").getBatiment(colonne).setProtege();
            quartier.choisirLigne("FONCTION").getBatiment(colonne).setProtege();
        }

    }

    public int calculerScore() {
        return 0;
    }

}
