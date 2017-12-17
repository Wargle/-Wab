<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/html">
<head>
    <title>Voici vos listes</title>
    <link rel="stylesheet" href="../css/userList.css"/>
</head>
<body>
<img src="../image/eclair.png" id="ecl1"/>
<header>
    <a href="/disconnect" onmouseover="EvalSoundHorse()"><img src="../image/deco.png" alt="deconnexion" width="50" height="50" class="img"/></a>
</header>
<h1>Voici vos listes</h1><br/>

<a href="/listes/createList" onmouseover="EvalSoundAh()"><img src="../image/plus.png" alt="ajouter liste" width="50" height="50" class="img"/></a>
<ul>
<#list lists as list>
    <li>
        <h4>${list.titre}</h4>
        <p>${list.des} </p>

        <a href="/listes/${list.idSurList}"><img src="../image/fleche.png" alt="click me" width="50" height="50" class="img"> </a>
        <a href="/listes/${list.idSurList}/modifList"><img src="../image/modifier.png" alt="click me" width="50" height="50" class="img"> </a>
        <form action="/listes/${list.idSurList}/deleteList" method="POST">
            <input type="image" src="../image/delete.png" alt="Submit" width="50" height="50" class="img"/>
        </form>
        <div id="dateCrea">Date de création : ${list.dateCrea}</div>
        <div id="dateModif">Date de modification : ${list.dateModif}</div>
    </li>
</#list>
</ul>

<script>
    function EvalSoundHorse() {
        var audio = new Audio('../sound/horse.mp3');
        audio.play();
    }
    function EvalSoundAh() {
        var audio = new Audio('../sound/ah.mp3');
        audio.play();
    }
</script>

<embed src="../sound/horse.mp3" autostart="false" type="audio" width="0" height="0" id="horse" enablejavascript="true">
</body>
</html>
