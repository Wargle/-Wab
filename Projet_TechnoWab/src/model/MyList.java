package model;

import java.util.ArrayList;
import java.util.List;

public class MyList extends AbstractComposite {
    private int idSurList, idUser;
    private String titre, des;
    public List<AbstractComposite> list;

    public int getIdSurList() {
        return idSurList;
    }

    public void setIdSurList(int idSurList) {
        this.idSurList = idSurList;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
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

    public MyList(List<Element> list) {
        this.list = new ArrayList<>(list);
    }
    
    public MyList(int idSurList, int idUser) {
        this.list = new ArrayList<AbstractComposite>();
        this.idSurList = idSurList;
        this.idUser = idUser;
    }
}
