<%-- 
    Document   : unit_reports
    Created on : 6 nov. 2012, 09:33:41
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
        <script language="Javascript">
            function modifyUnitContractSelectOperation(){
                var nameUnitReports = document.getElementById("nameUnitReports").value; 
                if (nameUnitReports != "") { 
                    document.getElementById("operationModifyUnitReports").style.display="block"; 
                } else{ 
                    document.getElementById("operationModifyUnitReports").style.display="none"; 
                } 
            } 
        
        function modifyUnitContractValidate() { 
            var operationModifyUnitReports = document.getElementById("operationModifyUnitReports").value; 
            if (operationModifyUnitReports != "") { 
                document.getElementById("ButtonNext").style.display="block"; 
            } else{ 
                document.getElementById("ButtonNext").style.display="none"; 
            } 
        } 
        </script>
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
                <img src="inc/pictures/edit.png" alt="" width="70px" style="margin-top:-15px;"/> &nbsp  Modify Unit Contract, step 1/2
            </h2><br />
            <p>Select below the Unit Contract and the action to realize :</p>
            <div id="filter" style=" width:960px; margin:auto; padding: 5px;">
                <form action="Modify_Unit_Reports_Site" method="GET" class="formContact">
                    <label><span>Unit Contract :</span>
                        <select name="nameUnitReports" id="nameUnitReports" tabindex="10" style="width:290px; margin-right: 20px;" onclick="modifyUnitContractSelectOperation();">
                            <option value="">- Choose a contract -</option>
                            ${ messageNameUnitReports }
                        </select>
                    </label><br />
                    <div id="operationModifyUnitReports" style="display: none;">
                        <label><span>Operation :</span>
                            <select name="operationUnitReports" id="operationUnitReports" tabindex="10" style="width:290px; margin-right: 20px;" onclick="modifyUnitContractValidate();">
                                <option value="">- Choose an operation -</option>
                                <option value="addSites">Add Sites</option>	   
                                <option value="removeSites">Remove Sites</option>
                            </select>
                        </label><br />
                    </div>
                    <div id="ButtonNext" style="display:none;">
                        <input id="buttonModify" class="button" type="submit" value="Next >" tabindex="30" style="float:right; margin-top:15px;"/>
                    </div>
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

