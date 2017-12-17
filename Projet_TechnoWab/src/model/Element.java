package model;

public class Element extends AbstractComposite {

    /** Un élément comprends :
     * un id d'élément
     * un titre
     * une description
     * l'id de la liste à laquelle il appartient*/

    private int idElement;
    private String titre, des, dateCrea, dateModif;
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

    public String getDateCrea() { return dateCrea; }

    public void setDateCrea(String dateCrea) { this.dateCrea = dateCrea; }

    public String getDateModif() { return dateModif; }

    public void setDateModif(String dateModif) { this.dateModif = dateModif; }

    protected Element() {}

    public Element(String titre, String des, int idList, String dateCrea, String dateModif) {
        this.idElement = 0;
        this.titre = titre;
        this.des = des;
        this.idSurList =idList;
        this.dateCrea = dateCrea;
        this.dateModif = dateModif;
    }
}
