package view;

import controller.QuartierLigneBatimentEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.*;

import java.util.*;

public class GameBoardView {

    private Pane zonePane; // Pane pour afficher les zones circulaires
    private HBox buttonContainer; // Contient les boutons d'actions
    private VBox grilleContainer;
    private Text currentPlayerLabel, ressourceLabel;
    private Button incrementButton, decrementButton, changeColorButton, gainResourceButton, buildButton, nextPlayer;
    private HashMap<Arc, Zone> zoneMap; // Map pour relier les arcs aux zones
    private Text tourLabel;
    private EventHandler<QuartierLigneBatimentEvent> onBatimentClicked;
    private HBox ligneSelectionnee = null;
    private VBox bonhommesContainer;
    private VBox resourcesContainer; // Conteneur pour les ressources
    private Stage stage;




    public GameBoardView(Stage s) {
        this.stage = s;
        // Label pour le joueur actuel et le tour
        currentPlayerLabel = new Text("Joueur actuel : ");
        currentPlayerLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        tourLabel = new Text("Tour : 1");
        tourLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        //VBox topContainer = new VBox(20, currentPlayerLabel, tourLabel);
        //topContainer.setAlignment(Pos.CENTER);

        // Conteneur pour afficher les bonhommes
        bonhommesContainer = new VBox(10);
        bonhommesContainer.setAlignment(Pos.TOP_LEFT);
        bonhommesContainer.setPadding(new Insets(10));

        // Pane pour dessiner les zones (plateau circulaire)
        zonePane = new Pane();
        zonePane.setPrefSize(400, 400); // Taille du plateau

        VBox zoneContainer = new VBox(zonePane); // Conteneur pour le plateau
        VBox.setMargin(zonePane, new Insets(0, 0, 0, 0)); // Marge de 50px en haut

        // Conteneur pour les quartiers
        grilleContainer = new VBox(10);
        grilleContainer.setAlignment(Pos.CENTER);
        grilleContainer.setStyle("-fx-background-color: white;"); // Supprime les contours noirs
        grilleContainer.setPrefWidth(400); // Largeur fixe pour la grilleF

        HBox mainContainer = new HBox(300, zoneContainer, grilleContainer); // Ajoute un espace fixe de 50px
        mainContainer.setAlignment(Pos.CENTER); // Centre les éléments dans l'HBox
        mainContainer.setPadding(new Insets(10)); // Ajoute un padding uniforme autour du contenu
        //mainContainer.setPrefHeight(400); // Assurez-vous que la hauteur totale est bien définie
        // Conteneur des ressources
        resourcesContainer = new VBox(10);
        resourcesContainer.setAlignment(Pos.CENTER);
        resourcesContainer.setPadding(new Insets(10));

        // Conteneur des boutons d'actions
        incrementButton = new Button("Incrémenter dé");
        decrementButton = new Button("Décrémenter dé");
        changeColorButton = new Button("Changer couleur jeton");
        gainResourceButton = new Button("Gagner ressource");
        buildButton = new Button("Construire bâtiment");
        nextPlayer = new Button("Joueur Suivant");

        buttonContainer = new HBox(10, incrementButton, decrementButton, changeColorButton, gainResourceButton, buildButton);
        buttonContainer.setAlignment(Pos.CENTER);
        buttonContainer.setPadding(new Insets(10));

        VBox topBar = new VBox(10, currentPlayerLabel, tourLabel, bonhommesContainer);
        topBar.setAlignment(Pos.CENTER_LEFT); // Alignement à gauche
        topBar.setPadding(new Insets(10));

        // BorderPane principal
        BorderPane root = new BorderPane();
        root.setTop(topBar);       // Tour et joueur en haut
        root.setCenter(mainContainer);   // Plateau et grille au centre
        HBox bottomButton = new HBox(buttonContainer, nextPlayer);
        bottomButton.setAlignment(Pos.CENTER);

        VBox bottomContainer = new VBox(10, resourcesContainer, bottomButton); // Conteneur combiné des ressources et boutons
        bottomContainer.setAlignment(Pos.CENTER);

        root.setBottom(bottomContainer); // Boutons en bas


        Scene scene = new Scene(root, 1100, 850); // Taille ajustée pour inclure tout
        stage.setScene(scene);
        stage.setResizable(true);
        stage.setTitle("Plateau de Jeu");
        stage.show();

        zoneMap = new HashMap<>();
    }


