package Modele;

public class Lieu {
    protected int id;
    protected String ville;
    protected int taille;
    protected int habitant;
    protected boolean estCapital;

    public Lieu(int id,String ville, int taille, int habitant, boolean estCapital) {
        this.ville = ville;
        this.taille = taille;
        this.habitant = habitant;
        this.estCapital = estCapital;
        this.id = id;
    }

    public int getId(){
        return id;
    }

    public void setId(int id){this.id = id;}

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public int getTaille() {
        return taille;
    }

    public void setTaille(int taille) {
        this.taille = taille;
    }

    public int getHabitant() {
        return habitant;
    }

    public void setHabitant(int habitant) {
        this.habitant = habitant;
    }

    public boolean getEstCapital() {
        return estCapital;
    }

    public void setEstCapital(boolean estCapital) {
        this.estCapital = estCapital;
    }
}
