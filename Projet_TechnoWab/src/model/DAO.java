package model;

import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.Date;
import java.util.List;

/**
 * Classe qui permet d'accèder aux données de la BDD à travers diverses requêtes
 * @author Alexis Arnould
 */
public class DAO {
    /**
     * Classe qui permet la connexion à la BDD
     */
    public static class DataBase {
        private static Sql2o instance;

        public static Sql2o getInstance() {
            if (instance == null) {
                try {
                    instance = new Sql2o("jdbc:h2:~/webBDD", "sa", "");
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
            return instance;
        }

        public static void open() {
            getInstance().open();
        }
    }

    /** permet de tester la connection à la BDD */
    public static boolean testConnection() {
        try (Connection conn = DataBase.getInstance().open()) {
            return true;
        }
        catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    /** récupère les liste en fonction de l'id de l'utilisateur */
    public static List<MyList> getAllListByUser(String idUser) throws Exception{
        try (Connection conn = DataBase.getInstance().open()) {
            final String query = "SELECT * FROM USERLIST WHERE idUser = :idU";

            return conn.createQuery(query)
                    .addParameter("idU", idUser)
                    .executeAndFetch(MyList.class);
        }
    }

    /** permets de récupérer les éléments d'une liste en fonction de son id et de celui de l'utilisateur */
    public static List<Element> getAllElementByList(String idUser, String idSurList) throws Exception{
        try (Connection conn = DataBase.getInstance().open()) {
            final String query = "SELECT idElement, e.titre, e.idSurList, e.des, e.dateCrea, e.dateModif, e.etat FROM ELEMENT e, USERLIST l WHERE l.idSurList = e.idSurList "
                    + " AND idUser = :idU AND l.idSurList = :idL";

            return conn.createQuery(query)
                    .addParameter("idU", idUser)
                    .addParameter("idL", idSurList)
                    .executeAndFetch(Element.class);
        }
    }

    /** autorise ou non une combinaison login/password à se connecter */
    public static boolean canConnect(String login, String pw) {
        try(Connection conn = DataBase.getInstance().open()) {
            final String query = "SELECT COUNT(*) FROM USER WHERE login = :l AND password = :p";
            
            int nb = conn.createQuery(query)
                    .addParameter("l", login)
                    .addParameter("p", pw)
                    .executeScalar(Integer.class);
            if(nb == 0)
                return false;
            return true;
        }
    }

    /** permets d'ajouter un nouvel utilisateur en fonction du formulaire remplis */
    public static void insertUser(User u) throws Exception{
        try (Connection conn = DataBase.getInstance().open()) {
            final String query = "INSERT INTO USER (nom,prenom,login,password) VALUES ( :n, :p, :l, :pa )";

            conn.createQuery(query)
                    .addParameter("n", u.getNom())
                    .addParameter("p", u.getPrenom())
                    .addParameter("l", u.getLogin())
                    .addParameter("pa", u.getPassword())
                    .executeUpdate();
        }
    }

    /** permets de créer une nouvelle liste */
    public static void insertList(MyList l, String login, String pw) throws Exception{
        try (Connection conn = DataBase.getInstance().open()) {
            final String query = "INSERT INTO USERLIST (titre, des, iduser, dateCrea, dateModif) VALUES (:t, :d, :id, :dc, :dm)";

            int idUser = getIdUser(login, pw);

            conn.createQuery(query)
                    .addParameter("t", l.getTitre())
                    .addParameter("d", l.getDes())
                    .addParameter("id", idUser)
                    .addParameter("dc", l.getDateCrea())
                    .addParameter("dm", l.getDateModif())
                    .executeUpdate();
        }
    }

    /** permets d'ajouter un nouvel élément à une liste */
    public static void insertElement(Element e) throws Exception{
        try (Connection conn = DataBase.getInstance().open()) {
            final String query = "INSERT INTO ELEMENT (titre, des, idSurList, dateCrea, dateModif, etat) VALUES ( :idT, :d, :idL, :dc, :dm, :e )";

            conn.createQuery(query)
                    .addParameter("idT", e.getTitre())
                    .addParameter("d", e.getDes())
                    .addParameter("idL", e.getIdSurList())
                    .addParameter("dc", e.getDateCrea())
                    .addParameter("dm", e.getDateModif())
                    .addParameter("e", e.getEtat())
                    .executeUpdate();
        }
    }

    /** récupère l'id d'un utilisateur en fonction de son login et de son password */
    public static int getIdUser (String login, String pw) throws Exception {
        try (Connection conn = DataBase.getInstance().open()) {
            final String queryIdUser = "SELECT IDUSER FROM USER WHERE LOGIN=:l AND PASSWORD=:p";

            int idUser = conn.createQuery(queryIdUser)
                    .addParameter("l", login)
                    .addParameter("p", pw)
                    .executeScalar(Integer.class);

            return idUser;
        }
    }

    /** permets de supprimer une liste et ses éléments en fonction de son id */
    public static void deleteList (int idList) throws Exception {
        try (Connection conn = DataBase.getInstance().open()) {
            final String queryEl = "DELETE FROM ELEMENT WHERE IDSURLIST=:id"; //on supprime en premier les élément de la liste
            final String queryLi = "DELETE FROM USERLIST WHERE IDSURLIST=:id";

            conn.createQuery(queryEl)
                    .addParameter("id", idList)
                    .executeUpdate();
            conn.createQuery(queryLi)
                    .addParameter("id", idList)
                    .executeUpdate();
        }
    }

    /** permets de supprimer un élément en fonction de son id */
    public static void deleteElem (int idElem) throws Exception {
        try (Connection conn = DataBase.getInstance().open()) {
            final String queryEl = "DELETE FROM ELEMENT WHERE IDELEMENT=:id";

            conn.createQuery(queryEl)
                    .addParameter("id", idElem)
                    .executeUpdate();
        }
    }

    /** permets de modifier le titre et la description d'une liste*/
    public static void modifList (int idList, String nvTitre, String nvDes, String dateModif) throws Exception {
        try (Connection conn = DataBase.getInstance().open()) {
            final String query = "UPDATE USERLIST SET TITRE=:nt, DES=:nd, DATEMODIF=:dm WHERE IDSURLIST=:id";
            conn.createQuery(query)
                    .addParameter("nt", nvTitre)
                    .addParameter("nd", nvDes)
                    .addParameter("id", idList)
                    .addParameter("dm", dateModif)
                    .executeUpdate();
        }
    }

    /** permets de modifier le nom er la description d'un élément*/
    public static void modifElem (int idElem, String nvTitre, String nvDes, String dateModif) throws Exception {
        try (Connection conn = DataBase.getInstance().open()) {
            final String query = "UPDATE ELEMENT SET TITRE=:nt, DES=:nd, DATEMODIF=:dm WHERE IDELEMENT=:id";
            conn.createQuery(query)
                    .addParameter("nt", nvTitre)
                    .addParameter("nd", nvDes)
                    .addParameter("id", idElem)
                    .addParameter("dm", dateModif)
                    .executeUpdate();
        }
    }

    /** Modifie l'etat d'un élément*/
    public static void modifEtat (int idElem, String etat) throws Exception {
        try (Connection conn = DataBase.getInstance().open()){
            final String query = "UPDATE ELEMENT SET ETAT=:e WHERE IDELEMENT=:id";
            conn.createQuery(query)
                    .addParameter("e", etat)
                    .addParameter("id", idElem)
                    .executeUpdate();
        }
    }
}
