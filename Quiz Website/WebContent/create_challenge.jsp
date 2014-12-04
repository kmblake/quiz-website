
<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII"%>
<%@ page import="quiz.*"%>
<%@ page import="java.sql.*"%>
<%@ page import="java.util.HashMap" %>

<%  User currentUser = (User) session.getAttribute("user");
	DBConnection con = (DBConnection) getServletContext().getAttribute("connection");
	String quiz_id_string = request.getParameter("quiz_id");
	String recipient_id_string = request.getParameter("recipient_id");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
	<link href="css/bootstrap.min.css" rel="stylesheet">
	<link href="css/bootstrap-theme.min.css" rel="stylesheet">
	<link href="css/stylesheet.css" rel="stylesheet">
	<title>New Challenge</title>
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
		<div class="jumbotron">
			<form action="CreateChallengeServlet" method="post">
				<h1>New Challenge</h1>
				<% if (recipient_id_string != null) {
					int recipient_id = Integer.parseInt(recipient_id_string);
					User recipient = new User(recipient_id, (Statement) getServletContext().getAttribute("statement"));
					QuizIndex index = new QuizIndex(con);
					index.loadAllQuizzes();
					HashMap<Integer, String> quizzes = index.getIndex(); %>
					
					<p>For: <span class="nobold"><%= recipient.getUsername() %></span></p>
					<div class="select-box form-group">
					  <select class="form-control" name="quiz">
						  <option value="" disabled selected>Select Quiz</option>
						  <% for (int id : index.getKeys()) { %>
						  	<option value="<%= id %>"><%= quizzes.get(id) %></option>
						  <% }  %>
					  </select>
				    </div>
				    <button class="btn btn-primary" type="submit">Send</button>
					<input type="hidden" name="challenged_user" value="<%= recipient_id %>">
					<input type="hidden" name="challenged_by" value="<%= currentUser.getId() %>">
					<input type="hidden" name="redirect_to" value="<%= "show_user.jsp?id=" + recipient_id %>">
				<% } else if (quiz_id_string != null){ 
					int quiz_id = Integer.parseInt(quiz_id_string);
					Quiz q = new Quiz(quiz_id, con);
					User[] friends = currentUser.getFriends();
				%>
					<div class="select-box form-group">
					  <select class="form-control" name="challenged_user">
						  <option value="" disabled selected>Select Recipient</option>
						  <% for (User u : friends) { %>
						  	<option value="<%= u.getId() %>"><%= u.getFullName() %></option>
						  <% }  %>
					  </select>
				    </div>
				    <p>Quiz: <span class="nobold"><%= q.getTitle() %></span></p>
				    <button class="btn btn-primary" type="submit">Send</button>
					<input type="hidden" name="quiz" value="<%= quiz_id %>">
					<input type="hidden" name="challenged_by" value="<%= currentUser.getId() %>">
					<input type="hidden" name="redirect_to" value="<%= "show_quiz.jsp?id=" + quiz_id %>">
				<% } %>
				
					
			</form> 
		</div>
	</div>
</body>
</html>