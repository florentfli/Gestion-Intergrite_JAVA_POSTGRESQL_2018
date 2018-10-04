package Modele;

public class Poisson {
    protected int id;
    protected String nom;
    protected int taille;
    protected int poids;
    protected int id_lieu;
    protected String famille;

    public Poisson(String nom, String famille, int taille, int poids) {
        this.nom = nom;
        this.taille = taille;
        this.poids = poids;
        this.famille = famille;
    }

    public Poisson(int id, String nom, String famille, int taille, int poids, int id_lieu) {
        this.id = id;
        this.nom = nom;
        this.taille = taille;
        this.poids = poids;
        this.famille = famille;
        this.id_lieu = id_lieu;
    }

    public Poisson(int id, String nom, String famille, int taille, int poids) {
        this.id = id;
        this.nom = nom;
        this.taille = taille;
        this.poids = poids;
        this.famille = famille;
    }

    public Poisson(String nom, String famille, int taille, int poids, int idLieu) {
        this.nom = nom;
        this.taille = taille;
        this.poids = poids;
        this.famille = famille;
        this.id_lieu = idLieu;
    }

    public int getId_lieu() {
        return id_lieu;
    }

    public void setId_lieu(int id_lieu) {
        this.id_lieu = id_lieu;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getTaille() {
        return taille;
    }

    public void setTaille(int taille) {
        this.taille = taille;
    }

    public int getPoids() {
        return poids;
    }

    public void setPoids(int poids) {
        this.poids = poids;
    }

    public String getFamille() {
        return famille;
    }

    public void setFamille(String famille) {
        this.famille = famille;
    }
}
