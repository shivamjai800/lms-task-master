
<!doctype html>
<html lang="en" xmlns="http://www.w3.org/1999/xhtml">
<head>
<!-- Required meta tags -->
<meta charset="utf-8">
<meta title="viewport"
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
.table-image{
	background-color: #fff;
	border: 1px solid #dee2e6;
	border-radius: .25rem;
	max-width: 15%;
	height: auto;
}
</style>
</head>
<body>
	<div class="content-section new">
		<nav th:replace="admin/adminNav :: nav"></nav>
		<span th:if="${books != null}">
			<table class="table">
				<thead>
					<tr>
						<th scope="col">S.NO</th>
						<th scope="col">Image</th>
						<th scope="col">BookId</th>
						<th scope="col">Title</th>
						<th scope="col">Author</th>
						<th scope="col">Quantity</th>
					</tr>
				</thead>
				<tbody>
					<tr th:id="'row' + ${iStat.count}" th:each="book, iStat: ${books}">
						<td th:text="${iStat.count}" />
						<td>
							<img class="table-image" alt=" image" th:src="@{'/image/'+${book.image}}">
						</td>
						<td th:id="'bookId' + ${iStat.count}" th:text="${book.id}" />
						<td th:id="'title' + ${iStat.count}" th:text="${book.title}" />
						<td th:id="'author' + ${iStat.count}" th:text="${book.author}" />
						<td th:id="'quantity' + ${iStat.count}" th:text="${book.quantity}" />

						<td>
							<button type="button" class="btn btn-success"
								th:onclick="|editBook('${iStat.count}')|">Edit</button>
							<button type="button" class="btn btn-danger"
								th:onclick="|deleteBook('${book.id}')|">Delete</button>
							<button type="button" class="btn btn-primary"
								th:onclick="|saveBook('${iStat.count}')|">Update</button>
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
	<script type="text/javascript" src="/js/admin/allBooks.js"></script>

</body>
</html>