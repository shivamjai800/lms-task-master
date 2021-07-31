<!doctype html>
<html lang="en" xmlns="http://www.w3.org/1999/xhtml">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport"
          content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet"
          href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css"
          integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk"
          crossorigin="anonymous">
    <link rel="stylesheet" href="/css/common/popUp.css">


    <title></title>
    <style>
        .table-image {

        td, th {
            vertical-align: middle;
        }

        }
    </style>
</head>
<body>
<div class="content-section new">
    <nav th:replace="admin/adminNav :: nav"></nav>
    <span th:if="${records != null}">
			<table class="table">
				<thead>
					<tr>
						<th scope="col">S.NO</th>
						<th scope="col">Id</th>
						<th scope="col">BookId</th>
						<th scope="col">Start Date</th>
						<th scope="col">Start Time</th>
						<th scope="col">
							<div th:with="status1=${status}!=null?'true':'false', requested=(${status}!=null and ${status.requested}!=null) ? ${status.requested}: 'false', approved=(${status}!=null and ${status.approved}!=null) ? ${status.approved}: 'false' , returned=(${status}!=null and ${status.returned}!=null) ? ${status.returned}: 'false', cancelled=(${status}!=null and ${status.cancelled}!=null) ? ${status.cancelled}: 'false'"
                                 class="dropdown" tabindex="100">
								<button id="status" class="btn btn-primary dropdown-toggle mr-4" type="button"
                                        data-toggle="dropdown"
                                        aria-haspopup="true" aria-expanded="false">Status(ALL)</button>
							<div class="dropdown-menu">
							<a class="dropdown-item">
							  <!-- Default unchecked -->
							  <div class="custom-control custom-checkbox">
								<input th:checked="${(requested and approved and returned and cancelled)?true:false} "
                                       type="checkbox" class="custom-control-input" id="all">
								<label class="custom-control-label" for="all">ALL</label>
							  </div>
							</a>
							<a class="dropdown-item" href="">
							  <div class="custom-control custom-checkbox">
								<input type="checkbox" class="custom-control-input" id="requested"
                                       th:checked="${(requested ) ?true:false}">
								<label class="custom-control-label" for="requested">Requested</label>
							  </div>
							</a>
							<a class="dropdown-item" href="">
							  <div class="custom-control custom-checkbox">
								<input type="checkbox" class="custom-control-input" id="approved"
                                       th:checked="${(approved ) ?true:false}">
								<label class="custom-control-label" for="approved">Approved</label>
							  </div>
							</a>

							<a class="dropdown-item" href="">
							  <div class="custom-control custom-checkbox">
								<input type="checkbox" class="custom-control-input" id="cancelled"
                                       th:checked="${(cancelled ) ?true:false}">
								<label class="custom-control-label" for="cancelled">Cancelled</label>
							  </div>
							</a>
							<a class="dropdown-item" href="">
							  <div class="custom-control custom-checkbox">
								<input type="checkbox" class="custom-control-input" id="returned"
                                       th:checked="${(returned ) ?true:false}">
								<label class="custom-control-label" for="returned">Returned</label>
							  </div>
							</a>
						  </div>
							</div>
						</th>
						<th scope="col">Unit Book Given</th>
						<th scope="col">User Username</th>
						<th scope="col">Admin Id</th>
						<th scope="col">End Date</th>
						<th scope="col">End Time</th>
						<th scope="col">Remarks</th>

					</tr>
				</thead>
				<tbody>
					<tr th:id="'row' + ${iStat.count}"
                        th:each="record , iStat: ${records}">
						<td th:text="${iStat.count}"/>
						<td th:id="'recordId' + ${iStat.count}" th:text="${record.id}"/>
						<td th:id="'bookId' + ${iStat.count}" th:text="${record.bookId}"/>
						<td th:id="'requestDate' + ${iStat.count}"
                            th:text="${record.startDateTime} != null ? ${record.startDateTime.toLocalDate()} : '' "/>
						<td th:id="'requestTime' + ${iStat.count}"
                            th:text="${record.startDateTime} != null ?${record.startDateTime.toLocalTime()} : '' "/>
						<td th:id="'status' + ${iStat.count}"
                            th:text="${record.status} != null ? ${record.status} :'' "/>
						<td th:id="'unitBook' + ${iStat.count}"
                            th:text="${record.unitBookReceived} != null ? ${record.unitBookReceived} :'' "/>
						<td th:id="'username' + ${iStat.count}"
                            th:text=" ${record.userUsername} != null ? ${record.userUsername} :'' "/>
						<td th:id="'adminId' + ${iStat.count}"
                            th:text="${record.adminId} != null ? ${record.adminId} :'' "/>
						
						<td th:id="'endDate' + ${iStat.count}"
                            th:text="${record.returnDateTime} != null ? ${record.returnDateTime.toLocalDate()}: ''"/>
						<td th:id="'endTime' + ${iStat.count}"
                            th:text="${record.returnDateTime} != null ? ${record.returnDateTime.toLocalTime()} : '' "/>
						
						
						<td th:contenteditable="${record.status!= null and (record.status!='CANCELLED' or record.status!='APPROVED') }? true: false"
                            th:id="'remarks' + ${iStat.count}" th:text="${record.remarks}"/>
						<td th:with="cancelled=${record.status=='CANCELLED'}? true: false , approved=${record.status=='APPROVED'}? true: false, returned=${record.status=='RETURNED'}?'true':'false'">
							<button type="button" class="btn "
                                    th:classappend="${approved} ? 'btn-success': 'btn-primary'"
                                    th:text="${approved} ? 'approved': 'approve'"
                                    th:disabled="${approved or cancelled or returned} ? 'true': 'false'"
                                    th:onclick="|changeBookStatus('${record.id}',1,'${iStat.count}')|"></button>
							<button type="button" class="btn btn-danger"
                                    th:text="${cancelled} ? 'cancelled': 'cancel'"
                                    th:disabled="${cancelled or approved or returned} ? 'true': 'false'"
                                    th:onclick="|changeBookStatus('${record.id}',0,'${iStat.count}')|"></button>
						</td>

					</tr>
				</tbody>
			</table>
			<nav aria-label="Page navigation example">
			  <ul class="pagination">
				<li th:if="${currentPage !=0}" class="page-item"><button class="page-link" th:onclick="|ontoPage('${currentPage-1}')|">Previous</button></li>
				<li th:classappend="${currentPage == (i-1) ? 'active': ''}"
					th:each="i : ${#numbers.sequence(T(Math).min(1,totalPages),totalPages)}" class="page-item"><button class="page-link"
																													   th:onclick="|ontoPage(' ${i-1}')|"
																													   th:text="${i}"></button></li>
				<li th:if="${totalPages!=0 and currentPage+1 != totalPages}" class="page-item"><button class="page-link" th:onclick="|ontoPage('${currentPage+1}')|">Next</button></li>
			  </ul>
			</nav>
		<button class="btn btn-primary" onclick="applyfilter()">Apply Filter</button>
		</span>
