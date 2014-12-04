
<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII"%>
<%@ page import="quiz.*"%>
<%@ page import="java.sql.*"%>

<% User currentUser = (User) session.getAttribute("user"); %>
<% int recipient_id = Integer.parseInt(request.getParameter("recipient_id")); %>
<% User recipient = new User(recipient_id, (Statement) getServletContext().getAttribute("statement")); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
	<link href="css/bootstrap.min.css" rel="stylesheet">
	<link href="css/bootstrap-theme.min.css" rel="stylesheet">
	<link href="css/stylesheet.css" rel="stylesheet">
	<title>New Message</title>
</head>
<body>
	<div class="navbar navbar-inverse navbar-static-top">
		<div class="container">
			<a class="navbar-brand" href="home.jsp">Let's Get Quizzical!</a>
			<div id="navbar" class="navbar-collapse collapse">
		        <ul class="nav navbar-nav navbar-right">
		          <li><a href="/Quiz_Website/LogoutServlet">Logout</a></li>
		          <li><a href="#">Messages</a></li>
		          <li><a href="home.jsp">Home</a></li>
		        </ul>
		        <form action="SearchServlet" method="post" class="navbar-form navbar-right">
		          <input type="text" class="navbar-search form-control" name="query" placeholder="Search for quiz or user...">
		        </form>
		        <img src="Workaholics.jpg" alt="Nothing found" class="img-rounded" style="width: 66px; height: 50px;">
	    	</div>
		</div>
	</div>
	
	<div class="container">
		<div class="jumbotron">
			<h1>New Message</h1>
		</div>
		<% String errorText = (String) request.getAttribute("SQLerror"); %>
		<% if (errorText != null ) { %>
			<div class="alert alert-danger"><%= errorText %></div>
		<% } %>
		<div>
			<form action="CreateMessageServlet" method="post">
				<ul class="stripped">
					<li class="form-item">To: <span class="nobold"><%= recipient.getUsername() %></span></li>
					<li class="form-item">Message:</li>
					<li class="form-item"><textarea style="font-weight: normal"rows="4" cols="75" name="body"></textarea>
					<li class="form-item"><button class="btn btn-primary" type="submit">Send</button>
				</ul>
				<input type="hidden" name="recipient_id" value="<%= recipient_id %>">
			</form> 
		</div>
	</div>
</body>
</html>