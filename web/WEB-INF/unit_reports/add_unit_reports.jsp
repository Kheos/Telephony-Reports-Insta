<%-- 
    Document   : add_unit_reports
    Created on : 6 nov. 2012, 09:46:00
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
        <script language="JavaScript"> 
            function verifInput(champ){
                if(champ.value.length < 1 ){
                    surligne(champ, true);
                    return false;
                } else {
                    surligne(champ, false);
                    return true;
                }
            }
            function verifSelectType(champ){
                if(champ.value.length== null){
                    surligne(champ, true);
                    return false;
                } else {
                    surligne(champ, false);
                    return true;
                }
            }
            function verifSelectCountry(champ){
                if(champ.value.length== null){
                    surligne(champ, true);
                    return false;
                } else {
                    surligne(champ, false);
                    return true;
                }
            }
            function verifForm(f){
                var pseudoOk = verifInput(f.nameUnitReports);
                var mailOk = verifSelectType(f.typeUnitReports);
                var ageOk = verifSelectCountry(f.countryUnitReports);
   
                if(pseudoOk && mailOk && ageOk){
                    return true;
                } else   {
                    alert("Please, register all field before submit the form, try again.");
                    error="<p style=\"color:red; \">Please register all fields before submit.</p>";
                    return false;
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
                <img src="inc/pictures/add.png" alt="" width="70px" style="margin-top:-15px;"/> &nbsp  Add a Unit Reports Step 1/2
            </h2>
            <c:if test="${redirectBool}">
                <p style="color: red; font-weight: bold;">
                    Warning : You have to add at least one unit report to be able to fill reporting datas.
                </p>
            </c:if>
            <p>
                Enter the informations below to create a Unit Reports :
            </p>
            <div id="filter" style=" width:930px; margin:auto; padding: 5px;">
                <form action="Add_Site_Unit_Reports" method="GET" class="formContact" onsubmit="return verifForm(this)" >
                    <label for="login"><span>Name :</span>
                        <input type="text" id="nameUnitReports" class="input_text" name="nameUnitReports" value="" size="30" maxlength="60" onblur="verifInput(this)" />
                    </label><br />
                    <label for="type"><span>Type :</span>
                        <select name="typeUnitReports" id="typeUnitReports" tabindex="10" style="width:290px; margin-right: 20px;" onblur="verifSelectType(this)" >
                            <option value="Mobile">Mobile</option>
                            <option value="Fixed">Fixed</option>
                            <option value="Both">Both</option>
                        </select>
                    </label><br />
                    <label><span>Country :</span>
                        <select name="countryUnitReports" id="countryUnitReports" tabindex="10" style="margin-right: 20px;" onblur="verifSelectCountry(this)" >
                            ${ messageCountry }
                        </select>
                    </label><br />
                    <input id="buttonAdd" class="button" type="submit" value="Next >" tabindex="30" />

                    <div id="generated_form">
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

