
<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII"%>
<%@ page import="quiz.*"%>
<%@ page import="java.sql.*"%>
<%@ page import="java.util.ArrayList" %>

<% User currentUser = (User) session.getAttribute("user");
Statement stmt = (Statement) getServletContext().getAttribute("statement"); 
DBConnection dbCon = (DBConnection) getServletContext().getAttribute("connection"); 
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
    	</div>
	</div>
</div>

<div class="container">
	<div class="row">
		<div class="col-md-9"><h1 class="page-header"><%= currentUser.getFullName() %></h1></div>
		<div class="col-md-3">
			<a id="msgLink" href="show_messages.jsp"> </a>
			<h4><%= Challenge.recentChallengesSummary(stmt, user_id) %></h4>
			<h4><%= Friend.friendRequestSummary(stmt, user_id) %></h4>
			<h4><%= Message.unreadNoteSummary(stmt, user_id) %></h4>
		</div>
	</div>
	<div class="row">
		<div class="col-md-9 larger-font">Try out a new quiz, <%= currentUser.getFirstName() %>!</div>
	</div>
	<div class="row">
		<div class="col-md-6">
			<% Quiz[] popular_quizzes = Quiz.topQuizzes(dbCon, Quiz.MOST_POPULAR); %>
			<h2>Popular Quizzes:</h2>
			<ul class="stripped">
			<% for (Quiz q : popular_quizzes) { %>
				<li><a href="show_quiz.jsp?id=<%= q.getQuizID() %>"><%= q.getTitle() %></a></li>
			<% } %>
			</ul>
		</div>
		<div class="col-md-6">
			<% Quiz[] recent_quizzes = Quiz.topQuizzes(dbCon, Quiz.MOST_RECENT); %>
			<h2>Recently Created Quizzes:</h2>
			<ul class="stripped">
			<% for (Quiz q : recent_quizzes) { %>
				<li><a href="show_quiz.jsp?id=<%= q.getQuizID() %>"><%= q.getTitle() %></a></li>
			<% } %>
			</ul>
		</div>
	</div>
	<div class="row">
		<div class="col-md-6">
			<% Quiz[] your_taken_quizzes = currentUser.recentlyTakenQuizzes(dbCon); %>
			<h2>Quizzes You Took Recently:</h2>
			<ul class="stripped">
			<% if (your_taken_quizzes.length == 0) { %>
				<li>You haven't taken any quizzes yet.  Try one!</li>
			<% } else { %>
				<% for (Quiz q : your_taken_quizzes) { %>
					<li><a href="show_quiz.jsp?id=<%= q.getQuizID() %>"><%= q.getTitle() %></a></li>
				<% } %>
					<li><a href="history_summary.jsp" class="btn btn-large btn-primary">View Complete History</a></li>
			<% } %>
			</ul>
		</div>
		<div class="col-md-6">
			<% Quiz[] your_created_quizzes = currentUser.recentlyCreatedQuizzes(dbCon); %>
			<h2>Quizzes You Created Recently:</h2>
			<ul class="stripped">
			<% for (Quiz q : your_created_quizzes) { %>
				<li class=""><a href="show_quiz.jsp?id=<%= q.getQuizID() %>"><%= q.getTitle() %></a></li>
			<% } %>
			</ul>
			<a href="create_quiz.jsp" class="btn btn-large btn-success"> Create A New Quiz</a>
		</div>
	</div>
	<div class="row">
		<% ArrayList<NewsItem> news = currentUser.getNewsFeed(); %>
		<h2>News Feed:</h2>
		<% if (news.size() > 0) { %>
			<ul class="list-group ul-striped">
				<% for (NewsItem n : news) { %>
					<li class="list-group-item center">Your friend <a href="show_user.jsp?id=<%= n.getUserId() %>"><%= n.getUsername() %></a> <%= n.getVerb() %> <a href="show_quiz.jsp?id=<%= n.getQuizId() %>"><%= n.getQuizTitle() %></a> on <%= n.getHappenedOnString() %>.</li>
				<% } %>
			</ul>
		<% } else { %>
			<p>Once you have added friends, you will see their recent activity here.  To add a user as a friend, search for them by name or username and click "Send Friend Request" from their user page.</p>
		<% } %>
	</div>
</div>


</body>
</html>