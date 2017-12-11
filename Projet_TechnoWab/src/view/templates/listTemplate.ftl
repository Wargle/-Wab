<!DOCTYPE html>
<html>
<head>
    <title>${title}</title>
</head>
<body>
<h1>${title}</h1>

<ul>
<#list list as element>
    <li>
        id::${element.idElement} // <b>${element.titre}</b> de la sous-liste ${element.idList}
    </li>
</#list>
</ul>

</body>
</html>