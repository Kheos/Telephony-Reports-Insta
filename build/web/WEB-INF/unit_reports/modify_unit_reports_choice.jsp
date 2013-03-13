<%-- 
    Document   : modify_unit_reports_choice
    Created on : 26 nov. 2012, 11:24:20
    Author     : Enji
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <title>Unit Reports</title>
        <link href="inc/styles/styles.css" rel="stylesheet" type="text/css" />
        <link rel="stylesheet" href="inc/styles/styles.css" type="text/css" media="screen"/>
        <link rel="shortcut icon" href="/pictures/oAlstom.ico" type="image/x-icon"/> 
        <link rel="icon" href="/pictures/oAlstom.ico" type="image/x-icon"/>   
    </head>
    <body>     
        <!---------------------------------------------------------------->
        <!---------------------- HEADER START ---------------------------->
        <%@include file="../header/header.jsp"%>
        <!---------------------- HEADER END ------------------------------>
        <!---------------------------------------------------------------->
        <!---------------------- CONTENT START --------------------------->       
        <div class="content">    
            <h2>
                <img src="inc/pictures/edit.png" alt="" width="70px" style="margin-top:-15px;"/> &nbsp  Modify Unit Reports
            </h2>
            <p>Select below an action to realize :</p>

            <div style="width:960px; margin:auto;">
                <center><br />
                    <table>
                        <tr>
                            <td style="padding: 15px 15px 0 15px; border: none;"><center><a href="Modify_Unit_Reports"><img src="inc/pictures/addSite.png" alt="" width="55px" style="margin-top:-15px;"/></a></center></td>
                            <td style="padding: 15px 15px 0 15px; border: none;"><center><a href="Delete_Unit_Reports"><img src="inc/pictures/deleteSite.png" alt="" width="55px" style="margin-top:-15px;"/></a></center></td>   
                        </tr>
                        <tr>
                            <td style="padding:0px 15px 0 15px; border: none;"><center><a href="Add_Unit_Reports"><p><strong>Add sites to a contract</strong></p></a></center></td>
                            <td style="padding:0px 15px 0 15px; border: none;"><center><a href="Delete_Unit_Reports"><p><strong>Delete sites to a contract</strong></p></a></center></td>
                        </tr>  
                    </table>
                </center>
            </div>
        </div>
        <!---------------------- CONTENT END ---------------------------->
        <!--------------------------------------------------------------->
        <!---------------------- FOOTER START --------------------------->
        <%@include file="../footer/footer.jsp"%>
        <!---------------------- FOOTER END -----------------------------> 
        <!--------------------------------------------------------------->
    </body>
</html>

