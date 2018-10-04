package Vue;

import Controleur.Controleur;
import Modele.Lieu;
import Modele.Poisson;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class VueAjouterPoisson extends Scene {

    protected TextField texteValeurNom;
    protected TextField texteValeurFamille;
    protected TextField texteValeurTaille;
    protected TextField texteValeurPoids;

    private Controleur controleur;
    private Pane panneau;
    private GridPane grillePoisson;
    private GridPane grillePrincipale;
    private Button actionAjouterPoisson;

    private int idLieu;

    public VueAjouterPoisson() {
        super(new Pane(), 450, 400);
        panneau = (Pane) this.getRoot();
        grillePoisson = new GridPane();
        grillePrincipale = new GridPane();


        controleur = Controleur.getInstance();
    }

    public void updateVueCelonLieu(Lieu lieu) {
        grillePoisson.getChildren().clear();
        grillePrincipale.getChildren().clear();
        panneau.getChildren().clear();

        idLieu = lieu.getId();

        texteValeurNom = new TextField();
        grillePoisson.add(new Label("Nom : "), 0, 0);
        grillePoisson.add(texteValeurNom, 1, 0);

        texteValeurFamille = new TextField();
        grillePoisson.add(new Label("Famille : "), 0, 1);
        grillePoisson.add(texteValeurFamille, 1, 1);

        texteValeurTaille = new TextField();
        grillePoisson.add(new Label("Taille : (en cm) "), 0, 2);
        grillePoisson.add(texteValeurTaille, 1, 2);

        texteValeurPoids = new TextField();
        grillePoisson.add(new Label("Poids : (en g) "), 0, 3);
        grillePoisson.add(texteValeurPoids, 1, 3);

        actionAjouterPoisson = new Button("Ajouter");
        actionAjouterPoisson.setOnAction(new EventHandler<javafx.event.ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                controleur.notifierActionAjouterPoisson();
            }
        });

        grillePrincipale.add(new Label("Poisson pecher dans la ville de " + lieu.getVille()),0,0);
        grillePrincipale.add(grillePoisson,0,1);
        grillePrincipale.add(actionAjouterPoisson,0,2);

        panneau.getChildren().add(grillePrincipale);
    }

    public Poisson demanderPoisson() {
        Poisson poissonAjouter = new Poisson(texteValeurNom.getText().toString(), texteValeurFamille.getText(), Integer.parseInt(texteValeurTaille.getText()), Integer.parseInt(texteValeurPoids.getText()), idLieu);
        return poissonAjouter;
    }
}
