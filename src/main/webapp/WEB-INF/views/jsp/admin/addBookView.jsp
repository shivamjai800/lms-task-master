
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


<title></title>
<style>
.table-image {td , th { vertical-align:middle;
	
}

}
.form_box {
	width: 30vw;
	margin: auto;
	margin-top: 5vh;
}
</style>
</head>
<body>
	<div class="content-section new">
		<nav th:replace="admin/adminNav :: nav"></nav>

		<div class="form_box mt-4">

			<h1 class="display-4 text-center">
				<i class="fas fa-book-open text-primary"></i>My<span
					class="text-primary">Book</span>Form
			</h1>
			<form action="#" th:action="@{/admin/book}" method="post"
				th:object="${book}" enctype="multipart/form-data">
				<div class="form-group">
					<label for="title">Title</label> <input type="text" id="title" name="title"
						class="form-control" placeholder="title" th:value="${book.title}"
						th:classappend="${#fields.hasErrors('title')? 'is-invalid':''}">

					<div novalidate th:each="e : ${#fields.errors('title')}"
						th:text="${e}" class="invalid-feedback"></div>
				</div>

				<div class="form-group">
					<label for="author">Author</label> <input type="text" id="author"
						class="form-control" th:value="${book.author}" name="author"
						th:classappend="${#fields.hasErrors('author')? 'is-invalid':''}">
					<div novalidate th:each="e : ${#fields.errors('author')}"
						th:text="${e}" class="invalid-feedback"></div>
				</div>
				<div class="form-group">
					<label for="quantity">Quantity</label> <input type="number"
						id="quantity" class="form-control" min="1" name="quantity"
						th:value="${book.author}"
						th:classappend="${#fields.hasErrors('author')? 'is-invalid':''}">

					<div novalidate th:each="e : ${#fields.errors('quantity')}"
						th:text="${e}" class="invalid-feedback"></div>
				</div>
				<div class="form-group">
					<label class="form-label" for="image">Image File</label>
					<input name="bookImage" type="file" class="form-control" id="image" />
				</div>
				<div class="container text-center">
					<button type="submit" class="btn btn-primary">Submit</button>
				</div>

			</form>
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

</body>
</html>