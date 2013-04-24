<%-- 
    Document   : delete_unit_reports_confirm
    Created on : 20 nov. 2012, 14:49:14
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
                <img src="inc/pictures/trash.png" alt="" width="70px" style="margin-top:-15px;"/> &nbsp  Delete a Unit Reports Step 2/3
            </h2>
            <div id="filter" style=" width:930px; margin:auto; padding: 5px;">
                <form action="Delete_Unit_Reports_Result" method="GET" class="formContact">
                    <p style="color:red;">Please, confirm the delete of the Unit Contract</p>
                    <label for="login"><span>Name :</span>
                        <input type="text" id="nameUnitReports" class="input_text" name="nameUnitReports" value="<%
							String parametreDisplayName = request.getParameter("nameUnitReports");
							out.println(parametreDisplayName);
                               %>" size="30" maxlength="60" readonly="true" />
                    </label><br />
                    <center>
                        <input id="buttonDelete" class="validButton" type="reset" value="Cancel" OnClick="window.location.href='Unit_Reports'" tabindex="30" style="margin-right: 20px;"/>
                        <input id="buttonDelete" class="validButton" type="submit" value="Confirm" tabindex="30" style="margin-right: 20px;"/>
                    </center>
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
