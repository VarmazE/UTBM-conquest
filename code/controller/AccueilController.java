package controller;

import javafx.stage.Stage;
import view.AccueilView;
import view.SelectionJoueurView;

public class AccueilController {
    public AccueilController(Stage stage, AccueilView accueilView) {
        accueilView.getPlayButton().setOnAction(event -> {
            // Transition vers l'écran de sélection du nombre de joueurs
            SelectionJoueurView selectionJoueurView = new SelectionJoueurView(stage);
            new SelectionJoueurController(stage, selectionJoueurView);
        });
    }
}
