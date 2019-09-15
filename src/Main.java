import javafx.application.Application;
import javafx.scene.Group;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Main extends Application {

    int biscuits = 5000;
    int emma = 1;
    int nbUp2, nbUp3, nbUp4, nbUp5;
    double facteurPrix = 0.1;

    public void generateur() {

        Timer time = new Timer();

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                biscuits += emma;
            }
        };
        time.scheduleAtFixedRate(task, 0, 5000 - nbUp3 * 500);
    }

    public static void main(String[] args) {
        launch(args);
    }


    public void start(Stage primaryStage) {

        Stage window = primaryStage;
        Scene mainScene;

        //compteur de biscuits
        Label compteurCookie = new Label();
        compteurCookie.setText("Vous avez " + biscuits + " biscuits!");
        Button biscuit = new Button("!!Cookie!!");
        biscuit.setOnAction(n -> {
            biscuits += emma;
            compteurCookie.setText("Vous avez " + biscuits + " biscuits!");
        });

        //boutons ameliorations
        Group ameliorations = new Group();
        int index[] = {1};
        Button up1 = new Button("Débloquer de nouvelles améliorations");
        Button up2 = new Button("Bouton biscuit plus généreux");
        Button up3 = new Button("Générer des biscuits automatiquement");
        Button up4 = new Button("Augmenter la vitesse de génération");
        Button up5 = new Button("Amélioration moins chères");
        Button quitter = new Button("Retour");
        List<Button> liste = Arrays.asList(up1, up2, up3, up4, up5);
        ameliorations.getChildren().add(up1);
        ameliorations.getChildren().add(quitter);


        //fonctions des upgrades

        up1.setOnAction(n -> {
            int prix = (10 * (index[0]));
            if (Fonctions_Autres.confirmerUpgrade("Vous êtes sur le point de deverouiller une amélioration au cout de " + prix) && checkArgent(prix)) {
                acheter(prix);
                if (index[0] <= 4) {
                    ameliorations.getChildren().add(liste.get(index[0]));
                    index[0] = index[0] + 1;

                } else {
                    Erreur.noUpgradeAvailable("ERREUR", "Il n'y a plus d'améliorations disponibles.");
                }
            }
        });

        up2.setOnAction(n -> {
            int prix = ((20 * (nbUp2)) + 20);
            if (Fonctions_Autres.confirmerUpgrade("Vous êtes sur le point d'améliorer la générosité du bouton de 1 pour " + prix + " biscuits") && !checkIfLevelMax(nbUp2, 4) && checkArgent(prix)) {
                acheter(prix);
                emma = emma + 1;
                nbUp2 += 1;
            }
        });

        up3.setOnAction(n -> {
            int prix = 300;
            if (Fonctions_Autres.confirmerUpgrade("Vous êtes sur le point d'acheter un générateur de biscuits automatique pour " + prix + " biscuits") && !checkIfLevelMax(nbUp3, 1) && checkArgent(prix)) {
                acheter(prix);
                generateur();
                nbUp3 += 1;
            }
        });

        up4.setOnAction(n -> {
            int prix = 20 * nbUp4 + 100;
            if (Fonctions_Autres.confirmerUpgrade("Vous êtes sur le point d'augmenter la rapidité du générateur de biscuits de 25% pour " + prix + " biscuits") && !checkIfLevelMax(nbUp4, 4) && checkArgent(prix)) {
                nbUp4 += 1;
                acheter(prix);
                nbUp4 += nbUp4 * 0.75;
            }
        });

        up5.setOnAction(n -> {
            int prix = 20 * nbUp5 + 400;
            if (Fonctions_Autres.confirmerUpgrade("Vous êtes sur le point d'augmenter la rapidité du générateur de biscuits de " + (int) (facteurPrix * 100) + "  % pour " + prix + " biscuits") && !checkIfLevelMax(nbUp5, 4) && checkArgent(prix)) {
                nbUp5 += 1;
                acheter(prix);
                facteurPrix += 0.1;
            }
        });

        //affichage améliorations
        Scene upgradeScene = new Scene(ameliorations, 400, 200);
        double largeur = upgradeScene.getWidth();
        double hauteur = 10;
        for (Button bouton : liste) {
            bouton.setTranslateX(largeur / 6);
            bouton.setTranslateY(hauteur + 30);
            hauteur = bouton.getTranslateY();
        }
        Button upgrades = new Button("Consulter les améliorations possibles");
        upgrades.setOnAction(n -> window.setScene(upgradeScene));

        //affichage principal
        window.setTitle("My Cookie Clicker Lite");
        VBox layout = new VBox(20);
        layout.getChildren().addAll(compteurCookie, biscuit, upgrades);
        layout.setAlignment(Pos.CENTER);
        mainScene = new Scene(layout, 400, 150);
        window.setScene(mainScene);
        window.show();
        quitter.setOnAction(n -> window.setScene(mainScene));

    }

    public Boolean checkArgent(int prix) {
        Boolean accepte = false;
        if (biscuits - prix >= 0) {
            accepte = true;
        } else Erreur.fondsInsuffisants();
        return accepte;
    }

    public void acheter(int prix) {
        biscuits = biscuits - prix;
    }

    public boolean checkIfLevelMax(int niv, int nivMax) {
        boolean max = false;
        System.out.println(niv);
        if (niv >= nivMax) {
            max = true;
        }
        else Erreur.noUpgradeAvailable("ERREUR", "Niveau maximum atteint.");
        return max;
    }


}
