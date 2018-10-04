package Accesseur;

public interface Acces {
    //\\ CONNECTION BDD //\\

    static final String BASEDEDONNEES_DRIVER = "org.postgresql.Driver";
    static final String BASEDEDONNEES_URL = "jdbc:postgresql://localhost:5432/lieuPeche";
    static final String BASEDEDONNEES_USAGER = "postgres";
    static final String BASEDEDONNEES_MOTDEPASSE = "postgres";

    //\\ REQUETE LIEU DAO //\\

    static final String SQL_LISTER_LIEU = "SELECT * FROM lieu";
    static final String SQL_PREPARER_REQUETE_INSERT_LIEU = "INSERT into lieu(ville, habitant,taille,estcapitale) VALUES (?,?,?,?)";
    static final String SQL_PREPARE_REQUETE_UPDATE_LIEU = "UPDATE lieu SET ville = ?, taille = ?, habitant = ?, estcapitale = ? WHERE id = ?";
    static final String SQL_PREPARER_REQUETE_DELETE_LIEU = "DELETE FROM lieu WHERE id = ?";
    static final String SQL_PREPARER_RAPPORTER_LIEU = "SELECT * FROM lieu WHERE id = ?";

    //\\ REQUETE POISSON DAO //\\

    static final String SQL_PREPARER_SELECT_POISSON_CELON_LIEU = "SELECT * FROM poisson WHERE id_lieu = ?";
    static final String SQL_PREPARER_TROUVER_POISSON = "SELECT * FROM poisson WHERE id = ?";
    static final String SQL_PREPARER_REQUETE_UPDATE_POISSON = "UPDATE poisson SET nom =?,famille = ?, taille = ?, poids = ? WHERE id = ?";
    static final String SQL_PREPARER_REQUETE_TROUVER_LIEU_CELON_POISSON = "SELECT * FROM lieu WHERE id = (SELECT poisson.id_lieu FROM poisson WHERE id = ?)";
    static final String SQL_PREPARER_REQUETE_INSERT_POISSON = "INSERT into poisson(nom, famille,taille,poids,id_lieu) VALUES (?,?,?,?,?)";
    static final String SQL_PREPARER_REQUETE_DELETE_POISSON = "DELETE FROM poisson WHERE id = ?";
}
