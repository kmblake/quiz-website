<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ page import="java.sql.Time"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.util.Calendar"%>
<%@ page import="java.util.Collections"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.TimeZone"%>
<%@ page import="quiz.*"%>

<!DOCTYPE html>
<html lang="en">

<%
	Integer id = Integer.parseInt(request.getParameter("id"));
	session.setAttribute("id", id);
	DBConnection con = (DBConnection) application
			.getAttribute("connection");
	Quiz quiz = new Quiz(id, con);
	session.setAttribute("quiz", quiz);
	session.setAttribute("current_question", 0);
	session.setAttribute("score", 0);
	long time_started = 0;
%>

<head>
<meta charset="ISO-8859-1">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<link href="css/bootstrap.min.css" rel="stylesheet">
<link href="css/bootstrap-theme.min.css" rel="stylesheet">
<link href="css/stylesheet.css" rel="stylesheet">
<title><%=quiz.getTitle()%></title>
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

<% 
			ArrayList<QuizHistory> history = quiz.getHistory();
			ArrayList<QuizHistory> yourHistory = new ArrayList<QuizHistory>();
			ArrayList<QuizHistory> recentHistory = new ArrayList<QuizHistory>();
			ArrayList<QuizHistory> topRecentPerformers = new ArrayList<QuizHistory>();
			
			
			Calendar currCalendar = Calendar.getInstance();
			currCalendar.add(Calendar.DATE, -1);
			Date yesterday = currCalendar.getTime();
			
			long averageTime = 0;
			double averageScore = 0;
			int numTakers = history.size();
			
			String currUser = (String) ((User) session.getAttribute("user")).getUsername();
			for(int i=0; i<numTakers;i++) {
				QuizHistory currentHistory = history.get(i);
				Date dateTaken = currentHistory.getWhenTaken();
				averageTime += currentHistory.getTime().getTime();
				averageScore += currentHistory.getScore();
				
				if(currentHistory.getUser().equals(currUser)) yourHistory.add(currentHistory);
				if(dateTaken.after(yesterday)) {
					recentHistory.add(currentHistory);
					topRecentPerformers.add(currentHistory);
				}
			}
			if(numTakers != 0) {
				averageTime = averageTime/numTakers;
				averageScore = averageScore/numTakers;
				averageScore = Math.round(averageScore*100.0)/100.0;
			}
			
			Time averageTimeTaken = new Time(averageTime);
			if(numTakers == 0) {
				TimeZone timeZone = TimeZone.getDefault();
				int offset = timeZone.getRawOffset();
				averageTimeTaken = new Time(averageTime - offset);
			}
			
			
			
			Collections.sort(recentHistory);
			%>


<div class="container">
<div class="jumbotron">
<h1><%= quiz.getTitle() %></h1>
<p>Created by: <a
	href="<%= "show_user.jsp?id=" + quiz.getCreatedByID() %>"><%= quiz.getCreatedBy() %></a>
on <%= quiz.getFormattedDateCreated() %></p>
<p><%= quiz.getQuizDescription() %></p>
<p>Average Time: <%= averageTimeTaken %></p>
<p>Average Score: <%= averageScore %></p>
</div>
<div>
<form action="take_quiz.jsp">
<input name="start" type="hidden"
	value="start">
<ul class="stripped">
	<% if(quiz.getIfPracticeMode()) { %>
	<li class="form-item">Take the quiz in practice mode: <input
		type="checkbox" name="practice_mode" value="on"></li>
	<% } %>
	<li class="form-item">
	<button class="btn btn-primary" type="submit">Take Quiz</button>
</ul>

