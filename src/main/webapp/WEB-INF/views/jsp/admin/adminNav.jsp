<nav th:fragment="nav"
	class="navbar navbar-expand-lg navbar-dark bg-dark">
	<a class="navbar-brand" href="#">Admin</a>
	<button class="navbar-toggler" type="button" data-toggle="collapse"
		data-target="#navbarSupportedContent"
		aria-controls="navbarSupportedContent" aria-expanded="false"
		aria-label="Toggle navigation">
		<span class="navbar-toggler-icon"></span>
	</button>

	<div class="collapse navbar-collapse" id="navbarSupportedContent">
		<ul class="navbar-nav mr-auto w-100">
			<li class="nav-item active"><a class="nav-link" href="">Home
					<span class="sr-only">(current)</span>
			</a></li>
			<li><a class="nav-link" href="/logout"><button
						class="btn btn-outline-success my-2 my-sm-0">Logout</button></a></li>

			<li><a class="nav-link" href="/admin/books/addBook"><button
						class="btn btn-outline-success my-2 my-sm-0">AddBook</button></a></li>
			<li><a class="nav-link" href="/admin/records"><button
						class="btn btn-outline-success my-2 my-sm-0">All Records</button></a></li>
		</ul>


		<!-- search and edit the user -->
		<ul class="navbar-nav">

			<form th:action="@{/admin/search}" method="post"
				class="form-inline my-2 my-lg-0">
				<input id="keyword" name="keyword" class="form-control mr-sm-2"
					type="search" placeholder="User Search" aria-label="Search">
				<button class="btn btn-outline-success my-2 my-sm-0" id="search"
					type="submit">Search</button>
			</form>
			
			
			<form th:action="@{/admin/books/search}" method="post"
				class="form-inline my-2 my-lg-0">
				<input id="keyword" name="keyword" class="form-control mr-sm-2"
					type="search" placeholder="Book Search" aria-label="Search">
				<button class="btn btn-outline-success my-2 my-sm-0" id="search"
					type="submit">Search</button>
			</form>

		</ul>


	</div>
</nav>