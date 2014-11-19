<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	<%@ page import="java.sql.*"%>
<%@ page import="java.util.*"%>
	<%@ page import="quiz.*"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="ISO-8859-1">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<link href="css/bootstrap.min.css" rel="stylesheet">
<title>Quiz</title>
</head>
<body>
<%
Integer id = Integer.parseInt(request.getParameter("id"));
session.setAttribute("id", id);
DBConnection con = (DBConnection)application.getAttribute("connection");
Quiz quiz = new Quiz(id, con);
request.setAttribute("quiz", quiz);

%>
<p>Quiz id:<%= id%>
<p>By: <%= quiz.getCreatedBy() %></p>
<p>Description: <%= quiz.getQuizDescription() %></p>
<form action="TakeQuizServlet" method="post">
<input type="submit" value="Take Quiz"/>
</form>
</body>
</html>