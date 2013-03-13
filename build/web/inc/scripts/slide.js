/*
 *	Function which select the block which have an id named as the variable "element" and show it in "duration" miliseconds
 */

function slideDown (element, duration) {
	
	document.getElementById('show'+element).style.display = "none";
	document.getElementById('hide'+element).style.display = "inline";
	
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

/*
 *	Function which select the block which have an id named as the variable "element" and hide it in "duration" miliseconds
 */
			
function slideUp (element, duration) {
	
	document.getElementById('show'+element).style.display = "inline";
	document.getElementById('hide'+element).style.display = "none";
	
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