    /**
     * Met à jour les zones circulaires et retourne la map des arcs associés à leurs zones.
     *
     * @param zones       Toutes les zones du plateau.
     * @param activeZones Liste des zones actives.
     * @param listDe      Liste des dés associés aux zones actives.
     * @return Map associant chaque arc cliquable à sa zone.
     */
    public HashMap<Arc, Zone> updateZones(List<Zone> zones, ArrayList<Zone> activeZones, List<De> listDe) {
        zonePane.getChildren().clear(); // Réinitialise les zones
        zoneMap.clear(); // Réinitialise la map

        double centerX = 300, centerY = 220; // Centre du plateau
        double radius = 170;
        double startAngle = 290;
        double angleStep = -(360.0 / zones.size());

        for (int i = 0; i < zones.size(); i++) {
            Zone zone = zones.get(i);

            // Créer un secteur pour chaque zone
            Arc arc = new Arc(centerX, centerY, radius, radius, startAngle, angleStep);
            arc.setType(ArcType.ROUND);
            arc.setStroke(Color.BLACK); // Contour des secteurs
            arc.setStrokeWidth(1.5);

            // Couleur selon si la zone est active ou non
            if (i == 0) {
                arc.setFill(Color.GRAY); // Première zone = zone morte
            } else if (activeZones.contains(zone)) {
                arc.setFill(Color.BEIGE);
                arc.setStyle("-fx-cursor: hand");
                zoneMap.put(arc, zone); // Associe cet arc à sa zone
            } else {
                arc.setFill(Color.LIGHTGRAY); // Zones inactives
            }

            zonePane.getChildren().add(arc);

            // Ajouter un cercle pour le jeton
            double jetonX = centerX + (radius + 10) * Math.cos(Math.toRadians(startAngle + angleStep / 2));
            double jetonY = centerY - (radius + 10) * Math.sin(Math.toRadians(startAngle + angleStep / 2));

            Circle jeton = new Circle(jetonX, jetonY, 45); // Rayon du jeton = 45
            if (activeZones.contains(zone)) {
                jeton.setFill(getColorFromZone(zone.getJeton().getCouleur())); // Couleur du jeton actif
                System.out.println(getColorFromZone(zone.getJeton().getCouleur()));
                zonePane.getChildren().add(jeton);

                // Ajouter une image pour le jeton
                int activeZoneIndex = activeZones.indexOf(zone);
                De de = listDe.get(activeZoneIndex);
                String diceImageName = (de instanceof DeNoir ? "b_" : "w_") + de.getValeurDe() + ".png";

                ImageView jetonImage = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/dices/" + diceImageName))));
                jetonImage.setFitWidth(40);
                jetonImage.setFitHeight(40);
                jetonImage.setLayoutX(jetonX - 20);
                jetonImage.setLayoutY(jetonY - 20);
                zonePane.getChildren().add(jetonImage);

                // Ajouter une autre image dans la zone
                double img1X = centerX + (radius - 100) * Math.cos(Math.toRadians(startAngle + angleStep / 2)) - 15;
                double img1Y = centerY - (radius - 100) * Math.sin(Math.toRadians(startAngle + angleStep / 2)) - 15;

                ImageView img1 = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream(getRightMonnaie(zone.getCout())))));
                img1.setFitWidth(30);
                img1.setFitHeight(30);
                img1.setLayoutX(img1X);
                img1.setLayoutY(img1Y);

