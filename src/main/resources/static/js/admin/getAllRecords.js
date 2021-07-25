function changeBookStatus(requestId, approve,columnNo)
{
	let status = approve==1?"APPROVED":"CANCELLED"
	let url = "/admin/book/changeBookStatus/" + requestId;
	let remarks = document.getElementById("remarks"+columnNo).innerText;
	$.ajax({
		type: "PUT",
		url: url,
		data: JSON.stringify({
			"status": status,
			"remarks": remarks
		}),
		contentType: "application/json; charset=utf-8",
		success: function (data, textStatus, xhr) {
			if (textStatus == "success") {
				document.getElementById("remarks"+columnNo).contentEditable=false
				showPopUp(data);
			}
		}
	});
}