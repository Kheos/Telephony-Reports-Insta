<%-- 
    Document   : delete_unit_reports
    Created on : 6 nov. 2012, 09:46:26
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
                <img src="inc/pictures/trash.png" alt="" width="70px" style="margin-top:-15px;"/> &nbsp  Delete a Unit Reports Step 1/3
            </h2>
            <div id="filter" style=" width:960px; margin:auto; padding: 5px;">
                <form action="Delete_Unit_Reports_Confirm" method="GET" class="formContact">
                    <label>
                        <span>Unit Contract :</span>
                    <select name="nameUnitReports" id="nameUnitReports" tabindex="10" style="width:150px; margin-right: 20px;">
                            ${ messageNameUnitReports }
                    </select>
                    </label>
                    <input id="buttonDelete" class="button" type="submit" value="Next >" tabindex="30" />
                </form>
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
