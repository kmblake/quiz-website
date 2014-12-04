<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII"%>
<%@ page import="quiz.*"%>
<%@ page import="java.sql.*"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.HashMap" %>

<% 
Statement stmt = (Statement) getServletContext().getAttribute("statement"); 
DBConnection dbCon = (DBConnection) getServletContext().getAttribute("connection"); 
QuizIndex index = new QuizIndex(dbCon);
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
	<title>Delete Quiz</title>
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
		<div class="jumbotron">
			<h1>Delete Quizzes</h1>
		</div>
		<a href="delete_quiz.jsp"> Reload Page</a>
		<% String notification = (String) request.getAttribute("error"); %>
		<% if (notification != null ) { %>
			<div class="alert alert-danger"><%= notification %></div>
		<% } %>
		<form action="DeleteQuizServlet" method="post">
			<div class="select-box form-group">
			  <select multiple class="form-control" name="quiz" style="height: 500px">
				  <option value="" disabled selected>Select Quiz</option>
				  <% for (int id : index.getKeys()) { %>
				  	<option value="<%= id %>"><%= quizzes.get(id) %></option>
				  <% }  %>
			  </select>
		    </div>
		    <button class="btn btn-danger" type="submit">Delete</button>
		</form> 
	</div>
	
</body>
</html>