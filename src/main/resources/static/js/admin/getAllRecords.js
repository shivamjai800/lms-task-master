function changeBookStatus(requestId, approve,columnNo)
{
	let status = approve==1?"APPROVED":"CANCELLED"
	let url = "/admin/records/" + requestId;
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
			// console.log("Inside success function")
			if (textStatus == "success") {
				document.getElementById("remarks"+columnNo).contentEditable=false
				showPopUp(data);
			}

			// else if(xhr)
		},
		error: function (xhr, textStatus,errorThrown)
		{
			// console.log(xhr);
			// console.log(textStatus);
			// console.log(errorThrown);
			if(xhr.status==406)
			{
				showPopUp(xhr.responseText);
			}
		}

	});
}