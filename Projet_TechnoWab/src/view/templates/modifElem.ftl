<!DOCTYPE html>
<!--
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
-->
<html>
<head>
    <link href="../../../css/createList.css" rel="stylesheet"/>
</head>
<body>
<h1>Modifiez votre element de liste</h1><br/><br/>

<a href="/retour"><img src="../../../image/annuler.png" alt="annuler" width="50" height="50" class="img"></a>

<form action="/listes/${idList}/${idElem}/modifElem" method="POST">
    <label for="titre">Nouveau titre :</label><input type="text" name="titre" id="titre" placeholder="Votre super titre"/><br/><br/><br/>
    <label for="descrip">Nouvelle description :</label><br/>
    <textarea name="descrip" id="descrip" cols="100" rows="20" placeholder="Votre super description"></textarea><br/><br/>
    <input type="image" src="../../../image/plus.png" alt="Submit" width="70" height="70" id="valid"/>
</form>

</body>
</html>
