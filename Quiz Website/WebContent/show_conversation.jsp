
<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII"%>
<%@ page import="quiz.*"%>
<%@ page import="java.sql.*"%>

<% User currentUser = (User) session.getAttribute("user"); %>
<% Statement stmt = (Statement) getServletContext().getAttribute("statement"); %>
<% int userId = currentUser.getId();  %>
<% int fromId = Integer.parseInt(request.getParameter("from_id")); %>
<% int toId = Integer.parseInt(request.getParameter("to_id")); %>
<% Message[] notes = Message.getConversation(stmt, fromId, toId); %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
	<link href="css/bootstrap.min.css" rel="stylesheet">
	<link href="css/bootstrap-theme.min.css" rel="stylesheet">
	<link href="css/stylesheet.css" rel="stylesheet">
	<title>Messages</title>
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
		<h1>Conversation</h1>
		<table class="table">
	      <thead>
	        <tr>
	          <th>From</th>
	          <th>Message</th>
	          <th>Received</th>
	        </tr>
	      </thead>
	      <tbody>
		<% for (Message m : notes) { %>
			<% if (m.getSenderId() != userId) { %>
				<tr>
		          <td><a href="<%= "show_user.jsp?id=" + m.getSenderId() %>"><%= m.getSenderName() %></a></td>
		          <td><%= m.getBody() %></td>
		          <td class="received-col"><%= m.getReceivedOn() %></td>
		        </tr>
			<% } else { %>
		        <tr class="shaded-row">
		          <td><%= m.getSenderName() %></td>
		          <td><%= m.getBody() %></td>
		          <td class="received-col"><%= m.getReceivedOn() %></td>
		        </tr>
		    <% } %>
		<% } %>
	      </tbody>
	    </table>
	    <div class="center">
	    	<a href="<%= "create_message.jsp?recipient_id=" + fromId %>" class="btn btn-primary btn-lg">Reply</a>	
	    </div>
	</div>
</body>
</html>