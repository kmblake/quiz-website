<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="quiz.*"%>
<%@ page import ="java.sql.Statement" %>

<% 	DBConnection c = (DBConnection) getServletContext().getAttribute("connection");
	Statement stmt = c.getStatement();
	int id = Integer.parseInt(request.getParameter("id"));
	User u = new User(id, stmt);
	User homeUser = (User) session.getAttribute("user");
	int homeUserId = homeUser.getId();
	int friendshipStatus = Friend.friendshipStatus(stmt, homeUserId, id); ;
	
	if ( homeUserId == id) response.sendRedirect("home.jsp");
%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="ISO-8859-1">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<link href="css/bootstrap.min.css" rel="stylesheet">
		<link href="css/stylesheet.css" rel="stylesheet">
		<title><%= u.getFullName() %></title>
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
			<form action="FriendRequestServlet" method="post">
				<h1 class="page-header">
					<%= u.getFullName() %>
					<% if (friendshipStatus == Friend.FRIENDS) { %>
						<span class="label label-info right">Friends</span>
					<% } else if (friendshipStatus == Friend.PENDING_REQUEST) { %>
						<span class="label label-info right">Friend Request Pending</span>
					<% } else { %>
						<input type="hidden" name="requested_by" value="<%= homeUserId %>">
						<input type="hidden" name="requested_for" value="<%= id %>">
						<input type="hidden" name="source" value="<%= Friend.REQUEST %>">
						<button class="btn btn-lg btn-success right" type="submit">Send Friend Request</button>
					<% } %>
				</h1>
			</form>
			<% String notification = (String) request.getAttribute("notification"); %>
			<% if (notification != null ) { %>
			<div class="alert alert-success"><%= notification %></div>
			<% } %>
			<div class="message-buttons">
				<% if (friendshipStatus == Friend.FRIENDS) { %>
					<a id="challenge-button" class="btn btn-lg btn-warning" href="<%= "create_challenge.jsp?recipient_id=" + id %>">Challenge</a>
				<% } %>
				<a class="btn btn-lg btn-primary" href="<%= "create_message.jsp?recipient_id=" + id %>">Send Note</a>
			</div>
			<% if (friendshipStatus == Friend.FRIENDS) { %>
				<h3>Recently Taken Quizzes:</h3>
				<h3>Recently Created Quizzes:</h3>
			<% } else if (friendshipStatus == Friend.NOT_FRIENDS) { %>
				<p class="notification">In order to see more information about this person, send them a friend request!</p>
			<% } %>
		</div>
	
	</body>
</html>