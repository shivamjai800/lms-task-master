function returnBook(recordId)
{
    let status = "RETURNED"
    let url = "/user/records/" + recordId;
    $.ajax({
        type: "PUT",
        url: url,
        data: JSON.stringify({
            "status": status
        }),
        contentType: "application/json; charset=utf-8",
        success: function (data, textStatus, xhr) {
            if (textStatus == "success") {
                showPopUp(data);
            }
        }
    });
}