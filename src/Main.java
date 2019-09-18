import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.image.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.util.Duration;


import java.awt.event.ActionEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Main extends Application {

    int biscuits = 1000;
    int emma = 1;
    int nbUp3,nbUp2, nbUp4, nbUp5;
    double facteurPrix = 0.0;
    FileInputStream input;
    Label compteurCookie;
    int period;
    Timeline timeline;
    KeyFrame k1,k2;

    public void generateur(int _period) {

        k1 = new KeyFrame(Duration.millis(5*_period), ActionEvent -> biscuits+=emma);
        timeline  = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.setAutoReverse(true);
        timeline.getKeyFrames().addAll(k1);
        timeline.play();
    }

    public void changerPeriode(int _period){

        k2 = new KeyFrame(Duration.millis(3*_period), ActionEvent -> biscuits+=emma);
        timeline.stop();
        timeline.getKeyFrames().removeAll(k1);
        timeline.getKeyFrames().addAll(k2);
        timeline.play();

    }

    public static void main(String[] args) {
        launch(args);
    }


    public void start(Stage primaryStage) {


        Stage window = primaryStage;
        Scene mainScene;


        //import l'image de biscuit
try {
    input = new FileInputStream("C://Users/Simon/image/téléchargement.jfif");

}
catch (FileNotFoundException ex){
    System.out.println(ex.toString());
}
        Image image = new Image(input);
        ImageView iv = new ImageView();
        iv.setFitHeight(80);
        iv.setPreserveRatio(true);
        iv.setImage(image);




        //compteur de biscuits
        compteurCookie = new Label();
        compteurCookie.setText("Vous avez " + biscuits + " biscuits!");
        compteurCookie.setTextFill(Color.DARKBLUE);
        Button biscuit = new Button("!!Cookie!!",iv);
        biscuit.setMinSize(50,50);
        biscuit.setMaxSize(100,100);

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
            double prix = (20 * index[0])*(1-facteurPrix);
            if (Fonctions_Autres.confirmerUpgrade("Vous êtes sur le point de deverouiller une amélioration au cout de " + (int)prix) && checkArgent(prix)) {
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
            double prix = (((20 * (nbUp2)) + 20)*(1-facteurPrix));
            if (Fonctions_Autres.confirmerUpgrade("Vous êtes sur le point d'améliorer la générosité du bouton de 1 pour " + (int)prix + " biscuits") && !checkIfLevelMax(nbUp2, 4) && checkArgent(prix)) {
                acheter(prix);
                emma = emma + 1;
                nbUp2 += 1;
            }
        });

        up3.setOnAction(n -> {
            double prix = 300*(1-facteurPrix);
            if (Fonctions_Autres.confirmerUpgrade("Vous êtes sur le point d'acheter un générateur de biscuits automatique pour " + (int)prix + " biscuits") && !checkIfLevelMax(nbUp3, 1) && checkArgent(prix)) {
                acheter(prix);
                generateur(1000);


                nbUp3 += 1;
            }
        });

        up4.setOnAction(n -> {
            double prix = (20 * nbUp4 + 100)*(1-facteurPrix);
            if (Fonctions_Autres.confirmerUpgrade("Vous êtes sur le point d'augmenter la rapidité du générateur de biscuits de 25% pour " + (int)prix + " biscuits") && !checkIfLevelMax(nbUp4, 5) && checkArgent(prix)) {
                nbUp4 += 1;
                acheter(prix);
                changerPeriode(1300- nbUp4*250);


            }
        });

        up5.setOnAction(n -> {
            int prix = 20 * nbUp5 + 400;
            if (Fonctions_Autres.confirmerUpgrade("Vous êtes sur le point de diminuer le cout des améliorations de " + (int) ((facteurPrix+0.1) * 100) + "  % pour " + prix + " biscuits") && !checkIfLevelMax(nbUp5, 5) && checkArgent(prix)) {
                nbUp5 += 1;
                acheter(prix);
                facteurPrix += 0.1;

            }
        });

        //affichage améliorations
        Scene upgradeScene = new Scene(ameliorations, 450, 300);
        double largeur = upgradeScene.getWidth();
        double hauteur = 10;
        for (Button bouton : liste) {
            bouton.setTranslateX(largeur / 6);
            bouton.setTranslateY(hauteur + 30);
            hauteur = bouton.getTranslateY();
        }
        Button upgrades = new Button("Consulter les améliorations possibles");
        upgrades.setOnAction(n -> window.setScene(upgradeScene));


        KeyFrame k = new KeyFrame(Duration.millis(10), ActionEvent -> compteurCookie.setText("Vous avez " + biscuits + " biscuits!"));
        Timeline timeline  = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.setAutoReverse(true);
        timeline.getKeyFrames().addAll(k);
        timeline.play();

        //affichage principal
        window.setTitle("My Cookie Clicker Lite");
        VBox layout = new VBox(20);
        layout.getChildren().addAll(compteurCookie, biscuit, upgrades);
        layout.setAlignment(Pos.CENTER);
        mainScene = new Scene(layout, 300, 300);

        window.setScene(mainScene);
        window.show();
        quitter.setOnAction(n -> window.setScene(mainScene));

    }

    public Boolean checkArgent(double prix) {
        Boolean accepte = false;
        if (biscuits - (int)prix >= 0) {
            accepte = true;
        } else Erreur.fondsInsuffisants();
        return accepte;
    }

    public void acheter(double prix) {
        biscuits = biscuits - (int)prix;
    }

    public boolean checkIfLevelMax(int niv, int nivMax) {
        boolean max = false;
        if (niv >= nivMax) {
            max = true;
            Erreur.noUpgradeAvailable("ERREUR", "Niveau maximum atteint.");
        }
        return max;
    }


}
