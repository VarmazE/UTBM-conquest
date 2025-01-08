package controller;

import javafx.scene.shape.Arc;
import javafx.stage.Stage;
import model.*;
import javafx.scene.paint.Color;
import view.GameBoardView;

import java.util.*;

public class GameBoardController {

    private Jeu jeu;
    private GameBoardView view;
    private int currentPlayerIndex;
    Joueur joueur;
    private Arc selectedArc = null;
    private int indexZone = -1;
    private boolean colorChangeUsed = false;
    private boolean incrementDecrementUsed = false;
    private boolean decisiveActionUsed = false;

    public Quartier quartier = null;
    public Ligne ligne = null;


    public GameBoardController(Stage stage, Jeu jeu) {
        this.jeu = jeu;
        this.view = new GameBoardView(stage);
        jeu.getPlateauJeu().lancerDes();
        this.currentPlayerIndex = 0;
        joueur = jeu.getJoueur(currentPlayerIndex);
        joueur.setZonesJoueur(jeu.getPlateauJeu().getAllZone());
        joueur.setDes(jeu.getPlateauJeu().getDes());

        // Désactiver tous les boutons au démarrage
        view.getIncrementButton().setDisable(true);
        view.getDecrementButton().setDisable(true);
        view.getChangeColorButton().setDisable(true);
        view.getGainResourceButton().setDisable(true);
        view.getBuildButton().setDisable(true);
        view.getNextPlayer().setDisable(true);
        view.getButtonContainer().setDisable(true);

        updateRessourcesDisplay();
        updateBonhommesDisplay();

        view.setGrilleDisabled(true);
        view.afficherGrille(joueur.getGrille().getQuartier(), null);

        // Configuration des gestionnaires de clic
        view.setOnBatimentClicked(event -> handleBatimentSelection(event.getQuartier(), event.getLigne(), event.getBatiment()));


        updateView();

        // Boutons d'action
        view.getNextPlayer().setOnAction(event -> nextPlayer());
        view.getIncrementButton().setOnAction(event -> incrementDice());
        view.getDecrementButton().setOnAction(event -> decrementDice());
        view.getChangeColorButton().setOnAction(event -> changeJetonColor());
        view.getGainResourceButton().setOnAction(event -> gainResource());
        view.getBuildButton().setOnAction(event -> buildStructure());

        attachZoneHandlers();
    }


    private void nextPlayer() {
        currentPlayerIndex++;
        decisiveActionUsed = false;
        incrementDecrementUsed = false;
        colorChangeUsed = false;

        if (currentPlayerIndex >= jeu.getJoueurs().size()) {

            currentPlayerIndex = 0;
            jeu.getPlateauJeu().lancerDes();
            jeu.passerSemestre();
            if(jeu.getTourCourant() >= 2){
                jeu.getPlateauJeu().setDeNoirActif();
            }
            view.updateTourLabel(jeu.getTourCourant());

            if (jeu.getTourCourant() == jeu.getToursTotal()) {
                System.out.println("Fin de partie");
                view.endGame(jeu.getJoueurs());
            }
            view.updateTourLabel(jeu.getTourCourant());

        }
        view.setGrilleDisabled(true);
        view.getNextPlayer().setDisable(true);
        quartier = null;
        ligne = null;

        joueur = jeu.getJoueur(currentPlayerIndex);
        joueur.setZonesJoueur(jeu.getPlateauJeu().getAllZone()); // Attribuer des zones indépendantes
        joueur.setDes(jeu.getPlateauJeu().getDes());


        updateRessourcesDisplay();
        updateBonhommesDisplay();

        for(int i= 0; i < joueur.getDes().size(); i ++){
            System.out.println("Essai");
            if(jeu.getPlateauJeu().getDe(i) instanceof DeNoir && (((DeNoir) jeu.getPlateauJeu().getDe(i)).isActif())){
                System.out.println("Reussite");
                joueur.getGrille().detruireColonne(getZonesActiveFromJoueur().get(i).getJeton().getCouleur(),jeu.getPlateauJeu().getDe(i).getValeurDe() -1 );

            }
        }


        // Réinitialiser la sélection
        if (selectedArc != null) {
            selectedArc.setFill(view.getColorFromZone(view.getZoneMap().get(selectedArc).getJeton().getCouleur()));
            selectedArc = null;
        }
        indexZone = -1;

        view.afficherGrille(joueur.getGrille().getQuartier(), null);
        updateView();
        attachZoneHandlers();
    }

    private void updateGrilleAndSelection() {
        Quartier accessibleQuartier = getAccessibleQuartier();
        view.afficherGrille(joueur.getGrille().getQuartier(), accessibleQuartier);
        updateBuildButtonState();
    }


    private void incrementDice() {
        if (!incrementDecrementUsed && !decisiveActionUsed && joueur.incrementerDes(jeu.getPlateauJeu().getDe(indexZone))) {
            incrementDecrementUsed = true;
            view.getIncrementButton().setDisable(true);
            view.getDecrementButton().setDisable(true);
            zoneUpdate();
        } else {
            view.showErrorDialog("Impossible d'incrémenter la valeur du dés.");
        }
    }