</div>
<div id="myModal" class="modal">
    <div class="modal-content">
        <span id="close">&times;</span>
        <p id="msg"></p>
    </div>
</div>

<script src="https://code.jquery.com/jquery-3.4.1.min.js"
        integrity="sha256-CSXorXvZcTkaix6Yvo6HppcZGetbYMGWSFlBw8HfCJo="
        crossorigin="anonymous"></script>
<script
        src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
        integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
        crossorigin="anonymous"></script>
<script
        src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"
        integrity="sha384-OgVRvuATP1z7JjHLkuOU7Xw704+h835Lr+6QL9UvYjZE3Ipu6Tp75j7Bh/kR0JKI"
        crossorigin="anonymous"></script>
<script type="text/javascript" src="/js/common/popUp.js"></script>
<script type="text/javascript" src="/js/admin/getAllRecords.js"></script>
<script>

    function precheckStatus() {
        let requested = document.getElementById('requested').checked;
        let cancelled = document.getElementById('cancelled').checked
        let approved = document.getElementById('approved').checked
        let returned = document.getElementById('returned').checked
        let all = document.getElementById('all');
        let status = document.getElementById('status');
        if (!requested || !cancelled || !approved || !returned) {
            all.checked = false
        }
        if (all.checked == false) {
            status.innerText = "Status"
        } else {
            status.innerText = "Status(ALL)"
        }

    }

    document.getElementById('requested').onclick = function () {
        precheckStatus()
    };
    document.getElementById('cancelled').onclick = function () {
        precheckStatus()
    };
    document.getElementById('approved').onclick = function () {
        precheckStatus()
    };
    document.getElementById('returned').onclick = function () {
        precheckStatus()
    };
    document.getElementById('all').onclick = function () {
        let requested = document.getElementById('requested');
        let cancelled = document.getElementById('cancelled');
        let approved = document.getElementById('approved');
        let returned = document.getElementById('returned');
        let status = document.getElementById('status');
        if (!document.getElementById('all').checked) {
            requested.checked = false
            cancelled.checked = false
            approved.checked = false
            returned.checked = false
        } else {
            requested.checked = true
            cancelled.checked = true
            approved.checked = true
            returned.checked = true
        }
        if (all.checked == false) {
            status.innerText = "Status"
        } else {
            status.innerText = "Status(ALL)"
        }
    }


    function applyfilter() {
        helper(0);
    }

    function ontoPage(pageNo) {
        helper(pageNo);
    }

    function helper(pageNo) {
        let requested = document.getElementById('requested').checked
        let cancelled = document.getElementById('cancelled').checked
        let approved = document.getElementById('approved').checked
        let returned = document.getElementById('returned').checked
        let all = document.getElementById('all').checked;

        let form = document.createElement('form');
        form.action = '/admin/records/' + pageNo;
        form.method = 'POST';
        let input = document.createElement('input');
        input.setAttribute("type", "checkbox");
        input.setAttribute("name", "requested");
        input.checked = requested
        // input.setAttribute("checked",requested?"true":"false");
        form.appendChild(input);

        input = document.createElement('input');
        input.setAttribute("type", "checkbox");
        input.setAttribute("name", "returned");
        input.checked = returned
        form.appendChild(input);

        input = document.createElement('input');
        input.setAttribute("type", "checkbox");
        input.setAttribute("name", "approved");
        input.checked = approved
        form.appendChild(input);

        input = document.createElement('input');
        input.setAttribute("type", "checkbox");
        input.setAttribute("name", "cancelled");
        input.checked = cancelled
        form.appendChild(input);
        document.body.append(form);
        form.style.display = "none";
        form.submit();

    }
</script>

</body>
</html>