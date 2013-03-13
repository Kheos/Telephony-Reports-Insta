<%-- 
    Document   : add_site_unit_reports
    Created on : 13 nov. 2012, 12:33:34
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
        <script type="text/javascript">
            checked=false;
            function checkedAll (formSite) {
                var aa= document.getElementById('formSite');
                if (checked == false)
                {
                    checked = true
                }
                else
                {
                    checked = false
                }
                for (var i =0; i < aa.elements.length; i++) 
                {
                    aa.elements[i].checked = checked;
                }
            }
        </script>
        <style type="text/css">
            .displaySite {
                color: #FFFFFF;
            }
        </style>
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
                <img src="inc/pictures/add.png" alt="" width="70px" style="margin-top:-15px;"/> &nbsp  Add a Unit Reports Step 2/2
            </h2>
            <p>
                Select below the site of your contract:
            </p>
            <div id="filter" style=" width:930px; margin:auto; padding: 5px;">
                <form action="Add_Unit_Reports_Result" method="GET" id="formSite" class="formContact">
                    <label for="login"><span>Name :</span>
                        <input type="text" id="nameUnitReports" class="input_text" name="nameUnitReports" value="<%
                            String parametreName = request.getParameter("nameUnitReports");
                            out.println(parametreName);
                               %>" readonly="readonly" size="30" maxlength="60" />
                    </label><br />
                    <label for="type"><span>Type :</span>
                        <input type="text" id="typeUnitReports" class="input_text" name="typeUnitReports" value="<%
                            String parametreType = request.getParameter("typeUnitReports");
                            out.println(parametreType);
                               %>" readonly="readonly" size="30" maxlength="60" />
                    </label><br />
                    <label for="country"><span>Country :</span>
                        <input type="text" id="countryUnitReports" class="input_text" name="countryUnitReports" value="<%
                            String parametreCountry = request.getParameter("countryUnitReports");
                            out.println(parametreCountry);
                               %>" size="30" maxlength="60" />
                    </label><br />
                    <label for="site"><span>Sites :</span>

                        <table style="margin-left:40px;">
                            <tr><td style="width:40px;"><input type="checkbox" name="checkall"  onclick="checkedAll(formSite);"/></td><td><span class="displaySite" style="font-weight: bold; font-size:14px; text-align: left;">Select all</span></td></tr>
                            ${ messageSite }
                        </table>

                    </label><br />
                    <input id="buttonAdd" class="button" type="submit" value="Add" tabindex="30"/>
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


