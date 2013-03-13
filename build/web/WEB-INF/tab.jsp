<%-- 
    Document   : tab
    Created on : 31 oct. 2012, 14:50:29
    Author     : Nico
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
		<style>
			td {
				text-align: center;
				padding: 3px;
				min-width: 50px;
			}

			.local {
				background-color: orange;
			}

			.international {
				background-color: #034694;
			}

			.total {
				background-color: red;
			}

			.total_cost {
				color: red;
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
        <table border> 
			<tr>
				<td></td>
				<td colspan="3">Fix Telephony</td>
				<td colspan="5">Mobile Telephony</td>
				<td colspan="3">3G Cards</td>
				<td colspan="5">Blackberry Smartphones</td>
			</tr>
			<tr>
				<td>Number of lines</td>
				<td colspan="3"></td>
				<td colspan="5"></td>
				<td colspan="3"></td>
				<td colspan="5"></td>
			</tr>
			<tr>
				<td rowspan="2"></td>
				<td rowspan="2" class="local">Local Calls</td>
				<td rowspan="2" class="international">International Calls</td>
				<td rowspan="2" class="total">TOTAL</td>
				<td colspan="2" class="local">Local</td>
				<td colspan="2" class="international">International</td>
				<td rowspan="2" class="total">TOTAL</td>
				<td rowspan="2" class="local">Local Calls</td>
				<td rowspan="2" class="international">International Calls</td>
				<td rowspan="2" class="total">TOTAL</td>
				<td colspan="2" class="local">Local</td>
				<td colspan="2" class="international">International</td>
				<td rowspan="2" class="total">TOTAL</td>
			</tr>
			<tr>
				<td class="local">Calls</td>
				<td class="local">Data</td>
				<td class="international">Calls</td>
				<td class="international">Data</td>
				<td class="local">Calls</td>
				<td class="local">Data</td>
				<td class="international">Calls</td>
				<td class="international">Data</td>
			</tr>
			<tr>
				<td>Fix Costs</td> 
				<td></td>
				<td></td>
				<td class="total_cost"></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td class="total_cost"></td>
				<td></td>
				<td></td>
				<td class="total_cost"></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td class="total_cost"></td>
			</tr>
			<tr>
				<td>Variable Costs</td> 
				<td></td>
				<td></td>
				<td class="total_cost"></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td class="total_cost"></td>
				<td></td>
				<td></td>
				<td class="total_cost"></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td class="total_cost"></td>
			</tr>        
		</table>
		<!---------------------- CONTENT END ---------------------------->
		<!--------------------------------------------------------------->
		<!---------------------- FOOTER START --------------------------->
		<%@include file="footer/footer.jsp"%>
		<!---------------------- FOOTER END -----------------------------> 
		<!--------------------------------------------------------------->
    </body>
</html>
