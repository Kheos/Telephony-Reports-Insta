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
        <meta content="TRAN-NGUYEN" name="author" />
        <meta content="Telephony Reports" name="description" />
        <meta content="width=device-width, initial-scale=1.0" name="viewport">
            <link href="inc/pictures/icone.png" rel="shortcut icon" type="image/x-icon" />
            <link href="inc/pictures/icone.png" rel="icon" type="image/x-icon" />  
            <script language="Javascript">
                function modifyUnitContractSelectOperation(){
                    var nameUnitReports = document.getElementById("nameUnitReports").value; 
                    if (nameUnitReports != "") { 
                        document.getElementById("operationModifyUnitReports").style.display="block"; 
                    } else{ 
                        document.getElementById("operationModifyUnitReports").style.display="none"; 
                    } 
                }
                function appearsAddReport(){
                    var valeur_visibility = document.getElementById("resultAddReport").style.display;
                    if (valeur_visibility == "none"){
                        document.getElementById("resultAddReport").style.display = "block";

                    } else {
                        document.getElementById("resultAddReport").style.display = "none";
                    }
                }
            </script>
            <style type='text/css'>
                .tableeN {
                    font-family: Arial; 
                    font-size: 12px;  
                    width: 60%; 
                    text-align: left; 
                    border-collapse: collapse;
                }
                .tableeN th {
                    text-align: center; 
                    font-size: 13px;
                    font-weight: bold; 
                    padding: 8px; 
                    border-top: 2px solid white; 
                    background: #034694 url('gradhead.png') repeat-x; 
                    border-bottom: 1px solid #fff; 
                    color: #FFFFFF;
                }
                .tableeN td {
                    text-align: center;
                    padding: 8px;
                    border-bottom: 1px solid #fff;
                    color: #034694;
                    border-top: 1px solid #fff;
                    background: #e8edff url('gradback.png') repeat-x;
                }
                .tableeN tbody tr:hover td {
                    background: #d0dafd url('gradhover.png') repeat-x;
                    color: #034694;
                }
                .tableeN a:hover {
                    text-decoration:underline;
                }
                #form {
                    border: 1px black solid;
                    padding: 10px;
                }
            </style>
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
                <img src="inc/pictures/Database.png" alt="" width="70px" style="margin-top:-15px;"/> &nbsp  Unit Contract
            </h2><br />
            <h3>
                <img src="inc/pictures/workProgress.png" alt="Unit Contract" width="35px" style="margin-bottom:-5px;"/> &nbsp Actions
            </h3>
            <p>This part let the management of the Unit Contract.Choose below an action to realize :</p><br />
            <table class="tableDisplayOption">
                <tr>
                    <div id="displayAddReport">
                        <td>
                            <a href="Add_Unit_Reports">
                                <h6><img src="inc/pictures/add.png" alt="" width="30px" style="margin-bottom: -5px;" /> &nbsp Add a Unit Contract</h6>
                                <p>This part let you to create a Unit Contract.</p>
                            </a>
                        </td>
                    </div>
                    <div id="displayModifyUnitContract"> 
                        <td>
                            <a href="Modify_Unit_Reports">
                                <h6><img src="inc/pictures/edit.png" alt="" height="30px" style="margin-bottom: -5px;" /> &nbsp Modify a Unit Contract</h6>
                                <p>This part let you to modify a Unit Contract.</p>
                            </a>
                        </td>
                    </div>
                </tr>
                <tr>
                    <div id="displayDeleteUnitContract"> 
                        <td>
                            <a href="Delete_Unit_Reports">
                                <h6><img src="inc/pictures/trash.png" alt="" width="30px" style="margin-bottom: -5px;" /> &nbsp Delete a Unit Contract</h6>
                                <p>This part let you to delete a Unit Contract.</p>
                            </a>
                        </td>
                    </div>
                </tr>
            </table><br /><br />
        </div>
        <!---------------------- CONTENT END ---------------------------->

    </body>
    <!--------------------------------------------------------------->
    <!---------------------- FOOTER START --------------------------->
    <%@include file="footer/footer.jsp"%>
    <!---------------------- FOOTER END -----------------------------> 
    <!--------------------------------------------------------------->
</html>
