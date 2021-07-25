let span = document.getElementById("close");
let modal = document.getElementById("myModal");
// When the user clicks on the button, open the modal
function showPopUp(msg) {
	modal.style.display = "block";
	document.getElementById("msg").innerHTML = msg;
}

// When the user clicks on <span> (x), close the modal
span.onclick = function () {
	window.location.reload();
	modal.style.display = "none";
}

// When the user clicks anywhere outside of the modal, close it
window.onclick = function (event) {
	if (event.target == modal) {
		window.location.reload();
		modal.style.display = "none";
	}
}