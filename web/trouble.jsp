<%-- 
    Document   : trouble
    Created on : 29 janv. 2013, 14:55:13
    Author     : 250665
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <title>Trouble</title>
        <link href="inc/styles/styles.css" rel="stylesheet" type="text/css" />
        <link rel="stylesheet" href="inc/styles/styles.css" type="text/css" media="screen"/>
        <link rel="shortcut icon" href="pictures/oAlstom.ico" type="image/x-icon"/> 
        <link rel="icon" href="pictures/oAlstom.ico" type="image/x-icon"/>
    </head>

    <!---------------------------------------------------------------->
    <!---------------------- HEADER START ---------------------------->
    <div id="header">
        <%@include file="headerIndex/headerIndex.jsp"%>
    </div>
    <!---------------------- HEADER END ------------------------------>
    <!---------------------------------------------------------------->
    <!---------------------- CONTENT START --------------------------->       
    <body>
        <div class="content">
            <h2>
                <img src="inc/pictures/supportPart.png" alt="" width="70px" style="margin-top:-15px;"/> &nbsp  Trouble
            </h2>
            <p>For any connection problems, contact the support with the informations below.</p><br />
            <h3 style="margin-bottom : -30px;">Contacts Informations</h3>
            <table style="margin:auto;">
                <tr>
                    <td style="padding:30px 30px 0 30px;"><h4>TRAN NGUYEN Enji</h4></td>
                    <td style="padding:30px 30px 0 30px;;"><h4>CROZE Nicolas</h4></td>
                </tr>
                <tr>
                    <td style="padding:10px 30px 0 30px;"><p class="text2"><em>Global IT, Operations Telecommunications Telephony.</em></p></td>
                    <td style="padding:10px 30px 0 30px;;"><p class="text2"><em>Global IT, Operations Telecommunications Telephony.</em></p></td>
                </tr>
                <tr>
                    <td style="padding:10px 30px 0 30px;"><p class="text2">51, Esplanade du Générale de Gaulle,<br />
                            92207 La Défense 10.</p></td>
                    <td style="padding:10px 30px 0 30px;;"><p class="text2">51, Esplanade du Générale de Gaulle,<br />
                            92207 La Défense 10.</p></td>
                </tr>
                <tr>
                    <td style="padding:10px 30px 0 30px;"><p class="text2">Phone: +33 149 01 67 61<br /><br />
                            E-mail: &nbsp; <a href="mailto:" class="button" style="width:150px">enji.tran-nguyen@alstom.com</a></p></td>
                    <td style="padding:10px 30px 0 30px;;"><p class="text2">Phone: +33 149 01 66 19<br /><br />
                            E-mail: &nbsp; <a href="mailto:" class="button" style="width:150px">nicolas.croze@alstom.com</a></p></td>
                </tr>
            </table>
        </div>
        <!---------------------- CONTENT END ---------------------------->
        <!--------------------------------------------------------------->
    </body>
    <!---------------------- FOOTER START --------------------------->
    <div id="footer">
        <%@include file="footerIndex/footerIndex.jsp"%>
    </div>
    <!---------------------- FOOTER END -----------------------------> 
    <!--------------------------------------------------------------->
</html>
