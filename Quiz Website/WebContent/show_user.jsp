<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="quiz.*"%>
<%@ page import ="java.sql.Statement" %>

<% 	DBConnection c = (DBConnection) getServletContext().getAttribute("connection");
	Statement stmt = c.getStatement();
	int id = Integer.parseInt(request.getParameter("id"));
	User u = new User(id, stmt);
%>
<% boolean isFriend = false;  
	boolean pendingRequest = false;
	
	if ( ((User) request.getSession().getAttribute("user")).getId() == id) response.sendRedirect("home.jsp");
%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="ISO-8859-1">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<link href="css/bootstrap.min.css" rel="stylesheet">
		<link href="css/stylesheet.css" rel="stylesheet">
		<title>Insert title here</title>
	</head>
	<body>
		<div class="container">
			<h1 class="page-header">
				<%= u.getFullName() %>
				<% if (isFriend) { %>
					<span class="label label-info right">Friends</span>
				<% } else if (pendingRequest) { %>
					<span class="label label-info right">Friend Request Pending</span>
				<% } else { %>
					<a class="btn btn-lg btn-success right" href="#">Send Friend Request</a>
				<% } %>
			</h1>
			<div class="message-buttons">
				<% if (isFriend) { %>
					<a id="challenge-button" class="btn btn-lg btn-warning">Challenge</a>
				<% } %>
				<a class="btn btn-lg btn-primary" href="<%= "create_message.jsp?recipient_id=" + id %>">Send Note</a>
			</div>
			<% if (isFriend) { %>
				<h3>Recently Taken Quizzes:</h3>
				<h3>Recently Created Quizzes:</h3>
			<% } else if (!pendingRequest) { %>
				<p class="notification">In order to see more information about this person, send them a friend request!</p>
			<% } %>
		</div>
	
	</body>
</html>