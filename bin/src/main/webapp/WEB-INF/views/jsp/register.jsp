 
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
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
        <div class="content-section new">
         <form:form action="/user" method="post" modelAttribute="User">
         
         		<div class="form-group">
				   <form:label for="name" path="name">Enter name</form:label>
					    <form:input path="name"  name = "name" type="name" class="form-control" id="name" aria-describedby="emailHelp" placeholder="Enter name"/>
				  </div>
	    		<div class="form-group">
	    			<form:label path="username" for="username">Email username</form:label>
					    <form:input  path="username" name = "username" type="username" class="form-control" id="username" aria-describedby="emailHelp" placeholder="Enter username"/>
	    		</div>
			  <div class="form-group">
			   <form:label path="password" for="exampleInputPassword1">Password</form:label>
					    <form:input path="password" type="password" class="form-control" id="exampleInputPassword1" placeholder="Password"/>
			  </div>
			  <div class="form-group">
				  <form:label path="roles" for="role">select Role</form:label>
				    <form:select path="roles" name = "role" class="form-control" id="role">
				      <form:option path="roles" value="ROLE_ADMIN" >Role_ADMIN</form:option>
				      <form:option path="roles" value="ROLE_USER" >Role_USER</form:option>
				    </form:select>
				</div>
			  <div class="container text-center">
			  	<form:button type="submit" class="btn btn-primary">Submit</form:button>
			  </div>
			  
		</form:form>
        <div class="border-top pt-3">
            <small class="text-muted">Already Have an account?<a href="/login" class="ml-2">SignIn</a></small>
        </div>
    </div>
    