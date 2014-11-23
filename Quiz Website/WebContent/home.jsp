
<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII"%>
<%@ page import="quiz.*"%>
<%@ page import="java.sql.*"%>

<% User currentUser = (User) session.getAttribute("user"); %>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
	<link href="css/bootstrap.min.css" rel="stylesheet">
	<link href="css/bootstrap-theme.min.css" rel="stylesheet">
	<link href="css/stylesheet.css" rel="stylesheet">
	<title>Quiz Website</title>
</head>
<body>
<div class="navbar navbar-inverse navbar-fixed-top">
	<div class="container">
		<a class="navbar-brand" href="home.jsp">Quiz Website</a>
		<div id="navbar" class="navbar-collapse collapse">
	        <ul class="nav navbar-nav navbar-right">
	          <li><a href="/Quiz_Website/LogoutServlet">Logout</a></li>
	          <li><a href="#">Messages</a></li>
	          <li><a href="home.jsp">Home</a></li>
	        </ul>
	        <form action="SearchServlet" method="post" class="navbar-form navbar-right">
	          <input type="text" class="navbar-search form-control" name="query" placeholder="Search for quiz or user...">
	        </form>
    	</div>
	</div>
</div>

<div class="container home-container">
	<h1 class="page-header"><%= currentUser.getFullName() %></h1>
	<a href="create_quiz.jsp"> Create Quiz</a>
<%
	QuizIndex index = (QuizIndex) application.getAttribute("index");
	Statement stmt = (Statement) application.getAttribute("statement");
	DBConnection con = (DBConnection) application
			.getAttribute("connection");
	index.loadAllQuizzes();
	for (Integer id : index.getKeys()) {
		Quiz quiz = new Quiz(id, con);

		String link = "<a href= \"take_quiz.jsp?id=" + id + "\">"
				+ quiz.getTitle() + "</a>";
		out.println("<li> " + link + "</li>");
	}
%>
</div>


</body>
</html>