<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

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
 
<div class="content-section new">
       <form action="/springmvc/submitRegistrationForm" method="post">
	   		<div class="form-group">
	   			<label for="username">Username</label>
	   			<input name="username" type="username" class="form-control" id="username" placeholder="Enter your username"/>
	   		</div>
		  <div class="form-group">
		    <label for="password">Password</label>
		    <input name="password" type="password" class="form-control" id="password" placeholder="Password">
		  </div>
		  <div class="container text-center">
		  	<button type="submit" class="btn btn-primary">Submit</button>
		  </div>
	</form>
    <div class="border-top pt-3">
        <small class="text-muted">Need an account?<a href="/register" class="ml-2">Register</a></small>
    </div>
</div>
    