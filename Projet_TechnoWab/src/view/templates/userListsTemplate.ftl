<!DOCTYPE html>
<html>
<head>
    <title>Voici vos listes</title>
</head>
<body>
<header>
    <a href="/disconnect">Au revoir !</a>
</header>
<h1>Voici vos listes</h1><br/>

<a href="/listes/createList">Ajouter une liste svp</a>
<ul>
<#list lists as list>
    <li>
        Le liste -- id:: ${list.idSurList} de l'utilisateur ${list.idUser}
        <a href="/listes/${list.idSurList}">Click me ! senpai </a>
    </li>
</#list>
</ul>

</body>
</html>
