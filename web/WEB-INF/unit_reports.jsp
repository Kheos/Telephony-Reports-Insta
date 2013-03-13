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
            <h3>Actions</h3>
            <p>Choose below an action to realize :</p><br />
            <div style="width:960px; margin:auto;">
                <center>
                    <table style="display: inline-block; text-align: center; color:black; margin-bottom: 20px;">
                        <tr>
                            <td style="padding:3px; padding: 15px 80px 0 80px; border: none; text-align: center;"><center><a href="Add_Unit_Reports"><img src="inc/pictures/add.png" alt="" width="35px" style="margin-top:-15px;"/></a></center></td>
                            <td style="padding:3px;padding: 15px 80px 0 80px; border: none;  text-align: center;"><center><a href="Delete_Unit_Reports"><img src="inc/pictures/trash.png" alt="" width="35px" style="margin-top:-15px;"/></a></center></td>
                            <td style="padding:3px; padding: 15px 80px 0 80px; border: none;  text-align: center;"><center><a href="Modify_Unit_Reports"><img src="inc/pictures/edit.png" alt="" width="35px" style="margin-top:-15px;"/></a></center></td>
                        </tr>
                        <tr>
                            <td style="padding:3px; padding:10px 80px 0 80px; border: none;  text-align: center;"><center><a href="Add_Unit_Reports"><p><strong>Add</strong></p></a></center></td>
                            <td style="padding:3px; padding:10px 80px 0 80px; border: none;  text-align: center;"><center><a href="Delete_Unit_Reports"><p><strong>Delete</strong></p></a></center></td>
                            <td style="padding:3px; padding:10px 80px 0 80px; border: none;  text-align: center;"><center><a href="Modify_Unit_Reports"><p><strong>Modify</strong></p></a></center></td>
                        </tr>  
                    </table>
                </center>
            </div>
            <h3>Existing Contracts</h3>
            <p>Find below the existing contracts :</p>
            <form action="Modify_Unit_Reports_Site" method="GET" class="formContact">

                    <label for="site">

                        <table style="margin-left:40px;">
                            <tr>
                                <td style="width:40px;"><h3 style="text-align:center; color:#FFFFFF;">Select</h3></td><td><h3 style="text-align:center; padding-left:10px; color:#FFFFFF;">Contract name</h3></td><td><h3 style="text-align:center; padding-left:15px; color:#FFFFFF;">Contract Type</h3></td>
                            </tr>
                            ${ messageListUnitReports }
                        </table>

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
            </form>
        </div>
        <!---------------------- CONTENT END ---------------------------->

    </body>
    <!--------------------------------------------------------------->
    <!---------------------- FOOTER START --------------------------->
    <%@include file="footer/footer.jsp"%>
    <!---------------------- FOOTER END -----------------------------> 
    <!--------------------------------------------------------------->
</html>
