package Vue;

import Controleur.Controleur;
import Modele.Lieu;
import Modele.Poisson;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.util.List;

public class VueEditerLieu extends Scene {

    protected TextField textValeurVille;
    protected TextField textValeurTaille;
    protected TextField textValeurHabitant;
    protected CheckBox textValeurEstCapital;

    private Button actionEnregistrerLieu;
    private Button actionNaviguerAjouterPoisson;
    private GridPane grilleLieu;
    private GridPane grillePoissons;
    private GridPane grillePrincipal;
    private Pane panneau;

    private Controleur controleur;

    private int idLieu = 0;

    public VueEditerLieu() {
        super(new Pane(), 520, 400);
        panneau = (Pane) this.getRoot();
        grilleLieu = new GridPane();
        grillePoissons = new GridPane();
        grillePrincipal = new GridPane();

        controleur = Controleur.getInstance();
    }

    public void updateVueEditerLieu(Lieu lieu) {
        this.panneau.getChildren().clear();
        this.grilleLieu.getChildren().clear();
        this.grillePrincipal.getChildren().clear();

        idLieu = lieu.getId();

        textValeurVille = new TextField(lieu.getVille());
        grilleLieu.add(new Label("Nom : "), 0, 0);
        grilleLieu.add(textValeurVille, 1, 0);

        textValeurTaille = new TextField("" + lieu.getTaille());
        grilleLieu.add(new Label("Taille : (en  km2) "), 0, 1);
        grilleLieu.add(textValeurTaille, 1, 1);

        textValeurHabitant = new TextField("" + lieu.getHabitant());
        grilleLieu.add(new Label("Habitants : "), 0, 2);
        grilleLieu.add(textValeurHabitant, 1, 2);

        textValeurEstCapital = new CheckBox();
        if (lieu.getEstCapital())textValeurEstCapital.setSelected(true);
        grilleLieu.add(new Label("Est capitale : "), 0, 3);
        grilleLieu.add(textValeurEstCapital, 1, 3);

        actionEnregistrerLieu = new Button("Enregistrer Lieu");
        actionEnregistrerLieu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                controleur.notifierActionEnregistrerLieu();
                controleur.getNavigateurDesVues().naviguerVersVueListeLieu();
            }
        });


        this.grillePrincipal.add(grilleLieu, 0, 1);
        this.grillePrincipal.add(new Label("\n\t Liste des poissons a : " + textValeurVille.getText() + "\n"), 0, 2);
        this.grillePrincipal.add(grillePoison(controleur.notifierListePoissonCelonLieu(lieu)), 0, 3);
        this.grillePrincipal.add(new Label(), 0, 4);
        this.grillePrincipal.add(actionEnregistrerLieu, 0, 5);
        this.panneau.getChildren().add(grillePrincipal);
    }

    public Lieu demanderLieu() {
        boolean booleanCapital = false;
        if (this.textValeurEstCapital.getText().equals("oui"))booleanCapital=true;
        Lieu lieu = new Lieu(idLieu, this.textValeurVille.getText(),
                Integer.parseInt(this.textValeurTaille.getText()),
                Integer.parseInt(this.textValeurHabitant.getText()),
                booleanCapital);
        return lieu;
    }

    private GridPane grillePoison(List<Poisson> list) {
        List<Poisson> listePoisson = list;

        this.grillePoissons.getChildren().clear();

        int numero = 1;
        this.grillePoissons.add(new Label("Nom : "), 0, numero);
        this.grillePoissons.add(new Label("Famille : "), 1, numero);
        this.grillePoissons.add(new Label("Taille (en cm): "), 2, numero);
        this.grillePoissons.add(new Label("Poids (en g):"), 3, numero);
        this.grillePoissons.add(new Label(""), 4, numero);

        for (Poisson poisson : listePoisson) {
            numero++;
            this.grillePoissons.add(new Label(poisson.getNom()), 0, numero);
            this.grillePoissons.add(new Label(poisson.getFamille()), 1, numero);
            this.grillePoissons.add(new Label("" + poisson.getTaille()), 2, numero);
            this.grillePoissons.add(new Label("" + poisson.getPoids()), 3, numero);

            Button actionEditer = new Button("Editer");
            actionEditer.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    System.out.println("Notifier navigation modifier poisson : " + poisson.getNom());
                    controleur.notifierActionNaviguerEditerPoisson(poisson.getId());
                }
            });
            this.grillePoissons.add(actionEditer, 4, numero);

            Button actionSupprimer = new Button("Supprimer");
            actionSupprimer.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    System.out.println("Notifier suppression poisson : " + poisson.getNom());
                    controleur.notifierActionSupprimerPoisson(poisson.getId(),idLieu);
                }
            });

            this.grillePoissons.add(actionSupprimer, 5, numero);

        }

        actionNaviguerAjouterPoisson = new Button("Ajouter poisson");

        this.grillePoissons.add(actionNaviguerAjouterPoisson, 0, ++numero);

        actionNaviguerAjouterPoisson.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                controleur.notifierActionNaviguerAjouterPoisson(idLieu);
            }
        });

        return grillePoissons;
    }
}
