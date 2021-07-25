function editUser() {
	let name = document.getElementById('name').value;
	let username = document.getElementById('username').value;
	let password = document.getElementById('password').value;
	let url = "/user/" + document.getElementById('oldUsername').innerText;
	
	$.ajax({
		type: "PUT",
		url: url,
		contentType: "application/json; charset=utf-8",
		data: JSON.stringify({ "name": name, "username": username, "password": password, "roles": "ROLE_USER" }),
		success: function (data, textStatus, xhr) {
			console.log("here")
			console.log("data = " + data + " textStatus = " + textStatus + " xhr = " + xhr);
			if (textStatus == "success") {
				window.prompt(username + " is updated from the database");
				window.location.reload();
			}
		}
	});
}