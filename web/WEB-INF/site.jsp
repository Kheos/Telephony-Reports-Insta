<%-- 
    Document   : site
    Created on : 12 avr. 2013, 13:23:22
    Author     : Enji
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <title>Site</title>
        <link href="styles/styles.css" rel="stylesheet" type="text/css" />
        <meta content="TRAN-NGUYEN" name="author" />
        <meta content="Telephony Reports" name="description" />
        <meta content="width=device-width, initial-scale=1.0" name="viewport">
            <link href="inc/pictures/icone.png" rel="shortcut icon" type="image/x-icon" />
            <link href="inc/pictures/icone.png" rel="icon" type="image/x-icon" />  
    </head>
    <body>
        <!---------------------------------------------------------------->
        <!---------------------- HEADER START ---------------------------->
        <%@include file="header/header.jsp"%>
        <!---------------------- HEADER END ------------------------------>
        <!---------------------------------------------------------------->
        <!---------------------- CONTENT START --------------------------->       
        <div class="content">
            <h2>
                <img src="inc/pictures/tree.png" alt="" width="70px" style="margin-bottom:-10px;"/> &nbsp  Site
            </h2><br />
            <ul>
                <li>
                    <a href="Reports" class="lienSite">Reports</a>
                </li>
                <ul>
                    <li>
                        
                    </li>
                </ul>
                <li>
                    <a href="Unit_Reports" class="lienSite">Unit Contract</a>
                </li>
                <li>
                    <a href="Data_Extract" class="lienSite">Data Extract</a>
                </li>
                <li>
                    <a href="Faq" class="lienSite">FAQ</a>
                </li>
            </ul>
        </div>
        <!---------------------- CONTENT END ---------------------------->
        <!--------------------------------------------------------------->
        <!---------------------- FOOTER START --------------------------->
        <%@include file="footer/footer.jsp"%>
        <!---------------------- FOOTER END -----------------------------> 
        <!--------------------------------------------------------------->
    </body>
</html>
