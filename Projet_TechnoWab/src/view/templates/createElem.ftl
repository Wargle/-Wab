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
    </form>
    </body>
</html>
