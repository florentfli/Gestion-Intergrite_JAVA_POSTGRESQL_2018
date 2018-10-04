package Vue;

import Controleur.Controleur;
import Modele.Lieu;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class VueAjouterLieu extends Scene {

    protected TextField valeurVille;
    protected TextField valeurTaille;
    protected TextField valeurhabitants;
    protected CheckBox valeurEstCapital;

    private Controleur controleur;

    private Button actionEnregistrerLieu;

    public VueAjouterLieu()  {
        super(new VBox(), 400, 400);
        VBox panneau = (VBox) this.getRoot();
        GridPane grilleLieu = new GridPane();

        controleur = Controleur.getInstance();

        valeurVille = new TextField();
        grilleLieu.add(new Label("Nom : "), 0, 0);
        grilleLieu.add(valeurVille, 1, 0);

        valeurTaille = new TextField();
        grilleLieu.add(new Label("Taille : (en  km2)"), 0, 1);
        grilleLieu.add(valeurTaille, 1, 1);

        valeurhabitants = new TextField();
        grilleLieu.add(new Label("Habitants : "), 0, 2);
        grilleLieu.add(valeurhabitants, 1, 2);

        valeurEstCapital = new CheckBox();
        grilleLieu.add(new Label("Est capital : "), 0, 3);
        grilleLieu.add(valeurEstCapital, 1, 3);

        actionEnregistrerLieu = new Button("Enregistrer");
        actionEnregistrerLieu.setOnAction(new EventHandler<javafx.event.ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Lieu lieuAjouter = new Lieu(0,valeurVille.getText().toString(),Integer.parseInt(valeurTaille.getText()),Integer.parseInt(valeurhabitants.getText()), valeurEstCapital.isSelected());
                System.out.println("ajout de : "+lieuAjouter.getVille());
                controleur.notifierActionAjouterLieu(lieuAjouter);
            }
        });

        panneau.getChildren().add(new Label("Ajouter un lieu"));
        panneau.getChildren().add(grilleLieu);
        panneau.getChildren().add(actionEnregistrerLieu);
    }

    public void netoyerGrilleChamp(){
        valeurEstCapital.setText("");
        valeurhabitants.setText("");
        valeurTaille.setText("");
        valeurVille.setText("");
    }

}
