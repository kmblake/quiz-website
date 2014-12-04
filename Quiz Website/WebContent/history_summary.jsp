
<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII"%>
<%@ page import="quiz.*"%>
<%@ page import="java.sql.*"%>
<%@ page import="java.util.ArrayList"%>

<%  User currentUser = (User) session.getAttribute("user"); 
	Statement stmt = (Statement) getServletContext().getAttribute("statement"); 
	DBConnection dbCon = (DBConnection) getServletContext().getAttribute("connection");
	int userId = currentUser.getId();  
	ArrayList<QuizHistory> history = currentUser.getHistory(dbCon);
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
	<link href="css/bootstrap.min.css" rel="stylesheet">
	<link href="css/bootstrap-theme.min.css" rel="stylesheet">
	<link href="css/stylesheet.css" rel="stylesheet">
	<script type="text/javascript" src="js/jquery-2.1.1.min.js"></script>
	<title>Messages</title>
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
		        <img src="Workaholics.jpg" alt="Nothing found" class="img-rounded" style="width: 66px; height: 50px;">
	    	</div>
		</div>
	</div>
	
	<div class="container">
		<div class="jumbotron"><h1>Your Quiz History</h1></div>
		<% if (history.size() == 0) { %>
			<p>No history to show.</p>
		<% } else { %>
		<table class="table table-striped">
			<thead>
				<tr>
					<th>Quiz</th>
					<th>Score</th>
					<th>Time</th>
					<th>Date Taken</th>
				</tr>
			</thead>
			<tbody>
				<% for(int i=0;i< history.size();i++) {
							QuizHistory currentHistory = history.get(i);
						%>
				<tr>
					<td><a href="<%= "show_quiz.jsp?id=" + currentHistory.getQuizID() %>"><%= currentHistory.getQuizName() %></a></td>
					<td><%= currentHistory.getScore() %></td>
					<td><%= currentHistory.getTime() %></td>
					<td><%= currentHistory.getFormattedWhenTaken() %></td>
				</tr>
		
				<% } %>
		
			</tbody>
		</table>
		<% } %>
	</div>
	
	</body>
</html>