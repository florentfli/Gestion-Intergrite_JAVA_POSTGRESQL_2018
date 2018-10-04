package Vue;

import Controleur.Controleur;
import javafx.application.Application;
import javafx.stage.Stage;

public class NavigateurDesVues extends Application {

    private VueAjouterLieu vueAjouterLieu;
    private VueListeLieu vueListeLieu;
    private VueEditerLieu vueEditerLieu;
    private VueEditerPoisson vueEditerPoisson;
    private VueAjouterPoisson vueAjouterPoisson;
    private VueLieu vueLieu;

    private Controleur controleur;

    public NavigateurDesVues() {
        this.vueAjouterLieu = new VueAjouterLieu();
        this.vueListeLieu = new VueListeLieu();
        this.vueEditerLieu = new VueEditerLieu();
        this.vueEditerPoisson = new VueEditerPoisson();
        this.vueAjouterPoisson = new VueAjouterPoisson();
        this.vueLieu = new VueLieu();
    }

    private Stage stade = null;

    @Override
    public void start(Stage stade) throws Exception {

        this.stade = stade;

        stade.setScene(this.vueListeLieu);
        stade.show();

        controleur = Controleur.getInstance();
        controleur.activerVues(this);

        //TEST
       /* this.naviguerVersVueAjouterLieu();
        this.naviguerVersVueLieu();*/

       //ACEUILL
        this.naviguerVersVueListeLieu();
    }

    public void naviguerVersVueLieu() {
        stade.setScene(vueLieu);
        stade.show();
    }

    public void naviguerVersVueAjouterLieu() {
        stade.setScene(vueAjouterLieu);
        stade.show();
    }

    public void naviguerVersVueListeLieu() {
        stade.setScene(vueListeLieu);
        stade.show();
    }

    public void naviguerVersVueEditerLieu() {
        stade.setScene(vueEditerLieu);
        stade.show();
    }

    public void naviguerVersVueEditerPoisson() {
        stade.setScene(vueEditerPoisson);
        stade.show();
    }

    public void naviguerVersVueAjouterPoisson() {
        stade.setScene(vueAjouterPoisson);
        stade.show();
    }

    public VueAjouterLieu getVueAjouterLieu() {
        return vueAjouterLieu;
    }

    public VueListeLieu getVueListeLieu() {
        return vueListeLieu;
    }

    public VueLieu getVueLieu() {
        return vueLieu;
    }

    public VueEditerLieu getVueEditerLieu() {
        return vueEditerLieu;
    }

    public VueEditerPoisson getVueEditerPoisson() {
        return vueEditerPoisson;
    }

    public VueAjouterPoisson getVueAjouterPoisson() {
        return vueAjouterPoisson;
    }
}