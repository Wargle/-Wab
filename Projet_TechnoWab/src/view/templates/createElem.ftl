<!DOCTYPE html>
<!--
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
-->
<html>
    <head>
        <link  href="../../CSS/createList.css" rel="stylesheet"/>
    </head>
<body>
    <h1>Ajouter un nouvel élément à votre liste</h1><br/>
    <a href="/retour"><img src="../../image/annuler.png" alt="annuler" width="50" height="50" class="img"></a>
    <form action="/listes/${idList}/createElem" method="POST">
        <label for="title">Nom de l'élément </label><br/>
        <input type="text" name="title" id="title" placeholder="Votre super nom"/><br/><br/>
        <label for="des">Description :</label><br/>
        <textarea name="des" id="des" cols="100" rows="20" placeholder="Votre super description"></textarea><br/>
        <input type="image" src="../../image/plus.png" alt="Submit" width="70" height="70" id="valid"/>
        <input type="text" name="dateCrea" id="dateCrea"/>
    </form>
    </body>

    <script>
        function dateFr() {
            var jours = new Array("dimanche", "lundi", "mardi", "mercredi", "jeudi", "vendredi", "samedi");
            var mois = new Array("janvier", "fevrier", "mars", "avril", "mai", "juin", "juillet", "aout", "septembre", "octobre", "novembre", "decembre");
            var date = new Date();
            var message = jours[date.getDay()] + " ";
            message += date.getDate() + " ";
            message += mois[date.getMonth()] + " ";
            return message;
        }

        function heure()
        {
            var date = new Date();
            var heure = date.getHours();
            var minutes = date.getMinutes();
            if(minutes < 10)
                minutes = "0" + minutes;
            return heure + "h" + minutes;
        }

        var dateCrea = dateFr();
        var heureCrea = heure();
        document.getElementById("dateCrea").value = dateCrea+" "+heureCrea;
    </script>
</html>
