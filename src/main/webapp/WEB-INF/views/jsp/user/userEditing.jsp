
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
.form_box {
	width: 30vw;
	margin: auto;
	margin-top: 5vh;
}
</style>
</head>
<body>

	<div class="content-section new">
		<nav th:replace="user/userNav :: nav"></nav>
	</div>
	<div class="form_box" th:object="${user}">
			
				<label for="name">Enter name</label> <input th:value="${user.name}"
					name="name" type="name" class="form-control" id="name"
					placeholder="Enter name"
					th:classappend="${#fields.hasErrors('name')? 'is-invalid':''}"  />

				<div novalidate th:each="e : ${#fields.errors('name')}"
					th:text="${e}" class="invalid-feedback"></div>
			
				<label for="username">Email username</label> <input th:value="${user.username}"
					name="username" type="username" class="form-control" id="username"
					placeholder="Enter username"
					th:classappend="${#fields.hasErrors('username')? 'is-invalid':''}" disabled />
				<div novalidate th:each="e : ${#fields.errors('username')}"
					th:text="${e}" class="invalid-feedback"></div>

			
				<label for="password">Enter your Current Password</label> <input name="password"
					type="password" class="form-control" id="password"
					placeholder="password"
					th:classappend="${#fields.hasErrors('password')? 'is-invalid':''}" />
				<div novalidate th:each="e : ${#fields.errors('password')}"
					th:text="${e}" class="invalid-feedback"></div>
					
				<span id="oldUsername" style="display:none" th:text="${user.username }"></span>

			<div class="container text-center">
				<button onclick="editUser()" class="btn btn-primary">Submit</button>
			</div>
		</form>
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
	<script type="text/javascript"  src="/js/admin/userEditing.js"></script>
	<script>
		
		
	</script>
</body>
</html>