</form>
</div>
<div class="quiz-history">
<%
int numWhoTookQuiz = history.size();
if(numWhoTookQuiz==0) {
%>
<h3>Top Scorers: No one has taken this quiz yet!</h3>
<%
} else {
%>
<h3>Top Scorers</h3>
<table class="table table-striped">
	<thead>
		<tr>
			<th>#</th>
			<th>Score</th>
			<th>Time</th>
			<th>Username</th>
			<th>Date Taken</th>
		</tr>
	</thead>
	<tbody>
		<% 
				for(int i=0;i<numWhoTookQuiz;i++) {
					QuizHistory currentHistory = history.get(i);
				if(i<5) {
				%>
		<tr>
			<td><%= i+1 %></td>
			<td><%= currentHistory.getScore() %></td>
			<td><%= currentHistory.getTime() %></td>
			<td><a
				href="<%= "show_user.jsp?id=" + currentHistory.getUserID() %>"><%= currentHistory.getUser() %></a></td>
			<td><%= currentHistory.getFormattedWhenTaken() %></td>
		</tr>

		<%
				}
			}
		

		%>

	</tbody>
</table>
<%
}
%>
</div>


<div class="your-history">
<%
int yourNumTakes = yourHistory.size();
if(numWhoTookQuiz==0) {
} else if (yourNumTakes==0) {
%>
<h3>Your Best Scores: You haven't taken this quiz yet!</h3>
<%
} else {
%>
<h3>Your Best Scores</h3>
<table class="table table-striped">
	<thead>
		<tr>
			<th>#</th>
			<th>Score</th>
			<th>Time</th>
			<th>Date Taken</th>
		</tr>
	</thead>
	<tbody>
		<%
			for (int i = 0; i < yourNumTakes; i++) {
				QuizHistory currentYourHistory = yourHistory.get(i);

				%>
		<tr>
			<td><%= i+1 %></td>
			<td><%= currentYourHistory.getScore() %></td>
			<td><%= currentYourHistory.getTime() %></td>
			<td><%= currentYourHistory.getFormattedWhenTaken() %></td>
		</tr>

		<%
			}
			%>

	</tbody>
</table>
<%
}
%>
</div>

<div class="quiz-history">
<%
int recentNumTakes = recentHistory.size();
if(recentNumTakes==0) {
} else {
%>
<h3>Top Recent Performers</h3>
<table class="table table-striped">
	<thead>
		<tr>
			<th>#</th>
			<th>Score</th>
			<th>Time</th>
			<th>Username</th>
			<th>Date Taken</th>
		</tr>
	</thead>
	<tbody>
		<% 
				for(int i=0;i<topRecentPerformers.size();i++) {
					QuizHistory currentHistory = topRecentPerformers.get(i);
				if(i<5) {
				%>
		<tr>
			<td><%= i+1 %></td>
			<td><%= currentHistory.getScore() %></td>
			<td><%= currentHistory.getTime() %></td>
			<td><a
				href="<%= "show_user.jsp?id=" + currentHistory.getUserID() %>"><%= currentHistory.getUser() %></a></td>
			<td><%= currentHistory.getFormattedWhenTaken() %></td>
		</tr>

		<%
				}
			}
			%>

	</tbody>
</table>
<%
}
%>
</div>


<div class="quiz-history">
<%
if(numWhoTookQuiz==0) {
} else if (recentNumTakes==0) {
%>
<h3>Recent Performances: No one has taken this quiz recently.</h3>
<%
} else {
%>
<h3>Recent Performances</h3>
<table class="table table-striped">
	<thead>
		<tr>
			<th>#</th>
			<th>Score</th>
			<th>Time</th>
			<th>Username</th>
			<th>Date Taken</th>
		</tr>
	</thead>
	<tbody>
		<% 
				for(int i=0;i<recentHistory.size();i++) {
					QuizHistory currentHistory = recentHistory.get(i);
				if(i<5) {
				%>
		<tr>
			<td><%= i+1 %></td>
			<td><%= currentHistory.getScore() %></td>
			<td><%= currentHistory.getTime() %></td>
			<td><a
				href="<%= "show_user.jsp?id=" + currentHistory.getUserID() %>"><%= currentHistory.getUser() %></a></td>
			<td><%= currentHistory.getFormattedWhenTaken() %></td>
		</tr>

		<%
				}
			}
		
		%>

	</tbody>
</table>
<%
}
%>
</div>

</div>
</body>
</html>