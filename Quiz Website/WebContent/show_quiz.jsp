<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	<%@ page import="java.sql.*"%>
<%@ page import="java.util.*"%>
	<%@ page import="quiz.*"%>

<!DOCTYPE html>
<html lang="en">

<%
Integer id = Integer.parseInt(request.getParameter("id"));
session.setAttribute("id", id);
DBConnection con = (DBConnection)application.getAttribute("connection");
Quiz quiz = new Quiz(id, con);
session.setAttribute("quiz", quiz);
session.setAttribute("current_question", 0);
session.setAttribute("score", 0);
long time_started = 0;

%>

<script>
function setParams() {
	time_started = System.currentTimeMillis();
}
</script>

<head>
		<meta charset="ISO-8859-1">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<link href="css/bootstrap.min.css" rel="stylesheet">
		<link href="css/bootstrap-theme.min.css" rel="stylesheet">
		<link href="css/stylesheet.css" rel="stylesheet">
		<title><%= quiz.getTitle() %></title>
	</head>
	<body>
		<div class="container">
			<div class="jumbotron">
				<h1><%= quiz.getTitle() %></h1>
				<p>Created by: <%= quiz.getCreatedBy() %> on <%= quiz.getDateCreated() %></p>
				<p><%= quiz.getQuizDescription() %></p>
			</div>
			<div>
				<form action="take_quiz.jsp">
					<ul class="stripped">
						<% if(quiz.getIfPracticeMode()) { %>
						<li class="form-item">Take the quiz in practice mode: <input type="checkbox" name="practice_mode"></li>
						<% } %>
						<li class="form-item"><button class="btn btn-primary" onclick="setParams()" type="submit">Take Quiz</button>
					</ul>
					<input name="time_started" type="hidden" value=<%=time_started%>>

				</form>
			</div>
			<div class="quiz-history">
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
			ArrayList<QuizHistory> history = quiz.getHistory();
			ArrayList<QuizHistory> yourHistory = new ArrayList<QuizHistory>();
			String currUser = (String) ((User) session.getAttribute("user")).getUsername();
			for(int i=0; i<history.size();i++) {
				QuizHistory currentHistory = history.get(i);
				if(currentHistory.getUser().equals(currUser)) yourHistory.add(currentHistory);
				%>
				<tr>
          <td><%= i+1 %></td>
          <td><%= currentHistory.getScore() %></td>
          <td><%= currentHistory.getTime() %></td>
          <td><%= currentHistory.getUser() %></td>
          <td><%= currentHistory.getWhenTaken() %></td>
        </tr>
				
				<%
			}
			%>
			
			</tbody>
			</table>
			</div>
			
			
			<div class="your-history">
			<h3>Your Best Scores:</h3>
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
			for(int i=0; i<yourHistory.size();i++) {
				QuizHistory currentYourHistory = yourHistory.get(i);
				%>
				<tr>
          <td><%= i+1 %></td>
          <td><%= currentYourHistory.getScore() %></td>
          <td><%= currentYourHistory.getTime() %></td>
          <td><%= currentYourHistory.getWhenTaken() %></td>
        </tr>
				
				<%
			}
			%>
			
			</tbody>
			</table>
			</div>
			
			
		</div>
	</body>
</html>