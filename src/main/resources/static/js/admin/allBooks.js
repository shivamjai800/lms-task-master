
function deleteBook(id) {

	let url = "/admin/books/" + id;
	$.ajax({
		type: "DELETE",
		url: url,
		success: function (data, textStatus, xhr) {
			if (textStatus == "success") {
				window.prompt("book with "+ id + "is deleted from the database");
				window.location.reload();
			}
			/*   $('#output').append(msg); */
		},
		error: function (xhr, textStatus,errorThrown)
		{
			// console.log(xhr);
			// console.log(textStatus);
			// console.log(errorThrown);
			if(xhr.status==417)
			{
				showPopUp(xhr.responseText);
			}
		}
	});
}
function editBook(id) {
	$("#" + "title" + id).attr('contenteditable', true);
	$("#" + "author" + id).attr('contenteditable', true);
	$("#" + "quantity" + id).attr('contenteditable', true);
}
function saveBook(id) {
	if (!document.getElementById("title" + id).getAttribute("contenteditable")) return;
	let Id = document.getElementById("bookId"+id).innerText;
	let title = document.getElementById('title' + id).innerText;
	let author = document.getElementById('author' + id).innerText;
	let quantity = document.getElementById('quantity' + id).innerText;
	let url = "/admin/books/" + Id;
	$.ajax({
		type: "PUT",
		url: url,
		contentType: "application/json; charset=utf-8",
		data: JSON.stringify({
			"id": Id,
			"title": title,
			"author": author,
			"quantity": quantity
		}),
		success: function (data, textStatus, xhr) {
			if (textStatus == "success") {
				window.prompt( "Book is updated on the database");
				window.location.reload();
			}
		},
		error: function (xhr, textStatus,errorThrown)
		{
			// console.log(xhr);
			// console.log(textStatus);
			// console.log(errorThrown);
			if(xhr.status==400)
			{
				showPopUp(xhr.responseText);
			}
		}
	});
}
function addBook() {

}