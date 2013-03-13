/*
 *	Function which forbid to write anyelse character that numbers (event js : onKeyPress)
 */

function onlyNumbers(event) {
	
	// Compatibility IE / Firefox
	if(!event && window.event) {
		event=window.event;
	}
 
	var code = event.keyCode; 
	var which = event.which;
 
	// IE
	if ((code < 48 || code > 57) && code != 46 && code != 8 && code != 9 && code != 16 && code != 13) {
		event.returnValue = false;
		event.cancelBubble = true;
	}
 
	// DOM (Firefox)
	if ((which < 48 || which > 57) && (code < 37 || code > 40) && code != 46 && code != 8 && code != 9 && code != 16 && code != 13 || event.ctrlKey) {
		event.preventDefault();
		event.stopPropagation();
	}
}

/*
 *	Function which forbid to write anyelse character that letters (event js : onKeyPress)
 */

function onlyLetters(event) {
	
	// Compatibility IE / Firefox
	if(!event&&window.event) {
		event=window.event;
	}
 
	var code = event.keyCode; 
	var which = event.which;
	
	// IE
	//If keycode isn't between [A-Z] or between [a-z] or others...
	if  (!((code >= 65 && code <= 90) || (code >= 97 && code <= 122) || code == 8 || code == 9 || code == 13 || code == 16 || code == 46)) {
		event.returnValue = false;
		event.cancelBubble = true;
	}
 
	// DOM (Firefox)
	if  (!((which >= 65 && which <= 90) || (which >= 97 && which <= 122) || (code >= 37 && code <= 40) || code == 8 || code == 9 || code == 13 || code == 16 || code == 46 || event.ctrlKey)) {
		event.preventDefault();
		event.stopPropagation();
	}
}