<!DOCTYPE html>
<html>
<head>
    <title>${title}</title>
    <link rel="stylesheet" href="../css/userList.css"/>
</head>
<body>
<img src="../image/eclair.png" id="ecl1"/>
<a href="/retour"><img src="../../image/retour.png" alt="retour" width="50" height="50" class="img"></a>

<h1>${title}</h1>

<a href="/listes/${idSurList}/createElem"><img src="../image/plus.png" alt="ajouter liste" width="50" height="50" class="img"/></a><br/>

<ul>
<#list list as element>
    <li>
        <h4>${element.titre}</h4>
        <p>${element.des}</p>
        <a href="/listes/${element.idSurList}/${element.idElement}/modifElem"><img src="../image/modifier.png" alt="click me" width="50" height="50" class="img"> </a>
        <form action="/listes/${element.idSurList}/${element.idElement}/deleteElem" method="POST">
            <input type="image" src="../image/delete.png" alt="Submit" width="50" height="50" class="img"/>
        </form>
    </li>
</#list>
</ul>

</body>
</html>