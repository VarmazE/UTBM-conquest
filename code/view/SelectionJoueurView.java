package view;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SelectionJoueurView {

    private List<Button> playerButtons;

    public SelectionJoueurView(Stage stage) {
        playerButtons = new ArrayList<>();

        // Texte principal
        Text questionText = new Text("Combien de joueurs dans cette partie ?");
        questionText.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        // Boutons avec images pour le nombre de joueurs
        HBox playerOptions = new HBox(20);
        playerOptions.setAlignment(Pos.CENTER);

        for (int i = 1; i <= 4; i++) {
            Button playerButton = createPlayerButton(i);
            playerButtons.add(playerButton);
            playerOptions.getChildren().add(playerButton);
        }

        // Footer
        Text footerText = new Text("Ce jeu est proposé par l'équipe 4Real dans le cadre d'un projet à l'UTBM");
        footerText.setStyle("-fx-font-size: 12px; -fx-fill: white;");
        StackPane footer = new StackPane(footerText);
        footer.setStyle("-fx-background-color: #007ACC;");
        footer.setPrefHeight(40);

        // Disposition verticale
        VBox centerLayout = new VBox(40, questionText, playerOptions);
        centerLayout.setAlignment(Pos.CENTER);

        // Organisation globale
        BorderPane root = new BorderPane();
        root.setCenter(centerLayout);
        root.setBottom(footer);

        // Création de la scène
        Scene scene = new Scene(root, 800, 600);
        stage.setScene(scene);
        stage.setTitle("Sélection des joueurs");
        stage.show();
    }

    /**
     * Crée un bouton pour le nombre de joueurs, avec une image et du texte.
     *
     * @param numPlayers Le nombre de joueurs.
     * @return Le bouton configuré.
     */
    private Button createPlayerButton(int numPlayers) {
        // Charger l'image
        Image playerImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/" + numPlayers + "_player.png")));
        ImageView imageView = new ImageView(playerImage);
        imageView.setFitWidth(140);
        imageView.setFitHeight(100);

        // Créer le bouton
        Button button = new Button(numPlayers + " Joueur" + (numPlayers > 1 ? "s" : ""), imageView);
        button.setStyle("-fx-font-size: 14px; -fx-background-color: #D3D3D3; -fx-text-fill: black; -fx-cursor: hand");
        button.setContentDisplay(javafx.scene.control.ContentDisplay.TOP); // Image au-dessus du texte

        return button;
    }

    public List<Button> getPlayerButtons() {
        return playerButtons;
    }
}
