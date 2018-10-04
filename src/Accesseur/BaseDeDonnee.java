package Accesseur;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BaseDeDonnee implements Acces {
    private Connection connection = null;

    private BaseDeDonnee() {

        try {
            Class.forName(BASEDEDONNEES_DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            connection = DriverManager.getConnection(BASEDEDONNEES_URL, BASEDEDONNEES_USAGER, BASEDEDONNEES_MOTDEPASSE);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static BaseDeDonnee instance = null; //null est important

    public static BaseDeDonnee getInstance() {
        if (null == instance) {
            instance = new BaseDeDonnee();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
}
