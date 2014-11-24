
<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII"%>
<%@ page import="quiz.*"%>
<%@ page import="java.sql.*"%>

<%  User currentUser = (User) session.getAttribute("user"); 
	Statement stmt = (Statement) getServletContext().getAttribute("statement"); 
	int userId = currentUser.getId();  
 	Message[] notes = Message.getNotesForRecipient(stmt, userId, Message.NOTE); 
 	Friend[] friendRequests = Friend.getFriendRequests(stmt, userId);
%>

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
		<% String notification = (String) request.getAttribute("notification"); %>
		<% if (notification != null ) { %>
			<div class="alert alert-success"><%= notification %></div>
		<% } %>
		<h1>Notes: </h1>
		<% if (notes.length == 0)  {%>
			<p>No notes<p>
		<% } else  { %>
			<table class="table">
		      <tbody>
			<% for (Message m : notes) { %>
				<%if (!m.isRead()) { %>
		        <tr class="info">
		        <% } else { %>
		        <tr>
		        <% } %>
		          <td><a href="<%= "show_user.jsp?id=" + m.getSenderId() %>"><%= m.getSenderName() %></a></td>
		          <td><a class="msg-preview" href="<%= "show_conversation.jsp?from_id=" + m.getSenderId() + "&to_id=" + m.getRecipientId() %>"><%= m.getPreview() %></a></td>
		          <td class="received-col"><%= m.getReceivedOn() %></td>
		        </tr>
			<% } %>
		      </tbody>
		    </table>	
		<% } %>
		
		<h1>Friend Requests: </h1>
		<% if (friendRequests.length == 0)  {%>
			<p>No friend requests<p>
		<% } else  { %>
			<ul class="stripped">
			<% for (Friend f : friendRequests) { %>
				<li>
					<form action="FriendRequestServlet" method="post">
						<input type="hidden" name="id" value="<%= f.getId() %>">
						<input type="hidden" name="source" value="<%= Friend.APPROVE %>">
						<div class="friend-request">Request from <a href="<%= "show_user.jsp?id=" + f.getRequestedBy() %>"><%= f.getSender() %></a>: <button class="btn btn-success" type="submit">Approve</button></div>
					</form>
				</li>
			<% } %>
		    </ul>	
		<% } %>
	</div>
</body>
</html>