package controller;

import javafx.stage.Stage;
import model.Jeu;
import model.Joueur;
import view.SelectionJoueurView;

import java.util.ArrayList;

public class SelectionJoueurController {

    private Jeu jeu;

    /**
     * Contrôleur de sélection des joueurs.
     *
     * @param stage Le stage principal de l'application.
     * @param view  La vue de sélection des joueurs.
     */
    public SelectionJoueurController(Stage stage, SelectionJoueurView view) {
        // Ajout des gestionnaires d'événements sur les boutons
        view.getPlayerButtons().forEach((button) -> {
            button.setOnAction(event -> {
                int playerNumber = Integer.parseInt(button.getText().substring(0, 1));

                // Créer la liste des joueurs
                ArrayList<Joueur> players = new ArrayList<>();
                for (int i = 0; i < playerNumber; i++) {
                    players.add(new Joueur("Joueur " + (i + 1)));
                }

                // Initialiser le modèle Jeu avec les joueurs
                jeu = new Jeu(players);
                new GameBoardController(stage, jeu);
            });
        });
    }
}
