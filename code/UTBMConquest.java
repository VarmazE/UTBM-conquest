import controller.AccueilController;
import javafx.application.Application;
import javafx.stage.Stage;
import view.AccueilView;


import java.util.ArrayList;

public class UTBMConquest extends Application {
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("UTBM Conquest");

        primaryStage.setResizable(false);
        primaryStage.setOnCloseRequest(event -> {
            System.exit(0);
        });

        AccueilView accueilView = new AccueilView(primaryStage);
        new AccueilController(primaryStage, accueilView);
    }

    public static void main(String[] args) {
        launch(args);
    }

}
