/*
 * Function which calculate total from others input which are in the same line. 
 */

function calculateTotal(idReport, idTotal) {
	
	var allInput = document.forms['report'+idReport].elements[idTotal].parentNode.parentNode.getElementsByTagName('input');
	var total = 0;
				
	for (var i = 0; i < (allInput.length - 1); i++) {
		if (allInput[i].value != "") {
			total += parseInt(allInput[i].value);
		}
	}
				
	document.forms['report'+idReport].elements[idTotal].value = total;
}

/*
 * Function which check if total filled is equal with total from others input which are in the same line. 
 */

function checkTotal(idReport, idTotal) {
				
	var allInput = document.forms['report'+idReport].elements[idTotal].parentNode.parentNode.getElementsByTagName('input');
	var total = 0;
				
	for (var i = 0; i < (allInput.length - 1); i++) {
		if (allInput[i].value != "") {
			total += parseInt(allInput[i].value);
		}
	}
	
	if (total != document.forms['report'+idReport].elements[idTotal].value && total != 0) {
		if (!confirm("The filled total isn't equal with the calculated total.\nCould you keep the filled total ?")) {
			document.forms['report'+idReport].elements[idTotal].value = total;
			alert("Total has been recalculated !");
		}
		else {
			for (var i = 0; i < (allInput.length - 1); i++) {
				allInput[i].value = 0;
			}
			alert("Other fields else the 'Total' field have been reset !");
		}
	}
}
