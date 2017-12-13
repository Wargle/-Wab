package model;

public class Element extends AbstractComposite {

    /** Un élément comprends :
     * un id d'élément
     * un titre
     * une description
     * l'id de la liste à laquelle il appartient*/

    private int idElement;
    private String titre, des;
    private int idSurList;

    public int getIdElement() {
        return idElement;
    }

    public void setIdElement(int idElement) {
        this.idElement = idElement;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public int getIdSurList() {
        return idSurList;
    }

    public void setIdSurList(int idList) {
        this.idSurList = idList;
    }

    protected Element() {}

    public Element(String titre, String des, int idList) {
        this.idElement = 0;
        this.titre = titre;
        this.des = des;
        this.idSurList =idList;
    }
}
