package Vue;

import Controleur.Controleur;
import Modele.Lieu;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.util.List;

public class VueListeLieu extends Scene {

    private GridPane grilleLieus;
    private GridPane grillePrincipale;
    private Pane panneau;
    private Controleur controleur = null;

    private Button actionNaviguerAjouterLieu;

    public VueListeLieu() {
        super(new GridPane(), 500, 400);
        panneau = (Pane) this.getRoot();
        grilleLieus = new GridPane();
        grillePrincipale = new GridPane();
        controleur = Controleur.getInstance();
        panneau.getChildren().add(grillePrincipale);
    }

    public void afficherListeLieu(List<Lieu> listeLieu) {
        this.grilleLieus.getChildren().clear();
        this.grillePrincipale.getChildren().clear();

        this.grillePrincipale.add(new Label("Page des lieus de pêches : \n\n"),0,0);

        int numero = 0;
        this.grilleLieus.add(new Label("Ville : "), 0, numero);
        this.grilleLieus.add(new Label("Taille : (en km2)"), 1, numero);
        this.grilleLieus.add(new Label("Habitants : "), 2, numero);
        this.grilleLieus.add(new Label("Capital :"), 3, numero);
        this.grilleLieus.add(new Label(""), 4, numero);
        for (Lieu lieu : listeLieu) {
            numero++;
            this.grilleLieus.add(new Label(lieu.getVille()), 0, numero);
            this.grilleLieus.add(new Label("" + lieu.getTaille()), 1, numero);
            this.grilleLieus.add(new Label("" + lieu.getHabitant()), 2, numero);
            String stringCapital = "non";
            if (lieu.getEstCapital()) stringCapital="oui";
            this.grilleLieus.add(new Label(stringCapital), 3, numero);

            Button actionEditer = new Button("Editer");
            actionEditer.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    controleur.notifierActionNaviguerEditerLieu(lieu.getId());
                    System.out.println("Notifier navigation modifier");
                }
            });
            this.grilleLieus.add(actionEditer, 4, numero);

            Button actionSupprimerLieu = new Button("Supprimer");
            actionSupprimerLieu.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    controleur.notifierActionSupprimerLieu(lieu.getId());
                    System.out.println("Notifier action Supprimer Lieu");
                }
            });
            this.grilleLieus.add(actionSupprimerLieu, 5, numero);
        }

        actionNaviguerAjouterLieu = new Button("Ajouter lieu");
        this.grillePrincipale.add(grilleLieus,0,1);
        this.grillePrincipale.add(actionNaviguerAjouterLieu, 0, 2);

        actionNaviguerAjouterLieu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                controleur.notifierNaviguerVersVueAjouterLieu();
            }
        });
    }
}