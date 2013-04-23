<%-- 
    Document   : add.jsp
    Created on : 31 oct. 2012, 16:25:28
    Author     : Nico
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta content="TRAN-NGUYEN" name="author" />
        <meta content="Telephony Reports" name="description" />
        <meta content="width=device-width, initial-scale=1.0" name="viewport">
        <link href="inc/pictures/icone.png" rel="shortcut icon" type="image/x-icon" />
        <link href="inc/pictures/icone.png" rel="icon" type="image/x-icon" />
        <title>JSP Page</title>
        <style>
            table {
                display: inline-block;
                text-align: center;
                color: black;
                margin-bottom: 20px;
            }

            td {
                text-align: center;
                padding: 3px;
                min-width: 100px;
                border: 1px solid black;
            }

            .local {
                background-color: orange;
            }

            .international {
                background-color: #034694;
            }

            .total {
                background-color: red;
                font-weight: bold;
            }

            .total_cost {
                color: red;
            }

            #form {
                border: 1px black solid;
                padding: 10px;
            }
        </style>
        <script language="JavaScript" type="text/javascript">
            function slideDown (element, duration) {
                var obj = document.getElementById(element);
                var finalHeight;
                var s = obj.style;
                s.height = '';
                finalHeight = obj.offsetHeight;
                s.height = '0px';
                s.visibility = 'visible';
                var y = 0;
                var framerate = 10;
                var interval = duration / framerate;
                var totalFrames = duration / interval;
                var heightIncrement = finalHeight / totalFrames;
                var tween = function () {
                    y += heightIncrement;
                    s.height = y+'px';
                    if (y < finalHeight) {
                        setTimeout(tween, interval);
                    }
                }
                tween();
            }
			
            function slideUp (element, duration) {
                var obj = document.getElementById(element);
                var finalHeight;
                var s = obj.style;
                finalHeight = '0';
                var y = obj.offsetHeight;
                var framerate = 10;
                var interval = duration / framerate;
                var totalFrames = duration / interval;
                var heightIncrement = y / totalFrames;
                var tween = function () {
                    y -= heightIncrement;
                    s.height = y+'px';
                    if (y > finalHeight) {
                        setTimeout(tween, interval);
                    }
                    else {
                        s.height = '0px'
                        s.visibility = 'hidden';
                    }
                }
                tween();
            }
			
            function generateForm() {
				
                var form = document.getElementById("hidden").innerHTML;
				
                document.getElementById('buttonGenerate').setAttribute('disabled', 'disabled');
				
                document.getElementById("generated_form").innerHTML = form;
                var allSelect = document.getElementsByTagName('select');
				
                for (var i = 0; i < allSelect.length; i++) {
                    allSelect[i].setAttribute('disabled','disabled');
                }
            }
				
            function resetForm() {
				
                document.getElementById('buttonGenerate').removeAttribute('disabled');
				
                document.getElementById("generated_form").innerHTML = form;
                var allSelect = document.getElementsByTagName('select');
				
                for (var i = 0; i < allSelect.length; i++) {
                    allSelect[i].removeAttribute('disabled');
                }
				
                document.getElementById("generated_form").innerHTML = "";
				
            }
        </script>
    </head>
    <body onload="resetForm();">
        <!---------------------------------------------------------------->
        <!---------------------- HEADER START ---------------------------->
        <%@include file="header/header.jsp"%>
        <!---------------------- HEADER END ------------------------------>
        <!---------------------------------------------------------------->
        <!---------------------- CONTENT START --------------------------->   
        <div class="content">
            <h2>Add a report :</h2>
            <div id="id_element" style="border: 1px black solid;">
                <input type="button" value="Down" onClick="slideDown('id_element', 200);"/>
                <input type="button" value="Up" onClick="slideUp('id_element', 200);"/>
                <div id="slide" style="height: 0px; overflow: hidden;">
                    <center>
                        <table> 
                            <tr>
                                <td></td>
                                <td colspan="3" style="font-weight: bold;">Fix Telephony</td>
                            </tr>
                            <tr>
                                <td style="font-weight: bold;">Number of lines</td>
                                <td colspan="3"><input type="text" id="fixeLines"/></td>
                            </tr>
                            <tr>
                                <td rowspan="2"></td>
                                <td rowspan="2" class="local">Local Calls</td>
                                <td rowspan="2" class="international">International Calls</td>
                                <td rowspan="2" class="total">TOTAL</td>
                            </tr>
                            <tr>
                            </tr>
                            <tr>
                                <td style="font-weight: bold;">Fix Costs</td> 
                                <td><input type="text" id="fixeLocalCallsFix"/></td>
                                <td><input type="text" id="fixeInternationalCallsFix"/></td>
                                <td class="total_cost"><input type="text" id="fixeTotalFix"/></td>
                            </tr>
                            <tr>
                                <td style="font-weight: bold;">Variable Costs</td> 
                                <td><input type="text" id="fixeLocalCallsVar"/></td>
                                <td><input type="text" id="fixeInternationalCallsVar"/></td>
                                <td class="total_cost"><input type="text" id="fixeTotalVar"/></td>
                            </tr>        
                        </table>
                    </center>
                    <center>
                        <table> 
                            <tr>
                                <td></td>
                                <td colspan="5" style="font-weight: bold;">Mobile Telephony</td>
                            </tr>
                            <tr>
                                <td style="font-weight: bold;">Number of lines</td>
                                <td colspan="5"><input type="text" id="mobileLines"/></td>
                            </tr>
                            <tr>
                                <td rowspan="2"></td>
                                <td colspan="2" class="local">Local</td>
                                <td colspan="2" class="international">International</td>
                                <td rowspan="2" class="total">TOTAL</td>
                            </tr>
                            <tr>
                                <td class="local">Calls</td>
                                <td class="local">Data</td>
                                <td class="international">Calls</td>
                                <td class="international">Data</td>
                            </tr>
                            <tr>
                                <td style="font-weight: bold;">Fix Costs</td>
                                <td><input type="text" id="mobileLocalCallsFix"/></td>
                                <td><input type="text" id="mobileLocalDataFix"/></td>
                                <td><input type="text" id="mobileInternationalCallsFix"/></td>
                                <td><input type="text" id="mobileInternationalDataFix"/></td>
                                <td class="total_cost"><input type="text" id="mobileTotalFix"/></td>
                            </tr>
                            <tr>
                                <td style="font-weight: bold;">Variable Costs</td>
                                <td><input type="text" id="mobileLocalCallsVar"/></td>
                                <td><input type="text" id="mobileLocalDataVar"/></td>
                                <td><input type="text" id="mobileInternationalCallsVar"/></td>
                                <td><input type="text" id="mobileInternationalDataVar"/></td>
                                <td class="total_cost"><input type="text" id="mobileTotalVar"/></td>
                            </tr>        
                        </table>
                    </center>
                    <center>
                        <table> 
                            <tr>
                                <td></td>
                                <td colspan="3" style="font-weight: bold;">3G Cards</td>
                            </tr>
                            <tr>
                                <td style="font-weight: bold;">Number of lines</td>
                                <td colspan="3"><input type="text" id="3gLines"/></td>
                            </tr>
                            <tr>
                                <td rowspan="2"></td>
                                <td rowspan="2" class="local">Local Data</td>
                                <td rowspan="2" class="international">International Data</td>
                                <td rowspan="2" class="total">TOTAL</td>
                            </tr>
                            <tr>
                            </tr>
                            <tr>
                                <td style="font-weight: bold;">Fix Costs</td> 
                                <td><input type="text" id="3gLocalCallsFix"/></td>
                                <td><input type="text" id="3gInternationalCallsFix"/></td>
                                <td class="total_cost"><input type="text" id="3gTotalFix"/></td>
                            </tr>
                            <tr>
                                <td style="font-weight: bold;">Variable Costs</td> 
                                <td><input type="text" id="3gLocalCallsVar"/></td>
                                <td><input type="text" id="3gInternationalCallsVar"/></td>
                                <td class="total_cost"><input type="text" id="3gTotalVar"/></td>
                            </tr>        
                        </table>
                    </center>
                    <center>
                        <table> 
                            <tr>
                                <td></td>
                                <td colspan="5" style="font-weight: bold;">BlackBerry Smartphones</td>
                            </tr>
                            <tr>
                                <td style="font-weight: bold;">Number of lines</td>
                                <td colspan="5"><input type="text" id="bbLines"/></td>
                            </tr>
                            <tr>
                                <td rowspan="2"></td>
                                <td colspan="2" class="local">Local</td>
                                <td colspan="2" class="international">International</td>
                                <td rowspan="2" class="total">TOTAL</td>
                            </tr>
                            <tr>
                                <td class="local">Calls</td>
                                <td class="local">Data</td>
                                <td class="international">Calls</td>
                                <td class="international">Data</td>
                            </tr>
                            <tr>
                                <td style="font-weight: bold;">Fix Costs</td>
                                <td><input type="text" id="bbLocalCallsFix"/></td>
                                <td><input type="text" id="bbLocalDataFix"/></td>
                                <td><input type="text" id="bbInternationalCallsFix"/></td>
                                <td><input type="text" id="bbInternationalDataFix"/></td>
                                <td class="total_cost"><input type="text" id="bbTotalFix"/></td>
                            </tr>
                            <tr>
                                <td style="font-weight: bold;">Variable Costs</td>
                                <td><input type="text" id="bbLocalCallsVar"/></td>
                                <td><input type="text" id="bbLocalDataVar"/></td>
                                <td><input type="text" id="bbInternationalCallsVar"/></td>
                                <td><input type="text" id="bbInternationalDataVar"/></td>
                                <td class="total_cost"><input type="text" id="bbTotalVar"/></td>
                            </tr>        
                        </table>
                    </center>
                    <center>
                        <input type="submit" value="Save Report"/>
                    </center>
                </div>
            </div>
            <div id="form">
                <form action="" method="POST">
                    <center>
                        <select name="country" id="country" tabindex="10" style="margin-right: 50px;">
                            <option value="France">France</option>
                            <option value="Espagne">Espagne</option>
                            <option value="Italie">Italie</option>
                            <option value="Royaume-uni">Royaume-Uni</option>
                            <option value="Canada">Canada</option>
                            <option value="Etats-unis">Etats-Unis</option>
                            <option value="Chine">Chine</option>
                            <option value="Japon">Japon</option>
                        </select>
                        <select name="site" id="site" tabindex="20" style="margin-right: 50px;">
                            <option value="France">La Défense</option>
                            <option value="Espagne">Saint Ouen</option>
                            <option value="Italie">Petit Quevilly</option>
                            <option value="Royaume-uni">Belfort</option>
                        </select>
                        <input id="buttonGenerate" type="button" value="Generate" onClick="generateForm();" disabled="false" tabindex="30" style="margin-right: 20px;"/>
                        <input type="reset" value="Reset" onClick="resetForm();" tabindex="40"/>
                    </center>
                    <div id="generated_form">
                    </div>
                </form>
            </div>
            <div id="hidden">
                </br>
                <center>
                    <table> 
                        <tr>
                            <td></td>
                            <td colspan="3" style="font-weight: bold;">Fix Telephony</td>
                        </tr>
                        <tr>
                            <td style="font-weight: bold;">Number of lines</td>
                            <td colspan="3"><input type="text" id="fixeLines"/></td>
                        </tr>
                        <tr>
                            <td rowspan="2"></td>
                            <td rowspan="2" class="local">Local Calls</td>
                            <td rowspan="2" class="international">International Calls</td>
                            <td rowspan="2" class="total">TOTAL</td>
                        </tr>
                        <tr>
                        </tr>
                        <tr>
                            <td style="font-weight: bold;">Fix Costs</td> 
                            <td><input type="text" id="fixeLocalCallsFix"/></td>
                            <td><input type="text" id="fixeInternationalCallsFix"/></td>
                            <td class="total_cost"><input type="text" id="fixeTotalFix"/></td>
                        </tr>
                        <tr>
                            <td style="font-weight: bold;">Variable Costs</td> 
                            <td><input type="text" id="fixeLocalCallsVar"/></td>
                            <td><input type="text" id="fixeInternationalCallsVar"/></td>
                            <td class="total_cost"><input type="text" id="fixeTotalVar"/></td>
                        </tr>        
                    </table>
                </center>
                <center>
                    <table> 
                        <tr>
                            <td></td>
                            <td colspan="5" style="font-weight: bold;">Mobile Telephony</td>
                        </tr>
                        <tr>
                            <td style="font-weight: bold;">Number of lines</td>
                            <td colspan="5"><input type="text" id="mobileLines"/></td>
                        </tr>
                        <tr>
                            <td rowspan="2"></td>
                            <td colspan="2" class="local">Local</td>
                            <td colspan="2" class="international">International</td>
                            <td rowspan="2" class="total">TOTAL</td>
                        </tr>
                        <tr>
                            <td class="local">Calls</td>
                            <td class="local">Data</td>
                            <td class="international">Calls</td>
                            <td class="international">Data</td>
                        </tr>
                        <tr>
                            <td style="font-weight: bold;">Fix Costs</td>
                            <td><input type="text" id="mobileLocalCallsFix"/></td>
                            <td><input type="text" id="mobileLocalDataFix"/></td>
                            <td><input type="text" id="mobileInternationalCallsFix"/></td>
                            <td><input type="text" id="mobileInternationalDataFix"/></td>
                            <td class="total_cost"><input type="text" id="mobileTotalFix"/></td>
                        </tr>
                        <tr>
                            <td style="font-weight: bold;">Variable Costs</td>
                            <td><input type="text" id="mobileLocalCallsVar"/></td>
                            <td><input type="text" id="mobileLocalDataVar"/></td>
                            <td><input type="text" id="mobileInternationalCallsVar"/></td>
                            <td><input type="text" id="mobileInternationalDataVar"/></td>
                            <td class="total_cost"><input type="text" id="mobileTotalVar"/></td>
                        </tr>        
                    </table>
                </center>
                <center>
                    <table> 
                        <tr>
                            <td></td>
                            <td colspan="3" style="font-weight: bold;">3G Cards</td>
                        </tr>
                        <tr>
                            <td style="font-weight: bold;">Number of lines</td>
                            <td colspan="3"><input type="text" id="3gLines"/></td>
                        </tr>
                        <tr>
                            <td rowspan="2"></td>
                            <td rowspan="2" class="local">Local Data</td>
                            <td rowspan="2" class="international">International Data</td>
                            <td rowspan="2" class="total">TOTAL</td>
                        </tr>
                        <tr>
                        </tr>
                        <tr>
                            <td style="font-weight: bold;">Fix Costs</td> 
                            <td><input type="text" id="3gLocalCallsFix"/></td>
                            <td><input type="text" id="3gInternationalCallsFix"/></td>
                            <td class="total_cost"><input type="text" id="3gTotalFix"/></td>
                        </tr>
                        <tr>
                            <td style="font-weight: bold;">Variable Costs</td> 
                            <td><input type="text" id="3gLocalCallsVar"/></td>
                            <td><input type="text" id="3gInternationalCallsVar"/></td>
                            <td class="total_cost"><input type="text" id="3gTotalVar"/></td>
                        </tr>        
                    </table>
                </center>
                <center>
                    <table> 
                        <tr>
                            <td></td>
                            <td colspan="5" style="font-weight: bold;">BlackBerry Smartphones</td>
                        </tr>
                        <tr>
                            <td style="font-weight: bold;">Number of lines</td>
                            <td colspan="5"><input type="text" id="bbLines"/></td>
                        </tr>
                        <tr>
                            <td rowspan="2"></td>
                            <td colspan="2" class="local">Local</td>
                            <td colspan="2" class="international">International</td>
                            <td rowspan="2" class="total">TOTAL</td>
                        </tr>
                        <tr>
                            <td class="local">Calls</td>
                            <td class="local">Data</td>
                            <td class="international">Calls</td>
                            <td class="international">Data</td>
                        </tr>
                        <tr>
                            <td style="font-weight: bold;">Fix Costs</td>
                            <td><input type="text" id="bbLocalCallsFix"/></td>
                            <td><input type="text" id="bbLocalDataFix"/></td>
                            <td><input type="text" id="bbInternationalCallsFix"/></td>
                            <td><input type="text" id="bbInternationalDataFix"/></td>
                            <td class="total_cost"><input type="text" id="bbTotalFix"/></td>
                        </tr>
                        <tr>
                            <td style="font-weight: bold;">Variable Costs</td>
                            <td><input type="text" id="bbLocalCallsVar"/></td>
                            <td><input type="text" id="bbLocalDataVar"/></td>
                            <td><input type="text" id="bbInternationalCallsVar"/></td>
                            <td><input type="text" id="bbInternationalDataVar"/></td>
                            <td class="total_cost"><input type="text" id="bbTotalVar"/></td>
                        </tr>        
                    </table>
                </center>
                <center>
                    <input type="submit" value="Save Report"/>
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
