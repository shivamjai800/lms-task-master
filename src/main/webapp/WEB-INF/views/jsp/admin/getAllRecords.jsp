
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
.table-image {td , th { vertical-align:middle;
	
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
						<th scope="col">status</th>
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
						<td th:text="${iStat.count}" />
						<td th:id="'recordId' + ${iStat.count}" th:text="${record.id}" />
						<td th:id="'bookId' + ${iStat.count}" th:text="${record.bookId}" />
						<td th:id="'requestDate' + ${iStat.count}"
							th:text="${record.startDateTime.toLocalDate()}" />
						<td th:id="'requestTime' + ${iStat.count}"
							th:text="${record.startDateTime.toLocalTime()}" />
						<td th:id="'status' + ${iStat.count}" th:text="${record.status}" />
						<td th:id="'unitBook' + ${iStat.count}"
							th:text="${record.unitBookReceived}" />
						<td th:id="'username' + ${iStat.count}"
							th:text="${record.userUsername}" />
						<td th:id="'adminId' + ${iStat.count}" th:text="${record.adminId}" />
						
						<td th:id="'endDate' + ${iStat.count}"
							th:text="${record.returnDateTime} != null ? ${record.returnDateTime.toLocalDate()}: ''" />
						<td th:id="'endTime' + ${iStat.count}"
							th:text="${record.returnDateTime} != null ? ${record.returnDateTime.toLocalTime()} : '' " />
						
						
						<td th:contenteditable="${record.status!='CANCELLED' or record.status!='APPROVED' }? true: false" th:id="'remarks' + ${iStat.count}" th:text="${record.remarks}" />
						<td  th:with = "cancelled=${record.status=='CANCELLED'}? true: false , approved=${record.status=='APPROVED'}? true: false">
							<button type="button" class="btn " th:classappend="${approved} ? 'btn-success': 'btn-primary'"
								th:text="${approved} ? 'approved': 'approve'"
								th:disabled="${approved or cancelled} ? 'true': 'false'"
								 th:onclick="|changeBookStatus('${record.id}',1,'${iStat.count}')|" ></button>
							<button type="button" class="btn btn-danger"
								th:text="${cancelled} ? 'cancelled': 'cancel'"
								th:disabled="${cancelled or approved} ? 'true': 'false'"
								 th:onclick="|changeBookStatus('${record.id}',0,'${iStat.count}')|" ></button>
						</td>
					</tr>
				</tbody>
			</table>
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


</body>
</html>