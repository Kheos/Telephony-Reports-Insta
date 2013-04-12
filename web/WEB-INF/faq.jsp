<%-- 
    Document   : faq
    Created on : 29 oct. 2012, 10:54:32
    Author     : Enji
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <title>FAQ</title>
        <link href="styles/styles.css" rel="stylesheet" type="text/css" />
        <link rel="stylesheet" href="styles/styles.css" type="text/css" media="screen"/>
        <meta content="TRAN-NGUYEN" name="author" />
        <meta content="Telephony Reports" name="description" />
        <meta content="width=device-width, initial-scale=1.0" name="viewport">
            <link href="inc/pictures/icone.png" rel="shortcut icon" type="image/x-icon" />
            <link href="inc/pictures/icone.png" rel="icon" type="image/x-icon" /> 
            <style type="text/css">
                .step{
                    font-weight: bold;
                    text-decoration: underline;
                }
                .faq{
                    color:white;
                    text-align: left;
                }
                .imgFAQ{
                    border: 1px solid #E9E8E2;
                    margin:auto;
                    width:550px;
                    text-align:center;
                }
                .warning{
                    color:red;
                    font-weight: bold;
                    font-size: 14px;
                }

            </style>
            <script language="Javascript"> 
                function appearsAddReport(){
                    var valeur_visibility = document.getElementById("resultAddReport").style.display;
                    if (valeur_visibility == "none"){
                        document.getElementById("resultAddReport").style.display = "block";
                        document.getElementById("resultModifyReport").style.display = "none";
                        document.getElementById("resultAddUnitContract").style.display = "none";
                        document.getElementById("resultModifyUnitContract").style.display = "none";
                        document.getElementById("resultDeleteUnitContract").style.display = "none";
                        document.getElementById("resultExportReport").style.display = "none";
                    } else {
                        document.getElementById("resultAddReport").style.display = "none";
                    }
                }   
                function appearsModifyReport(){
                    var valeur_visibility = document.getElementById("resultModifyReport").style.display;
                    if (valeur_visibility == "none"){
                        document.getElementById("resultModifyReport").style.display = "block";
                        document.getElementById("resultAddReport").style.display = "none";
                        document.getElementById("resultAddUnitContract").style.display = "none";
                        document.getElementById("resultModifyUnitContract").style.display = "none";
                        document.getElementById("resultDeleteUnitContract").style.display = "none";
                        document.getElementById("resultExportReport").style.display = "none";
                    } else {
                        document.getElementById("resultModifyReport").style.display = "none";
                    }
                }
                function appearsAddUnitContract(){
                    var valeur_visibility = document.getElementById("resultAddUnitContract").style.display;
                    if (valeur_visibility == "none"){
                        document.getElementById("resultAddUnitContract").style.display = "block";
                        document.getElementById("resultAddReport").style.display = "none";
                        document.getElementById("resultModifyReport").style.display = "none";
                        document.getElementById("resultModifyUnitContract").style.display = "none";
                        document.getElementById("resultDeleteUnitContract").style.display = "none";
                        document.getElementById("resultExportReport").style.display = "none";
                    } else {
                        document.getElementById("resultAddUnitContract").style.display = "none";
                    }
                }
                function appearsModifyUnitContract(){
                    var valeur_visibility = document.getElementById("resultModifyUnitContract").style.display;
                    if (valeur_visibility == "none"){
                        document.getElementById("resultModifyUnitContract").style.display = "block";
                        document.getElementById("resultAddReport").style.display = "none";
                        document.getElementById("resultModifyReport").style.display = "none";
                        document.getElementById("resultAddUnitContract").style.display = "none";
                        document.getElementById("resultDeleteUnitContract").style.display = "none";
                        document.getElementById("resultExportReport").style.display = "none";
                    } else {
                        document.getElementById("resultModifyUnitContract").style.display = "none";
                    }
                }
                function appearsDeleteUnitContract(){
                    var valeur_visibility = document.getElementById("resultDeleteUnitContract").style.display;
                    if (valeur_visibility == "none"){
                        document.getElementById("resultDeleteUnitContract").style.display = "block";
                        document.getElementById("resultAddReport").style.display = "none";
                        document.getElementById("resultModifyReport").style.display = "none";
                        document.getElementById("resultAddUnitContract").style.display = "none";
                        document.getElementById("resultModifyUnitContract").style.display = "none";
                        document.getElementById("resultExportReport").style.display = "none";
                    } else {
                        document.getElementById("resultDeleteUnitContract").style.display = "none";
                    }
                }
                function appearsExportReport(){
                    var valeur_visibility = document.getElementById("resultExportReport").style.display;
                    if (valeur_visibility == "none"){
                        document.getElementById("resultExportReport").style.display = "block";
                        document.getElementById("resultAddReport").style.display = "none";
                        document.getElementById("resultModifyReport").style.display = "none";
                        document.getElementById("resultAddUnitContract").style.display = "none";
                        document.getElementById("resultModifyUnitContract").style.display = "none";
                        document.getElementById("resultDeleteUnitContract").style.display = "none";
                    } else {
                        document.getElementById("resultExportReport").style.display = "none";
                    }
                }
            </script>
    </head>
    <body>
        <!---------------------------------------------------------------->
        <!---------------------- HEADER START ---------------------------->
        <%@include file="header/header.jsp"%>
        <!---------------------- HEADER END ------------------------------>
        <!---------------------------------------------------------------->
        <!---------------------- CONTENT START --------------------------->       
        <div class="content">
            <div id="resultAddReport" class="displayFaq" style="display: none;">
                <div style="margin-bottom:0px; margin-right:-10px; ">
                    <table style="float:right;">
                        <tr>
                            <td>
                                <center>
                                    <a href="Support" style="float:right;"><img src="inc/pictures/help.png" alt="" width="30px" style="float:right;"/><br /></a>
                                </center>
                            </td>
                            <td>
                                <center>
                                    <a href='#' onclick='appearsAddReport();'><img src="inc/pictures/close.png" alt="" width="30px" style="float:right;"/></a>
                                </center>
                            </td>
                        </tr>
                    </table>
                </div>
                <div style="width:800px; margin:auto; ">
                    <h3>How add a report ?</h3>
                    <p class="warning"><img src="inc/pictures/warning.png"  alt="" width="60px" style="margin-bottom:-17px;" />   You must creat your Unit Contract before to register the data of the report.</p><br />
                    <p class="warning"> All reports must be complete in Euros (€).</p><br />
                    <p><span class="step">Step 1 : </span>Select into the form the name of the Unit Contract, a fiscal year, a month and click on "Search" (figure 1)</p><br />
                    <img src="inc/pictures/process/addReports1.jpg" class="imgFAQ" alt="" /><br />
                    <p><span class="step">Step 2 : </span>If the Reports is notify in red, it means that it's not register. Click on "Show the report" to complete it. (figure 2).</p><br />
                    <img src="inc/pictures/process/addReports2.jpg" class="imgFAQ" alt="" /><br />
                    <p><span class="step">Step 3 :</span> Complete the data into the table and click on "Save the report" (figure 3)</p><br />
                    <img src="inc/pictures/process/addReports3.jpg" class="imgFAQ" alt="" /><br />
                    <p>When your report is complete with success, it becomes green (figure 4).</p><br />
                    <img src="inc/pictures/process/addReports4.jpg" class="imgFAQ" alt="" /><br /><br />
                </div>
                <h3><img src="inc/pictures/imagePc.jpg" alt="" width="30px" style="margin-bottom: -5px;" /> &nbsp Contact Us</h3>
            </div>
            <!-- --------------------------------------------------------------------- -->
            <div id="resultModifyReport" class="displayFaq" style="display: none;">
                <div style="margin-bottom:0px; margin-right:-10px;">
                    <table style="float:right;">
                        <tr>
                            <td>
                                <center>
                                    <a href="Support" style="float:right;"><img src="inc/pictures/help.png" alt="" width="30px" style="float:right;"/><br /></a>
                                </center>
                            </td>
                            <td>
                                <center>
                                    <a href='#' onclick='appearsModifyReport();'><img src="inc/pictures/close.png" alt="" width="30px" style="float:right;"/></a>
                                </center>
                            </td>
                        </tr>
                    </table>
                </div>
                <div style="width:800px; margin:auto; text-align:center;">
                    <h3>How modify a report ?</h3>
                    <p><span class="step">Step 1 : </span>Select into the form the name of the Unit Contract, a fiscal year, a month and click on "Search" (figure 1)</p><br />
                    <img src="inc/pictures/process/addReports1.jpg" class="imgFAQ" alt="" /><br />
                    <p><span class="step">Step 2 : </span>Select a report. Click on "Edit the report" to update it. (figure 2).</p><br />
                    <img src="inc/pictures/process/modifyReports1.jpg" class="imgFAQ" alt="" /><br />

                    <p><span class="step">Step 3 :</span> Complete the data into the table and click on "Save the report" (figure 3)</p><br />
                    <img src="inc/pictures/process/modifyReports2.jpg" class="imgFAQ" alt="" /><br />
                    <p>When your report is complete with success, it becomes green (figure 4).</p><br />
                    <img src="inc/pictures/process/addReports4.jpg" class="imgFAQ" alt="" /><br /><br />
                </div>     
                <h3><img src="inc/pictures/imagePc.jpg" alt="" width="30px" style="margin-bottom: -5px;" /> &nbsp Contact Us</h3>
            </div>
            <!-- --------------------------------------------------------------------- -->
            <div id="resultAddUnitContract" class="displayFaq" style="display: none;">
                <div style="margin-bottom:0px; margin-right:-10px;">
                    <table style="float:right;">
                        <tr>
                            <td>
                                <center>
                                    <a href="Support" style="float:right;"><img src="inc/pictures/help.png" alt="" width="30px" style="float:right;"/><br /></a>
                                </center>
                            </td>
                            <td>
                                <center>
                                    <a href='#' onclick='appearsAddUnitContract();'><img src="inc/pictures/close.png" alt="" width="30px" style="float:right;"/></a>
                                </center>
                            </td>
                        </tr>
                    </table>
                </div>
                <div style="width:800px; margin:auto; text-align:center;">
                    <h3>How add a unit contract ?</h3>
                    <p><span class="step">Step 1 : </span>Click on "Add" in the Unit Contract section (figure 1)</p><br />
                    <img src="inc/pictures/process/addUnitReports3.jpg" class="imgFAQ" alt="" /><br />
                    <p><span class="step">Step 2 : </span>Complete the Name's field, register the type of your contract and the country which is concern (figure 2)</p>
                    <p>Now, click on "Next >"</p><br />
                    <img src="inc/pictures/process/addUnitReports1.jpg" class="imgFAQ" alt="" /><br />
                    <p><span class="step">Step 3 :</span> Select sites which  will be indicated in your contract, and click on "Add" (figure 3)</p><br />
                    <img src="inc/pictures/process/addUnitReports2.jpg" class="imgFAQ" alt="" /><br /><br />
                    <p>Your Unit Contract has been created.</p><br />
                </div>
                <h3><img src="inc/pictures/imagePc.jpg" alt="" width="30px" style="margin-bottom: -5px;" /> &nbsp Contact Us</h3>
            </div>
            <!-- --------------------------------------------------------------------- -->
            <div id="resultModifyUnitContract" class="displayFaq" style="display: none;">
                <div style="margin-bottom:0px; margin-right:-10px;">
                    <table style="float:right;">
                        <tr>
                            <td>
                                <center>
                                    <a href="Support" style="float:right;"><img src="inc/pictures/help.png" alt="" width="30px" style="float:right;"/><br /></a>
                                </center>
                            </td>
                            <td>
                                <center>
                                    <a href='#' onclick='appearsModifyUnitContract();'><img src="inc/pictures/close.png" alt="" width="30px" style="float:right;"/></a>
                                </center>
                            </td>
                        </tr>
                    </table>
                </div>
                <div style="width:800px; margin:auto; text-align:center;">
                    <h3>How modify a unit contract ?</h3>
                    <p><span class="step">Step 1 : </span>Click on "Add" in the Unit Contract section (figure 1)</p><br />
                    <img src="inc/pictures/process/addUnitReports3.jpg" class="imgFAQ" alt="" /><br />
                    <p><span class="step">Step 2 : </span>Complete the Name's field, register the type of your contract and the country which is concern (figure 2)</p>
                    <p>Now, click on "Next >"</p><br />
                    <img src="inc/pictures/process/addUnitReports1.jpg" class="imgFAQ" alt="" /><br />
                    <p><span class="step">Step 3 :</span> Select sites which  will be indicated in your contract, and click on "Add" (figure 3)</p><br />
                    <img src="inc/pictures/process/addUnitReports2.jpg" class="imgFAQ" alt="" /><br /><br />
                    <p>Your Unit Contract has been created.</p><br />
                </div>
                <h3><img src="inc/pictures/imagePc.jpg" alt="" width="30px" style="margin-bottom: -5px;" /> &nbsp Contact Us</h3>
            </div>
            <!-- --------------------------------------------------------------------- -->
            <div id="resultDeleteUnitContract" class="displayFaq" style="display: none;">
                <div style="margin-bottom:0px; margin-right:-10px;">
                    <table style="float:right;">
                        <tr>
                            <td>
                                <center>
                                    <a href="Support" style="float:right;"><img src="inc/pictures/help.png" alt="" width="30px" style="float:right;"/><br /></a>
                                </center>
                            </td>
                            <td>
                                <center>
                                    <a href='#' onclick='appearsDeleteUnitContract();'><img src="inc/pictures/close.png" alt="" width="30px" style="float:right;"/></a>
                                </center>
                            </td>
                        </tr>
                    </table>
                </div>
                <div style="width:800px; margin:auto; text-align:center;"> 
                    <h3>How delete a unit contract ?</h3>
                    <p class="warning"><img src="inc/pictures/warning.png"  alt="" width="60px" style="margin-bottom:-17px;" />   If you delete a unit contract,  it's not possible to go back </p><br />
                    <p class="faq"><span class="step">Step 1 : </span>Click on "Delete" in the Unit Contract section (figure 1)</p><br />
                    <img src="inc/pictures/process/deleteUnitReports1.jpg" class="imgFAQ" alt="" width="600px" /><br />
                    <p class="faq"><span class="step">Step 2 : </span>Choose into the select list the name of your contract  (figure 2).</p>
                    <p class="faq">Now, click on "Next >"</p><br />
                    <img src="inc/pictures/process/deleteUnitReports2.jpg" class="imgFAQ" alt="" width="600px" /><br />

                    <p class="faq"><span class="step">Step 3 :</span> Confirm the removal of your unit contract by the way of the button "Confirm" (figure 3)</p><br />
                    <img src="inc/pictures/process/deleteUnitReports3.jpg" class="imgFAQ" alt="" width="600px" /><br /><br />
                    <p class="faq">Your Unit Contract has been deleted.</p><br />
                </div>
                <h3><img src="inc/pictures/imagePc.jpg" alt="" width="30px" style="margin-bottom: -5px;" /> &nbsp Contact Us</h3>
            </div>
            <!-- --------------------------------------------------------------------- -->
            <div id="resultExportReport" class="displayFaq" style="display: none;">
                <div style="margin-bottom:0px; margin-right:-10px;">
                    <table style="float:right;">
                        <tr>
                            <td>
                                <center>
                                    <a href="Support" style="float:right;"><img src="inc/pictures/help.png" alt="" width="30px" style="float:right;"/><br /></a>
                                </center>
                            </td>
                            <td>
                                <center>
                                    <a href='#' onclick='appearsExportReport();'><img src="inc/pictures/close.png" alt="" width="30px" style="float:right;"/></a>
                                </center>
                            </td>
                        </tr>
                    </table>
                </div>
                <div style="width:800px; margin:auto; text-align:center;"> 
                    <h3>How report a report ?</h3>
                    <img src="inc/pictures/imageCle.jpg" alt="" width="160px" style=""/><br />
                    <p style="font-size:26px;">Sorry, We are currently working on this process</p>
                </div>
                <h3><img src="inc/pictures/imagePc.jpg" alt="" width="30px" style="margin-bottom: -5px;" /> &nbsp Contact Us</h3>
            </div>
            <h2>
                <img src="inc/pictures/help.png" alt="" width="50px" style="margin-bottom:-10px;"/> &nbsp  FAQ
            </h2><br />
            <h3>
                <img src="inc/pictures/reportsPart.png" alt="Reports" width="35px" style="margin-bottom:-5px;"/> &nbsp Reports
            </h3>
            <table class="tableDisplayOption">
                <tr>
                    <div id="displayAddReport">
                        <td>
                            <a href="#" onclick="appearsAddReport();">
                                <h6><img src="inc/pictures/add.png" alt="" width="25px" style="margin-bottom: -5px;" /> &nbsp Add a Report</h6>
                                <p>This part help you to add a report.</p>
                            </a>
                        </td>
                    </div>
                    <div id="displayModifyReport"> 
                        <td>
                            <a href="#" onclick="appearsModifyReport();">
                                <h6><img src="inc/pictures/edit.png" alt="" height="25px" style="margin-bottom: -5px;" /> &nbsp Modify a Report</h6>
                                <p>This part help you to modify a report.</p>
                            </a>
                        </td>
                    </div>
                </tr>
            </table><br /><br />
            <h3>
                <img src="inc/pictures/Database.png" alt="Unit Contract" width="35px" style="margin-bottom:-5px;"/> &nbsp Unit Contract
            </h3>
            <table class="tableDisplayOption">
                <tr>
                    <div id="displayAddReport">
                        <td>
                            <a href="#" onclick="appearsAddUnitContract();">
                                <h6><img src="inc/pictures/add.png" alt="" width="25px" style="margin-bottom: -5px;" /> &nbsp Add a UnitContract</h6>
                                <p>This part help you to create a Unit Contract.</p>
                            </a>
                        </td>
                    </div>
                    <div id="displayModifyUnitContract"> 
                        <td>
                            <a href="#" onclick="appearsModifyUnitContract();">
                                <h6><img src="inc/pictures/edit.png" alt="" height="25px" style="margin-bottom: -5px;" /> &nbsp Modify a UnitContract</h6>
                                <p>This part help you to modify a Unit Contract.</p>
                            </a>
                        </td>
                    </div>
                </tr>
                <tr>
                    <div id="displayDeleteUnitContract"> 
                        <td>
                            <a href="#" onclick="appearsDeleteUnitContract();">
                                <h6><img src="inc/pictures/trash.png" alt="" width="25px" style="margin-bottom: -5px;" /> &nbsp Delete a UnitContract</h6>
                                <p>This part help you to delete a Unit Contract.</p>
                            </a>
                        </td>
                    </div>
                </tr>
            </table><br /><br />
            <h3>
                <img src="inc/pictures/Download.png" alt="Export" width="35px" style="margin-bottom:-5px;"/> &nbsp Export
            </h3>
            <table class="tableDisplayOption">
                <tr>
                    <div id="displayExportReport">
                        <td>
                            <a href="#" onclick="appearsExportReport();">
                                <h6><img src="inc/pictures/add.png" alt="" width="25px" style="margin-bottom: -5px;" /> &nbsp Create a report</h6>
                                <p>This part help you to learn how export an export in a Microsoft Excel document.</p>
                            </a>
                        </td>
                    </div>
                </tr>    
            </table><br /><br />
            <h3>
                <img src="inc/pictures/imagePc.jpg" alt="Support" width="35px" style="margin-bottom:-5px;"/> &nbsp Other Problems
            </h3>
            <table class="tableDisplayOption">
                <tr>
                    <div id="displayExportReport">
                        <td>
                            <a href="Support" >
                                <h6><img src="inc/pictures/contact.png" alt="" width="30px" style="margin-bottom: -5px;" /> &nbsp Contact the support</h6>
                                <p>If you encounter other problems, contact us !</p>
                            </a>
                        </td>
                    </div>
                </tr>    
            </table><br />
        </div>
        <!---------------------- CONTENT END ---------------------------->
        <!--------------------------------------------------------------->
        <!---------------------- FOOTER START --------------------------->
        <%@include file="footer/footer.jsp"%>
        <!---------------------- FOOTER END -----------------------------> 
        <!--------------------------------------------------------------->
    </body>
</html>
