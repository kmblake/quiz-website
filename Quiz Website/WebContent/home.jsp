
<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII"%>
<%@ page import="quiz.*"%>
<%@ page import="java.sql.*"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>Quiz Website</title>
</head>
<body>
<a href="create_quiz.jsp"> Create Quiz</a>
<%
	QuizIndex index = (QuizIndex) application.getAttribute("index");
	Statement stmt = (Statement) application.getAttribute("statement");
	DBConnection con = (DBConnection) application
			.getAttribute("connection");
	index.loadAllQuizzes();
	for (Integer id : index.getKeys()) {
		Quiz quiz = new Quiz(id, con);

		String link = "<a href= \"show_quiz.jsp?id=" + id + "\">"
				+ quiz.getTitle() + "</a>";
		out.println("<li> " + link + "</li>");
	}
%>
</body>
</html>