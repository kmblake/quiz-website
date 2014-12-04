
<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII"%>
<%@ page import="quiz.*"%>
<%@ page import="java.sql.*"%>
<%@ page import="java.util.HashMap" %>

<%  User currentUser = (User) session.getAttribute("user");
	int recipient_id = Integer.parseInt(request.getParameter("recipient_id"));
	User recipient = new User(recipient_id, (Statement) getServletContext().getAttribute("statement"));
	DBConnection con = (DBConnection) getServletContext().getAttribute("connection");
	QuizIndex index = new QuizIndex(con);
	index.loadAllQuizzes();
	HashMap<Integer, String> quizzes = index.getIndex();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
	<link href="css/bootstrap.min.css" rel="stylesheet">
	<link href="css/bootstrap-theme.min.css" rel="stylesheet">
	<link href="css/stylesheet.css" rel="stylesheet">
	<title>New Challenge</title>
</head>
<body>
	<div class="navbar navbar-inverse navbar-static-top">
		<div class="container">
			<a class="navbar-brand" href="home.jsp">Let's Get Quizzical!</a>
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
		<div class="jumbotron">
			<h1>New Challenge</h1>
			<p>For: <span class="nobold"><%= recipient.getUsername() %></span></p>
			<form action="CreateChallengeServlet" method="post">
				<div class="select-box form-group">
				  <select class="form-control" name="quiz">
					  <option value="" disabled selected>Select Quiz</option>
					  <% for (int id : index.getKeys()) { %>
					  	<option value="<%= id %>"><%= quizzes.get(id) %></option>
					  <% }  %>
				  </select>
			    </div>
			    <button class="btn btn-primary" type="submit">Send</button>
				<input type="hidden" name="challenged_user" value="<%= recipient_id %>">
				<input type="hidden" name="challenged_by" value="<%= currentUser.getId() %>">
			</form> 
		</div>
	</div>
</body>
</html>