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


function precheckStatus()
{
    let requested = document.getElementById('requested').checked;
    let cancelled = document.getElementById('cancelled').checked
    let approved = document.getElementById('approved').checked
    let returned = document.getElementById('returned').checked
    let all = document.getElementById('all');
    let status = document.getElementById('status');
    if(!requested || !cancelled || !approved || !returned)
    {
        all.checked = false
    }
    if(all.checked==false)
    {
        status.innerText = "Status"
    }
    else
    {
        status.innerText = "Status(ALL)"
    }

}
document.getElementById('requested').onclick= function() {precheckStatus()};
document.getElementById('cancelled').onclick= function() {precheckStatus()};
document.getElementById('approved').onclick= function() {precheckStatus()};
document.getElementById('returned').onclick= function() {precheckStatus()};
document.getElementById('all').onclick = function (){
    let requested = document.getElementById('requested');
    let cancelled = document.getElementById('cancelled');
    let approved = document.getElementById('approved');
    let returned = document.getElementById('returned');
    let status = document.getElementById('status');
    if(!document.getElementById('all').checked)
    {
        requested.checked = false
        cancelled.checked = false
        approved.checked = false
        returned.checked = false
    }
    else
    {
        requested.checked = true
        cancelled.checked = true
        approved.checked = true
        returned.checked = true
    }
    if(all.checked==false)
    {
        status.innerText = "Status"
    }
    else
    {
        status.innerText = "Status(ALL)"
    }
}

function applyfilter()
{
    helper(0);
}
function ontoPage(pageNo)
{
    helper(pageNo);
}

function helper(pageNo) {
    let requested = document.getElementById('requested').checked
    let cancelled = document.getElementById('cancelled').checked
    let approved = document.getElementById('approved').checked
    let returned = document.getElementById('returned').checked
    let all = document.getElementById('all').checked;

    let form = document.createElement('form');
    form.action = '/user/records/'+pageNo;
    form.method = 'POST';
    let input = document.createElement('input');
    input.setAttribute("type","checkbox");
    input.setAttribute("name","requested");
    input.checked = requested
    // input.setAttribute("checked",requested?"true":"false");
    form.appendChild(input);

    input = document.createElement('input');
    input.setAttribute("type","checkbox");
    input.setAttribute("name","returned");
    input.checked = returned
    form.appendChild(input);

    input = document.createElement('input');
    input.setAttribute("type","checkbox");
    input.setAttribute("name","approved");
    input.checked = approved
    form.appendChild(input);

    input = document.createElement('input');
    input.setAttribute("type","checkbox");
    input.setAttribute("name","cancelled");
    input.checked = cancelled
    form.appendChild(input);
    document.body.append(form);
    form.style.display="none";
    form.submit();

}
