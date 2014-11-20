<%@ page import="quiz.*"%>
<%@ page import="java.sql.*"%>

<% User currentUser = (User) session.getAttribute("user");
   String term = (String) request.getAttribute("search_term"); 
   if (term == null) term = "";
   SearchResult[] userResults = (SearchResult[]) request.getAttribute("userResults");
   SearchResult[] quizResults = (SearchResult[]) request.getAttribute("quizResults");
   %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
	<link href="css/bootstrap.min.css" rel="stylesheet">
	<link href="css/bootstrap-theme.min.css" rel="stylesheet">
	<link href="css/stylesheet.css" rel="stylesheet">
	<title>Search</title>
</head>
<body>
<div class="navbar navbar-inverse navbar-static-top">
	<div class="container">
		<a class="navbar-brand" href="home.jsp">Quiz Website</a>
		<div id="navbar" class="navbar-collapse collapse">
	        <ul class="nav navbar-nav navbar-right">
	          <li><a href="/Quiz_Website/LogoutServlet">Logout</a></li>
	          <li><a href="#">Messages</a></li>
	          <li><a href="home.jsp">Home</a></li>
	        </ul>
    	</div>
	</div>
</div>

<div class="container">
	<div class="jumbotron">
		<h1>Search</h1>
		<form action="SearchServlet" method="post">
			<ul class="stripped">
				<li class="form-item">Search For: <input type="text" class="title-input" name="query" value="<%= term %>"></li>
				<li class="form-item"> Show: 
					<span class="nobold">
						<input type="radio" name="filter" value="<%= Search.QUIZZES_ONLY %>"> Quizzes 
						<input type="radio" name="filter" value="<%= Search.USERS_ONLY %>"> Users 
						<input type="radio" name="filter" value="<%= Search.BOTH %>" checked="checked"> Both
					</span>
				</li>
				<li class="form-item"><button class="btn btn-primary" type="submit">Search</button>
			</ul>
		</form> 
	</div>

</div>


</body>
</html>