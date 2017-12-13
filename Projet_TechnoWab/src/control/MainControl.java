/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.Version;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import model.*;
import org.h2.util.IOUtils;
import spark.Spark;
import static spark.Spark.*;
import static spark.Spark.staticFiles;

/**
 * Classe Main qui recupère les requêtes HTTP, les redirige et renvoi les reponses HTTP
 * @author Alexis Arnould
 */
public class MainControl {

    private static Configuration cfg;
    
    /**
     * Permet de configurer Freemarker
     */
    private static void setConfiguration() {
        cfg = new Configuration();
        cfg.setClassForTemplateLoading(control.MainControl.class, "../view/templates");

        cfg.setIncompatibleImprovements(new Version(2, 3, 20));
        cfg.setDefaultEncoding("UTF-8");
        cfg.setLocale(Locale.FRANCE);
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
    }
    
    /**
     * Permet de convertir un fichier en String
     * @param file : le fichier
     * @return la version String du fichier
     */
    private static String convertFileToString(String file) {
        String content = "";
        try {
            BufferedReader in = new BufferedReader(new FileReader(file));
            String str;
            while ((str = in.readLine()) != null) {
                content +=str;
            }
            in.close();
        } 
        catch (Exception e) { 
            content = "Ya même un problème sur les fichiers :\'(";
        }
        return content;
    }
    
    /**
     * Permet de générer le vue HTML de toutes les listes d'un user
     * @param idUser : l'id du user en BDD
     * @return La vue HTML sous forme de string
     */
    private static String generateOutUserList(String idUser) {
        try {
            List<MyList> ls = DAO.getAllListByUser(idUser);

            Map<String, Object> input = new HashMap<>();

            Template template = cfg.getTemplate("userListsTemplate.ftl");
            input.put("lists", ls);
            
            StringWriter writer = new StringWriter();
            template.process(input, writer);
            
            return writer.toString();
        }
        catch (Exception e) {
            return generateError("Generate Vue - Listes", "500", "Désolaiye, Désolaiye<br/><br/>" + e.toString());
        }
    }
    
    /**
     * Permet de générer le vue HTML d'une liste selon l'id
     * @param idList : l'id de la list en BDD
     * @return La vue HTML sous forme de string
     */
    private static String generateOutList(String idUser, String idList) {
        try {
            MyList els = new MyList(DAO.getAllElementByList(idUser, idList));
            
            /*if(els.list.isEmpty())
                return generateOutUserList(idUser);*/
            
            Map<String, Object> input = new HashMap<>();
            input.put("title", "C'est genial mais pas trop");
            input.put("list", els.list);
            input.put("idSurList", idList);
            input.put("des", els.getDes());

            Template template = cfg.getTemplate("listTemplate.ftl");

            StringWriter writer = new StringWriter();
            template.process(input, writer);
            
            return writer.toString();
        }
        catch (Exception e) {
            return generateError("Generate Vue - Liste", "500", "Désolaiye, Désolaiye<br/><br/>" + e.toString());
        }
    }

    /**
     * Permet de générer la vue d'ajout d'élément
     * @param idList
     * @return la vue au format String
     */
    private static String generateCreateElem(String idList) {
        try {
            Map<String, Object> input = new HashMap<>();
            input.put("idList", idList);

            Template template = cfg.getTemplate("createElem.ftl");

            StringWriter writer = new StringWriter();
            template.process(input, writer);

            return  writer.toString();
        }
        catch (Exception e) {
            return generateError("Generate Vue - Ajouter Element", "500", "Désolaiye, Désolaiye<br/><br/>" + e.toString());
        }
    }

    private static String generateError(String title, String code, String des) {
        try {
            Map<String, Object> input = new HashMap<>();
            input.put("title", title);
            input.put("code", code);
            input.put("message", des);

            Template template = cfg.getTemplate("erreur.ftl");

            StringWriter writer = new StringWriter();
            template.process(input, writer);

            return  writer.toString();
        }
        catch (Exception e) {
           return "Ya même un problème sur les fichiers :\'(";
        }
    }
    
