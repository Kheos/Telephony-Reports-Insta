<%-- 
    Document   : reports
    Created on : 29 oct. 2012, 10:54:08
    Author     : Enji
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <title>Reports</title>
        <link href="styles/styles.css" rel="stylesheet" type="text/css" />
        <link rel="shortcut icon" href="pictures/oAlstom.ico" type="image/x-icon"/> 
        <link rel="icon" href="pictures/oAlstom.ico" type="image/x-icon"/>
        <script type="text/javascript" src="inc/scripts/slide.js"></script>
        <script type="text/javascript" src="inc/scripts/bubulle.v2.min.js"></script>
        <script type="text/javascript" src="inc/scripts/calculForm.js"></script>
        <script type="text/javascript" src="inc/scripts/onlyKeys.js"></script>
        <style>
            table {
                color: black;
                width: 836px;
                margin: auto;
                margin-bottom: 20px;
            }

            td {
                text-align: center;
                padding: 3px;
                min-width: 100px;
                border: 1px solid black;
            }

            .double_td {
                min-width: 280px;
            }

            .local {
                background-color: orange;
                font-weight: bold;
            }

            .international {
                background-color: #00aca8;
                font-weight: bold;
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

            .submit {
                width: 836px;
                margin: auto;
                text-align: center;
            }

            .errors {
                margin-left: 10px;
                margin-bottom: 10px;
                color: red;
            }

            .valid {
                margin-left: 20px;
                margin-bottom: 10px;
                color: green;
                font-weight: bold;
            }

            td.info {
                min-width: 130px;
            }

            td.info:hover {
                z-index: 500; /* On définit une valeur pour l'ordre d'affichage. */

                cursor: help; /* On change le curseur par défaut par un curseur d'aide. */
            }

            #bubulle {
                max-width: 350px;

                background: #daeeed;

                color: #00aca8;
                padding: 3px;

                border: 1px solid #00aca8;
                border-radius: 5px;
                border-left: 4px solid #00aca8;

                box-shadow: 2px 2px 2px grey;

                z-index: 600;

                overflow: auto;
            }
        </style>
        <script language="JavaScript" type="text/javascript">
			
            function editReport(div) {
		
                var allInput = document.getElementById(div).getElementsByTagName('input');
		
                for (var i = 0; i < (allInput.length - 2); i++) {
                    allInput[i].removeAttribute('readonly');
                    allInput[i].removeAttribute('style');
                }
				
                allInput[allInput.length - 2].setAttribute('style', 'display: none;');
                allInput[allInput.length - 1].setAttribute('style', 'display: inline;');
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
                <img src="inc/pictures/reportsPart.png" alt="" width="70px" style="margin-top:-15px;"/> &nbsp Reports per Contract
            </h2>
            <c:choose>
                <c:when test="${fn:length(reportMap) == 0 and form != null}">
                    <p class="errors" style="font-size:15px; font-weight: bold;">Error :</p>
                    <p class="errors" style="font-size:15px;">No report found with these research criterias.</p>
                    <p class="errors" style="font-size:15px;">Please try to search report(s) with current or passed date criterias.</p>
                </c:when>
                <c:otherwise>
                    </br>
                    <p>Select an unit report and then, choose current or passed date criterias :</p>
                    <p>Mandatory note : Fill all fields in euros only.</p>
                </c:otherwise>
            </c:choose>
            <div id="filter">
                <form name="filter" action="Reports" method="POST" class="formContact">
                    <center>
                        <label><span>Unit Contract :</span>
                            <select name="site" id="site" tabindex="20"  style="width:290px; margin-right: 20px;">
                                <c:forEach var="i" begin="0" end="${fn:length(siteList) - 1}" step="1">
                                    <c:choose>
                                        <c:when test="${filter != null}">
                                            <c:choose>
                                                <c:when test="${siteList[i] == filter['refContract']}">
                                                    <option selected value="<c:out value='${siteList[i]}'/>"><c:out value='${siteList[i]}'/></option>
                                                </c:when>
                                                <c:otherwise>
                                                    <option value="<c:out value='${siteList[i]}'/>"><c:out value='${siteList[i]}'/></option>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:when>
                                        <c:otherwise>
                                            <option value="<c:out value='${siteList[i]}'/>"><c:out value='${siteList[i]}'/></option>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                            </select></label><br />
                        <label><span>Fiscal Year :</span>
                            <select id='year' name='year' tabindex="30" style="width:290px; margin-right: 20px;" >
                                <c:choose>
                                    <c:when test="${currentMonth > 3}">
                                        <c:forEach var="i" begin="2011" end="${currentYear}" step="1">
                                            <c:choose>
                                                <c:when test="${filter != null}">
                                                    <c:choose>
                                                        <c:when test="${i == filter['year']}">
                                                            <option selected value="<c:out value='${i}'/>"><c:out value='${i} - ${i + 1}'/></option>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <option value="<c:out value='${i}'/>"><c:out value='${i} - ${i + 1}'/></option>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </c:when>
                                                <c:otherwise>
                                                    <option value="<c:out value='${i}'/>"><c:out value='${i} - ${i + 1}'/></option>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:forEach>
                                    </c:when>
                                    <c:otherwise>
                                        <c:forEach var="i" begin="2011" end="${currentYear - 1}" step="1">
                                            <c:choose>
                                                <c:when test="${filter != null}">
                                                    <c:choose>
                                                        <c:when test="${i == filter['year']}">
                                                            <option selected value="<c:out value='${i}'/>"><c:out value='${i} - ${i + 1}'/></option>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <option value="<c:out value='${i}'/>"><c:out value='${i} - ${i + 1}'/></option>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </c:when>
                                                <c:otherwise>
                                                    <option value="<c:out value='${i}'/>"><c:out value='${i} - ${i + 1}'/></option>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:forEach>
                                    </c:otherwise>
                                </c:choose>
                            </select></label><br />
                        <label><span>Month :</span>
                            <select name="month" id="month" tabindex="40" style="width:290px; margin-right: 20px;" >
                                <option value="0">All</option>
                                <c:forEach var="i" begin="1" end="12" step="1">
                                    <c:choose>
                                        <c:when test="${filter != null}">
                                            <c:choose>
                                                <c:when test="${i == filter['month']}">
                                                    <option selected value="<c:out value='${i}'/>"><c:out value='${monthList[i]}'/></option>
                                                </c:when>
                                                <c:otherwise>
                                                    <option value="<c:out value='${i}'/>"><c:out value='${monthList[i]}'/></option>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:when>
                                        <c:otherwise>
                                            <option value="<c:out value='${i}'/>"><c:out value='${monthList[i]}'/></option>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                            </select></label><br />
                        <input id="buttonGenerate" type="submit" name="filter" value="Search" tabindex="30" class="button"/>
                    </center>
                </form><br /><br /><br />
                <div id="generated_form">
                </div>
            </div>
            <c:choose>
                <c:when test="${fn:length(reportMap) != 0 and form != null}">
                    <c:forEach var="i" begin="4" end="12" step="1">
                        <c:if test="${reportMap[i] != null}">
                            <c:choose>
                                <c:when test="${reportMap[i]['editable']}">
                                    <form name="<c:out value='report${i}'/>" action="Reports" method="POST">
                                        <div id="<c:out value='report${i}'/>" style="border: 1px black solid; margin-top: 30px;">
                                            <div style="background-color: crimson;">
                                                <span style="color: white; margin-left: 10px;"><c:out value='${monthList[i]} ${reportMap[i]["year"]}'/> Report</span>
                                                <span id="showslide${i}" onClick="slideDown('slide${i}', 500);" style="float: right; color: white; cursor: pointer; margin-right: 10px;">Show the report</span>
                                                <span id="hideslide${i}" onClick="slideUp('slide${i}', 500);" style="float: right; display: none; color: white; cursor: pointer; margin-right: 10px;">Hide the report</span>
                                            </div>
                                            <div id="slide${i}" style="height: 0px; overflow: hidden;">
                                                <c:if test="${result != null && modifiedReport == i}">
                                                    <c:choose>
                                                        <c:when test="${'Report saved !' eq result}">
                                                            <div class="valid">
                                                                <ul>
                                                                    <li><c:out value="${result}"/></li>
                                                                </ul>
                                                            </div>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <div class="errors">
                                                                <ul>
                                                                    <li style="font-weight: bold;"><c:out value="${result}"/></li>
                                                                </ul>
                                                            </div>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </c:if>
                                                <c:if test="${type eq 'Fixed' || type eq 'Both'}">
                                                    <table style="margin-top: 20px;">
                                                        <c:if test="${form['errors'] != null  && modifiedReport == i}">
                                                            <div class="errors">
                                                                <ul>
                                                                    <c:if test="${!empty (form.errors['linesFix'])}">
                                                                        <li><c:out value="${form.errors['linesFix']}"/></li>
                                                                    </c:if>
                                                                    <c:if test="${!empty (form.errors['fixFix'])}">
                                                                        <li><c:out value="${form.errors['fixFix']}"/></li>
                                                                    </c:if>
                                                                    <c:if test="${!empty (form.errors['varFix'])}">
                                                                        <li><c:out value="${form.errors['varFix']}"/></li>
                                                                    </c:if>
                                                                </ul>
                                                            </div>
                                                        </c:if>
                                                        <tr>
                                                            <td></td>
                                                            <td colspan="3" style="font-weight: bold;">Fix Telephony</td>
                                                        </tr>
                                                        <tr>
                                                            <td class="info" name="infoLinesCount" id="infoLinesCount" style="font-weight: bold;">
                                                                Number of lines
                                                            </td>
                                                            <td colspan="3"><input type="text" name="linesFix" value="<c:out value="${reportMap[i]['linesFix']}"/>" id="linesFix"/></td>
                                                        </tr>
                                                        <tr>
                                                            <td rowspan="2"></td>
                                                            <td rowspan="2" class="local">Domestic Voice</td>
                                                            <td rowspan="2" class="international">International Voice</td>
                                                            <td rowspan="2" class="total">TOTAL</td>
                                                        </tr>
                                                        <tr>
                                                        </tr>
                                                        <tr>
                                                            <td class="info" name="infoFixCosts" id="infoFixCosts" style="font-weight: bold;">
                                                                Fix Costs (€)
                                                            </td> 
                                                            <td class="double_td"><input type="text" name="fixLocalCallsFix" value="<c:out value="${reportMap[i]['fixLocalCallsFix']}"/>" id="fixLocalCallsFix" onkeypress="onlyNumbers(event);" onkeyup="calculateTotal(<c:out value='${i}'/>, 'fixTotalFix');"/></td>
                                                            <td class="double_td"><input type="text" name="fixInternationalCallsFix" value="<c:out value="${reportMap[i]['fixInternationalCallsFix']}"/>" id="fixInternationalCallsFix" onkeypress="onlyNumbers(event);" onkeyup="calculateTotal(<c:out value='${i}'/>, 'fixTotalFix');"/></td>
                                                            <td class="total_cost"><input type="text" name="fixTotalFix" value="<c:out value="${reportMap[i]['fixTotalFix']}"/>" id="fixTotalFix" onkeypress="onlyNumbers(event);" onblur="checkTotal(<c:out value='${i}'/>, 'fixTotalFix');"/></td>
                                                        </tr>
                                                        <tr>
                                                            <td class="info" name="infoVarCosts" id="infoVarCosts" style="font-weight: bold;">
                                                                Variable Costs (€)
                                                            </td>
                                                            <td class="double_td"><input type="text" name="varLocalCallsFix" value="<c:out value="${reportMap[i]['varLocalCallsFix']}"/>" id="varLocalCallsFix" onkeypress="onlyNumbers(event);" onkeyup="calculateTotal(<c:out value='${i}'/>, 'varTotalFix');"/></td>
                                                            <td class="double_td"><input type="text" name="varInternationalCallsFix" value="<c:out value="${reportMap[i]['varInternationalCallsFix']}"/>" id="varInternationalCallsFix" onkeypress="onlyNumbers(event);" onkeyup="calculateTotal(<c:out value='${i}'/>, 'varTotalFix');"/></td>
                                                            <td class="total_cost"><input type="text" name="varTotalFix" value="<c:out value="${reportMap[i]['varTotalFix']}"/>" id="varTotalFix" onkeypress="onlyNumbers(event);" onblur="checkTotal(<c:out value='${i}'/>, 'varTotalFix');"/></td>
                                                        </tr>        
                                                    </table>
                                                </c:if>
                                                <c:if test="${type eq 'Mobile' || type eq 'Both'}">
                                                    <table <c:if test="${type eq 'Mobile'}"> style="margin-top: 20px;"</c:if>>
                                                        <c:if test="${form['errors'] != null  && modifiedReport == i}">
                                                            <div class="errors">
                                                                <ul>
                                                                    <c:if test="${!empty (form.errors['lines3G'])}">
                                                                        <li><c:out value="${form.errors['lines3G']}"/></li>
                                                                    </c:if>
                                                                    <c:if test="${!empty (form.errors['fix3G'])}">
                                                                        <li><c:out value="${form.errors['fix3G']}"/></li>
                                                                    </c:if>
                                                                    <c:if test="${!empty (form.errors['var3G'])}">
                                                                        <li><c:out value="${form.errors['var3G']}"/></li>
                                                                    </c:if>
                                                                </ul>
                                                            </div>
                                                        </c:if>
                                                        <tr>
                                                            <td></td>
                                                            <td colspan="3" style="font-weight: bold;">3G / Aircards</td>
                                                        </tr>
                                                        <tr>
                                                            <td class="info" name="infoLinesCount" id="infoLinesCount" style="font-weight: bold;">
                                                                Number of lines
                                                            </td>
                                                            <td colspan="3"><input type="text" name="lines3G" id="lines3G" value="<c:out value="${reportMap[i]['lines3G']}"/>"/></td>
                                                        </tr>
                                                        <tr>
                                                            <td rowspan="2"></td>
                                                            <td rowspan="2" class="local">Domestic Data Traffic</td>
                                                            <td rowspan="2" class="international">International Data Traffic</td>
                                                            <td rowspan="2" class="total">TOTAL</td>
                                                        </tr>
                                                        <tr>
                                                        </tr>
                                                        <tr>
                                                            <td class="info" name="infoFixCosts" id="infoFixCosts" style="font-weight: bold;">
                                                                Fix Costs (€)
                                                            </td>
                                                            <td class="double_td"><input type="text" id="fixLocalData3G" name="fixLocalData3G" value="<c:out value="${reportMap[i]['fixLocalData3G']}"/>" onkeypress="onlyNumbers(event);" onkeyup="calculateTotal(<c:out value='${i}'/>, 'fixTotal3G');"/></td>
                                                            <td class="double_td"><input type="text" id="fixInternationalData3G" name="fixInternationalData3G" value="<c:out value="${reportMap[i]['fixInternationalData3G']}"/>" onkeypress="onlyNumbers(event);" onkeyup="calculateTotal(<c:out value='${i}'/>, 'fixTotal3G');"/></td>
                                                            <td class="total_cost"><input type="text" id="fixTotal3G" name="fixTotal3G" value="<c:out value="${reportMap[i]['fixTotal3G']}"/>" onkeypress="onlyNumbers(event);" onblur="checkTotal(<c:out value='${i}'/>, 'fixTotal3G');"/></td>
                                                        </tr>
                                                        <tr>
                                                            <td class="info" name="infoVarCosts" id="infoVarCosts" style="font-weight: bold;">
                                                                Variable Costs (€)
                                                            </td>
                                                            <td class="double_td"><input type="text" id="varLocalData3G" name="varLocalData3G" value="<c:out value="${reportMap[i]['varLocalData3G']}"/>" onkeypress="onlyNumbers(event);" onkeyup="calculateTotal(<c:out value='${i}'/>, 'varTotal3G');"/></td>
                                                            <td class="double_td"><input type="text" id="varInternationalData3G" name="varInternationalData3G" value="<c:out value="${reportMap[i]['varInternationalData3G']}"/>" onkeypress="onlyNumbers(event);" onkeyup="calculateTotal(<c:out value='${i}'/>, 'varTotal3G');"/></td>
                                                            <td class="total_cost"><input type="text" id="varTotal3G" name="varTotal3G" value="<c:out value="${reportMap[i]['varTotal3G']}"/>" onkeypress="onlyNumbers(event);" onblur="checkTotal(<c:out value='${i}'/>, 'varTotal3G');"/></td>
                                                        </tr>        
                                                    </table>
                                                    <table>
                                                        <c:if test="${form['errors'] != null  && modifiedReport == i}">
                                                            <div class="errors">
                                                                <ul>
                                                                    <c:if test="${!empty (form.errors['linesMobile'])}">
                                                                        <li><c:out value="${form.errors['linesMobile']}"/></li>
                                                                    </c:if>
                                                                    <c:if test="${!empty (form.errors['fixMobile'])}">
                                                                        <li><c:out value="${form.errors['fixMobile']}"/></li>
                                                                    </c:if>
                                                                    <c:if test="${!empty (form.errors['varMobile'])}">
                                                                        <li><c:out value="${form.errors['varMobile']}"/></li>
                                                                    </c:if>
                                                                </ul>
                                                            </div>
                                                        </c:if>
                                                        <tr>
                                                            <td></td>
                                                            <td colspan="5" style="font-weight: bold;">Mobile Telephony</td>
                                                        </tr>
                                                        <tr>
                                                            <td class="info" name="infoLinesCount" id="infoLinesCount" style="font-weight: bold;">
                                                                Number of lines
                                                            </td>
                                                            <td colspan="5"><input type="text" name="linesMobile" id="linesMobile" value="<c:out value="${reportMap[i]['linesMobile']}"/>"/></td>
                                                        </tr>
                                                        <tr>
                                                            <td rowspan="2"></td>
                                                            <td colspan="2" class="local">Domestic</td>
                                                            <td colspan="2" class="international">International</td>
                                                            <td rowspan="2" class="total">TOTAL</td>
                                                        </tr>
                                                        <tr>
                                                            <td class="local">Voice</td>
                                                            <td class="local">Data Traffic</td>
                                                            <td class="international">Voice</td>
                                                            <td class="international">Data Traffic</td>
                                                        </tr>
                                                        <tr>
                                                            <td class="info" name="infoFixCosts" id="infoFixCosts" style="font-weight: bold;">
                                                                Fix Costs (€)
                                                            </td>
                                                            <td><input type="text" id="fixLocalCallsMobile" name="fixLocalCallsMobile" value="<c:out value="${reportMap[i]['fixLocalCallsMobile']}"/>" onkeypress="onlyNumbers(event);" onkeyup="calculateTotal(<c:out value='${i}'/>, 'fixTotalMobile');"/></td>
                                                            <td><input type="text" id="fixLocalDataMobile" name="fixLocalDataMobile" value="<c:out value="${reportMap[i]['fixLocalDataMobile']}"/>" onkeypress="onlyNumbers(event);" onkeyup="calculateTotal(<c:out value='${i}'/>, 'fixTotalMobile');"/></td>
                                                            <td><input type="text" id="fixInternationalCallsMobile" name="fixInternationalCallsMobile" value="<c:out value="${reportMap[i]['fixLocalCallsMobile']}"/>" onkeypress="onlyNumbers(event);" onkeyup="calculateTotal(<c:out value='${i}'/>, 'fixTotalMobile');"/></td>
                                                            <td><input type="text" id="fixInternationalDataMobile" name="fixInternationalDataMobile" value="<c:out value="${reportMap[i]['fixLocalDataMobile']}"/>" onkeypress="onlyNumbers(event);" onkeyup="calculateTotal(<c:out value='${i}'/>, 'fixTotalMobile');"/></td>
                                                            <td class="total_cost"><input type="text" id="fixTotalMobile" name="fixTotalMobile" value="<c:out value="${reportMap[i]['fixTotalMobile']}"/>" onkeypress="onlyNumbers(event);" onblur="checkTotal(<c:out value='${i}'/>, 'fixTotalMobile');"/></td>
                                                        </tr>
                                                        <tr>
                                                            <td class="info" name="infoVarCosts" id="infoVarCosts" style="font-weight: bold;">
                                                                Variable Costs (€)
                                                            </td>
                                                            <td><input type="text" id="varLocalCallsMobile" name="varLocalCallsMobile" value="<c:out value="${reportMap[i]['varLocalCallsMobile']}"/>" onkeypress="onlyNumbers(event);" onkeyup="calculateTotal(<c:out value='${i}'/>, 'varTotalMobile');"/></td>
                                                            <td><input type="text" id="varLocalDataMobile" name="varLocalDataMobile" value="<c:out value="${reportMap[i]['varLocalDataMobile']}"/>" onkeypress="onlyNumbers(event);" onkeyup="calculateTotal(<c:out value='${i}'/>, 'varTotalMobile');"/></td>
                                                            <td><input type="text" id="varInternationalCallsMobile" name="varInternationalCallsMobile" value="<c:out value="${reportMap[i]['varLocalCallsMobile']}"/>" onkeypress="onlyNumbers(event);" onkeyup="calculateTotal(<c:out value='${i}'/>, 'varTotalMobile');"/></td>
                                                            <td><input type="text" id="varInternationalDataMobile" name="varInternationalDataMobile" value="<c:out value="${reportMap[i]['varLocalDataMobile']}"/>" onkeypress="onlyNumbers(event);" onkeyup="calculateTotal(<c:out value='${i}'/>, 'varTotalMobile');"/></td>
                                                            <td class="total_cost"><input type="text" id="varTotalMobile" name="varTotalMobile" value="<c:out value="${reportMap[i]['varTotalMobile']}"/>" onkeypress="onlyNumbers(event);" onblur="checkTotal(<c:out value='${i}'/>, 'varTotalMobile');"/></td>
                                                        </tr>        
                                                    </table>
                                                    <table>
                                                        <c:if test="${form['errors'] != null  && modifiedReport == i}">
                                                            <div class="errors">
                                                                <ul>
                                                                    <c:if test="${!empty (form.errors['linesBB'])}">
                                                                        <li><c:out value="${form.errors['linesBB']}"/></li>
                                                                    </c:if>
                                                                    <c:if test="${!empty (form.errors['fixBB'])}">
                                                                        <li><c:out value="${form.errors['fixBB']}"/></li>
                                                                    </c:if>
                                                                    <c:if test="${!empty (form.errors['varBB'])}">
                                                                        <li><c:out value="${form.errors['varBB']}"/></li>
                                                                    </c:if>
                                                                </ul>
                                                            </div>
                                                        </c:if>
                                                        <tr>
                                                            <td></td>
                                                            <td colspan="5" style="font-weight: bold;">BlackBerry Smartphones</td>
                                                        </tr>
                                                        <tr>
                                                            <td class="info" name="infoLinesCount" id="infoLinesCount" style="font-weight: bold;">
                                                                Number of lines
                                                            </td>
                                                            <td colspan="5"><input type="text" id="linesBB" name="linesBB" value="<c:out value="${reportMap[i]['linesBB']}"/>"/></td>
                                                        </tr>
                                                        <tr>
                                                            <td rowspan="2"></td>
                                                            <td colspan="2" class="local">Domestic</td>
                                                            <td colspan="2" class="international">International</td>
                                                            <td rowspan="2" class="total">TOTAL</td>
                                                        </tr>
                                                        <tr>
                                                            <td class="local">Voice</td>
                                                            <td class="local">Data Traffic</td>
                                                            <td class="international">Voice</td>
                                                            <td class="international">Data Traffic</td>
                                                        </tr>
                                                        <tr>
                                                            <td class="info" name="infoFixCosts" id="infoFixCosts" style="font-weight: bold;">
                                                                Fix Costs (€)
                                                            </td>
                                                            <td><input type="text" id="fixLocalCallsBB" name="fixLocalCallsBB" value="<c:out value="${reportMap[i]['fixLocalCallsBB']}"/>" onkeypress="onlyNumbers(event);" onkeyup="calculateTotal(<c:out value='${i}'/>, 'fixTotalBB');"/></td>
                                                            <td><input type="text" id="fixLocalDataBB" name="fixLocalDataBB" value="<c:out value="${reportMap[i]['fixLocalDataBB']}"/>" onkeypress="onlyNumbers(event);" onkeyup="calculateTotal(<c:out value='${i}'/>, 'fixTotalBB');"/></td>
                                                            <td><input type="text" id="fixInternationalCallsBB" name="fixInternationalCallsBB" value="<c:out value="${reportMap[i]['fixLocalCallsBB']}"/>" onkeypress="onlyNumbers(event);" onkeyup="calculateTotal(<c:out value='${i}'/>, 'fixTotalBB');"/></td>
                                                            <td><input type="text" id="fixInternationalDataBB" name="fixInternationalDataBB" value="<c:out value="${reportMap[i]['fixLocalDataBB']}"/>" onkeypress="onlyNumbers(event);" onkeyup="calculateTotal(<c:out value='${i}'/>, 'fixTotalBB');"/></td>
                                                            <td class="total_cost"><input type="text" id="fixTotalBB" name="fixTotalBB" value="<c:out value="${reportMap[i]['fixTotalBB']}"/>" onkeypress="onlyNumbers(event);" onblur="checkTotal(<c:out value='${i}'/>, 'fixTotalBB');"/></td>
                                                        </tr>
                                                        <tr>
                                                            <td class="info" name="infoVarCosts" id="infoVarCosts" style="font-weight: bold;">
                                                                Variable Costs (€)
                                                            </td>
                                                            <td><input type="text" id="varLocalCallsBB" name="varLocalCallsBB" value="<c:out value="${reportMap[i]['varLocalCallsBB']}"/>" onkeypress="onlyNumbers(event);" onkeyup="calculateTotal(<c:out value='${i}'/>, 'varTotalBB');"/></td>
                                                            <td><input type="text" id="varLocalDataBB" name="varLocalDataBB" value="<c:out value="${reportMap[i]['varLocalDataBB']}"/>" onkeypress="onlyNumbers(event);" onkeyup="calculateTotal(<c:out value='${i}'/>, 'varTotalBB');"/></td>
                                                            <td><input type="text" id="varInternationalCallsBB" name="varInternationalCallsBB" value="<c:out value="${reportMap[i]['varLocalCallsBB']}"/>" onkeypress="onlyNumbers(event);" onkeyup="calculateTotal(<c:out value='${i}'/>, 'varTotalBB');"/></td>
                                                            <td><input type="text" id="varInternationalDataBB" name="varInternationalDataBB" value="<c:out value="${reportMap[i]['varLocalDataBB']}"/>" onkeypress="onlyNumbers(event);" onkeyup="calculateTotal(<c:out value='${i}'/>, 'varTotalBB');"/></td>
                                                            <td class="total_cost"><input type="text" id="varTotalBB" name="varTotalBB" value="<c:out value="${reportMap[i]['varTotalBB']}"/>" onkeypress="onlyNumbers(event);" onblur="checkTotal(<c:out value='${i}'/>, 'varTotalBB');"/></td>
                                                        </tr>         
                                                    </table>
                                                </c:if>
                                                <div class="submit">
                                                    <input type="button" class="button" id="buttonEdit" value="Edit Report" style="display: none;" onClick="editReport('<c:out value='report${i}'/>')"/>
                                                    <input type="submit" class="button" name="<c:out value='report${i}'/>" value="Save Report"/>
                                                </div>
                                            </div>
                                        </div>
                                    </form>
                                </c:when>
                                <c:otherwise>
                                    <form name="<c:out value='report${i}'/>" action="Reports" method="POST">
                                        <div id="<c:out value='report${i}'/>" style="border: 1px black solid; margin-top: 30px;">
                                            <div style="background-color: green;">
                                                <span style="color: white; margin-left: 10px;"><c:out value='${monthList[i]} ${reportMap[i]["year"]}'/> Report</span>
                                                <span id="showslide${i}" onClick="slideDown('slide${i}', 500);" style="float: right; color: white; cursor: pointer; margin-right: 10px;">Show the report</span>
                                                <span id="hideslide${i}" onClick="slideUp('slide${i}', 500);" style="float: right; display: none; color: white; cursor: pointer; margin-right: 10px;">Hide the report</span>
                                            </div>
                                            <div id="slide${i}" style="height: 0px; overflow: hidden;">
                                                <c:if test="${result != null  && modifiedReport == i}">
                                                    <c:choose>
                                                        <c:when test="${'Report saved !' eq result}">
                                                            <div class="valid">
                                                                <ul>
                                                                    <li><c:out value="${result}"/></li>
                                                                </ul>
                                                            </div>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <div class="errors">
                                                                <ul>
                                                                    <li style="font-weight: bold;"><c:out value="${result}"/></li>
                                                                </ul>
                                                            </div>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </c:if>
                                                <c:if test="${type eq 'Fixed' || type eq 'Both'}">
                                                    <table style="margin-top: 20px;">
                                                        <c:if test="${form['errors'] != null  && modifiedReport == i}">
                                                            <div class="errors">
                                                                <ul>
                                                                    <c:if test="${!empty (form.errors['linesFix'])}">
                                                                        <li><c:out value="${form.errors['linesFix']}"/></li>
                                                                    </c:if>
                                                                    <c:if test="${!empty (form.errors['fixFix'])}">
                                                                        <li><c:out value="${form.errors['fixFix']}"/></li>
                                                                    </c:if>
                                                                    <c:if test="${!empty (form.errors['varFix'])}">
                                                                        <li><c:out value="${form.errors['varFix']}"/></li>
                                                                    </c:if>
                                                                </ul>
                                                            </div>
                                                        </c:if>
                                                        <tr>
                                                            <td></td>
                                                            <td colspan="3" style="font-weight: bold;">Fix Telephony</td>
                                                        </tr>
                                                        <tr>
                                                            <td class="info" name="infoLinesCount" id="infoLinesCount" style="font-weight: bold;">
                                                                Number of lines
                                                            </td>
                                                            <td colspan="3"><input readonly="true" type="text" style="background-color: #cacaca;" name="linesFix" value="<c:out value="${reportMap[i]['linesFix']}"/>" id="linesFix"/></td>
                                                        </tr>
                                                        <tr>
                                                            <td rowspan="2"></td>
                                                            <td rowspan="2" class="local">Domestic Voice</td>
                                                            <td rowspan="2" class="international">International Voice</td>
                                                            <td rowspan="2" class="total">TOTAL</td>
                                                        </tr>
                                                        <tr>
                                                        </tr>
                                                        <tr>
                                                            <td class="info" name="infoFixCosts" id="infoFixCosts" style="font-weight: bold;">
                                                                Fix Costs (€)
                                                            </td> 
                                                            <td class="double_td"><input readonly="true" type="text" style="background-color: #cacaca;" name="fixLocalCallsFix" value="<c:out value="${reportMap[i]['fixLocalCallsFix']}"/>" id="fixLocalCallsFix" onkeypress="onlyNumbers(event);" onkeyup="calculateTotal(<c:out value='${i}'/>, 'fixTotalFix');"/></td>
                                                            <td class="double_td"><input readonly="true" type="text" style="background-color: #cacaca;" name="fixInternationalCallsFix" value="<c:out value="${reportMap[i]['fixInternationalCallsFix']}"/>" id="fixInternationalCallsFix" onkeypress="onlyNumbers(event);" onkeyup="calculateTotal(<c:out value='${i}'/>, 'fixTotalFix');"/></td>
                                                            <td class="total_cost"><input readonly="true" type="text" style="background-color: #cacaca;" name="fixTotalFix" value="<c:out value="${reportMap[i]['fixTotalFix']}"/>" id="fixTotalFix" onkeypress="onlyNumbers(event);" onblur="checkTotal(<c:out value='${i}'/>, 'fixTotalFix');"/></td>
                                                        </tr>
                                                        <tr>
                                                            <td class="info" name="infoVarCosts" id="infoVarCosts" style="font-weight: bold;">
                                                                Variable Costs (€)
                                                            </td> 
                                                            <td class="double_td"><input readonly="true" type="text" style="background-color: #cacaca;" name="varLocalCallsFix" value="<c:out value="${reportMap[i]['varLocalCallsFix']}"/>" id="varLocalCallsFix" onkeypress="onlyNumbers(event);" onkeyup="calculateTotal(<c:out value='${i}'/>, 'varTotalFix');"/></td>
                                                            <td class="double_td"><input readonly="true" type="text" style="background-color: #cacaca;" name="varInternationalCallsFix" value="<c:out value="${reportMap[i]['varInternationalCallsFix']}"/>" id="varInternationalCallsFix" onkeypress="onlyNumbers(event);" onkeyup="calculateTotal(<c:out value='${i}'/>, 'varTotalFix');"/></td>
                                                            <td class="total_cost"><input readonly="true" type="text" style="background-color: #cacaca;" name="varTotalFix" value="<c:out value="${reportMap[i]['varTotalFix']}"/>" id="varTotalFix" onkeypress="onlyNumbers(event);" onblur="checkTotal(<c:out value='${i}'/>, 'varTotalFix');"/></td>
                                                        </tr>        
                                                    </table>
                                                </c:if>
                                                <c:if test="${type eq 'Mobile' || type eq 'Both'}">
                                                    <table <c:if test="${type eq 'Mobile'}"> style="margin-top: 20px;"</c:if>>
                                                        <c:if test="${form['errors'] != null  && modifiedReport == i}">
                                                            <div class="errors">
                                                                <ul>
                                                                    <c:if test="${!empty (form.errors['lines3G'])}">
                                                                        <li><c:out value="${form.errors['lines3G']}"/></li>
                                                                    </c:if>
                                                                    <c:if test="${!empty (form.errors['fix3G'])}">
                                                                        <li><c:out value="${form.errors['fix3G']}"/></li>
                                                                    </c:if>
                                                                    <c:if test="${!empty (form.errors['var3G'])}">
                                                                        <li><c:out value="${form.errors['var3G']}"/></li>
                                                                    </c:if>
                                                                </ul>
                                                            </div>
                                                        </c:if>
                                                        <tr>
                                                            <td></td>
                                                            <td colspan="3" style="font-weight: bold;">3G / Aircards</td>
                                                        </tr>
                                                        <tr>
                                                            <td class="info" name="infoLinesCount" id="infoLinesCount" style="font-weight: bold;">
                                                                Number of lines
                                                            </td>
                                                            <td colspan="3"><input readonly="true" type="text" name="lines3G" style="background-color: #cacaca;" id="lines3G" value="<c:out value="${reportMap[i]['lines3G']}"/>"/></td>
                                                        </tr>
                                                        <tr>
                                                            <td rowspan="2"></td>
                                                            <td rowspan="2" class="local">Domestic Data Traffic</td>
                                                            <td rowspan="2" class="international">International Data Traffic</td>
                                                            <td rowspan="2" class="total">TOTAL</td>
                                                        </tr>
                                                        <tr>
                                                        </tr>
                                                        <tr>
                                                            <td class="info" name="infoFixCosts" id="infoFixCosts" style="font-weight: bold;">
                                                                Fix Costs (€)
                                                            </td> 
                                                            <td class="double_td"><input readonly="true" type="text" style="background-color: #cacaca;" id="fixLocalData3G" name="fixLocalData3G" value="<c:out value="${reportMap[i]['fixLocalData3G']}"/>" onkeypress="onlyNumbers(event);" onkeyup="calculateTotal(<c:out value='${i}'/>, 'fixTotal3G');"/></td>
                                                            <td class="double_td"><input readonly="true" type="text" style="background-color: #cacaca;" id="fixInternationalData3G" name="fixInternationalData3G" value="<c:out value="${reportMap[i]['fixInternationalData3G']}"/>" onkeypress="onlyNumbers(event);" onkeyup="calculateTotal(<c:out value='${i}'/>, 'fixTotal3G');"/></td>
                                                            <td class="total_cost"><input readonly="true" type="text" style="background-color: #cacaca;" id="fixTotal3G" name="fixTotal3G" value="<c:out value="${reportMap[i]['fixTotal3G']}"/>" onkeypress="onlyNumbers(event);" onblur="checkTotal(<c:out value='${i}'/>, 'fixTotal3G');"/></td>
                                                        </tr>
                                                        <tr>
                                                            <td class="info" name="infoVarCosts" id="infoVarCosts" style="font-weight: bold;">
                                                                Variable Costs (€)
                                                            </td>
                                                            <td class="double_td"><input readonly="true" type="text" style="background-color: #cacaca;" id="varLocalData3G" name="varLocalData3G" value="<c:out value="${reportMap[i]['varLocalData3G']}"/>" onkeypress="onlyNumbers(event);" onkeyup="calculateTotal(<c:out value='${i}'/>, 'varTotal3G');"/></td>
                                                            <td class="double_td"><input readonly="true" type="text" style="background-color: #cacaca;" id="varInternationalData3G" name="varInternationalData3G" value="<c:out value="${reportMap[i]['varInternationalData3G']}"/>" onkeypress="onlyNumbers(event);" onkeyup="calculateTotal(<c:out value='${i}'/>, 'varTotal3G');"/></td>
                                                            <td class="total_cost"><input readonly="true" type="text" style="background-color: #cacaca;" id="varTotal3G" name="varTotal3G" value="<c:out value="${reportMap[i]['varTotal3G']}"/>" onkeypress="onlyNumbers(event);" onblur="checkTotal(<c:out value='${i}'/>, 'varTotal3G');"/></td>
                                                        </tr>        
                                                    </table>
                                                    <table>
                                                        <c:if test="${form['errors'] != null  && modifiedReport == i}">
                                                            <div class="errors">
                                                                <ul>
                                                                    <c:if test="${!empty (form.errors['linesMobile'])}">
                                                                        <li><c:out value="${form.errors['linesMobile']}"/></li>
                                                                    </c:if>
                                                                    <c:if test="${!empty (form.errors['fixMobile'])}">
                                                                        <li><c:out value="${form.errors['fixMobile']}"/></li>
                                                                    </c:if>
                                                                    <c:if test="${!empty (form.errors['varMobile'])}">
                                                                        <li><c:out value="${form.errors['varMobile']}"/></li>
                                                                    </c:if>
                                                                </ul>
                                                            </div>
                                                        </c:if>
                                                        <tr>
                                                            <td></td>
                                                            <td colspan="5" style="font-weight: bold;">Mobile Telephony</td>
                                                        </tr>
                                                        <tr>
                                                            <td class="info" name="infoLinesCount" id="infoLinesCount" style="font-weight: bold;">
                                                                Number of lines
                                                            </td>
                                                            <td colspan="5"><input readonly="true" type="text" name="linesMobile" style="background-color: #cacaca;" id="linesMobile" value="<c:out value="${reportMap[i]['linesMobile']}"/>"/></td>
                                                        </tr>
                                                        <tr>
                                                            <td rowspan="2"></td>
                                                            <td colspan="2" class="local">Domestic</td>
                                                            <td colspan="2" class="international">International</td>
                                                            <td rowspan="2" class="total">TOTAL</td>
                                                        </tr>
                                                        <tr>
                                                            <td class="local">Voice</td>
                                                            <td class="local">Data Traffic</td>
                                                            <td class="international">Voice</td>
                                                            <td class="international">Data Traffic</td>
                                                        </tr>
                                                        <tr>
                                                            <td class="info" name="infoFixCosts" id="infoFixCosts" style="font-weight: bold;">
                                                                Fix Costs (€)
                                                            </td>
                                                            <td><input readonly="true" type="text" style="background-color: #cacaca;" id="fixLocalCallsMobile" name="fixLocalCallsMobile" value="<c:out value="${reportMap[i]['fixLocalCallsMobile']}"/>" onkeypress="onlyNumbers(event);" onkeyup="calculateTotal(<c:out value='${i}'/>, 'fixTotalMobile');"/></td>
                                                            <td><input readonly="true" type="text" style="background-color: #cacaca;" id="fixLocalDataMobile" name="fixLocalDataMobile" value="<c:out value="${reportMap[i]['fixLocalDataMobile']}"/>" onkeypress="onlyNumbers(event);" onkeyup="calculateTotal(<c:out value='${i}'/>, 'fixTotalMobile');"/></td>
                                                            <td><input readonly="true" type="text" style="background-color: #cacaca;" id="fixInternationalCallsMobile" name="fixInternationalCallsMobile" value="<c:out value="${reportMap[i]['fixLocalCallsMobile']}"/>" onkeypress="onlyNumbers(event);" onkeyup="calculateTotal(<c:out value='${i}'/>, 'fixTotalMobile');"/></td>
                                                            <td><input readonly="true" type="text" style="background-color: #cacaca;" id="fixInternationalDataMobile" name="fixInternationalDataMobile" value="<c:out value="${reportMap[i]['fixLocalDataMobile']}"/>" onkeypress="onlyNumbers(event);" onkeyup="calculateTotal(<c:out value='${i}'/>, 'fixTotalMobile');"/></td>
                                                            <td class="total_cost"><input readonly="true" type="text" style="background-color: #cacaca;" id="fixTotalMobile" name="fixTotalMobile" value="<c:out value="${reportMap[i]['fixTotalMobile']}"/>" onkeypress="onlyNumbers(event);" onblur="checkTotal(<c:out value='${i}'/>, 'fixTotalMobile');"/></td>
                                                        </tr>
                                                        <tr>
                                                            <td class="info" name="infoVarCosts" id="infoVarCosts" style="font-weight: bold;">
                                                                Variable Costs (€)
                                                            </td>
                                                            <td><input readonly="true" type="text" style="background-color: #cacaca;" id="varLocalCallsMobile" name="varLocalCallsMobile" value="<c:out value="${reportMap[i]['varLocalCallsMobile']}"/>" onkeypress="onlyNumbers(event);" onkeyup="calculateTotal(<c:out value='${i}'/>, 'varTotalMobile');"/></td>
                                                            <td><input readonly="true" type="text" style="background-color: #cacaca;" id="varLocalDataMobile" name="varLocalDataMobile" value="<c:out value="${reportMap[i]['varLocalDataMobile']}"/>" onkeypress="onlyNumbers(event);" onkeyup="calculateTotal(<c:out value='${i}'/>, 'varTotalMobile');"/></td>
                                                            <td><input readonly="true" type="text" style="background-color: #cacaca;" id="varInternationalCallsMobile" name="varInternationalCallsMobile" value="<c:out value="${reportMap[i]['varLocalCallsMobile']}"/>" onkeypress="onlyNumbers(event);" onkeyup="calculateTotal(<c:out value='${i}'/>, 'varTotalMobile');"/></td>
                                                            <td><input readonly="true" type="text" style="background-color: #cacaca;" id="varInternationalDataMobile" name="varInternationalDataMobile" value="<c:out value="${reportMap[i]['varLocalDataMobile']}"/>" onkeypress="onlyNumbers(event);" onkeyup="calculateTotal(<c:out value='${i}'/>, 'varTotalMobile');"/></td>
                                                            <td class="total_cost"><input readonly="true" type="text" style="background-color: #cacaca;" id="varTotalMobile" name="varTotalMobile" value="<c:out value="${reportMap[i]['varTotalMobile']}"/>" onkeypress="onlyNumbers(event);" onblur="checkTotal(<c:out value='${i}'/>, 'varTotalMobile');"/></td>
                                                        </tr>        
                                                    </table>
                                                    <table>
                                                        <c:if test="${form['errors'] != null  && modifiedReport == i}">
                                                            <div class="errors">
                                                                <ul>
                                                                    <c:if test="${!empty (form.errors['linesBB'])}">
                                                                        <li><c:out value="${form.errors['linesBB']}"/></li>
                                                                    </c:if>
                                                                    <c:if test="${!empty (form.errors['fixBB'])}">
                                                                        <li><c:out value="${form.errors['fixBB']}"/></li>
                                                                    </c:if>
                                                                    <c:if test="${!empty (form.errors['varBB'])}">
                                                                        <li><c:out value="${form.errors['varBB']}"/></li>
                                                                    </c:if>
                                                                </ul>
                                                            </div>
                                                        </c:if>
                                                        <tr>
                                                            <td></td>
                                                            <td colspan="5" style="font-weight: bold;">BlackBerry Smartphones</td>
                                                        </tr>
                                                        <tr>
                                                            <td class="info" name="infoLinesCount" id="infoLinesCount" style="font-weight: bold;">
                                                                Number of lines
                                                            </td>
                                                            <td colspan="5"><input readonly="true" type="text" name="linesBB" style="background-color: #cacaca;" id="linesBB" value="<c:out value="${reportMap[i]['linesBB']}"/>"/></td>
                                                        </tr>
                                                        <tr>
                                                            <td rowspan="2"></td>
                                                            <td colspan="2" class="local">Domestic</td>
                                                            <td colspan="2" class="international">International</td>
                                                            <td rowspan="2" class="total">TOTAL</td>
                                                        </tr>
                                                        <tr>
                                                            <td class="local">Voice</td>
                                                            <td class="local">Data Traffic</td>
                                                            <td class="international">Voice</td>
                                                            <td class="international">Data Traffic</td>
                                                        </tr>
                                                        <tr>
                                                            <td class="info" name="infoFixCosts" id="infoFixCosts" style="font-weight: bold;">
                                                                Fix Costs (€)
                                                            </td>
                                                            <td><input readonly="true" type="text" style="background-color: #cacaca;" id="fixLocalCallsBB" name="fixLocalCallsBB" value="<c:out value="${reportMap[i]['fixLocalCallsBB']}"/>" onkeypress="onlyNumbers(event);" onkeyup="calculateTotal(<c:out value='${i}'/>, 'fixTotalBB');"/></td>
                                                            <td><input readonly="true" type="text" style="background-color: #cacaca;" id="fixLocalDataBB" name="fixLocalDataBB" value="<c:out value="${reportMap[i]['fixLocalDataBB']}"/>" onkeypress="onlyNumbers(event);" onkeyup="calculateTotal(<c:out value='${i}'/>, 'fixTotalBB');"/></td>
                                                            <td><input readonly="true" type="text" style="background-color: #cacaca;" id="fixInternationalCallsBB" name="fixInternationalCallsBB" value="<c:out value="${reportMap[i]['fixLocalCallsBB']}"/>" onkeypress="onlyNumbers(event);" onkeyup="calculateTotal(<c:out value='${i}'/>, 'fixTotalBB');"/></td>
                                                            <td><input readonly="true" type="text" style="background-color: #cacaca;" id="fixInternationalDataBB" name="fixInternationalDataBB" value="<c:out value="${reportMap[i]['fixLocalDataBB']}"/>" onkeypress="onlyNumbers(event);" onkeyup="calculateTotal(<c:out value='${i}'/>, 'fixTotalBB');"/></td>
                                                            <td class="total_cost"><input readonly="true" type="text" style="background-color: #cacaca;" id="fixTotalBB" name="fixTotalBB" value="<c:out value="${reportMap[i]['fixTotalBB']}"/>" onkeypress="onlyNumbers(event);" onblur="checkTotal(<c:out value='${i}'/>, 'fixTotalBB');"/></td>
                                                        </tr>
                                                        <tr>
                                                            <td class="info" name="infoVarCosts" id="infoVarCosts" style="font-weight: bold;">
                                                                Variable Costs (€)
                                                            </td>
                                                            <td><input readonly="true" type="text" style="background-color: #cacaca;" id="varLocalCallsBB" name="varLocalCallsBB" value="<c:out value="${reportMap[i]['varLocalCallsBB']}"/>" onkeypress="onlyNumbers(event);" onkeyup="calculateTotal(<c:out value='${i}'/>, 'varTotalBB');"/></td>
                                                            <td><input readonly="true" type="text" style="background-color: #cacaca;" id="varLocalDataBB" name="varLocalDataBB" value="<c:out value="${reportMap[i]['varLocalDataBB']}"/>" onkeypress="onlyNumbers(event);" onkeyup="calculateTotal(<c:out value='${i}'/>, 'varTotalBB');"/></td>
                                                            <td><input readonly="true" type="text" style="background-color: #cacaca;" id="varInternationalCallsBB" name="varInternationalCallsBB" value="<c:out value="${reportMap[i]['varLocalCallsBB']}"/>" onkeypress="onlyNumbers(event);" onkeyup="calculateTotal(<c:out value='${i}'/>, 'varTotalBB');"/></td>
                                                            <td><input readonly="true" type="text" style="background-color: #cacaca;" id="varInternationalDataBB" name="varInternationalDataBB" value="<c:out value="${reportMap[i]['varLocalDataBB']}"/>" onkeypress="onlyNumbers(event);" onkeyup="calculateTotal(<c:out value='${i}'/>, <c:out value='${i}'/>, 'varTotalBB');"/></td>
                                                            <td class="total_cost"><input readonly="true" type="text" style="background-color: #cacaca;" id="varTotalBB" name="varTotalBB" value="<c:out value="${reportMap[i]['varTotalBB']}"/>" onkeypress="onlyNumbers(event);" onblur="checkTotal(<c:out value='${i}'/>, 'varTotalBB');"/></td>
                                                        </tr>         
                                                    </table>
                                                </c:if>
                                                <div class="submit">
                                                    <input type="button" class="button" id="buttonEdit" value="Edit Report" onClick="editReport('<c:out value='report${i}'/>')"/>
                                                    <input type="submit" class="button" name="<c:out value='report${i}'/>" style="display: none;" value="Save Report"/>
                                                </div>
                                            </div>
                                        </div>
                                    </form>
                                </c:otherwise>
                            </c:choose>
                        </c:if>
                    </c:forEach>
                    <c:forEach var="i" begin="1" end="3" step="1">
                        <c:if test="${reportMap[i] != null}">
                            <c:choose>
                                <c:when test="${reportMap[i]['editable']}">
                                    <form name="<c:out value='report${i}'/>" action="Reports" method="POST">
                                        <div id="<c:out value='report${i}'/>" style="border: 1px black solid; margin-top: 30px;">
                                            <div style="background-color: crimson;">
                                                <span style="color: white; margin-left: 10px;"><c:out value='${monthList[i]} ${reportMap[i]["year"]}'/> Report</span>
                                                <span id="showslide${i}" onClick="slideDown('slide${i}', 500);" style="float: right; color: white; cursor: pointer; margin-right: 10px;">Show the report</span>
                                                <span id="hideslide${i}" onClick="slideUp('slide${i}', 500);" style="float: right; display: none; color: white; cursor: pointer; margin-right: 10px;">Hide the report</span>
                                            </div>
                                            <div id="slide${i}" style="height: 0px; overflow: hidden;">
                                                <c:if test="${result != null && modifiedReport == i}">
                                                    <c:choose>
                                                        <c:when test="${'Report saved !' eq result}">
                                                            <div class="valid">
                                                                <ul>
                                                                    <li><c:out value="${result}"/></li>
                                                                </ul>
                                                            </div>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <div class="errors">
                                                                <ul>
                                                                    <li style="font-weight: bold;"><c:out value="${result}"/></li>
                                                                </ul>
                                                            </div>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </c:if>
                                                <c:if test="${type eq 'Fixed' || type eq 'Both'}">
                                                    <table style="margin-top: 20px;">
                                                        <c:if test="${form['errors'] != null  && modifiedReport == i}">
                                                            <div class="errors">
                                                                <ul>
                                                                    <c:if test="${!empty (form.errors['linesFix'])}">
                                                                        <li><c:out value="${form.errors['linesFix']}"/></li>
                                                                    </c:if>
                                                                    <c:if test="${!empty (form.errors['fixFix'])}">
                                                                        <li><c:out value="${form.errors['fixFix']}"/></li>
                                                                    </c:if>
                                                                    <c:if test="${!empty (form.errors['varFix'])}">
                                                                        <li><c:out value="${form.errors['varFix']}"/></li>
                                                                    </c:if>
                                                                </ul>
                                                            </div>
                                                        </c:if>
                                                        <tr>
                                                            <td></td>
                                                            <td colspan="3" style="font-weight: bold;">Fix Telephony</td>
                                                        </tr>
                                                        <tr>
                                                            <td class="info" name="infoLinesCount" id="infoLinesCount" style="font-weight: bold;">
                                                                Number of lines
                                                            </td>
                                                            <td colspan="3"><input type="text" name="linesFix" value="<c:out value="${reportMap[i]['linesFix']}"/>" id="linesFix"/></td>
                                                        </tr>
                                                        <tr>
                                                            <td rowspan="2"></td>
                                                            <td rowspan="2" class="local">Domestic Voice</td>
                                                            <td rowspan="2" class="international">International Voice</td>
                                                            <td rowspan="2" class="total">TOTAL</td>
                                                        </tr>
                                                        <tr>
                                                        </tr>
                                                        <tr>
                                                            <td class="info" name="infoFixCosts" id="infoFixCosts" style="font-weight: bold;">
                                                                Fix Costs (€)
                                                            </td> 
                                                            <td class="double_td"><input type="text" name="fixLocalCallsFix" value="<c:out value="${reportMap[i]['fixLocalCallsFix']}"/>" id="fixLocalCallsFix" onkeypress="onlyNumbers(event);" onkeyup="calculateTotal(<c:out value='${i}'/>, 'fixTotalFix');"/></td>
                                                            <td class="double_td"><input type="text" name="fixInternationalCallsFix" value="<c:out value="${reportMap[i]['fixInternationalCallsFix']}"/>" id="fixInternationalCallsFix" onkeypress="onlyNumbers(event);" onkeyup="calculateTotal(<c:out value='${i}'/>, 'fixTotalFix');"/></td>
                                                            <td class="total_cost"><input type="text" name="fixTotalFix" value="<c:out value="${reportMap[i]['fixTotalFix']}"/>" id="fixTotalFix" onkeypress="onlyNumbers(event);" onblur="checkTotal(<c:out value='${i}'/>, 'fixTotalFix');"/></td>
                                                        </tr>
                                                        <tr>
                                                            <td class="info" name="infoVarCosts" id="infoVarCosts" style="font-weight: bold;">
                                                                Variable Costs (€)
                                                            </td>
                                                            <td class="double_td"><input type="text" name="varLocalCallsFix" value="<c:out value="${reportMap[i]['varLocalCallsFix']}"/>" id="varLocalCallsFix" onkeypress="onlyNumbers(event);" onkeyup="calculateTotal(<c:out value='${i}'/>, 'varTotalFix');"/></td>
                                                            <td class="double_td"><input type="text" name="varInternationalCallsFix" value="<c:out value="${reportMap[i]['varInternationalCallsFix']}"/>" id="varInternationalCallsFix" onkeypress="onlyNumbers(event);" onkeyup="calculateTotal(<c:out value='${i}'/>, 'varTotalFix');"/></td>
                                                            <td class="total_cost"><input type="text" name="varTotalFix" value="<c:out value="${reportMap[i]['varTotalFix']}"/>" id="varTotalFix" onkeypress="onlyNumbers(event);" onblur="checkTotal(<c:out value='${i}'/>, 'varTotalFix');"/></td>
                                                        </tr>        
                                                    </table>
                                                </c:if>
                                                <c:if test="${type eq 'Mobile' || type eq 'Both'}">
                                                    <table <c:if test="${type eq 'Mobile'}"> style="margin-top: 20px;"</c:if>>
                                                        <c:if test="${form['errors'] != null  && modifiedReport == i}">
                                                            <div class="errors">
                                                                <ul>
                                                                    <c:if test="${!empty (form.errors['lines3G'])}">
                                                                        <li><c:out value="${form.errors['lines3G']}"/></li>
                                                                    </c:if>
                                                                    <c:if test="${!empty (form.errors['fix3G'])}">
                                                                        <li><c:out value="${form.errors['fix3G']}"/></li>
                                                                    </c:if>
                                                                    <c:if test="${!empty (form.errors['var3G'])}">
                                                                        <li><c:out value="${form.errors['var3G']}"/></li>
                                                                    </c:if>
                                                                </ul>
                                                            </div>
                                                        </c:if>
                                                        <tr>
                                                            <td></td>
                                                            <td colspan="3" style="font-weight: bold;">3G / Aircards</td>
                                                        </tr>
                                                        <tr>
                                                            <td class="info" name="infoLinesCount" id="infoLinesCount" style="font-weight: bold;">
                                                                Number of lines
                                                            </td>
                                                            <td colspan="3"><input type="text" name="lines3G" id="lines3G" value="<c:out value="${reportMap[i]['lines3G']}"/>"/></td>
                                                        </tr>
                                                        <tr>
                                                            <td rowspan="2"></td>
                                                            <td rowspan="2" class="local">Domestic Data Traffic</td>
                                                            <td rowspan="2" class="international">International Data Traffic</td>
                                                            <td rowspan="2" class="total">TOTAL</td>
                                                        </tr>
                                                        <tr>
                                                        </tr>
                                                        <tr>
                                                            <td class="info" name="infoFixCosts" id="infoFixCosts" style="font-weight: bold;">
                                                                Fix Costs (€)
                                                            </td>
                                                            <td class="double_td"><input type="text" id="fixLocalData3G" name="fixLocalData3G" value="<c:out value="${reportMap[i]['fixLocalData3G']}"/>" onkeypress="onlyNumbers(event);" onkeyup="calculateTotal(<c:out value='${i}'/>, 'fixTotal3G');"/></td>
                                                            <td class="double_td"><input type="text" id="fixInternationalData3G" name="fixInternationalData3G" value="<c:out value="${reportMap[i]['fixInternationalData3G']}"/>" onkeypress="onlyNumbers(event);" onkeyup="calculateTotal(<c:out value='${i}'/>, 'fixTotal3G');"/></td>
                                                            <td class="total_cost"><input type="text" id="fixTotal3G" name="fixTotal3G" value="<c:out value="${reportMap[i]['fixTotal3G']}"/>" onkeypress="onlyNumbers(event);" onblur="checkTotal(<c:out value='${i}'/>, 'fixTotal3G');"/></td>
                                                        </tr>
                                                        <tr>
                                                            <td class="info" name="infoVarCosts" id="infoVarCosts" style="font-weight: bold;">
                                                                Variable Costs (€)
                                                            </td>
                                                            <td class="double_td"><input type="text" id="varLocalData3G" name="varLocalData3G" value="<c:out value="${reportMap[i]['varLocalData3G']}"/>" onkeypress="onlyNumbers(event);" onkeyup="calculateTotal(<c:out value='${i}'/>, 'varTotal3G');"/></td>
                                                            <td class="double_td"><input type="text" id="varInternationalData3G" name="varInternationalData3G" value="<c:out value="${reportMap[i]['varInternationalData3G']}"/>" onkeypress="onlyNumbers(event);" onkeyup="calculateTotal(<c:out value='${i}'/>, 'varTotal3G');"/></td>
                                                            <td class="total_cost"><input type="text" id="varTotal3G" name="varTotal3G" value="<c:out value="${reportMap[i]['varTotal3G']}"/>" onkeypress="onlyNumbers(event);" onblur="checkTotal(<c:out value='${i}'/>, 'varTotal3G');"/></td>
                                                        </tr>        
                                                    </table>
                                                    <table>
                                                        <c:if test="${form['errors'] != null  && modifiedReport == i}">
                                                            <div class="errors">
                                                                <ul>
                                                                    <c:if test="${!empty (form.errors['linesMobile'])}">
                                                                        <li><c:out value="${form.errors['linesMobile']}"/></li>
                                                                    </c:if>
                                                                    <c:if test="${!empty (form.errors['fixMobile'])}">
                                                                        <li><c:out value="${form.errors['fixMobile']}"/></li>
                                                                    </c:if>
                                                                    <c:if test="${!empty (form.errors['varMobile'])}">
                                                                        <li><c:out value="${form.errors['varMobile']}"/></li>
                                                                    </c:if>
                                                                </ul>
                                                            </div>
                                                        </c:if>
                                                        <tr>
                                                            <td></td>
                                                            <td colspan="5" style="font-weight: bold;">Mobile Telephony</td>
                                                        </tr>
                                                        <tr>
                                                            <td class="info" name="infoLinesCount" id="infoLinesCount" style="font-weight: bold;">
                                                                Number of lines
                                                            </td>
                                                            <td colspan="5"><input type="text" name="linesMobile" id="linesMobile" value="<c:out value="${reportMap[i]['linesMobile']}"/>"/></td>
                                                        </tr>
                                                        <tr>
                                                            <td rowspan="2"></td>
                                                            <td colspan="2" class="local">Domestic</td>
                                                            <td colspan="2" class="international">International</td>
                                                            <td rowspan="2" class="total">TOTAL</td>
                                                        </tr>
                                                        <tr>
                                                            <td class="local">Voice</td>
                                                            <td class="local">Data Traffic</td>
                                                            <td class="international">Voice</td>
                                                            <td class="international">Data Traffic</td>
                                                        </tr>
                                                        <tr>
                                                            <td class="info" name="infoFixCosts" id="infoFixCosts" style="font-weight: bold;">
                                                                Fix Costs (€)
                                                            </td>
                                                            <td><input type="text" id="fixLocalCallsMobile" name="fixLocalCallsMobile" value="<c:out value="${reportMap[i]['fixLocalCallsMobile']}"/>" onkeypress="onlyNumbers(event);" onkeyup="calculateTotal(<c:out value='${i}'/>, 'fixTotalMobile');"/></td>
                                                            <td><input type="text" id="fixLocalDataMobile" name="fixLocalDataMobile" value="<c:out value="${reportMap[i]['fixLocalDataMobile']}"/>" onkeypress="onlyNumbers(event);" onkeyup="calculateTotal(<c:out value='${i}'/>, 'fixTotalMobile');"/></td>
                                                            <td><input type="text" id="fixInternationalCallsMobile" name="fixInternationalCallsMobile" value="<c:out value="${reportMap[i]['fixLocalCallsMobile']}"/>" onkeypress="onlyNumbers(event);" onkeyup="calculateTotal(<c:out value='${i}'/>, 'fixTotalMobile');"/></td>
                                                            <td><input type="text" id="fixInternationalDataMobile" name="fixInternationalDataMobile" value="<c:out value="${reportMap[i]['fixLocalDataMobile']}"/>" onkeypress="onlyNumbers(event);" onkeyup="calculateTotal(<c:out value='${i}'/>, 'fixTotalMobile');"/></td>
                                                            <td class="total_cost"><input type="text" id="fixTotalMobile" name="fixTotalMobile" value="<c:out value="${reportMap[i]['fixTotalMobile']}"/>" onkeypress="onlyNumbers(event);" onblur="checkTotal(<c:out value='${i}'/>, 'fixTotalMobile');"/></td>
                                                        </tr>
                                                        <tr>
                                                            <td class="info" name="infoVarCosts" id="infoVarCosts" style="font-weight: bold;">
                                                                Variable Costs (€)
                                                            </td>
                                                            <td><input type="text" id="varLocalCallsMobile" name="varLocalCallsMobile" value="<c:out value="${reportMap[i]['varLocalCallsMobile']}"/>" onkeypress="onlyNumbers(event);" onkeyup="calculateTotal(<c:out value='${i}'/>, 'varTotalMobile');"/></td>
                                                            <td><input type="text" id="varLocalDataMobile" name="varLocalDataMobile" value="<c:out value="${reportMap[i]['varLocalDataMobile']}"/>" onkeypress="onlyNumbers(event);" onkeyup="calculateTotal(<c:out value='${i}'/>, 'varTotalMobile');"/></td>
                                                            <td><input type="text" id="varInternationalCallsMobile" name="varInternationalCallsMobile" value="<c:out value="${reportMap[i]['varLocalCallsMobile']}"/>" onkeypress="onlyNumbers(event);" onkeyup="calculateTotal(<c:out value='${i}'/>, 'varTotalMobile');"/></td>
                                                            <td><input type="text" id="varInternationalDataMobile" name="varInternationalDataMobile" value="<c:out value="${reportMap[i]['varLocalDataMobile']}"/>" onkeypress="onlyNumbers(event);" onkeyup="calculateTotal(<c:out value='${i}'/>, 'varTotalMobile');"/></td>
                                                            <td class="total_cost"><input type="text" id="varTotalMobile" name="varTotalMobile" value="<c:out value="${reportMap[i]['varTotalMobile']}"/>" onkeypress="onlyNumbers(event);" onblur="checkTotal(<c:out value='${i}'/>, 'varTotalMobile');"/></td>
                                                        </tr>        
                                                    </table>
                                                    <table>
                                                        <c:if test="${form['errors'] != null  && modifiedReport == i}">
                                                            <div class="errors">
                                                                <ul>
                                                                    <c:if test="${!empty (form.errors['linesBB'])}">
                                                                        <li><c:out value="${form.errors['linesBB']}"/></li>
                                                                    </c:if>
                                                                    <c:if test="${!empty (form.errors['fixBB'])}">
                                                                        <li><c:out value="${form.errors['fixBB']}"/></li>
                                                                    </c:if>
                                                                    <c:if test="${!empty (form.errors['varBB'])}">
                                                                        <li><c:out value="${form.errors['varBB']}"/></li>
                                                                    </c:if>
                                                                </ul>
                                                            </div>
                                                        </c:if>
                                                        <tr>
                                                            <td></td>
                                                            <td colspan="5" style="font-weight: bold;">BlackBerry Smartphones</td>
                                                        </tr>
                                                        <tr>
                                                            <td class="info" name="infoLinesCount" id="infoLinesCount" style="font-weight: bold;">
                                                                Number of lines
                                                            </td>
                                                            <td colspan="5"><input type="text" id="linesBB" name="linesBB" value="<c:out value="${reportMap[i]['linesBB']}"/>"/></td>
                                                        </tr>
                                                        <tr>
                                                            <td rowspan="2"></td>
                                                            <td colspan="2" class="local">Domestic</td>
                                                            <td colspan="2" class="international">International</td>
                                                            <td rowspan="2" class="total">TOTAL</td>
                                                        </tr>
                                                        <tr>
                                                            <td class="local">Voice</td>
                                                            <td class="local">Data Traffic</td>
                                                            <td class="international">Voice</td>
                                                            <td class="international">Data Traffic</td>
                                                        </tr>
                                                        <tr>
                                                            <td class="info" name="infoFixCosts" id="infoFixCosts" style="font-weight: bold;">
                                                                Fix Costs (€)
                                                            </td>
                                                            <td><input type="text" id="fixLocalCallsBB" name="fixLocalCallsBB" value="<c:out value="${reportMap[i]['fixLocalCallsBB']}"/>" onkeypress="onlyNumbers(event);" onkeyup="calculateTotal(<c:out value='${i}'/>, 'fixTotalBB');"/></td>
                                                            <td><input type="text" id="fixLocalDataBB" name="fixLocalDataBB" value="<c:out value="${reportMap[i]['fixLocalDataBB']}"/>" onkeypress="onlyNumbers(event);" onkeyup="calculateTotal(<c:out value='${i}'/>, 'fixTotalBB');"/></td>
                                                            <td><input type="text" id="fixInternationalCallsBB" name="fixInternationalCallsBB" value="<c:out value="${reportMap[i]['fixLocalCallsBB']}"/>" onkeypress="onlyNumbers(event);" onkeyup="calculateTotal(<c:out value='${i}'/>, 'fixTotalBB');"/></td>
                                                            <td><input type="text" id="fixInternationalDataBB" name="fixInternationalDataBB" value="<c:out value="${reportMap[i]['fixLocalDataBB']}"/>" onkeypress="onlyNumbers(event);" onkeyup="calculateTotal(<c:out value='${i}'/>, 'fixTotalBB');"/></td>
                                                            <td class="total_cost"><input type="text" id="fixTotalBB" name="fixTotalBB" value="<c:out value="${reportMap[i]['fixTotalBB']}"/>" onkeypress="onlyNumbers(event);" onblur="checkTotal(<c:out value='${i}'/>, 'fixTotalBB');"/></td>
                                                        </tr>
                                                        <tr>
                                                            <td class="info" name="infoVarCosts" id="infoVarCosts" style="font-weight: bold;">
                                                                Variable Costs (€)
                                                            </td>
                                                            <td><input type="text" id="varLocalCallsBB" name="varLocalCallsBB" value="<c:out value="${reportMap[i]['varLocalCallsBB']}"/>" onkeypress="onlyNumbers(event);" onkeyup="calculateTotal(<c:out value='${i}'/>, 'varTotalBB');"/></td>
                                                            <td><input type="text" id="varLocalDataBB" name="varLocalDataBB" value="<c:out value="${reportMap[i]['varLocalDataBB']}"/>" onkeypress="onlyNumbers(event);" onkeyup="calculateTotal(<c:out value='${i}'/>, 'varTotalBB');"/></td>
                                                            <td><input type="text" id="varInternationalCallsBB" name="varInternationalCallsBB" value="<c:out value="${reportMap[i]['varLocalCallsBB']}"/>" onkeypress="onlyNumbers(event);" onkeyup="calculateTotal(<c:out value='${i}'/>, 'varTotalBB');"/></td>
                                                            <td><input type="text" id="varInternationalDataBB" name="varInternationalDataBB" value="<c:out value="${reportMap[i]['varLocalDataBB']}"/>" onkeypress="onlyNumbers(event);" onkeyup="calculateTotal(<c:out value='${i}'/>, 'varTotalBB');"/></td>
                                                            <td class="total_cost"><input type="text" id="varTotalBB" name="varTotalBB" value="<c:out value="${reportMap[i]['varTotalBB']}"/>" onkeypress="onlyNumbers(event);" onblur="checkTotal(<c:out value='${i}'/>, 'varTotalBB');"/></td>
                                                        </tr>         
                                                    </table>
                                                </c:if>
                                                <div class="submit">
                                                    <input type="button" class="button" id="buttonEdit" value="Edit Report" style="display: none;" onClick="editReport('<c:out value='report${i}'/>')"/>
                                                    <input type="submit" class="button" name="<c:out value='report${i}'/>" value="Save Report"/>
                                                </div>
                                            </div>
                                        </div>
                                    </form>
                                </c:when>
                                <c:otherwise>
                                    <form name="<c:out value='report${i}'/>" action="Reports" method="POST">
                                        <div id="<c:out value='report${i}'/>" style="border: 1px black solid; margin-top: 30px;">
                                            <div style="background-color: green;">
                                                <span style="color: white; margin-left: 10px;"><c:out value='${monthList[i]} ${reportMap[i]["year"]}'/> Report</span>
                                                <span id="showslide${i}" onClick="slideDown('slide${i}', 500);" style="float: right; color: white; cursor: pointer; margin-right: 10px;">Show the report</span>
                                                <span id="hideslide${i}" onClick="slideUp('slide${i}', 500);" style="float: right; display: none; color: white; cursor: pointer; margin-right: 10px;">Hide the report</span>
                                            </div>
                                            <div id="slide${i}" style="height: 0px; overflow: hidden;">
                                                <c:if test="${result != null  && modifiedReport == i}">
                                                    <c:choose>
                                                        <c:when test="${'Report saved !' eq result}">
                                                            <div class="valid">
                                                                <ul>
                                                                    <li><c:out value="${result}"/></li>
                                                                </ul>
                                                            </div>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <div class="errors">
                                                                <ul>
                                                                    <li style="font-weight: bold;"><c:out value="${result}"/></li>
                                                                </ul>
                                                            </div>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </c:if>
                                                <c:if test="${type eq 'Fixed' || type eq 'Both'}">
                                                    <table style="margin-top: 20px;">
                                                        <c:if test="${form['errors'] != null  && modifiedReport == i}">
                                                            <div class="errors">
                                                                <ul>
                                                                    <c:if test="${!empty (form.errors['linesFix'])}">
                                                                        <li><c:out value="${form.errors['linesFix']}"/></li>
                                                                    </c:if>
                                                                    <c:if test="${!empty (form.errors['fixFix'])}">
                                                                        <li><c:out value="${form.errors['fixFix']}"/></li>
                                                                    </c:if>
                                                                    <c:if test="${!empty (form.errors['varFix'])}">
                                                                        <li><c:out value="${form.errors['varFix']}"/></li>
                                                                    </c:if>
                                                                </ul>
                                                            </div>
                                                        </c:if>
                                                        <tr>
                                                            <td></td>
                                                            <td colspan="3" style="font-weight: bold;">Fix Telephony</td>
                                                        </tr>
                                                        <tr>
                                                            <td class="info" name="infoLinesCount" id="infoLinesCount" style="font-weight: bold;">
                                                                Number of lines
                                                            </td>
                                                            <td colspan="3"><input readonly="true" type="text" style="background-color: #cacaca;" name="linesFix" value="<c:out value="${reportMap[i]['linesFix']}"/>" id="linesFix"/></td>
                                                        </tr>
                                                        <tr>
                                                            <td rowspan="2"></td>
                                                            <td rowspan="2" class="local">Domestic Voice</td>
                                                            <td rowspan="2" class="international">International Voice</td>
                                                            <td rowspan="2" class="total">TOTAL</td>
                                                        </tr>
                                                        <tr>
                                                        </tr>
                                                        <tr>
                                                            <td class="info" name="infoFixCosts" id="infoFixCosts" style="font-weight: bold;">
                                                                Fix Costs (€)
                                                            </td> 
                                                            <td class="double_td"><input readonly="true" type="text" style="background-color: #cacaca;" name="fixLocalCallsFix" value="<c:out value="${reportMap[i]['fixLocalCallsFix']}"/>" id="fixLocalCallsFix" onkeypress="onlyNumbers(event);" onkeyup="calculateTotal(<c:out value='${i}'/>, 'fixTotalFix');"/></td>
                                                            <td class="double_td"><input readonly="true" type="text" style="background-color: #cacaca;" name="fixInternationalCallsFix" value="<c:out value="${reportMap[i]['fixInternationalCallsFix']}"/>" id="fixInternationalCallsFix" onkeypress="onlyNumbers(event);" onkeyup="calculateTotal(<c:out value='${i}'/>, 'fixTotalFix');"/></td>
                                                            <td class="total_cost"><input readonly="true" type="text" style="background-color: #cacaca;" name="fixTotalFix" value="<c:out value="${reportMap[i]['fixTotalFix']}"/>" id="fixTotalFix" onkeypress="onlyNumbers(event);" onblur="checkTotal(<c:out value='${i}'/>, 'fixTotalFix');"/></td>
                                                        </tr>
                                                        <tr>
                                                            <td class="info" name="infoVarCosts" id="infoVarCosts" style="font-weight: bold;">
                                                                Variable Costs (€)
                                                            </td> 
                                                            <td class="double_td"><input readonly="true" type="text" style="background-color: #cacaca;" name="varLocalCallsFix" value="<c:out value="${reportMap[i]['varLocalCallsFix']}"/>" id="varLocalCallsFix" onkeypress="onlyNumbers(event);" onkeyup="calculateTotal(<c:out value='${i}'/>, 'varTotalFix');"/></td>
                                                            <td class="double_td"><input readonly="true" type="text" style="background-color: #cacaca;" name="varInternationalCallsFix" value="<c:out value="${reportMap[i]['varInternationalCallsFix']}"/>" id="varInternationalCallsFix" onkeypress="onlyNumbers(event);" onkeyup="calculateTotal(<c:out value='${i}'/>, 'varTotalFix');"/></td>
                                                            <td class="total_cost"><input readonly="true" type="text" style="background-color: #cacaca;" name="varTotalFix" value="<c:out value="${reportMap[i]['varTotalFix']}"/>" id="varTotalFix" onkeypress="onlyNumbers(event);" onblur="checkTotal(<c:out value='${i}'/>, 'varTotalFix');"/></td>
                                                        </tr>        
                                                    </table>
                                                </c:if>
                                                <c:if test="${type eq 'Mobile' || type eq 'Both'}">
                                                    <table <c:if test="${type eq 'Mobile'}"> style="margin-top: 20px;"</c:if>>
                                                        <c:if test="${form['errors'] != null  && modifiedReport == i}">
                                                            <div class="errors">
                                                                <ul>
                                                                    <c:if test="${!empty (form.errors['lines3G'])}">
                                                                        <li><c:out value="${form.errors['lines3G']}"/></li>
                                                                    </c:if>
                                                                    <c:if test="${!empty (form.errors['fix3G'])}">
                                                                        <li><c:out value="${form.errors['fix3G']}"/></li>
                                                                    </c:if>
                                                                    <c:if test="${!empty (form.errors['var3G'])}">
                                                                        <li><c:out value="${form.errors['var3G']}"/></li>
                                                                    </c:if>
                                                                </ul>
                                                            </div>
                                                        </c:if>
                                                        <tr>
                                                            <td></td>
                                                            <td colspan="3" style="font-weight: bold;">3G / Aircards</td>
                                                        </tr>
                                                        <tr>
                                                            <td class="info" name="infoLinesCount" id="infoLinesCount" style="font-weight: bold;">
                                                                Number of lines
                                                            </td>
                                                            <td colspan="3"><input readonly="true" type="text" name="lines3G" style="background-color: #cacaca;" id="lines3G" value="<c:out value="${reportMap[i]['lines3G']}"/>"/></td>
                                                        </tr>
                                                        <tr>
                                                            <td rowspan="2"></td>
                                                            <td rowspan="2" class="local">Domestic Data Traffic</td>
                                                            <td rowspan="2" class="international">International Data Traffic</td>
                                                            <td rowspan="2" class="total">TOTAL</td>
                                                        </tr>
                                                        <tr>
                                                        </tr>
                                                        <tr>
                                                            <td class="info" name="infoFixCosts" id="infoFixCosts" style="font-weight: bold;">
                                                                Fix Costs (€)
                                                            </td> 
                                                            <td class="double_td"><input readonly="true" type="text" style="background-color: #cacaca;" id="fixLocalData3G" name="fixLocalData3G" value="<c:out value="${reportMap[i]['fixLocalData3G']}"/>" onkeypress="onlyNumbers(event);" onkeyup="calculateTotal(<c:out value='${i}'/>, 'fixTotal3G');"/></td>
                                                            <td class="double_td"><input readonly="true" type="text" style="background-color: #cacaca;" id="fixInternationalData3G" name="fixInternationalData3G" value="<c:out value="${reportMap[i]['fixInternationalData3G']}"/>" onkeypress="onlyNumbers(event);" onkeyup="calculateTotal(<c:out value='${i}'/>, 'fixTotal3G');"/></td>
                                                            <td class="total_cost"><input readonly="true" type="text" style="background-color: #cacaca;" id="fixTotal3G" name="fixTotal3G" value="<c:out value="${reportMap[i]['fixTotal3G']}"/>" onkeypress="onlyNumbers(event);" onblur="checkTotal(<c:out value='${i}'/>, 'fixTotal3G');"/></td>
                                                        </tr>
                                                        <tr>
                                                            <td class="info" name="infoVarCosts" id="infoVarCosts" style="font-weight: bold;">
                                                                Variable Costs (€)
                                                            </td>
                                                            <td class="double_td"><input readonly="true" type="text" style="background-color: #cacaca;" id="varLocalData3G" name="varLocalData3G" value="<c:out value="${reportMap[i]['varLocalData3G']}"/>" onkeypress="onlyNumbers(event);" onkeyup="calculateTotal(<c:out value='${i}'/>, 'varTotal3G');"/></td>
                                                            <td class="double_td"><input readonly="true" type="text" style="background-color: #cacaca;" id="varInternationalData3G" name="varInternationalData3G" value="<c:out value="${reportMap[i]['varInternationalData3G']}"/>" onkeypress="onlyNumbers(event);" onkeyup="calculateTotal(<c:out value='${i}'/>, 'varTotal3G');"/></td>
                                                            <td class="total_cost"><input readonly="true" type="text" style="background-color: #cacaca;" id="varTotal3G" name="varTotal3G" value="<c:out value="${reportMap[i]['varTotal3G']}"/>" onkeypress="onlyNumbers(event);" onblur="checkTotal(<c:out value='${i}'/>, 'varTotal3G');"/></td>
                                                        </tr>        
                                                    </table>
                                                    <table>
                                                        <c:if test="${form['errors'] != null  && modifiedReport == i}">
                                                            <div class="errors">
                                                                <ul>
                                                                    <c:if test="${!empty (form.errors['linesMobile'])}">
                                                                        <li><c:out value="${form.errors['linesMobile']}"/></li>
                                                                    </c:if>
                                                                    <c:if test="${!empty (form.errors['fixMobile'])}">
                                                                        <li><c:out value="${form.errors['fixMobile']}"/></li>
                                                                    </c:if>
                                                                    <c:if test="${!empty (form.errors['varMobile'])}">
                                                                        <li><c:out value="${form.errors['varMobile']}"/></li>
                                                                    </c:if>
                                                                </ul>
                                                            </div>
                                                        </c:if>
                                                        <tr>
                                                            <td></td>
                                                            <td colspan="5" style="font-weight: bold;">Mobile Telephony</td>
                                                        </tr>
                                                        <tr>
                                                            <td class="info" name="infoLinesCount" id="infoLinesCount" style="font-weight: bold;">
                                                                Number of lines
                                                            </td>
                                                            <td colspan="5"><input readonly="true" type="text" name="linesMobile" style="background-color: #cacaca;" id="linesMobile" value="<c:out value="${reportMap[i]['linesMobile']}"/>"/></td>
                                                        </tr>
                                                        <tr>
                                                            <td rowspan="2"></td>
                                                            <td colspan="2" class="local">Domestic</td>
                                                            <td colspan="2" class="international">International</td>
                                                            <td rowspan="2" class="total">TOTAL</td>
                                                        </tr>
                                                        <tr>
                                                            <td class="local">Voice</td>
                                                            <td class="local">Data Traffic</td>
                                                            <td class="international">Voice</td>
                                                            <td class="international">Data Traffic</td>
                                                        </tr>
                                                        <tr>
                                                            <td class="info" name="infoFixCosts" id="infoFixCosts" style="font-weight: bold;">
                                                                Fix Costs (€)
                                                            </td>
                                                            <td><input readonly="true" type="text" style="background-color: #cacaca;" id="fixLocalCallsMobile" name="fixLocalCallsMobile" value="<c:out value="${reportMap[i]['fixLocalCallsMobile']}"/>" onkeypress="onlyNumbers(event);" onkeyup="calculateTotal(<c:out value='${i}'/>, 'fixTotalMobile');"/></td>
                                                            <td><input readonly="true" type="text" style="background-color: #cacaca;" id="fixLocalDataMobile" name="fixLocalDataMobile" value="<c:out value="${reportMap[i]['fixLocalDataMobile']}"/>" onkeypress="onlyNumbers(event);" onkeyup="calculateTotal(<c:out value='${i}'/>, 'fixTotalMobile');"/></td>
                                                            <td><input readonly="true" type="text" style="background-color: #cacaca;" id="fixInternationalCallsMobile" name="fixInternationalCallsMobile" value="<c:out value="${reportMap[i]['fixLocalCallsMobile']}"/>" onkeypress="onlyNumbers(event);" onkeyup="calculateTotal(<c:out value='${i}'/>, 'fixTotalMobile');"/></td>
                                                            <td><input readonly="true" type="text" style="background-color: #cacaca;" id="fixInternationalDataMobile" name="fixInternationalDataMobile" value="<c:out value="${reportMap[i]['fixLocalDataMobile']}"/>" onkeypress="onlyNumbers(event);" onkeyup="calculateTotal(<c:out value='${i}'/>, 'fixTotalMobile');"/></td>
                                                            <td class="total_cost"><input readonly="true" type="text" style="background-color: #cacaca;" id="fixTotalMobile" name="fixTotalMobile" value="<c:out value="${reportMap[i]['fixTotalMobile']}"/>" onkeypress="onlyNumbers(event);" onblur="checkTotal(<c:out value='${i}'/>, 'fixTotalMobile');"/></td>
                                                        </tr>
                                                        <tr>
                                                            <td class="info" name="infoVarCosts" id="infoVarCosts" style="font-weight: bold;">
                                                                Variable Costs (€)
                                                            </td>
                                                            <td><input readonly="true" type="text" style="background-color: #cacaca;" id="varLocalCallsMobile" name="varLocalCallsMobile" value="<c:out value="${reportMap[i]['varLocalCallsMobile']}"/>" onkeypress="onlyNumbers(event);" onkeyup="calculateTotal(<c:out value='${i}'/>, 'varTotalMobile');"/></td>
                                                            <td><input readonly="true" type="text" style="background-color: #cacaca;" id="varLocalDataMobile" name="varLocalDataMobile" value="<c:out value="${reportMap[i]['varLocalDataMobile']}"/>" onkeypress="onlyNumbers(event);" onkeyup="calculateTotal(<c:out value='${i}'/>, 'varTotalMobile');"/></td>
                                                            <td><input readonly="true" type="text" style="background-color: #cacaca;" id="varInternationalCallsMobile" name="varInternationalCallsMobile" value="<c:out value="${reportMap[i]['varLocalCallsMobile']}"/>" onkeypress="onlyNumbers(event);" onkeyup="calculateTotal(<c:out value='${i}'/>, 'varTotalMobile');"/></td>
                                                            <td><input readonly="true" type="text" style="background-color: #cacaca;" id="varInternationalDataMobile" name="varInternationalDataMobile" value="<c:out value="${reportMap[i]['varLocalDataMobile']}"/>" onkeypress="onlyNumbers(event);" onkeyup="calculateTotal(<c:out value='${i}'/>, 'varTotalMobile');"/></td>
                                                            <td class="total_cost"><input readonly="true" type="text" style="background-color: #cacaca;" id="varTotalMobile" name="varTotalMobile" value="<c:out value="${reportMap[i]['varTotalMobile']}"/>" onkeypress="onlyNumbers(event);" onblur="checkTotal(<c:out value='${i}'/>, 'varTotalMobile');"/></td>
                                                        </tr>        
                                                    </table>
                                                    <table>
                                                        <c:if test="${form['errors'] != null  && modifiedReport == i}">
                                                            <div class="errors">
                                                                <ul>
                                                                    <c:if test="${!empty (form.errors['linesBB'])}">
                                                                        <li><c:out value="${form.errors['linesBB']}"/></li>
                                                                    </c:if>
                                                                    <c:if test="${!empty (form.errors['fixBB'])}">
                                                                        <li><c:out value="${form.errors['fixBB']}"/></li>
                                                                    </c:if>
                                                                    <c:if test="${!empty (form.errors['varBB'])}">
                                                                        <li><c:out value="${form.errors['varBB']}"/></li>
                                                                    </c:if>
                                                                </ul>
                                                            </div>
                                                        </c:if>
                                                        <tr>
                                                            <td></td>
                                                            <td colspan="5" style="font-weight: bold;">BlackBerry Smartphones</td>
                                                        </tr>
                                                        <tr>
                                                            <td class="info" name="infoLinesCount" id="infoLinesCount" style="font-weight: bold;">
                                                                Number of lines
                                                            </td>
                                                            <td colspan="5"><input readonly="true" type="text" name="linesBB" style="background-color: #cacaca;" id="linesBB" value="<c:out value="${reportMap[i]['linesBB']}"/>"/></td>
                                                        </tr>
                                                        <tr>
                                                            <td rowspan="2"></td>
                                                            <td colspan="2" class="local">Domestic</td>
                                                            <td colspan="2" class="international">International</td>
                                                            <td rowspan="2" class="total">TOTAL</td>
                                                        </tr>
                                                        <tr>
                                                            <td class="local">Voice</td>
                                                            <td class="local">Data Traffic</td>
                                                            <td class="international">Voice</td>
                                                            <td class="international">Data Traffic</td>
                                                        </tr>
                                                        <tr>
                                                            <td class="info" name="infoFixCosts" id="infoFixCosts" style="font-weight: bold;">
                                                                Fix Costs (€)
                                                            </td>
                                                            <td><input readonly="true" type="text" style="background-color: #cacaca;" id="fixLocalCallsBB" name="fixLocalCallsBB" value="<c:out value="${reportMap[i]['fixLocalCallsBB']}"/>" onkeypress="onlyNumbers(event);" onkeyup="calculateTotal(<c:out value='${i}'/>, 'fixTotalBB');"/></td>
                                                            <td><input readonly="true" type="text" style="background-color: #cacaca;" id="fixLocalDataBB" name="fixLocalDataBB" value="<c:out value="${reportMap[i]['fixLocalDataBB']}"/>" onkeypress="onlyNumbers(event);" onkeyup="calculateTotal(<c:out value='${i}'/>, 'fixTotalBB');"/></td>
                                                            <td><input readonly="true" type="text" style="background-color: #cacaca;" id="fixInternationalCallsBB" name="fixInternationalCallsBB" value="<c:out value="${reportMap[i]['fixLocalCallsBB']}"/>" onkeypress="onlyNumbers(event);" onkeyup="calculateTotal(<c:out value='${i}'/>, 'fixTotalBB');"/></td>
                                                            <td><input readonly="true" type="text" style="background-color: #cacaca;" id="fixInternationalDataBB" name="fixInternationalDataBB" value="<c:out value="${reportMap[i]['fixLocalDataBB']}"/>" onkeypress="onlyNumbers(event);" onkeyup="calculateTotal(<c:out value='${i}'/>, 'fixTotalBB');"/></td>
                                                            <td class="total_cost"><input readonly="true" type="text" style="background-color: #cacaca;" id="fixTotalBB" name="fixTotalBB" value="<c:out value="${reportMap[i]['fixTotalBB']}"/>" onkeypress="onlyNumbers(event);" onblur="checkTotal(<c:out value='${i}'/>, 'fixTotalBB');"/></td>
                                                        </tr>
                                                        <tr>
                                                            <td class="info" name="infoVarCosts" id="infoVarCosts" style="font-weight: bold;">
                                                                Variable Costs (€)
                                                            </td>
                                                            <td><input readonly="true" type="text" style="background-color: #cacaca;" id="varLocalCallsBB" name="varLocalCallsBB" value="<c:out value="${reportMap[i]['varLocalCallsBB']}"/>" onkeypress="onlyNumbers(event);" onkeyup="calculateTotal(<c:out value='${i}'/>, 'varTotalBB');"/></td>
                                                            <td><input readonly="true" type="text" style="background-color: #cacaca;" id="varLocalDataBB" name="varLocalDataBB" value="<c:out value="${reportMap[i]['varLocalDataBB']}"/>" onkeypress="onlyNumbers(event);" onkeyup="calculateTotal(<c:out value='${i}'/>, 'varTotalBB');"/></td>
                                                            <td><input readonly="true" type="text" style="background-color: #cacaca;" id="varInternationalCallsBB" name="varInternationalCallsBB" value="<c:out value="${reportMap[i]['varLocalCallsBB']}"/>" onkeypress="onlyNumbers(event);" onkeyup="calculateTotal(<c:out value='${i}'/>, 'varTotalBB');"/></td>
                                                            <td><input readonly="true" type="text" style="background-color: #cacaca;" id="varInternationalDataBB" name="varInternationalDataBB" value="<c:out value="${reportMap[i]['varLocalDataBB']}"/>" onkeypress="onlyNumbers(event);" onkeyup="calculateTotal(<c:out value='${i}'/>, <c:out value='${i}'/>, 'varTotalBB');"/></td>
                                                            <td class="total_cost"><input readonly="true" type="text" style="background-color: #cacaca;" id="varTotalBB" name="varTotalBB" value="<c:out value="${reportMap[i]['varTotalBB']}"/>" onkeypress="onlyNumbers(event);" onblur="checkTotal(<c:out value='${i}'/>, 'varTotalBB');"/></td>
                                                        </tr>         
                                                    </table>
                                                </c:if>
                                                <div class="submit">
                                                    <input type="button" class="button" id="buttonEdit" value="Edit Report" onClick="editReport('<c:out value='report${i}'/>')"/>
                                                    <input type="submit" class="button" name="<c:out value='report${i}'/>" style="display: none;" value="Save Report"/>
                                                </div>
                                            </div>
                                        </div>
                                    </form>
                                </c:otherwise>
                            </c:choose>
                        </c:if>
                    </c:forEach>
                </c:when>
            </c:choose>
        </div>
        <!---------------------- CONTENT END ---------------------------->
        <!--------------------------------------------------------------->
        <!---------------------- FOOTER START --------------------------->
        <%@include file="footer/footer.jsp"%>
        <!---------------------- FOOTER END -----------------------------> 
        <!--------------------------------------------------------------->
        <script type="text/javascript">
			
            var options = {
                fixeApres : 0
            };
			
            var infoLinesCount = document.getElementsByName('infoLinesCount');
            for (var i = 0; i < (infoLinesCount.length); i++) {
                bubulle.apply(infoLinesCount[i] ,'Total of lines from all sites in the unit contract.', options);
            }
			
            var infoFixCosts = document.getElementsByName('infoFixCosts');
            for (var i = 0; i < (infoFixCosts.length); i++) {
                bubulle.apply(infoFixCosts[i] ,'Subscription and service costs (other than call or usage costs).', options);
            }
			
            var infoVarCosts = document.getElementsByName('infoVarCosts');
            for (var i = 0; i < (infoVarCosts.length); i++) {
                bubulle.apply(infoVarCosts[i] ,'Call or usage costs.', options);
            }
        </script>
    </body>
</html>
