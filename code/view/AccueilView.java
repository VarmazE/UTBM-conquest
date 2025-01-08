package view;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Objects;

public class AccueilView {

    private Button playButton;

    public AccueilView(Stage stage) {
        // Chargement du logo
        Image logo = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/logo.png")));
        ImageView logoImageView = new ImageView(logo);
        logoImageView.setFitWidth(300);
        logoImageView.setPreserveRatio(true);

        playButton = new Button("> Jouer une partie");
        playButton.setStyle("-fx-font-size: 28px; -fx-border-width: 0; -fx-background-color: transparent; -fx-text-fill: black; -fx-cursor: hand;");
        playButton.setFocusTraversable(false);

        Text rulesText = new Text("> Explications des règles");
        rulesText.setStyle("-fx-font-size: 24px; -fx-fill: grey;");

        Text footerText = new Text("Ce jeu est proposé par l'équipe 4Real dans le cadre d'un projet à l'UTBM");
        footerText.setStyle("-fx-font-size: 12px; -fx-fill: white;");

        StackPane footer = new StackPane(footerText);
        footer.setStyle("-fx-background-color: #007ACC;"); // Bleu UTBM
        footer.setPrefHeight(40);

        VBox centerLayout = new VBox(20, logoImageView, playButton, rulesText);
        centerLayout.setAlignment(Pos.CENTER);
        centerLayout.setSpacing(20);

        BorderPane root = new BorderPane();
        root.setCenter(centerLayout);
        root.setBottom(footer);

        Scene scene = new Scene(root, 800, 550);
        stage.setScene(scene);
        stage.setTitle("UTBM Conquest");
        stage.show();
    }

    public Button getPlayButton() {
        return playButton;
    }
}
