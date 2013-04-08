<%-- 
    Document   : download_extract
    Created on : 4 févr. 2013, 12:20:42
    Author     : 250665
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <title>Data Extract</title>
        <link href="styles/styles.css" rel="stylesheet" type="text/css" />
        <link rel="stylesheet" href="styles/styles.css" type="text/css" media="screen"/>
        <link rel="shortcut icon" href="pictures/oAlstom.ico" type="image/x-icon"/> 
        <link rel="icon" href="pictures/oAlstom.ico" type="image/x-icon"/>   
        <script language="Javascript">      
            function changementType() { 
                var typeExtract = document.getElementById("typeExtract").value; 
                if (typeExtract == "country") { 
                    document.getElementById("country").style.display="block"; 
                } else{ 
                    document.getElementById("country").style.display="none"; 
                } 
                if (typeExtract == "contract"){ 
                    document.getElementById("contract").style.display="block"; 
                }else{ 
                    document.getElementById("contract").style.display="none"; 
                } 
            } 
            
            function Date() { 
                var nameExtractCountry = document.getElementById("nameExtractCountry").value; 
                var nameExtractContract = document.getElementById("nameExtractContract").value;
                 
                if (nameExtractCountry != "") { 
                    document.getElementById("dateModeCountry").style.display="block";
                } else {
                    document.getElementById("dateModeCountry").style.display="none";
                } if(nameExtractContract != "") { 
                    document.getElementById("dateModeContract").style.display="block"; 
                } else {
                    document.getElementById("dateModeContract").style.display="none";
                }
                
            } 
            
            function DateDetail() { 
                var selectDateModeCountry = document.getElementById("selectDateModeCountry").value;
                var selectDateModeContract = document.getElementById("selectDateModeContract").value;
                
                 
                if (selectDateModeCountry == "monthlyMode") { 
                    document.getElementById("dateMonthlyCountry").style.display="block"; 
                    document.getElementById("dateFiscalYearCountry").style.display="none";
                } if (selectDateModeCountry == "fiscalYearMode"){ 
                    document.getElementById("dateMonthlyCountry").style.display="none"; 
                    document.getElementById("dateFiscalYearCountry").style.display="block";
                }
                if (selectDateModeContract == "monthlyMode") { 
                    document.getElementById("dateMonthlyContract").style.display="block"; 
                    document.getElementById("dateFiscalYearContract").style.display="none";
                } if (selectDateModeContract == "fiscalYearMode"){ 
                    document.getElementById("dateMonthlyContract").style.display="none"; 
                    document.getElementById("dateFiscalYearContract").style.display="block";
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
                <img src="inc/pictures/excel.png" alt="" width="50px" style="margin-bottom:-10px;"/> &nbsp  Data Extract in Excel Mode
            </h2><br />
            <p>In this module, you could download tha data of a report in the .xls format.
                Register the name and a date of the contract which you want download by the way of the lists :</p>
            <p class="errors">
                <c:out value="${error}"/>
            </p>
            <div id="filter" style=" width:960px; margin:auto; padding: 5px;">
                <form action="Download_Extract" method="POST" class="formContact">
                    <label for="type"><span>Type of Extract :</span>
                        <select name="typeExtract" id="typeExtract" tabindex="10" style="width:290px; margin-right: 20px;" onclick="changementType();" >
                            <option value="">- Choose a type -</option>
                            <option value="country">Country</option>	   
                            <option value="contract">Contract</option>
                        </select>
                    </label>
                    <br />
                    <div id="country" style="display:none">
                        <center><h3 style="margin-bottom:-12px; margin-left: -350px;">
                                <img src="inc/pictures/globe.png" alt="" width="50px" style="margin-bottom: -8px;"/> &nbsp  Country
                            </h3></center>
                        <br />
                        <label>
                            <span>Country :</span>
                            <select name="nameExtractCountry" id="nameExtractCountry" tabindex="10" style="width:290px; margin-right: 20px;" onclick="Date();">
                                <option value="">- Choose a country -</option> 
                                ${ messageCountry }
                            </select>
                        </label><br />
                        <div id="dateModeCountry" style="display:none">
                            <label>
                                <span>Period :</span>
                                <select name="dateModeCountry" id="selectDateModeCountry" tabindex="10" style="width:290px; margin-right: 20px;" onclick="DateDetail();">
                                    <option value="">- Choose a period -</option> 
                                    <option value="monthlyMode">Monthly</option>
                                    <option value="fiscalYearMode">Fiscal Year</option>                                
                                </select>
                            </label>
                            <br />
                        </div>
                        <div id="dateMonthlyCountry" style="display:none">
                            <label><span>Month :</span>
                                <select name="monthCountry" id="monthCountry" tabindex="40" style="width:290px; margin-right: 20px;" >
                                    <option value="0">All</option>
                                    <c:forEach var="i" begin="1" end="12" step="1">
                                        <option value="<c:out value='${i}'/>"><c:out value='${monthList[i]}'/></option>
                                    </c:forEach>
                                </select></label><br />
                            <label><span>Year :</span>
                                <select id='yearCountry' name='yearCountry' tabindex="30" style="width:290px; margin-right: 20px;" >
                                    <c:forEach var="i" begin="2011" end="${currentYear}" step="1">
                                        <option value="<c:out value='${i}'/>"><c:out value='${i}'/></option>
                                    </c:forEach>
                                </select></label><br />
                            <input id="buttonDelete" name="buttonValidationForm" class="button" type="submit" value="Generate >" tabindex="30" />
                        </div>
                        <div id="dateFiscalYearCountry" style="display:none">
                            <label><span>Fiscal Year :</span>
                                <select id='fiscalYearCountry' name='fiscalYearCountry' fiscalYear="30" style="width:290px; margin-right: 20px;" >
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
                            <br />
                            <input id="buttonDelete" name="buttonValidationForm" class="button" type="submit" value="Generate >" tabindex="30" />
                        </div>
                    </div>











                    <div id="contract" style="display:none">
                        <center><h3 style="margin-bottom:-12px; margin-left: -350px;">
                                <img src="inc/pictures/contract.png" alt="" width="50px" style="margin-bottom:-8px;"/> &nbsp  Contract
                            </h3></center>
                        <br />
                        <label>
                            <span>Contract :</span>

                            <select name="nameExtractContract" id="nameExtractContract" tabindex="10" style="width:290px; margin-right: 20px;" onclick="Date();">
                                <option value="">- Choose a contract -</option> 
                                ${ messageNameUnitReports }
                            </select>
                        </label><br />
                        <div id="dateModeContract" style="display:none">
                            <label>
                                <span>Period :</span>
                                <select name="dateModeContract" id="selectDateModeContract" tabindex="10" style="width:290px; margin-right: 20px;" onclick="DateDetail();">
                                    <option value="">- Choose a period -</option> 
                                    <option value="monthlyMode">Monthly</option>
                                    <option value="fiscalYearMode">Fiscal Year</option>                                
                                </select>
                            </label>
                            <br />
                        </div>
                        <div id="dateMonthlyContract" style="display:none">
                            <label><span>Month :</span>
                                <select name="monthContract" id="monthContract" tabindex="40" style="width:290px; margin-right: 20px;" >
                                    <option value="0">All</option>
                                    <c:forEach var="i" begin="1" end="12" step="1">
                                        <option value="<c:out value='${i}'/>"><c:out value='${monthList[i]}'/></option>
                                    </c:forEach>
                                </select></label><br />
                            <label><span>Year :</span>
                                <select id='yearContract' name='yearContract' tabindex="30" style="width:290px; margin-right: 20px;" >
                                    <c:forEach var="i" begin="2011" end="${currentYear}" step="1">
                                        <option value="<c:out value='${i}'/>"><c:out value='${i}'/></option>
                                    </c:forEach>
                                </select></label><br />
                            <input id="buttonDelete" class="button" type="submit" value="Generate >" tabindex="30" />
                        </div>
                        <div id="dateFiscalYearContract" style="display:none">
                            <label><span>Fiscal Year :</span>
                                <select id='fiscalYearContract' name='fiscalYearContract' tabindex="30" style="width:290px; margin-right: 20px;" >
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
                            <br />
                            <input id="buttonDelete" class="button" type="submit" value="Generate >" tabindex="30" />
                        </div>
                </form>
            </div>
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

