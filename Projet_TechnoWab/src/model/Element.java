package model;

public class Element extends AbstractComposite {

    private int idElement;
    private String titre;
    private int idList;

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

    public int getIdList() {
        return idList;
    }

    public void setIdList(int idList) {
        this.idList = idList;
    }

    protected Element() {}

    public Element(String titre, int idListd) {
        this.idElement = 0;
        this.titre = titre;
        this.idList = idListd;
    }
}
