<%-- 
    Document   : index
    Created on : 29 oct. 2012, 15:46:24
    Author     : Enji
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <title>Loginn</title>
        <link href="inc/styles/styles.css" rel="stylesheet" type="text/css" />
        <link rel="stylesheet" href="inc/styles/styles.css" type="text/css" media="screen"/>
        <link rel="shortcut icon" href="pictures/oAlstom.ico" type="image/x-icon"/> 
        <link rel="icon" href="pictures/oAlstom.ico" type="image/x-icon"/>
    </head>

    <!---------------------------------------------------------------->
    <!---------------------- HEADER START ---------------------------->
    <div id="header">
        <%@include file="headerIndex/headerIndex.jsp"%>
    </div>
    <!---------------------- HEADER END ------------------------------>
    <!---------------------------------------------------------------->
    <!---------------------- CONTENT START --------------------------->       
    <body>
        <div class="content">
            <h2>
                <img src="inc/pictures/secure.png" alt="" width="70px" style="margin-top:-15px;"/> &nbsp  Log in
            </h2>
			<c:if test="${redirectBool}">
				<p style="color: red; font-weight: bold;">
					Warning : You have to be connected to be able to access to the other pages.
				</p>
			</c:if>
            <form method="POST" action="Index" class="formContact">
                    <a href="Trouble" style="float:right; margin-top:-20px;">Trouble ?</a>
                    <label for="login"><span>Login :</span>
                        <input type="text" id="login" class="input_text" name="login" value="" size="20" maxlength="60" />
                    </label>
                    <br />
                    <label for="password"><span>Password :</span>
                        <input type="password" id="password" class="input_text" name="password" value="" size="20" maxlength="20" />
                    </label>
                    <br />
                    <input type="submit" value="Connect" class="buttonConnect" />
                    <br />
            </form>
        </div>
        <!---------------------- CONTENT END ---------------------------->
        <!--------------------------------------------------------------->
    </body>
    <!---------------------- FOOTER START --------------------------->
    <div id="footer">
        <%@include file="footerIndex/footerIndex.jsp"%>
    </div>
    <!---------------------- FOOTER END -----------------------------> 
    <!--------------------------------------------------------------->
</html>