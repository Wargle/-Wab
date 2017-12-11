package model;

public class User extends AbstractComposite {

    private int idUser;
    private String nom;
    private String prenom;
    private String login;
    private String password;

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User () {}

    public User (int id, String n, String p, String l, String pa) {
        this.idUser = id;
        this.nom = n;
        this.prenom = p;
        this.login = l;
        this.password = pa;
    }
}
