<%-- 
    Document   : extract
    Created on : 29 janv. 2013, 15:49:20
    Author     : 250665
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <title>Data Extract</title>
        <link href="styles/styles.css" rel="stylesheet" type="text/css" />
        <link rel="stylesheet" href="styles/styles.css" type="text/css" media="screen"/>
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
                <img src="inc/pictures/extract.png" alt="" width="50px" style="margin-top:-8px;"/> &nbsp  Data Extract
            </h2><br />
            <p>Find in this part the extraction module of data.</p>
            <p>Choose an action :</p>
            <div style="width:960px; margin:auto;">
                <center>
                    <table style="display: inline-block; text-align: center; color:black; margin-bottom: 20px;">
                        <tr>
                            <td style="padding:3px; padding: 15px 80px 0 80px; border: none; text-align: center;"><center><a href="Download_Extract"><img src="inc/pictures/Download.png" alt="" width="35px" style="margin-top:-15px;"/></a></center></td>
                            <td style="padding:3px;padding: 15px 80px 0 80px; border: none;  text-align: center;"><center><a href="Download_Graphic"><img src="inc/pictures/graphic.png" alt="" width="35px" style="margin-top:-15px;"/></a></center></td>
                            <td style="padding:3px;padding: 15px 80px 0 80px; border: none;  text-align: center;"><center><a href="Reports"><img src="inc/pictures/magnifier.png" alt="" width="35px" style="margin-top:-15px;"/></a></center></td>
                        </tr>
                        <tr>
                            <td style="padding:3px; padding:10px 80px 0 80px; border: none;  text-align: center;"><center><a href="Download_Extract"><p><strong>Download Data</strong></p></a></center></td>
                            <td style="padding:3px; padding:10px 80px 0 80px; border: none;  text-align: center;"><center><a href="Download_Graphic"><p><strong>Download Graphics</strong></p></a></center></td>
                            <td style="padding:3px; padding:10px 80px 0 80px; border: none;  text-align: center;"><center><a href="Reports"><p><strong>View Data Online</strong></p></a></center></td>
                        </tr>  
                    </table>
                </center>
            </div>
        </div>
        <!---------------------- CONTENT END ---------------------------->
        <!--------------------------------------------------------------->
        <!---------------------- FOOTER START --------------------------->
        <%@include file="footer/footer.jsp"%>
        <!---------------------- FOOTER END -----------------------------> 
        <!--------------------------------------------------------------->
    </body>
</html>