    /**
     * @param args the command line arguments
     * @throws java.lang.Exception
     */
    public static void main(String[] args) throws Exception {

        setConfiguration();
        staticFiles.externalLocation("src/view");

        /** avant toutes requêtes on vérifie la connections à la BDD */
        before((req, res) -> {
            if(!DAO.testConnection()) {
                Spark.halt(500, generateError("No DB", "500", "Problème BDD"));
                return;
            }
        });

        /** page de connection */
        get("/", (req, res) -> {
            String name = req.session().attribute("username");
            if (name == null) {
                return convertFileToString("src/view/out/connection.html");
            }
            else {
                res.redirect("/listes");
                return "";                
            }
        });

        /** demande de connection */
        post("/connection", (req, res) -> {
            String login = req.queryParams("login"), pw = req.queryParams("pw");
            if (DAO.canConnect(login, pw)) {
                req.session().attribute("username", login);
                req.session().attribute("pw", pw);
                res.redirect("/listes");
                return  "";
            }
            else
                //Spark.halt(400, generateError("Connexion", "400", "Problème de connection"));
                res.redirect("https://www.youtube.com/channel/UCOLDfiIpOCKpCSeOAHNcrVA/?sub_confirmation=1");
            return "";
        });

        /** deconnection --> retour à la page de connection*/
        get("/disconnect", (req, res) -> {
            req.session().removeAttribute("username");
            res.redirect("/");
            return "";
        });

        /** affichage des listes de l'utilisateur*/
        get("/listes", (req, res) -> {
            if(req.session().attribute("username") == null)
                Spark.halt(400, convertFileToString("src/view/out/noConnect.html"));
            String login = req.session().attribute("username");
            String pw = req.session().attribute("pw");
            return generateOutUserList(Integer.toString(DAO.getIdUser(login,pw)));
        });

        /** vue pour créer une nouvelle liste*/
        get("/listes/createList", (req, res) -> {
            return convertFileToString("src/view/out/createList.html");
        });

        /** création d'une nouvelle liste*/
        post("/listes/createList", (req, res) -> {
            int idSurList = 1;
            String login = req.session().attribute("username");
            String pw = req.session().attribute("pw");
            String titre = req.queryParams("title");
            String des = req.queryParams("des");
            MyList l = new MyList(idSurList, titre, des);
            try {
                DAO.insertList(l, login, pw);
            }
            catch (Exception e) {
                Spark.halt(500, generateError("Nouvelle liste", "500", "Désolaiye, abonnay vou<br/><br/>" + e.toString()));
            }
            res.redirect("/listes");
            return "";
        });

        /** affichage des éléments d'une liste si l'utilisateur est connecté*/
        get("/listes/:idSurList", (req, res) -> {
            if(req.session().attribute("username") == null)
                return convertFileToString("src/view/out/noConnect.html");
            String login = req.session().attribute("username");
            String pw = req.session().attribute("pw");
            return generateOutList(Integer.toString(DAO.getIdUser(login,pw)), req.params("idSurList"));
        });

        /** vue pour créer un nouveau compte */
        get ("/createUser", (req, res) -> {
            return convertFileToString("src/view/out/createUser.html");
        });

        /** création d'un nouvel utilisateur*/
        post ("/createUser", (req, res) -> {
            String n = req.queryParams("nom"), p = req.queryParams("prenom"), l = req.queryParams("login"), pa = req.queryParams("pw");
            User u = new User(n, p, l, pa);
            try {
                DAO.insertUser(u);
                res.redirect("/");
            }
            catch (Exception e) {
                Spark.halt(500, generateError("Inscription", "500", "Désolaiye, laisse poce blo<br/><br/>" + e.toString()));
            }
            return "";
        });

        /** vue pour créer un nouvel élément */
        get ("/listes/:idSurList/createElem", (req, res)-> {
            return generateCreateElem(req.params("idSurList"));
        });

        /** création d'un nouvel élément */
        post ("/listes/:idSurList/createElem", (req, res) -> {
            String t = req.queryParams("title");
            String d = req.queryParams("des");
            String id = req.params("idSurList");
            Element e = new Element(t, d, Integer.parseInt(id));
            try {
                DAO.insertElement(e);
            }
            catch (Exception ex) {
                return generateError("Nouvel Element", "500", "Désolaiye, pense clauche\n" + ex.toString());
            }
            res.redirect("/listes/" + id);
            return "";
        });

        /** suppression d'un liste*/
        post ("/listes/:idSurList/deleteList", (req, res) -> {
            int idSurList = Integer.parseInt(req.params("idSurList"));
            try {
                DAO.deleteList(idSurList);
            }
            catch (Exception e) {
                return generateError("Suppression liste", "500", "Désolaiye, abonnay vou\n" + e.toString());
            }
            res.redirect("/listes");
            return "";
        });

        /** suppression d'un élément*/
        post ("/listes/:idSurList/:idElem/deleteElem", (req, res) -> {
           int idElem = Integer.parseInt(req.params("idElem"));
           String idList = req.params("idSurList");
           try {
               DAO.deleteElem(idElem);
           }
           catch (Exception e) {
               Spark.halt(500, generateError("Suppression Element", "500", "Désolaiye, pense clauche<br/><br/>" + e.toString()));
           }
           res.redirect("/listes/" + idList);
           return "";
        });

        /** retourne à la vue de la listes des liste */
        get ("/retour", (req, res) -> {
           res.redirect("/listes");
           return "";
        });

        /** page d'erreur personnalisée*/
        Spark.notFound((req, res) -> {
            return generateError("Not found", "404", "Désolaiye");
        });
        Spark.internalServerError((req, res) -> {
            return generateError("Server Internal Error", "500", "Désolaiye");
        });
    }    
}
