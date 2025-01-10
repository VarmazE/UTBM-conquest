# UTBM-conquest

## Description
**UTBM-conquest** est un jeu développé en Java avec JavaFX. Ce projet utilise une architecture MVC avec les dossiers suivants :
- `model/`
- `controller/`
- `view/`

La classe principale du projet est `UTBMConquest.java`.

## Prérequis
Pour exécuter le jeu via le fichier JAR, assurez-vous d'avoir :
1. **Java (à partir de 19) ou une version compatible** installée sur votre système.
   - Vous pouvez vérifier la version avec la commande :
     ```bash
     java -version
     ```
2. **JavaFX SDK** :
   - Téléchargez la version correcte de JavaFX (à partir de 21, 21.0.5 de préférence) à partir de [https://openjfx.io/](https://openjfx.io/).
   - Décompressez l'archive téléchargée et notez le chemin vers le dossier `lib`.

## Lancer le jeu
Pour lancer le jeu, procédez comme suit :

1. Ouvrez un terminal et naviguez dans le dossier contenant `UTBM-conquest-main.jar`.

2. Exécutez la commande suivante en remplaçant `<chemin-vers-javafx-lib>` par le chemin vers le dossier `lib` de votre SDK JavaFX :
   ```bash
   java --module-path <chemin-vers-javafx-lib> --add-modules javafx.controls,javafx.fxml -jar UTBM-conquest-main.jar

