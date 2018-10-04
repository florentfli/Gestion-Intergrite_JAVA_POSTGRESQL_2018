package Controleur;

import Accesseur.PoissonDAO;
import Modele.Lieu;
import Modele.Poisson;
import Vue.*;
import Accesseur.LieuDAO;

import java.util.List;

public class Controleur {

    private NavigateurDesVues navigateurDesVues;

    private VueAjouterLieu vueAjouterLieu;
    private VueEditerLieu vueEditerLieu;
    private VueListeLieu vueListeLieu;
    private VueEditerPoisson vueEditerPoisson;
    private VueAjouterPoisson vueAjouterPoisson;
    private VueLieu vueLieu;

    private LieuDAO lieuDAO = new LieuDAO();
    private PoissonDAO poissonDAO = new PoissonDAO();

    private Controleur() {}

    private static Controleur instance = null;

    public static Controleur getInstance() {
        if (null == instance) {
            instance = new Controleur();
        }
        return instance;
    }

    public void activerVues(NavigateurDesVues navigateurDesVues) {

        this.navigateurDesVues = navigateurDesVues;

        this.vueAjouterLieu = this.navigateurDesVues.getVueAjouterLieu();
        this.vueListeLieu = this.navigateurDesVues.getVueListeLieu();
        this.vueLieu = this.navigateurDesVues.getVueLieu();
        this.vueEditerLieu = this.navigateurDesVues.getVueEditerLieu();
        this.vueEditerPoisson = this.navigateurDesVues.getVueEditerPoisson();
        this.vueAjouterPoisson = this.navigateurDesVues.getVueAjouterPoisson();

        List<Lieu> listeLieuTest = lieuDAO.listerLieu();
        this.vueListeLieu.afficherListeLieu(listeLieuTest);

        //test
        /*this.navigateurDesVues.naviguerVersVueAjouterLieu();
        this.navigateurDesVues.naviguerVersVueLieu();
        this.navigateurDesVues.naviguerVersVueListeLieu();*/
    }

    ////\\//        \\//\\//
    //\\//\\  LIEU  //\\//\\
    ////\\//        \\//\\//

    public void notifierNaviguerVersVueAjouterLieu() {
        this.vueAjouterLieu.netoyerGrilleChamp();
        this.navigateurDesVues.naviguerVersVueAjouterLieu();
    }

    public void notifierActionAjouterLieu(Lieu lieuAjouter) {
        this.lieuDAO.ajouterLieu(lieuAjouter);
        this.vueListeLieu.afficherListeLieu(lieuDAO.listerLieu());
        this.navigateurDesVues.naviguerVersVueListeLieu();
    }

    public void notifierActionNaviguerEditerLieu(int idLieu) {
        this.vueEditerLieu.updateVueEditerLieu(this.lieuDAO.rapporterLieu(idLieu));
        this.navigateurDesVues.naviguerVersVueEditerLieu();
    }

    public void notifierActionEnregistrerLieu() {
        Lieu lieuModifier = this.navigateurDesVues.getVueEditerLieu().demanderLieu();
        lieuDAO.modifierLieu(lieuModifier);
        vueListeLieu.afficherListeLieu(lieuDAO.listerLieu());
        this.navigateurDesVues.naviguerVersVueListeLieu();
    }

    public void notifierActionSupprimerLieu(int id) {
        lieuDAO.supprimerLieu(id);

        //actualiser liste lieu
        this.vueListeLieu.afficherListeLieu(lieuDAO.listerLieu());
        this.navigateurDesVues.naviguerVersVueListeLieu();
    }

    public List<Poisson> notifierListePoissonCelonLieu(Lieu lieu) {
        return this.poissonDAO.listerPoissonCelonLieu(lieu.getId());
    }

    ////\\//         \\//\\//
    //\\//\\ POISSON //\\//\\
    ////\\//         \\//\\//

    public void notifierActionNaviguerEditerPoisson(int idPoisson) {
        this.vueEditerPoisson.updateVueEditerPoisson(this.poissonDAO.rapporterPoisson(idPoisson));
        this.navigateurDesVues.naviguerVersVueEditerPoisson();
    }

    public void notifierActionEnregistrerPoisson() {
        Poisson poissonModifier = this.navigateurDesVues.getVueEditerPoisson().demanderPoisson();
        int idLieu = poissonDAO.trouverLieuCelonPoisson(poissonModifier).getId();
        this.poissonDAO.modifierPoisson(poissonModifier);

        //update et retour a la page de modification du lieu
        this.vueEditerLieu.updateVueEditerLieu(lieuDAO.rapporterLieu(idLieu));
        this.navigateurDesVues.naviguerVersVueEditerLieu();
    }

    public void notifierActionNaviguerAjouterPoisson(int idLieu) {
        Lieu lieuPoisson = lieuDAO.rapporterLieu(idLieu);
        this.vueAjouterPoisson.updateVueCelonLieu(lieuPoisson);
        this.navigateurDesVues.naviguerVersVueAjouterPoisson();
    }

    public void notifierActionAjouterPoisson() {
        Poisson poissonAjouter = vueAjouterPoisson.demanderPoisson();
        poissonDAO.ajouterPoisson(poissonAjouter);

        //update et retour a la page de modification du lieu
        this.vueEditerLieu.updateVueEditerLieu(lieuDAO.rapporterLieu(poissonAjouter.getId_lieu()));
        this.navigateurDesVues.naviguerVersVueEditerLieu();
    }

    public void notifierActionSupprimerPoisson(int idPoisson,int idLieu) {
        poissonDAO.supprimerPoisson(idPoisson);

        //actualiser liste poisson
        this.vueEditerLieu.updateVueEditerLieu(lieuDAO.rapporterLieu(idLieu));
        this.navigateurDesVues.naviguerVersVueEditerLieu();
    }



    public NavigateurDesVues getNavigateurDesVues() {
        return navigateurDesVues;
    }
}
