<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="quiz.*"%>
<%@ page import="java.sql.*"%>
<%@ page import="java.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="css/bootstrap.min.css" rel="stylesheet">
<link href="css/bootstrap-theme.min.css" rel="stylesheet">
<link href="css/stylesheet.css" rel="stylesheet">
<title>Feedback</title>
</head>
<body>

<div class="navbar navbar-inverse navbar-static-top">
<div class="container"><a class="navbar-brand" href="home.jsp">Quiz
Website</a>
<div id="navbar" class="navbar-collapse collapse">
<ul class="nav navbar-nav navbar-right">
	<li><a href="/Quiz_Website/LogoutServlet">Logout</a></li>
	<li><a href="show_messages.jsp">Messages</a></li>
	<li><a href="home.jsp">Home</a></li>
</ul>
<form action="SearchServlet" method="post"
	class="navbar-form navbar-right"><input type="text"
	class="navbar-search form-control" name="query"
	placeholder="Search for quiz or user..."></form>
</div>
</div>
</div>

<div class="container">
<div class="jumbotron">
<%
	ArrayList<Question> questions = (ArrayList<Question>) session
			.getAttribute("questions");
	Integer curr_question = (Integer) session
			.getAttribute("current_question");
	Question q = questions.get(curr_question);
	String questionID = Integer.toString(q.getQuestionID());
	String answer = (String) session.getAttribute(questionID);
	
	if (q.isCorrect(answer)) {
		out.println("<h1>Correct!</h1>");
	} else {
		out.println("<h1>Incorrect!</h1>");
	}
	out.println("<dt>" + q.getQuestion() + "</dt>");
	out.println("<dd>Your Answer: " + answer);
	out.println("<br>Correct Answer(s): " + q.getAnswer()
			+ "</span></dd>");

	session.setAttribute("current_question", curr_question + 1);
	out.println("<p> <a href=\"take_quiz.jsp\">Next Question</a></p>");	
%>
</div>
</div>
</body>
</html>