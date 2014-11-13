<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="ISO-8859-1">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<link href="css/bootstrap.min.css" rel="stylesheet">
		<link href="css/bootstrap-theme.min.css" rel="stylesheet">
		<link href="css/stylesheet.css" rel="stylesheet">
		<title>Create Quiz</title>
	</head>
	<body>
		<div class="container">
			<div class="jumbotron">
				<h1>Create A Quiz</h1>
				<p>Step 1: Enter some basic information about your quiz</p>
			</div>
			<% String errorText = (String) request.getAttribute("SQLerror"); %>
			<% if (errorText != null ) { %>
				<div class="alert alert-danger"><%= errorText %></div>
			<% } %>
			<div>
				<form action="CreateQuizServlet" method="post">
					<ul class="stripped">
						<li class="form-item">Title: <input type="text" class="title-input" name="title"></li>
						<li class="form-item">Description:</li>
						<li class="form-item"><textarea style="font-weight: normal"rows="4" cols="75" name="description"></textarea>
						<li class="form-item">Randomly order the questions: <input type="checkbox" name="randomized"></li>
						<li class="form-item">Present each question on one page: <input type="checkbox" name="multiple_pages"></li>
						<li class="form-item">Provide immediate feedback after each question: <input type="checkbox" name="immediate_feedback"></li>
						<li class="form-item">Allow the quiz to be taken in practice mode: <input type="checkbox" name="practice_mode"></li>
						<li class="form-item"><button class="btn btn-primary" type="submit">Add A Question</button>
					</ul>
				</form> 
			</div>
		</div>
	</body>
</html>