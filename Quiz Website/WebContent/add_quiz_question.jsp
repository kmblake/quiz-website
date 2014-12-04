<%@ page import ="quiz.QuestionType" %>
<%@ page import ="java.sql.Statement;" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="ISO-8859-1">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <link href="css/bootstrap.min.css" rel="stylesheet">
  <link href="css/bootstrap-theme.min.css" rel="stylesheet">
  <link href="css/stylesheet.css" rel="stylesheet">
  <script type="text/javascript" src="js/jquery-2.1.1.min.js"></script>
  <script type="text/javascript" src="js/create_question.js"></script>
  <title>Add Questions</title>
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
   <div class="jumbotron">
    <h1>Create A Quiz</h1>
    <p>Step 2: Add Questions</p>
  </div>
  <div class="float">
	  <h3>Question <span id="questionNumber"><%= request.getSession().getAttribute("question-num") %></span></h3>
	  <div>
		<% String errorText = (String) request.getAttribute("SQLerror"); %>
		<% if (errorText != null ) { %>
			<div class="alert alert-danger"><%= errorText %></div>
		<% } %>
	  </div>
	  <div class="select-box form-group">
	  <% QuestionType[] types = QuestionType.getQuestionTypes((Statement) getServletContext().getAttribute("statement")); %>
		  <select class="form-control" id="question-selector">
			  <option value="" disabled selected>Select Question Type</option>
			  <% for (QuestionType qt : types) { %>
			  	<option value="<%= qt.getType() %>"><%= qt.getName() %></option>
			  <% }  %>
		  </select>
	  </div>
	  <div id="form-container">
	  </div>
	</div>
	<div id="image-container" class="float image-container">
	</div>
</div>
</body>
</html>