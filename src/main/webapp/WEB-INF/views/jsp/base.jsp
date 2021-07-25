
<!doctype html>
<html lang="en" xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css" integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">
    
    
    <title>
    
    </title>
    <style>
    	
    	.new{
	    width: 30vw;
	    margin: auto;
	    margin-top: 5vh;
	    font-family: sans-serif;
	    box-shadow: 0px 0px 9px 0px black;
	    padding: 2vw;
	    background-color: #ebdfdf;
	    text-shadow: 0px 0px black;
	}
	.form-group label{
	    font-size: 2.5vh;
	    display: inline-block;
	    margin-top: 0.25vh;
	    margin-bottom: 0.25vh;
	    font-family: sans-serif;
	}
    
    </style>
  </head>
  <body>
 	<nav th:replace="nav :: nav"></nav>
  <div class="content-section new">
  	<form action="#" th:action="@{/user}" th:object="${user}" method="post">
    	<div class="form-group">
				   <label for="name" >Enter name</label>
					    <input  th:value="${user.name}" name = "name" type="name" class="form-control" id="name" placeholder="Enter name"/>
				  </div>
	    		<div class="form-group">
	    			<label  for="username">Email username</label>
					    <input  ${user.username} name = "username" type="username" class="form-control" id="username"  placeholder="Enter username"/>
	    		</div>
			  <div class="form-group">
			   <label for="exampleInputPassword1">Password</label>
					    <input name="password" type="password" class="form-control" id="exampleInputPassword1" placeholder="Password"/>
			  </div>
			  <div class="form-group">
				  <label  for="roles">select Role</label>
				    <select ${user.roles} name = "roles" class="form-control" id="roles">
				      <option  value="ROLE_ADMIN" >Role_ADMIN</option>
				      <option  value="ROLE_USER" >Role_USER</option>
				    </select>
				</div>
			  <div class="container text-center">
			  	<button type="submit" class="btn btn-primary">Submit</button>
			  </div>
    </form>
   </div>
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js" integrity="sha384-OgVRvuATP1z7JjHLkuOU7Xw704+h835Lr+6QL9UvYjZE3Ipu6Tp75j7Bh/kR0JKI" crossorigin="anonymous"></script>
    
  </body>
</html>