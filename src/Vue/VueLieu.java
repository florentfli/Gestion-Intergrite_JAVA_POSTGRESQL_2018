package Vue;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;

public class VueLieu extends Scene {

    protected Label valeurVille;
    protected Label valeurTaille;
    protected Label valeurHabitant;
    protected Label valeurEstCapital;

    public VueLieu(){
        super(new Pane(),400,400);
        Pane panneau = (Pane) this.getRoot();
        GridPane grilleLieu = new GridPane();

        // https://docs.oracle.com/javase/8/javafx/api/javafx/scene/layout/GridPane.html
        valeurVille = new Label("");
        grilleLieu.add(new Label("Nom : "), 0, 0);
        grilleLieu.add(valeurVille, 1, 0);

        valeurTaille = new Label("");
        grilleLieu.add(new Label("Taille : "), 0, 1);
        grilleLieu.add(valeurTaille, 1, 1);

        valeurHabitant = new Label("");
        grilleLieu.add(new Label("Habitants : "), 0, 2);
        grilleLieu.add(valeurHabitant, 1, 2);

        valeurEstCapital = new Label("");
        grilleLieu.add(new Label("Est capitale : "), 0, 3);
        grilleLieu.add(valeurEstCapital, 1, 3);

        panneau.getChildren().add(grilleLieu);
    }
}