                zonePane.getChildren().add(img1);
            } else {
                jeton.setFill(Color.DARKGRAY); // Couleur du jeton inactif
                zonePane.getChildren().add(jeton); // Ajouter le jeton inactif uniquement
            }

            startAngle += angleStep;
        }

        return zoneMap; // Retourne la map pour que le contrôleur puisse gérer les événements
    }

    /**
     * Retourne une couleur pour une zone donnée.
     *
     * @param couleur La couleur de la zone.
     * @return Une couleur JavaFX.
     */
    public Color getColorFromZone(String couleur) {
        return switch (couleur) {
            case Constante.DIRECTION -> Color.RED;
            case Constante.ETUDIANT -> Color.YELLOW;
            case Constante.PROFESSEUR -> Color.GREEN;
            case "Gris" -> Color.GRAY;
            default -> Color.LIGHTGRAY;
        };
    }

    public void showPaymentDialog(int zoneName, Cout cout, Runnable onCommunication, Runnable onEcts, Runnable onSavoir) {
        Stage paymentStage = new Stage();
        paymentStage.setTitle("Mode de Paiement");

        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);

        String type = "";
        Text paymentText = new Text("Choisissez une ressource pour payer la zone : " + zoneName);
        for (Ressources t : cout.getListRessources()) {
            if (cout.getListRessources().indexOf(t) == 0) {
                type += t.getType();
            } else {
                type += ", " + t.getType();
            }
        }


        Text costText = new Text("Coût : " + cout.getMontant() + "( " + type + ")");

        Button communicationButton = new Button("Payer avec Communication");
        Button ectsButton = new Button("Payer avec ECTS");
        Button savoirButton = new Button("Payer avec Savoir");

        communicationButton.setOnAction(event -> {
            onCommunication.run();
            paymentStage.close();
        });

        ectsButton.setOnAction(event -> {
            onEcts.run();
            paymentStage.close();
        });

        savoirButton.setOnAction(event -> {
            onSavoir.run();
            paymentStage.close();
        });

        layout.getChildren().addAll(paymentText, costText, communicationButton, ectsButton, savoirButton);

        Scene scene = new Scene(layout, 400, 200);
        paymentStage.setScene(scene);
        paymentStage.show();
    }

    public void showErrorDialog(String message) {
        Stage errorDialog = new Stage();
        errorDialog.setTitle("Mode de Paiement");

        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);


        Text errorText = new Text(message);


        Button okButton = new Button("Ok");


        okButton.setOnAction(event -> {
            errorDialog.close();
        });

        layout.getChildren().addAll(errorText, okButton);

        Scene scene = new Scene(layout, 400, 200);
        errorDialog.setScene(scene);
        errorDialog.show();
    }

    public void setRessourceLabel(ListeRessources[] lr) {
        String text = "";
        for (ListeRessources l : lr) {
            text += l.getType() + " : " + l.calculerRessource() + "\n";
        }
        this.ressourceLabel.setText(text);
    }

    public void setOnBatimentClicked(EventHandler<QuartierLigneBatimentEvent> handler) {
        this.onBatimentClicked = handler;
    }

    /*public void afficherGrille(Quartier[] quartiers, Quartier quartierAccessible) {
        grilleContainer.getChildren().clear(); // Réinitialise l'affichage

        for (Quartier quartier : quartiers) {
            VBox quartierBox = new VBox(10);
            quartierBox.setAlignment(Pos.CENTER);
            quartierBox.setPadding(new Insets(10));

            // Couleur de fond basée sur la couleur du quartier
            quartierBox.setStyle("-fx-background-color: " + getBackgroundColor(quartier.getCouleurQuartier()) + ";");

            // Vérifiez si le quartier est accessible
            boolean isAccessible = quartier.equals(quartierAccessible);
            if (isAccessible) {
                quartierBox.setStyle(quartierBox.getStyle() + "-fx-cursor: hand;"); // Curseur pointer
            } else {
                quartierBox.setDisable(true); // Désactiver
                quartierBox.setStyle(quartierBox.getStyle() + "-fx-opacity: 0.5;"); // Griser
            }

            Text quartierTitle = new Text("Quartier : " + quartier.getCouleurQuartier());
            quartierBox.getChildren().add(quartierTitle);

            // Ajouter les deux lignes (Prestige et Fonction)
            for (int i = 0; i < 2; i++) {
                Ligne ligne = quartier.choisirLigne(i == 0 ? "prestige" : "fonction");
                Text ligneTitle = new Text(i == 0 ? "Prestige" : "Fonction");

                HBox ligneBox = new HBox(5);
                ligneBox.setAlignment(Pos.CENTER);
                ligneBox.setPadding(new Insets(5));
                ligneBox.setStyle("-fx-border-color: black; -fx-border-width: 1px;");

                // Ajouter la case grise par défaut
                StackPane defaultBatimentPane = new StackPane();
                defaultBatimentPane.setPrefSize(50, 50);

                ImageView defaultBatimentImage = new ImageView();
                if (i == 0) {
                    // Ligne Prestige - Image dépendante du quartier
                    String prestigeImagePath = "/img/batiment/skyline.png";
                    defaultBatimentImage.setImage(new Image(getClass().getResourceAsStream(prestigeImagePath)));
                } else {
                    // Ligne Fonction - Image fixe
                    String fonctionImagePath = "/img/batiment/home.png";
                    defaultBatimentImage.setImage(new Image(getClass().getResourceAsStream(fonctionImagePath)));
                }

                defaultBatimentImage.setFitWidth(20);
                defaultBatimentImage.setFitHeight(20);
                defaultBatimentPane.getChildren().add(defaultBatimentImage);

                // Ajouter la case par défaut au début de la ligne
                ligneBox.getChildren().add(defaultBatimentPane);

                // Ajouter les bâtiments existants
                for (Batiment batiment : ligne.getBatiments()) {
                    StackPane batimentPane = new StackPane();
                    batimentPane.setPrefSize(50, 50);
                    batimentPane.setStyle("-fx-border-color: black; -fx-border-width: 1px;");

                    if (batiment.isConstruit()) {
                        ImageView batimentImage = new ImageView(new Image(getClass().getResourceAsStream("/img/batiment/home.png")));
                        batimentImage.setFitWidth(20);
                        batimentImage.setFitHeight(20);
                        batimentPane.getChildren().add(batimentImage);
                    } else if (batiment.isDetruit()) {
                        ImageView detruitImage = new ImageView(new Image(getClass().getResourceAsStream("/img/batiment/cross.png")));
                        detruitImage.setFitWidth(20);
                        detruitImage.setFitHeight(20);
                        batimentPane.getChildren().add(detruitImage);
                    } else {
                        batimentPane.setStyle("-fx-background-color: lightgray;");
                    }

                    ligneBox.getChildren().add(batimentPane);
                }

                quartierBox.getChildren().addAll(ligneTitle, ligneBox);
            }

            grilleContainer.getChildren().add(quartierBox);
        }
    }*/

    public void afficherGrille(Quartier[] quartiers, Quartier quartierAccessible) {
        grilleContainer.getChildren().clear(); // Réinitialise l'affichage

        for (Quartier quartier : quartiers) {
            VBox quartierBox = new VBox(5);
            quartierBox.setAlignment(Pos.CENTER);
            quartierBox.setPadding(new Insets(5));

            // Couleur de fond basée sur la couleur du quartier
            quartierBox.setStyle("-fx-background-color: " + getBackgroundColor(quartier.getCouleurQuartier()) + ";");

            // Vérifiez si le quartier est accessible
            boolean isAccessible = quartier.equals(quartierAccessible);
            if (isAccessible) {
                quartierBox.setStyle(quartierBox.getStyle() + "-fx-cursor: hand;"); // Curseur pointer
            } else {
                quartierBox.setDisable(true); // Désactiver
                quartierBox.setStyle(quartierBox.getStyle() + "-fx-opacity: 0.5;"); // Griser
            }

            Text quartierTitle = new Text(quartier.getCouleurQuartier());
            quartierBox.getChildren().add(quartierTitle);

            // Ajouter les deux lignes (Prestige et Fonction)
            for (int i = 0; i < 2; i++) {
                Ligne ligne = quartier.choisirLigne(i == 0 ? "prestige" : "fonction");

                HBox ligneBox = new HBox(5);
                ligneBox.setAlignment(Pos.CENTER_LEFT);
                ligneBox.setPadding(new Insets(5));
                ligneBox.setStyle("-fx-border-color: black; -fx-border-width: 1px;");

                // Ajouter la case grise par défaut
                StackPane defaultBatimentPane = new StackPane();
                defaultBatimentPane.setPrefSize(40, 40);

                ImageView defaultBatimentImage = new ImageView();
                if (i == 0) {
                    // Ligne Prestige - Image dépendante du quartier
                    String bat = "/img/batiment";
                    switch (quartier.getCouleurQuartier()) {
                        case Constante.DIRECTION -> bat += "/penchee/office.png";
                        case Constante.ETUDIANT -> bat += "/skyline.png";
                        case Constante.PROFESSEUR -> bat += "/school.png";
                        default -> bat += "/home.png";
                    }
                    defaultBatimentImage.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream(bat))));
                } else {
                    // Ligne Fonction - Image fixe
                    String fonctionImagePath = "/img/batiment/home.png";
                    defaultBatimentImage.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream(fonctionImagePath))));
                }

                defaultBatimentImage.setFitWidth(30);
                defaultBatimentImage.setFitHeight(30);
                defaultBatimentPane.getChildren().add(defaultBatimentImage);

                // Ajouter la case par défaut au début de la ligne
                ligneBox.getChildren().add(defaultBatimentPane);

                // Itération sur les bâtiments avec chemin d’image dynamique
                for (int j = 0; j < ligne.getBatiments().length; j++) {
                    Batiment batiment = ligne.getBatiments()[j];

                    // Définir le chemin d’image
                    String path;
                    if (i == 0) { // Ligne Prestige
                        path = getRightPathBatiment(batiment, j);
                    } else { // Ligne Fonction
                        path = batiment.isConstruit() ? "/img/batiment/home.png"
                                : batiment.isDetruit() ? "/img/batiment/croix.png"
                                : "";
                    }

                    // Créer le panneau du bâtiment
                    StackPane batimentPane = new StackPane();
                    batimentPane.setPrefSize(40, 40);
                    batimentPane.setStyle("-fx-border-color: black; -fx-border-width: 1px;");

                    try {
                        if (!path.equals("")) {
                            ImageView batimentImage = new ImageView(new Image(getClass().getResourceAsStream(path)));
                            batimentImage.setFitWidth(30);
                            batimentImage.setFitHeight(30);
                            batimentPane.getChildren().add(batimentImage);
                        }
                    } catch (Exception e) {
                        System.err.println("Erreur lors du chargement de l'image : " + path);
                    }

                    batimentPane.setOnMouseClicked(event -> {
                        if (onBatimentClicked != null) {
                            onBatimentClicked.handle(new QuartierLigneBatimentEvent(quartier, ligne, batiment));
                        }
                    });

                    // Ajouter le panneau du bâtiment à la ligne
                    ligneBox.getChildren().add(batimentPane);
                }

                // Ajouter un cercle avec le multiplicateur
                int multiplicateur = ligne.getPersonnageAssocie().getMultiplicateur();
                StackPane multiplicateurPane = new StackPane();
                multiplicateurPane.setPrefSize(40, 40);

                Circle circle = new Circle(20, Color.LIGHTGRAY);
                circle.setStroke(Color.BLACK);

                Text multiplicateurText = new Text(String.valueOf(multiplicateur));
                multiplicateurText.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

                multiplicateurPane.getChildren().addAll(circle, multiplicateurText);

                // Ajouter le cercle à droite
                ligneBox.getChildren().add(multiplicateurPane);

                // Gérer la sélection de la ligne
                ligneBox.setOnMouseClicked(event -> {
                    // Réinitialiser la ligne précédemment sélectionnée
                    if (ligneSelectionnee != null) {
                        ligneSelectionnee.setStyle("-fx-border-color: black; -fx-border-width: 1px;");
                    }

                    // Mettre à jour la ligne sélectionnée
                    ligneSelectionnee = ligneBox;
                    ligneSelectionnee.setStyle("-fx-border-color: darkgreen; -fx-border-width: 3px; -fx-border-radius: 5px;");
                });

                quartierBox.getChildren().addAll(ligneBox);
            }

            grilleContainer.getChildren().add(quartierBox);
        }
    }



    public String getRightPathBatiment(Batiment batiment, int index) {
        String path = "";
        if (batiment == null) {
            return path;
        }
        if (batiment.isDetruit()) {
            return "/img/batiment/croix.png";
        }
        if (batiment instanceof TourPenchee) {
            if(!batiment.isConstruit()) return "";
            return "/img/batiment/penchee/office.png";

        } else if (batiment instanceof BureauAssociation) {
            if(!batiment.isConstruit()) return "";
            return "/img/batiment/skyline.png";

        } else if (batiment instanceof SalleProfesseurs) {
            String chemin = "/img/batiment/salle/";
            switch (index) {
                case 0:
                    path = chemin + "fg";
                    break;
                case 1:
                    path = chemin + "gm";
                    break;
                case 2:
                    path = chemin + "ab";
                    break;
                case 3:
                    path = chemin + "fe";
                    break;
                case 4:
                    path = chemin + "gf";
                    break;
                case 5:
                    path = chemin + "bc";
                    break;
                default:
                    return "";
            }

        }
        if(batiment.isConstruit()){
            path += "_c.png";
        } else {
            path += "_pc.png";
        }
        return path;

    }


    private String getBackgroundColor(String couleurQuartier) {
        return switch (couleurQuartier) {
            case Constante.DIRECTION -> "lightcoral"; // Rouge clair
            case Constante.ETUDIANT -> "lightyellow"; // Jaune clair
            case Constante.PROFESSEUR -> "lightgreen"; // Vert clair
            default -> "white"; // Blanc par défaut
        };
    }



    public void setGrilleDisabled(boolean disabled) {
        grilleContainer.setDisable(disabled);
        if (disabled) {
            grilleContainer.setStyle("-fx-opacity: 0.5;"); // Visuellement montrer que c'est désactivé
        } else {
            grilleContainer.setStyle("-fx-opacity: 1;"); // Rendre la grille normale
        }
    }


    public void afficherBonhommes(ListeBonhommes[] listeBonhommes) {
        bonhommesContainer.getChildren().clear(); // Réinitialise l’affichage

        for (ListeBonhommes liste : listeBonhommes) {
            HBox groupeBox = new HBox(5);
            groupeBox.setAlignment(Pos.CENTER_LEFT);
            String path_img= "", couleur="";


            // Définir le chemin de l'image en fonction du type de bonhomme
            switch (liste.getType()) {
                case Constante.ROUGE -> {
                    path_img = "b_rouge.png";
                    couleur = "lightcoral";
                }
                case Constante.JAUNE ->{
                    path_img = "b_jaune.png";
                    couleur="lightyellow";
                }
                case Constante.BLANC ->{
                    path_img = "b_vert.png";
                    couleur = "lightgreen";
                }
                default -> {
                    System.err.println("Type de bonhomme inconnu : " + liste.getType());
                    continue; // Ignore ce groupe si le type est inconnu
                }
            }

            groupeBox.setStyle("-fx-background-color: %s;-fx-border-radius: 10".formatted(couleur));
            groupeBox.setMaxSize(20 * liste.getNombre(), 20);


            for (int i = 0; i < liste.getNombre(); i++) {
                try {
                    System.out.println("yalala");
                    System.out.println("/img/" + path_img);
                    ImageView bonhommeImage = new ImageView(new Image(
                            Objects.requireNonNull(getClass().getResourceAsStream("/img/" + path_img))
                    ));
                    bonhommeImage.setFitWidth(20); // Largeur ajustée
                    bonhommeImage.setFitHeight(20); // Hauteur ajustée
                    groupeBox.getChildren().add(bonhommeImage); // Ajoute l'image au groupe
                } catch (Exception e) {
                    System.err.println("Erreur lors du chargement de l'image : " + path_img);
                    e.printStackTrace();
                }
            }

            bonhommesContainer.getChildren().add(groupeBox); // Ajoute le groupe au conteneur principal
        }
    }

    public void afficherRessources(ListeRessources[] listeRessources) {
        resourcesContainer.getChildren().clear(); // Réinitialise l’affichage

        for (ListeRessources ressource : listeRessources) {
            HBox resourceBox = new HBox(5); // Conteneur pour une ressource
            resourceBox.setAlignment(Pos.CENTER_LEFT);

            // Définir le chemin de l'image pour la ressource
            String path_img;
            switch (ressource.getType()) {
                case Constante.COMMUNICATION -> path_img = "communication.png";
                case Constante.ECTS -> path_img = "ects.png";
                case Constante.SAVOIR -> path_img = "Savoir.png";
                default -> {
                    System.err.println("Type de ressource inconnu : " + ressource.getType());
                    continue;
                }
            }

            try {
                // Image de la ressource
                ImageView resourceImage = new ImageView(new Image(
                        Objects.requireNonNull(getClass().getResourceAsStream("/img/" + path_img))
                ));
                resourceImage.setFitWidth(30);
                resourceImage.setFitHeight(30);

                // Texte pour le nombre de ressources
                Text resourceText = new Text(" : " + ressource.calculerRessource());
                resourceText.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

                resourceBox.getChildren().addAll(resourceImage, resourceText);
                resourcesContainer.getChildren().add(resourceBox);
            } catch (Exception e) {
                System.err.println("Erreur lors du chargement de l'image : " + path_img);
                e.printStackTrace();
            }
        }
    }

    public String getRightMonnaie(Cout c){
        String path = "/img/monnaie/";
        if(c.getListRessources().size() > 1){
            return path + "1_ects_and_other.png";
        }
        int montant = c.getMontant();
        return switch (montant){
            case 0 -> path + "0_ects.png";
            case 1 -> path + "1_ects.png";
            case 2 -> path + "2_ects.png";
            default -> "";
        };

    }




    // Accesseurs pour les boutons
    public Button getIncrementButton() {
        return incrementButton;
    }

    public Button getDecrementButton() {
        return decrementButton;
    }

    public Button getChangeColorButton() {
        return changeColorButton;
    }

    public Button getGainResourceButton() {
        return gainResourceButton;
    }

    public Button getBuildButton() {
        return buildButton;
    }

    public Text getCurrentPlayerLabel() {
        return currentPlayerLabel;
    }

    public Button getNextPlayer() {
        return nextPlayer;
    }

    public HBox getButtonContainer() {
        return buttonContainer;
    }

    public HashMap<Arc, Zone> getZoneMap() {
        return zoneMap;
    }

    public void updateTourLabel(int tour) {
        tourLabel.setText("Tour: " + tour);
    }

    public void endGame(List<Joueur> liste) {
        // Sort the players by score in descending order
        liste.sort((j1, j2) -> Integer.compare(j2.getScore(), j1.getScore()));

        // Create a new stage for the end game screen
        stage.close();
        Stage endStage = new Stage();
        endStage.setTitle("Fin de la Partie");

        VBox mainContainer = new VBox(10);
        mainContainer.setAlignment(Pos.CENTER);
        mainContainer.setPadding(new Insets(20));

        Text title = new Text("Résultats Finaux");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        mainContainer.getChildren().add(title);

        for (int i = 0; i < liste.size(); i++) {
            Joueur joueur = liste.get(i);

            String rank = switch (i) {
                case 0 -> "Premier";
                case 1 -> "Deuxième";
                case 2 -> "Troisième";
                default -> (i + 1) + "ème";
            };

            HBox playerBox = new HBox(10);
            playerBox.setAlignment(Pos.CENTER_LEFT);

            Text playerText = new Text(rank + " : " + joueur.getNom() + " - Score : " + joueur.getScore());
            playerText.setStyle("-fx-font-size: 18px;");

            playerBox.getChildren().add(playerText);
            mainContainer.getChildren().add(playerBox);
        }

        Button closeButton = new Button("Fermer");
        closeButton.setOnAction(event -> endStage.close());
        mainContainer.getChildren().add(closeButton);

        Scene scene = new Scene(mainContainer, 400, 300);
        endStage.setScene(scene);
        endStage.show();
    }
}
