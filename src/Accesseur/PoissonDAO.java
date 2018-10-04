package Accesseur;

import Modele.Lieu;
import Modele.Poisson;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static Accesseur.Acces.*;

public class PoissonDAO {

    List<Poisson> listePoissonTeste = new ArrayList<>();

    private Connection connection = null;

    public PoissonDAO() {
        connection = BaseDeDonnee.getInstance().getConnection();
    }

    public List<Poisson> simulelisterLieus() {
        listePoissonTeste.add(new Poisson(1, "Saumon", "salmonidée", 58, 832));
        listePoissonTeste.add(new Poisson(1, "Truite", "salmonidée", 32, 234));
        listePoissonTeste.add(new Poisson(1, "Brochet", "salmonidée", 58, 832));
        listePoissonTeste.add(new Poisson(1, "Saumon", "salmonidée", 58, 832));

        return listePoissonTeste;
    }

    public List<Poisson> listerPoissonCelonLieu(int id_lieu) {
        List<Poisson> listePoisson = new ArrayList<>();
        try {
            PreparedStatement requetePreparerListePoisson = connection.prepareStatement(SQL_PREPARER_SELECT_POISSON_CELON_LIEU);
            requetePreparerListePoisson.setInt(1, id_lieu);
            ResultSet curseurListePoisson = requetePreparerListePoisson.executeQuery();

            while (curseurListePoisson.next()) {
                String nom = curseurListePoisson.getString("nom");
                String famille = curseurListePoisson.getString("famille");
                int taille = curseurListePoisson.getInt("taille");
                int poids = curseurListePoisson.getInt("poids");
                int id = curseurListePoisson.getInt("id");
                System.out.println("SQL DATA :  nom:" + nom + " famille:" + famille + " Taille : " + taille + " Poids : " + poids);
                listePoisson.add(new Poisson(id, nom, famille, taille, poids, id_lieu));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listePoisson;
    }

    public Poisson rapporterPoisson(int idPoisson) {
        try {
            PreparedStatement requetePreparerRapporterPoisson = connection.prepareStatement(SQL_PREPARER_TROUVER_POISSON);
            requetePreparerRapporterPoisson.setInt(1, idPoisson);
            System.out.println("SQL PREPARER : " + SQL_PREPARER_TROUVER_POISSON);
            ResultSet curseurPoisson = requetePreparerRapporterPoisson.executeQuery();
            curseurPoisson.next();

            int id = curseurPoisson.getInt("id");
            String nom = curseurPoisson.getString("nom");
            String famille = curseurPoisson.getString("famille");
            int taille = curseurPoisson.getInt("taille");
            int poids = curseurPoisson.getInt("poids");
            int id_lieu = curseurPoisson.getInt("id_lieu");

            System.out.println("Nom poisson : " + nom + " Famille : " + famille + " Taille : " + taille + " Poids : " + poids);
            Poisson poisson = new Poisson(id, nom, famille, taille, poids, id_lieu);
            return poisson;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void modifierPoisson(Poisson poisson) {
        System.out.println("SQL PREPARER:" + SQL_PREPARER_REQUETE_UPDATE_POISSON);

        PreparedStatement requetePrearerModifierPoisson;
        try {
            requetePrearerModifierPoisson = connection.prepareStatement(SQL_PREPARER_REQUETE_UPDATE_POISSON);

            requetePrearerModifierPoisson.setString(1, poisson.getNom());
            requetePrearerModifierPoisson.setString(2, poisson.getFamille());
            requetePrearerModifierPoisson.setInt(3, poisson.getTaille());
            requetePrearerModifierPoisson.setInt(4, poisson.getPoids());
            requetePrearerModifierPoisson.setInt(5, poisson.getId());

            requetePrearerModifierPoisson.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Lieu trouverLieuCelonPoisson(Poisson poisson) {
        PreparedStatement requetePreparerTouverLieuCelonPoisson;
        try {
            requetePreparerTouverLieuCelonPoisson = connection.prepareStatement(SQL_PREPARER_REQUETE_TROUVER_LIEU_CELON_POISSON);
            requetePreparerTouverLieuCelonPoisson.setInt(1, poisson.getId());
            System.out.println("SQL PREPARER" + SQL_PREPARER_REQUETE_TROUVER_LIEU_CELON_POISSON);

            ResultSet curseurPoisson = requetePreparerTouverLieuCelonPoisson.executeQuery();
            curseurPoisson.next();

            int id = curseurPoisson.getInt("id");
            String ville = curseurPoisson.getString("ville");
            int taille = curseurPoisson.getInt("taille");
            int habitant = curseurPoisson.getInt("habitant");
            String estcapitale = curseurPoisson.getString("estcapitale");
            System.out.println("SQL DATA :  nom:" + ville + " taille:" + taille + " Habitants : " + habitant + " est capital : " + estcapitale);
            boolean booleanCapital = false;
            if (estcapitale.equals("oui")) booleanCapital = true;
            Lieu lieu = new Lieu(id, ville, taille, habitant, booleanCapital);
            return lieu;
        } catch (
                SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void ajouterPoisson(Poisson poisson) {
        PreparedStatement requetePreparerAjouterPoisson;
        try {
            requetePreparerAjouterPoisson = connection.prepareStatement(SQL_PREPARER_REQUETE_INSERT_POISSON);

            requetePreparerAjouterPoisson.setString(1, poisson.getNom());
            requetePreparerAjouterPoisson.setString(2, poisson.getFamille());
            requetePreparerAjouterPoisson.setInt(3, poisson.getTaille());
            requetePreparerAjouterPoisson.setInt(4, poisson.getPoids());
            requetePreparerAjouterPoisson.setInt(5, poisson.getId_lieu());


            System.out.println("SQL PREPARER : " + SQL_PREPARER_REQUETE_INSERT_POISSON);
            requetePreparerAjouterPoisson.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void supprimerPoisson(int idPoisson) {
        PreparedStatement requetePreparerSupprimerPoisson;
        try {
            requetePreparerSupprimerPoisson = connection.prepareStatement(SQL_PREPARER_REQUETE_DELETE_POISSON);
            requetePreparerSupprimerPoisson.setInt(1,idPoisson);
            System.out.println("SQL PREPARER : " + SQL_PREPARER_REQUETE_DELETE_POISSON);
            requetePreparerSupprimerPoisson.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
