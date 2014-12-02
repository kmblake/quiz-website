
<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII"%>
<%@ page import="quiz.*"%>
<%@ page import="java.sql.*"%>

<% User currentUser = (User) session.getAttribute("user");
Statement stmt = (Statement) getServletContext().getAttribute("statement"); 
int user_id = currentUser.getId();
%>

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
	<h1 class="page-header"><%= currentUser.getFullName() %></h1>
	<div class="row">
		<div class="col-md-9 larger-font">Try out a new quiz, <%= currentUser.getFirstName() %>!</div>
		<div class="col-md-3">
			<h4><%= Challenge.recentChallengesSummary(stmt, user_id) %></h4>
			<h4><%= Friend.friendRequestSummary(stmt, user_id) %></h4>
			<h4><%= Message.unreadNoteSummary(stmt, user_id) %></h4>
		</div>
	</div>
	<div class="row">
		<div class="col-md-6">
			<h2>Popular Quizzes:</h2>
			Presidents Quiz<br>Complex Quiz
		</div>
		<div class="col-md-6">
			<h2>Recently Created Quizzes:</h2>
			Authors Quiz<br>Presidents Quiz
		</div>
	</div>
	<div class="row">
		<div class="col-md-6">
			<h2>Quizzes You Took Recently:</h2>
			Presidents Quiz<br>Complex Quiz
		</div>
		<div class="col-md-6">
			<h2>Quizzes You Created Recently:</h2>
			Authors Quiz<br>Presidents Quiz<br>
			<a href="create_quiz.jsp" class="btn btn-large btn-success"> Create A New Quiz</a>
		</div>
	</div>
	<div class="row">
		<h2>News Feed:</h2>
		<p>Gordon recently took Presidents Quiz</p>
	</div>
<%
	QuizIndex index = (QuizIndex) application.getAttribute("index");
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
</div>


</body>
</html>