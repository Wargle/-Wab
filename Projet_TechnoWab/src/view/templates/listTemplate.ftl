<!DOCTYPE html>
<html>
<head>
    <title>${title}</title>
</head>
<body>
<a href="/retour">Retour à la liste de listes</a>

<h1>${title}</h1>

<a href="/listes/${idSurList}/createElem">Ajouter un élément !</a><br/>

<ul>
<#list list as element>
    <li>
        <h4>${element.titre}</h4>

        id::${element.idElement} // <b>${element.titre}</b> de la liste ${element.idSurList}
        <form action="/listes/${element.idSurList}/${element.idElement}/deleteElem" method="POST">
            <input type="submit" value="supprimer"/>
        </form>
    </li>
</#list>
</ul>

</body>
</html>