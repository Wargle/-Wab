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
    
    public static boolean testConnection() {
        try (Connection conn = DataBase.getInstance().open()) {
            return true;
        }
        catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }
    
    public static List<MyList> getAllListByUser(String idUser) throws Exception{
        try (Connection conn = DataBase.getInstance().open()) {
            final String query = "SELECT * FROM USERLIST WHERE idUser = :idU";

            return conn.createQuery(query)
                    .addParameter("idU", idUser)
                    .executeAndFetch(MyList.class);
        }
    }

    public static List<Element> getAllElementByList(String idUser, String idSurList) throws Exception{
        try (Connection conn = DataBase.getInstance().open()) {
            final String query = "SELECT idElement, e.titre, e.idSurList FROM ELEMENT e, USERLIST l WHERE l.idSurList = e.idSurList "
                    + " AND idUser = :idU AND l.idSurList = :idL";

            return conn.createQuery(query)
                    .addParameter("idU", idUser)
                    .addParameter("idL", idSurList)
                    .executeAndFetch(Element.class);
        }
    }
    
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

    public static void insertList(MyList l, String login, String pw) throws Exception{
        try (Connection conn = DataBase.getInstance().open()) {
            final String query = "INSERT INTO USERLIST (titre, des, iduser) VALUES (:t, :d, :id)";

            int idUser = getIdUser(login, pw);

            conn.createQuery(query)
                    .addParameter("t", l.getTitre())
                    .addParameter("d", l.getDes())
                    .addParameter("id", idUser)
                    .executeUpdate();
        }
    }

    public static void insertElement(Element e) throws Exception{
        try (Connection conn = DataBase.getInstance().open()) {
            final String query = "INSERT INTO ELEMENT (titre, des, idSurList) VALUES ( :idT, :d, :idL )";

            conn.createQuery(query)
                    .addParameter("idT", e.getTitre())
                    .addParameter("d", e.getDes())
                    .addParameter("idL", e.getIdSurList())
                    .executeUpdate();
        }
    }

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
}
