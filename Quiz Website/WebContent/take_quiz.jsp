<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	<%@ page import="quiz.*"%>
<%@ page import="java.sql.*"%>
<%@ page import="java.util.*"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Take Quiz</title>
</head>
<body>
<%
Integer id = Integer.parseInt(request.getParameter("id"));
DBConnection con = (DBConnection)application.getAttribute("connection");
Quiz quiz = new Quiz(id, con);
ArrayList<Question> questions= quiz.getQuestions();
%>

<p>Quiz id:<%= id %> </p>
<p>By: <%= quiz.getCreatedBy() %></p>
<%
for (int i = 0; i < questions.size(); i++) {
	Question currQuestion = questions.get(i);
	out.print("<p>" + currQuestion.getQuestion() + "</p>");
}
%>
</body>
</html>