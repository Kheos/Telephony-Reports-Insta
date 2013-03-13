<%-- 
    Document   : faq
    Created on : 29 oct. 2012, 10:54:32
    Author     : Enji
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <title>Support Reports</title>
        <link href="styles/styles.css" rel="stylesheet" type="text/css" />
        <link rel="stylesheet" href="styles/styles.css" type="text/css" media="screen"/>
        <link rel="shortcut icon" href="pictures/oAlstom.ico" type="image/x-icon"/> 
        <link rel="icon" href="pictures/oAlstom.ico" type="image/x-icon"/>   
        <style type="text/css">
            .step{
                font-weight: bold;
                text-decoration: underline;
                color:#FFFFFF;
            }
            .faq{
                color:white;
                text-align: left;
            }
            .imgFAQ{
                border: 1px solid black;
            }
            .warning{
                color:red;
                font-weight: bold;
                font-size: 14px;
            }
        </style>
        <script language="Javascript"> 
            function visibilite(thingId){
                var targetElement;
                targetElement = document.getElementById(thingId) ;
                if (targetElement.style.display == "none")
                {
                    targetElement.style.display = "" ;
                } else {
                    targetElement.style.display = "none" ;
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
            <h2>
                <img src="inc/pictures/help.png" alt="" width="50px" style="margin-top:-8px;"/> &nbsp  Support Reports
            </h2><br />
            <table style="width:960px;">
                <h3>Table of Content</h3>
                <tr>
                    <td style="padding:0px 0px 0 0px;">

                        <h4>Reports</h4>
                        <ul>
                            <li  class="buttonFAQ"><a href="javascript:visibilite('add_report');"  class="aFAQ" >How add a report ?</a></li>
                            <div id="add_report" class="texte_faq" style="display:none;">
                                <h5>How add a report ?</h5>
                                <div style="width:800px; margin:auto; text-align:center;">
                                    <p class="warning"><img src="inc/pictures/warning.png"  alt="" width="60px" style="margin-bottom:-17px;" />   You must creat your Unit Contract before to register the data of the report.</p><br />
                                    <p class="warning"> All reports must be complete in Euros (€).</p><br />
                                    <p class="faq"><span class="step">Step 1 : </span>Select into the form the name of the Unit Contract, a fiscal year, a month and click on "Search" (figure 1)</p><br />
                                    <img src="inc/pictures/process/addReports1.jpg" class="imgFAQ" alt="" width="600px" /><br />
                                    <p class="faq"><span class="step">Step 2 : </span>If the Reports is notify in red, it means that it's not register. Click on "Show the report" to complete it. (figure 2).</p><br />
                                    <img src="inc/pictures/process/addReports2.jpg" class="imgFAQ" alt="" width="600px" /><br />

                                    <p class="faq"><span class="step">Step 3 :</span> Complete the data into the table and click on "Save the report" (figure 3)</p><br />
                                    <img src="inc/pictures/process/addReports3.jpg" class="imgFAQ" alt="" width="600px" /><br />
                                    <p class="faq">When your report is complete with success, it becomes green (figure 4).</p><br />
                                    <img src="inc/pictures/process/addReports4.jpg" class="imgFAQ" alt="" width="600px" /><br /><br />
                                </div>
                            </div>
                            <li class="buttonFAQ"><a href="javascript:visibilite('modify_report');" class="aFAQ" >How modify a report ?</a></li>
                            <div id="modify_report" class="texte_faq" style="display:none;">
                                <h5>How modify a report ?</h5>
                                <div style="width:800px; margin:auto; text-align:center;">
                                    <p class="faq"><span class="step">Step 1 : </span>Select into the form the name of the Unit Contract, a fiscal year, a month and click on "Search" (figure 1)</p><br />
                                    <img src="inc/pictures/process/addReports1.jpg" class="imgFAQ" alt="" width="600px" /><br />
                                    <p class="faq"><span class="step">Step 2 : </span>Select a report. Click on "Edit the report" to update it. (figure 2).</p><br />
                                    <img src="inc/pictures/process/modifyReports1.jpg" class="imgFAQ" alt="" width="600px" /><br />

                                    <p class="faq"><span class="step">Step 3 :</span> Complete the data into the table and click on "Save the report" (figure 3)</p><br />
                                    <img src="inc/pictures/process/modifyReports2.jpg" class="imgFAQ" alt="" width="600px" /><br />
                                    <p class="faq">When your report is complete with success, it becomes green (figure 4).</p><br />
                                    <img src="inc/pictures/process/addReports4.jpg" class="imgFAQ" alt="" width="600px" /><br /><br />
                                </div>                                
                            </div>
                        </ul><br />
                        <h4>Unit Contract</h4>
                        <ul>
                            <li class="buttonFAQ"><a href="javascript:visibilite('add_contract');"  class="aFAQ">How add a unit contract ?</a></li>
                            <div id="add_contract" class="texte_faq" style="display:none;">
                                <h5>How add a unit contract ?</h5>
                                <div style="width:800px; margin:auto; text-align:center;">
                                    <p class="faq"><span class="step">Step 1 : </span>Click on "Add" in the Unit Contract section (figure 1)</p><br />
                                    <img src="inc/pictures/process/addUnitReports3.jpg" class="imgFAQ" alt="" width="600px" /><br />
                                    <p class="faq"><span class="step">Step 2 : </span>Complete the Name's field, register the type of your contract and the country which is concern (figure 2)</p>
                                    <p class="faq">Now, click on "Next >"</p><br />
                                    <img src="inc/pictures/process/addUnitReports1.jpg" class="imgFAQ" alt="" width="600px" /><br />

                                    <p class="faq"><span class="step">Step 3 :</span> Select sites which  will be indicated in your contract, and click on "Add" (figure 3)</p><br />
                                    <img src="inc/pictures/process/addUnitReports2.jpg" class="imgFAQ" alt="" width="600px" /><br /><br />
                                    <p class="faq">Your Unit Contract has been created.</p><br />
                                </div>
                            </div>
                            <li class="buttonFAQ"><a href="javascript:visibilite('mofiy_contract');"  class="aFAQ">How modify a unit contract ?</a></li>
                            <div id="mofiy_contract" class="texte_faq" style="display:none;">
                                <h5>How modify a unit contract ?</h5>
                                <div style="width:800px; margin:auto; text-align:center;">
                                    <img src="inc/pictures/work.jpg" alt="" width="160px" style=""/><br />
                                    <p style="color:#ffffff; font-size:26px;">Sorry, We are currently working on this process</p>
                                </div>
                            </div>
                            <li class="buttonFAQ"><a href="javascript:visibilite('delete_contract');"  class="aFAQ">How delete a unit contract ?</a></li>
                            <div id="delete_contract" class="texte_faq" style="display:none;">
                                <h5>How delete a unit contract ?</h5>
                                <div style="width:800px; margin:auto; text-align:center;">
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
                            </div>
                        </ul><br />
                        <h4>Export</h4>
                        <ul>
                            <li class="buttonFAQ"><a href="javascript:visibilite('export_report');"  class="aFAQ">How export a report ?</a></li>
                            <div id="export_report" class="texte_faq" style="display:none;">
                                <h5>How export a report ?</h5>
                                <div style="width:800px; margin:auto; text-align:center;">
                                    <img src="inc/pictures/work.jpg" alt="" width="160px" style=""/><br />
                                    <p style="color:#ffffff; font-size:26px;">Sorry, We are currently working on this process</p>
                                </div>
                            </div><br />
                        </ul><br />
                    </td>
                </tr>
            </table><br />
            <div style="width:960px; margin:auto; text-align:center;">
                <p>If you encounter other problems, contact us : <br /><br />
                    <a href="Support" class="buttonFAQ">here</a></p>
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
