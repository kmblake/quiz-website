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
	<div id="image-container" class="float">
		<!--<img id="question-image" src="http://upload.wikimedia.org/wikipedia/en/thumb/b/b7/Stanford_University_seal_2003.svg/1024px-Stanford_University_seal_2003.svg.png" alt="Stanford">-->
	</div>
</div>
</body>
</html>