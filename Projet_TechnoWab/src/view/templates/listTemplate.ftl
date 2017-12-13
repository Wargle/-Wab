<!DOCTYPE html>
<html>
<head>
    <title>${title}</title>
    <link rel="stylesheet" href="../CSS/userList.css"/>
</head>
<body>
<a href="/retour"><img src="../image/retour.png" alt="retour" width="50" height="50"></a>

<h1>${title}</h1>

<a href="/listes/${idSurList}/createElem"><img src="../image/plus.png" alt="ajouter liste" width="50" height="50"/></a><br/>

<ul>
<#list list as element>
    <li>
        <h4>${element.titre}</h4>
        <p>${element.des}</p>
        <form action="/listes/${element.idSurList}/${element.idElement}/deleteElem" method="POST">
            <input type="image" src="../image/delete.png" alt="Submit" width="50" height="50"/>
        </form>
    </li>
</#list>
</ul>

</body>
</html>