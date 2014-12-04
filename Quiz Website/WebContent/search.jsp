<%@ page import="quiz.*"%>
<%@ page import="java.sql.*"%>

<% User currentUser = (User) session.getAttribute("user");
   String term = (String) request.getAttribute("term"); 
   if (term == null) term = "";
   int oldFilter = (request.getAttribute("oldFilter") == null) ? Search.BOTH : (Integer) request.getAttribute("oldFilter");
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
	<script type="text/javascript" src="js/jquery-2.1.1.min.js"></script>
	<title>Search</title>
</head>

<script>
$( document ).ready(function() {
	$('[name=filter][value="<%= oldFilter %>"]').prop('checked',true);
}); 
</script>

<body>
<div class="navbar navbar-inverse navbar-static-top">
	<div class="container">
		<a class="navbar-brand" href="home.jsp">Let's Get Quizzical!</a>
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
						<input type="radio" name="filter" value="<%= Search.BOTH %>"> Both
					</span>
				</li>
				<li class="form-item"><button class="btn btn-primary" type="submit">Search</button>
			</ul>
		</form> 
	</div>

<% if (userResults != null) { %>
	<h2>User Results:</h2>
	<% if (userResults.length == 0)  {%>
		<p>No matching users<p>
	<% } else  { %>
		<table class="table table-striped">
	      <thead>
	        <tr>
	          <th>Name</th>
	          <th>Username</th>
	        </tr>
	      </thead>
	      <tbody>
		<% for (SearchResult sr : userResults) { %>
	        <tr>
	          <td><%= sr.getName() %></td>
	          <td><a href="show_user.jsp?id=<%= sr.getId() %>"><%= sr.getUsername() %></a></td>
	        </tr>
		<% } %>
	      </tbody>
	    </table>	
	<% } %>
<% } %>

<% if (quizResults != null) { %>
	<h2>Quiz Results:</h2>
	<% if (quizResults.length == 0)  {%>
		<p>No matching quizzes<p>
	<% } else  {%>
		<table class="table table-striped">
	      <thead>
	        <tr>
	          <th>Quiz</th>
	        </tr>
	      </thead>
	      <tbody>
		<% for (SearchResult sr : quizResults) { %>
	        <tr>
	          <td><a href="show_quiz.jsp?id=<%= sr.getId() %>"><%= sr.getName() %></a></td>
	        </tr>
		<% } %>
	      </tbody>
	    </table>	
	<% } %>
<% } %>
</div>


</body>
</html>