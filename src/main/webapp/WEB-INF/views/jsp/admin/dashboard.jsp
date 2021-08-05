
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
		<span th:if="${users != null}">
			<table class="table">
				<thead>
					<tr>
						<th scope="col">S.NO</th>
						<th scope="col">Name</th>
						<th scope="col">Username</th>
						<th scope="col">Roles</th>
					</tr>
				</thead>
				<tbody>
					<tr th:id="'row' + ${iStat.count}" th:each="user, iStat: ${users}">
						<td th:text="${iStat.count}" />
						<td th:id="'name' + ${iStat.count}" th:text="${user.name}" />
						<td th:id="'username' + ${iStat.count}" th:text="${user.username}" />
						<td th:id="'roles' + ${iStat.count}" th:text="${user.roles}" />

						<td>
							<button type="button" class="btn btn-success"
								th:onclick="|editUser('${iStat.count}')|">Edit</button>
							<button type="button" class="btn btn-danger"
								th:onclick="|deleteUser('${iStat.count}')|">Delete</button>
							<button type="button" class="btn btn-primary"
								th:onclick="|saveUser('${iStat.count}')|">Update</button>
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
	<script type="text/javascript" src="/js/admin/dashboard.js"></script>

</body>
</html>