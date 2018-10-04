package Vue;

import Controleur.Controleur;
import Modele.Poisson;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class VueEditerPoisson extends Scene {

    Controleur controleur;
    Pane panneau;
    GridPane grillePoissonEditer;

    protected TextField textValeurNom;
    protected TextField textValeurFamile;
    protected TextField textValeurTaille;
    protected TextField textValeurPoids;

    private int idPoisson;
    private Button actionEnregistrerPoisson;

    public VueEditerPoisson() {
        super(new Pane(), 450, 400);
        panneau = (Pane) this.getRoot();

        grillePoissonEditer = new GridPane();

        controleur = Controleur.getInstance();
    }

    public void updateVueEditerPoisson(Poisson poisson) {
        this.panneau.getChildren().clear();
        this.grillePoissonEditer.getChildren().clear();

        idPoisson = poisson.getId();

        textValeurNom = new TextField(poisson.getNom());
        grillePoissonEditer.add(new Label("Nom : "), 0, 0);
        grillePoissonEditer.add(textValeurNom, 1, 0);

        textValeurFamile = new TextField(poisson.getFamille());
        grillePoissonEditer.add(new Label("Famille : "), 0, 2);
        grillePoissonEditer.add(textValeurFamile, 1, 2);

        textValeurTaille = new TextField("" + poisson.getTaille());
        grillePoissonEditer.add(new Label("Taille : (en cm) "), 0, 1);
        grillePoissonEditer.add(textValeurTaille, 1, 1);

        textValeurPoids = new TextField("" + poisson.getPoids());
        grillePoissonEditer.add(new Label("Poids : (en g) "), 0, 3);
        grillePoissonEditer.add(textValeurPoids, 1, 3);

        actionEnregistrerPoisson = new Button("Enregistrer");
        actionEnregistrerPoisson.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                controleur.notifierActionEnregistrerPoisson();
            }
        });

        this.grillePoissonEditer.add(actionEnregistrerPoisson, 0, 4);

        this.panneau.getChildren().add(grillePoissonEditer);
    }


    public Poisson demanderPoisson() {
        Poisson poissonEditer = new Poisson(idPoisson,textValeurNom.getText().toString(), textValeurFamile.getText(), Integer.parseInt(textValeurTaille.getText()), Integer.parseInt(textValeurPoids.getText()));
        return poissonEditer;
    }
}
