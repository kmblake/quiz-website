<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<link href="css/bootstrap.min.css" rel="stylesheet">
<link href="css/signin.css" rel="stylesheet">
<link href="css/stylesheet.css" rel="stylesheet">
<title>Create Account</title>
</head>
<body>

<div class="navbar navbar-inverse navbar-static-top">
	<div class="container">
		<a class="navbar-brand" href="home.jsp">Quiz Website</a>
		<div id="navbar" class="navbar-collapse collapse">
	        <ul class="nav navbar-nav navbar-right">
	          <li><a href="/Quiz_Website/LogoutServlet">Logout</a></li>
	          <li><a href="show_messages.jsp">Messages</a></li>
	          <li><a href="home.jsp">Home</a></li>
	        </ul>
	        <form action="SearchServlet" method="post" class="navbar-form navbar-right">
	          <input type="text" class="navbar-search form-control" name="query" placeholder="Search for quiz or user...">
	        </form>
    	</div>
	</div>
</div>

<div class="container">
	<form class="create-account-form" action="NewAccountServlet" method="post">
		<div class="alert alert-danger"><%=request.getAttribute("errorText") %></div>
		<h1>Create New Account</h1>
		<p>Please enter your information below.</p>
		<ul class="stripped">
			<li class="form-item">First Name: <input type="text" name="first" class="nobold" /></li>
			<li class="form-item">Last Name: <input type="text" name="last" class="nobold" /></li>
			<li class="form-item">User Name: <input type="text" name="user" class="nobold" /></li>
			<li class="form-item">Password: <input type="text" name="password" class="nobold" /></li>
			<li class="form-item nobold"><input class="btn btn-lg btn-primary btn-block" type="submit" value="Create Account" /></li>			
		</ul>
	</form>
</div>