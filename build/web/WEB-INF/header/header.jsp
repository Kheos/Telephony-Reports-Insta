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
                    <a href="Reports"><img src="inc/pictures/logoHeader.png" alt="Logo" class="logoHeader"/></a>
                </div>
                <div class="menu">
                    <ul>         
                        <li><a href="Reports">Reports Contract</a></li>
                        <li><a href="Unit_Reports">Unit Contract</a></li>
                        <li><a href="Data_Extract">Data Extract</a></li>
                        <li><a href="Faq">FAQ</a></li>
                    </ul>
                    <br style="clear:left"/>
                </div>
                <div class="infoUser">
                    <div class="user">
                        <span>Welcome <c:out value='${sessionUser}'/></span>
                    </div><br />
                    <div class="deconnexion">
                        <a href="Disconnection" class="deconnexionLink">
                            <img src="inc/pictures/shutdown.png" alt="Shutdown" class="Shutdown" width="30px;"/> &nbsp; Disconnection
                        </a>
                    </div>
                </div>
            </div>
        </div>
        <!---------------------- HEADER END ------------------------------>
        <!---------------------------------------------------------------->
    </body>
</html>
