<%-- 
    Document   : header
    Created on : 29 oct. 2012, 10:55:36
    Author     : Enji
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <link href="inc/styles/styles.css" rel="stylesheet" type="text/css" />
        <link rel="stylesheet" href="inc/styles/styles.css" type="text/css" media="screen"/>
    </head> 
    <body>
        <!---------------------------------------------------------------->
        <!---------------------- HEADER START ---------------------------->
        <div class="header">
            <div class="headerContent">
                <div>
                    <img src="inc/pictures/logoHeader.png" alt="Logo" class="logoHeader"/>
                </div>
                <div class="menu">
                    <ul>         
                        <li><a href="Reports">Reports Contract</a></li>
                        <li><a href="Unit_Reports">Unit Contract</a></li>
                        <li><a href="Data_Extract">Data Extract</a></li>
                        <li><a href="Faq">Support Reports</a></li>
                    </ul>
                    <br style="clear:left"/>
                </div>
                <%-- Vérification de la présence d'un objet utilisateur en session --%>
                <c:if test="${!empty sessionScope.sessionUtilisateur}">
                    <%-- Si l'utilisateur existe en session, alors on affiche son adresse email. --%>
                    <p class="succes">Vous êtes connecté(e) avec le login : ${sessionScope.sessionUtilisateur.login}</p>
                </c:if>
            </div>
        </div>
        <!---------------------- HEADER END ------------------------------>
        <!---------------------------------------------------------------->
    </body>
</html>