    private void decrementDice() {
        if (!incrementDecrementUsed && !decisiveActionUsed && joueur.decrementerDes(jeu.getPlateauJeu().getDe(indexZone))) {
            incrementDecrementUsed = true;
            view.getDecrementButton().setDisable(true);
            view.getIncrementButton().setDisable(true);
            zoneUpdate();
        } else {
            view.showErrorDialog("Impossible de décrémenter la valeur du dés.");
        }
    }

    private void changeJetonColor() {
        System.out.println(jeu.getPlateauJeu().getActiveZone(jeu.getSemestre(),joueur.getZonesJoueur()));
        if (!colorChangeUsed && !decisiveActionUsed && joueur.changerCouleurJeton(jeu.getPlateauJeu().getActiveZone(jeu.getSemestre(),joueur.getZonesJoueur()).get(indexZone))) {
            colorChangeUsed = true;
            view.getChangeColorButton().setDisable(true);
            zoneUpdate();
        } else {
            view.showErrorDialog("Impossible de changer la couleur du jeton.");
        }
    }

    private void gainResource() {
        if (!decisiveActionUsed) {
            String couleurJeton = jeu.getPlateauJeu().getActiveZone(jeu.getSemestre(),joueur.getZonesJoueur()).get(indexZone).getJeton().getCouleur();
            int montantRessource = joueur.getDes().get(indexZone).getValeurDe();
            if (joueur.ajouterRessource(jeu.getRightRessource(couleurJeton), montantRessource)) {
                decisiveActionUsed = true;
                disableAllButtonsExceptNextPlayer();
                zoneUpdate();
            } else {
                view.showErrorDialog("Impossible d'ajouter des ressources.");
            }
        }
    }

    private void buildStructure() {
        if (indexZone >= 0 && !decisiveActionUsed && quartier != null && ligne != null) {
            int valeurDe = joueur.getDes().get(indexZone).getValeurDe();
            int batimentIndex = valeurDe - 1;

            if (batimentIndex >= 0 && batimentIndex < ligne.getBatiments().length) {
                Batiment batiment = ligne.getBatiments()[batimentIndex];

                if (!batiment.isConstruit() && joueur.getGrille().construireBatiment(ligne.getTypeLigne(), jeu.getPlateauJeu().getActiveZone(jeu.getSemestre(),joueur.getZonesJoueur()), joueur.getDes(), indexZone)) {
                    decisiveActionUsed = true;
                    disableAllButtonsExceptNextPlayer();
                    zoneUpdate();
                    System.out.println("Bâtiment construit avec succès !");
                } else {
                    view.showErrorDialog("Construction impossible pour ce bâtiment.");
                }
            } else {
                view.showErrorDialog("Aucun bâtiment valide pour la valeur du dé.");
            }
        }
    }


    private void disableAllButtonsExceptNextPlayer() {
        view.getButtonContainer().setDisable(true);
        view.getNextPlayer().setDisable(false);
    }

    private void updateView() {
        view.getCurrentPlayerLabel().setText("Joueur actuel : " + joueur.getNom());
        zoneUpdate();

    }

    private void zoneUpdate() {
        if (decisiveActionUsed) {
            disableAllButtonsExceptNextPlayer();
        } else {
            view.getIncrementButton().setDisable(incrementDecrementUsed);
            view.getDecrementButton().setDisable(incrementDecrementUsed);
            view.getChangeColorButton().setDisable(colorChangeUsed);
            view.getGainResourceButton().setDisable(false);
            updateBuildButtonState();
        }

        List<Zone> allZones = joueur.getZonesJoueur();
        ArrayList<Zone> activeZones = jeu.getPlateauJeu().getActiveZone(jeu.getSemestre());
        List<De> listDe = joueur.getDes();

        updateGrilleAndSelection();
        updateRessourcesDisplay();

        updateBonhommesDisplay();
        view.updateZones(allZones, activeZones, listDe);


        if (indexZone != -1) {
            System.out.println("je suis la ");

            view.setGrilleDisabled(false);
            Zone selectedZone = jeu.getPlateauJeu().getActiveZone(jeu.getSemestre()).get(indexZone);
            Arc updatedArc = findArcForZone(selectedZone);
            if (updatedArc != null) {
                selectedArc = updatedArc;
                selectedArc.setFill(Color.LIGHTCYAN);
            }
        }
    }

    private void attachZoneHandlers() {
        List<Zone> zones = joueur.getZonesJoueur();
        ArrayList<Zone> activeZones = jeu.getPlateauJeu().getActiveZone(jeu.getSemestre());
        List<De> listDe = joueur.getDes();
        HashMap<Arc, Zone> zoneMap = view.updateZones(zones, activeZones, listDe);

        for (Arc arc : zoneMap.keySet()) {
            Zone zone = zoneMap.get(arc);
            arc.setOnMouseClicked(event -> handleZoneSelection(zone, arc));
        }
    }

