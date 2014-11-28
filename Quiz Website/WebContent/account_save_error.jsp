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