<%-- 
    Document   : error
    Created on : 28 nov. 2012, 14:47:08
    Author     : Nico
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <title>Error</title>
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
                <img src="inc/pictures/warning.png" alt="" width="70px" style="margin-top:-15px;"/> &nbsp Error
            </h2>
            <p style="color: red; font-weight: bold; font-size: 15px;">
                Warning :
            </p>
            <p style="color: red; font-size: 15px;">
                You can't access to the login page if you are already connected.
                </br>
                You can go to :
                <ul>
                    <li>
                        <a style="color: blue; font-weight: bold;" href="/Telephony-Reports/Reports">- Reports Per Contract</a>
                    </li>
                    <li>
                        <a style="color: blue; font-weight: bold;" href="/Telephony-Reports/Unit_Reports">- Unit Reports</a>
                    </li>
                    <li>
                        <a style="color: blue; font-weight: bold;" href="/Telephony-Reports/Extract">- Extract</a>
                    </li>
                    <li>
                        <a style="color: blue; font-weight: bold;" href="/Telephony-Reports/Faq">- FAQ</a>
                    </li>
                </ul>
            </p>
        </div>
        <!---------------------- CONTENT END ---------------------------->
        <!--------------------------------------------------------------->
        <!---------------------- FOOTER START --------------------------->
        <%@include file="footer/footer.jsp"%>
        <!---------------------- FOOTER END -----------------------------> 
        <!--------------------------------------------------------------->
    </body>
</html>
