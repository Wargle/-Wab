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

    public static List<Element> getAllElementByList(String idUser, String idList) throws Exception{
        try (Connection conn = DataBase.getInstance().open()) {
            final String query = "SELECT idElement, e.titre, e.idList FROM ELEMENT e, LIST l, USERLIST u WHERE l.idList = e.idList AND l.idSurList = u.idSurList" 
                    + " AND idUser = :idU AND u.idSurList = :idL";

            return conn.createQuery(query)
                    .addParameter("idU", idUser)
                    .addParameter("idL", idList)
                    .executeAndFetch(Element.class);
        }
    }

    public static void insertElement(Element e) throws Exception{
        try (Connection conn = DataBase.getInstance().open()) {
            final String query = "INSERT INTO ELEMENT (titre, idList) VALUES ( :idT, :idL )";

            conn.createQuery(query)
                    .addParameter("idT", e.getTitre())
                    .addParameter("idL", e.getIdList())
                    .executeUpdate();
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
}
