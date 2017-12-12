<!DOCTYPE html>
<html>
<head>
    <title>${title}</title>
</head>
<body>
<h1>${title}</h1>

<a href="/listes/${idSurList}/createElem">Ajouter un élément !</a><br/>

<ul>
<#list list as element>
    <li>
        id::${element.idElement} // <b>${element.titre}</b> de la liste ${element.idSurList}
    </li>
</#list>
</ul>

</body>
</html>