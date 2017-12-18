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
<#list els as element>
    <li>
        <h4>${element.titre}</h4>
        <p>${element.des}</p>
        <div id="etat">
        <form action="/listes/${element.idSurList}/${element.idElement}/etatElem" method="post">
            <div id="choix${element.idElement}">

            </div>
        </form>
        </div>
        <a href="/listes/${element.idSurList}/${element.idElement}/modifElem"><img src="../image/modifier.png" alt="click me" width="50" height="50" class="img"> </a>
        <form action="/listes/${element.idSurList}/${element.idElement}/deleteElem" method="POST">
            <input type="image" src="../image/delete.png" alt="Submit" width="50" height="50" class="img" onclick="reEvalEtat()"/>
        </form>
        <div id="dateCrea">Date de création : ${element.dateCrea}</div>
        <div id="dateModif">Date de modification : ${element.dateModif}</div>
    </li>

</#list>
</ul>

</body>

<script>
    var afairePasCheck = "<input type=\"radio\" name=\"etat\" value=\"à faire\" id=\"etat\"/><label for=\"afaire\"> à faire</label>";
    var afaireCheck = "<input type=\"radio\" name=\"etat\" value=\"à faire\" id=\"etat\" checked/><label for=\"afaire\"> à faire</label>";

    var faitPasCheck = "<input type=\"radio\" name=\"etat\" value=\"fait\" id=\"etat\"/><label for=\"fait\"> fait</label>";
    var faitCheck = "<input type=\"radio\" name=\"etat\" value=\"fait\" id=\"etat\" checked/><label for=\"fait\"> fait</label>";

    var boutton = "<input type=\"image\" src=\"../image/valider.png\" alt=\"submit\" width=\"20\" height=\"20\" class=\"img\"/>";

    <#list els as element>
        <#if (element.etat) = "fait">
        document.getElementById("choix${element.idElement}").innerHTML = (afairePasCheck + faitCheck + boutton);
     <#else>
        document.getElementById("choix${element.idElement}").innerHTML = (afaireCheck + faitPasCheck + boutton);
        </#if>
    </#list>

</script>

</html>