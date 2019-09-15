
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.*;
import javafx.scene.control.*;

public class Erreur {

    public static void noUpgradeAvailable(String title, String message) {
        Stage window = new Stage();
        //la ligne 9 bloque l'interraction avec les fenetres ouvertes en dessous de celle-ci jusqu'à ce qu'on la ferme
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);


        Label texte = new Label(message);
        Button closeButton = new Button("CLOSE");
        closeButton.setOnAction(n -> {
            window.close();
        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll(texte, closeButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.show();
    }

    public static void fondsInsuffisants() {

        Stage window = new Stage();
        //la ligne 9 bloque l'interraction avec les fenetres ouvertes en dessous de celle-ci jusqu'à ce qu'on la ferme
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("ERREUR");

        Label texte = new Label("Vous n'avez pas assez de biscuits");
        Button closebutton = new Button("Close");
        closebutton.setOnAction(n -> window.close());
        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(texte,closebutton);
        Scene scene = new Scene(layout, 300, 80);
        window.setScene(scene);
        window.show();

    }

}

