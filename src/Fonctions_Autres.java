import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.scene.control.*;


public class Fonctions_Autres {

    public static boolean confirmerUpgrade(String message){
        boolean reponse[]  = new boolean[1];
        Stage window = new Stage();
        //la ligne 9 bloque l'interraction avec les fenetres ouvertes en dessous de celle-ci jusqu'à ce qu'on la ferme
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("AMÉLIORATION");
        window.setMinWidth(250);

        Label texte = new Label(message);
        Button closeButton = new Button("Close");
        Button confirmer= new Button("OUI");
        Button refuser= new Button("NON");


        closeButton.setOnAction(n-> {
            reponse[0] = false;
            window.close();
        });


        confirmer.setOnAction(n->{
            reponse[0] = true;
            window.close();
        } );


        refuser.setOnAction(n-> {
            reponse[0]= false;
            window.close();
        });


        VBox layout = new VBox(10);
        layout.getChildren().addAll(closeButton,texte,confirmer,refuser);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);

        window.setScene(scene);
        window.showAndWait();

        return reponse[0];
    }

}



