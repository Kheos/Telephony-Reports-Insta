<%-- 
    Document   : data_extract
    Created on : 8 avr. 2013, 16:03:48
    Author     : Enji
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <title>Data Extract</title>
        <meta content="TRAN-NGUYEN" name="author" />
        <meta content="Telephony Reports" name="description" />
        <meta content="width=device-width, initial-scale=1.0" name="viewport">
            <link href="inc/pictures/icone.png" rel="shortcut icon" type="image/x-icon" />
            <link href="inc/pictures/icone.png" rel="icon" type="image/x-icon" />
            <link href="styles/styles.css" rel="stylesheet" type="text/css" />
            <link rel="stylesheet" href="styles/styles.css" type="text/css" media="screen"/>  
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
                <img src="inc/pictures/extract.png" alt="" width="50px" style="margin-top:0px;"/> &nbsp  Data Extract
            </h2><br />
            <p>In this module, you could download tha data of a report.
                Select below the mode of data that you want:</p>

            <h3>
                <img src="inc/pictures/workProgress.png" alt="Unit Contract" width="35px" style="margin-bottom:-5px;"/> &nbsp Actions
            </h3>
            <table class="tableDisplayOption">
                <tr>
                    <div id="displayExtractReport">
                        <td>
                            <a href="Download_Extract">
                                <h6><img src="inc/pictures/excel.png" alt="" width="30px" style="margin-bottom: -5px;" /> &nbsp Export in Excel Mode</h6>
                                <p>This part let you to export a report into a Microsoft Excel document.</p>
                            </a>
                        </td>
                    </div>
                </tr>
            </table><br /><br />
        </div>
        <!---------------------- CONTENT END ---------------------------->
        <!--------------------------------------------------------------->
        <!---------------------- FOOTER START --------------------------->
        <%@include file="footer/footer.jsp"%>
        <!---------------------- FOOTER END -----------------------------> 
        <!--------------------------------------------------------------->
    </body>
</html>