    private void handleZoneSelection(Zone zone, Arc arc) {
        int zoneIndex = jeu.getPlateauJeu().getActiveZone(jeu.getSemestre()).indexOf(zone);
        if(indexZone == -1){
            view.showPaymentDialog(
                    zoneIndex,
                    zone.getCout(),
                    () -> processZoneSelection(zoneIndex, zone, new Ressources(Constante.COMMUNICATION, zone.getCout().getMontant()), arc),
                    () -> processZoneSelection(zoneIndex, zone, new Ressources(Constante.ECTS, zone.getCout().getMontant()), arc),
                    () -> processZoneSelection(zoneIndex, zone, new Ressources(Constante.SAVOIR, zone.getCout().getMontant()), arc)
            );
        }

    }

    private void processZoneSelection(int zoneIndex, Zone zone, Ressources ressources, Arc arc) {
        List<De> listDe = joueur.getDes();
        List<Zone> zones = jeu.getPlateauJeu().getActiveZone(jeu.getSemestre());

        boolean isZoneChosen = joueur.choisirZone(zoneIndex, ressources, zones, listDe);

        if (isZoneChosen) {
            indexZone = zoneIndex;

            if (selectedArc != null) {
                selectedArc.setFill(view.getColorFromZone(view.getZoneMap().get(selectedArc).getJeton().getCouleur()));
            }

            selectedArc = arc;
            selectedArc.setFill(Color.GREEN);
            view.getButtonContainer().setDisable(false);
            zoneUpdate();
            updateBuildButtonState();
        } else {
            view.showErrorDialog("La zone " + zoneIndex + " n'a pas pu être choisie avec la ressource " + ressources.getType() + ".");
        }
    }

    private Arc findArcForZone(Zone zone) {
        for (Arc arc : view.getZoneMap().keySet()) {
            if (view.getZoneMap().get(arc).equals(zone)) {
                return arc;
            }
        }
        return null;
    }

    private void handleBatimentSelection(Quartier q, Ligne l, Batiment batiment) {
        Quartier accessibleQuartier = getAccessibleQuartier();

        if (accessibleQuartier != null && !accessibleQuartier.equals(q)) {
            view.showErrorDialog("Ce quartier n'est pas accessible avec la zone active.");
            return;
        }

        if (indexZone >= 0) {
            int valeurDe = joueur.getDes().get(indexZone).getValeurDe();
            int batimentIndex = valeurDe - 1; // Les dés commencent à 1

            if (batimentIndex >= 0 && batimentIndex < l.getBatiments().length) {
                Batiment batimentCible = l.getBatiments()[batimentIndex];

                if (!batimentCible.isConstruit()) {
                    quartier = q;
                    ligne = l;
                    updateBuildButtonState();
                } else {
                    view.showErrorDialog("Le bâtiment est déjà construit.");
                }
            } else {
                view.showErrorDialog("Aucun bâtiment valide pour la valeur du dé : " + valeurDe);
            }
        } else {
            view.showErrorDialog("Aucune zone sélectionnée.");
        }
    }



    private Quartier getAccessibleQuartier() {
        if (indexZone < 0) {
            return null; // Pas de zone sélectionnée
        }

        Jeton jeton = getJetonFromJoueur();

        if (joueur.getDes().get(indexZone) instanceof DeNoir) {
            return null; // Tous les quartiers sont accessibles
        }

        if (jeton != null) {
            for (Quartier quartier : joueur.getGrille().getQuartier()) {
                if (quartier.getCouleurQuartier().equals(jeton.getCouleur())) {
                    return quartier;
                }
            }
        }

        return null; // Aucun quartier accessible
    }


    private void updateBuildButtonState() {
        boolean shouldEnable = (indexZone != -1 && quartier != null && ligne != null);
        view.getBuildButton().setDisable(!shouldEnable);
    }

    public Jeton getJetonFromJoueur(){
        Zone activeZone = jeu.getPlateauJeu().getActiveZone(jeu.getSemestre()).get(indexZone);
        Jeton jeton = null;
        List<Zone> joueurZones = joueur.getZonesJoueur();
        for (Zone zone : joueurZones) {
            if (zone.equals(activeZone)) {
                jeton = zone.getJeton();
            }
        }
        return jeton;
    }

    public List<Zone> getZonesActiveFromJoueur(){
        List<Zone> list = new ArrayList<>();
        if(jeu.getSemestre().equalsIgnoreCase(Constante.AUTOMNE)){
                list.addAll(joueur.getZonesJoueur().subList(1, 5));
            } else {
                list.addAll(joueur.getZonesJoueur().subList(5, 9));
            }
        return list;
    }

    private void updateBonhommesDisplay() {
        System.out.println("updateBonhommesDisplay");
        view.afficherBonhommes(joueur.getGrille().getListeBonhommes());
    }

    private void updateRessourcesDisplay() {
        System.out.println("updateBonhommesDisplay");
        view.afficherRessources(joueur.getGrille().getListeRessources());
    }


}
