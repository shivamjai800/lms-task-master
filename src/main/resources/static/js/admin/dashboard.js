
function deleteUser(id) {
	let username = document.getElementById('username' + id).innerText;
	let url = "/admin/" + username;
	$.ajax({
		type: "DELETE",
		url: username,
		success: function (data, textStatus, xhr) {
			console.log("data = " + data + " textStatus = " + textStatus + " xhr = " + xhr);
			if (textStatus == "success") {
				showPopUp(username + " is deleted from the database");
			}
			/*   $('#output').append(msg); */
		},
		error: function (xhr, textStatus,errorThrown)
		{
			if(xhr.status==405)
			{
				showPopUp("Default User deletion is not allowed");
			}
		}
	});
}
function editUser(id) {
	$("#" + "name" + id).attr('contenteditable', true);
	let roles = document.getElementById("roles" + id).innerText;
	let select = document.createElement('select');
	select.id = "select" + id;
	let array = ["ROLE_ADMIN", "ROLE_USER"];
	for (let i = 0; i < array.length; i++) {
		let option = document.createElement("option");
		option.value = array[i];
		option.text = array[i];
		select.appendChild(option);
	}
	if (roles.includes("ADMIN")) select.selectedIndex = 0;
	else select.selectedIndex = 1;
	document.getElementById("roles" + id).innerText = "";
	document.getElementById("roles" + id).appendChild(select);

}
function saveUser(id) {
	if (!document.getElementById("name" + id).getAttribute("contenteditable")) return;
	let username = document.getElementById('username' + id).innerText;
	let name = document.getElementById('name' + id).innerText;
	let select = document.getElementById('select' + id);
	let roles = select.options[select.selectedIndex].text;
	let url = "/admin/" + username;
	$.ajax({
		type: "PUT",
		url: username,
		contentType: "application/json; charset=utf-8",
		data: JSON.stringify({
			"name": name,
			"username": username,
			"roles": roles
		}),
		success: function (data, textStatus, xhr) {
			if (textStatus == "success") {
				showPopUp(username + " is updated from the database");
			}
		},
		error: function (xhr, textStatus,errorThrown)
		{
			if(xhr.status==405)
			{
				showPopUp("Default User Modification is not allowed");
			}
		}
	});
}
function addBook() {

}