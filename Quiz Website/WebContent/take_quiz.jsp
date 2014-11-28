<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="quiz.*"%>
<%@ page import="java.sql.*"%>
<%@ page import="java.util.*"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="ISO-8859-1">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<link href="css/bootstrap.min.css" rel="stylesheet">
<link href="css/bootstrap-theme.min.css" rel="stylesheet">
<link href="css/stylesheet.css" rel="stylesheet">

<%
	Quiz quiz = (Quiz) session.getAttribute("quiz");
	int quizID = quiz.getQuizID();
	String quizName = quiz.getTitle();
	String quizDescription = quiz.getQuizDescription();
	boolean multiplePages = quiz.getIfHasMultiplePages();
	session.setAttribute("multiple", multiplePages);
	boolean immediate = quiz.getIfImmediateFeedback();
	session.setAttribute("immediate", immediate);
	ArrayList<Question> questions = quiz.getQuestions();
	int currQuestion = (Integer)session.getAttribute("current_question");
	if (currQuestion == 0)
		session.setAttribute("time_started", System.currentTimeMillis());
%>

<title><%=quizName%></title>
</head>
<body>
<div class="container">
<div class="jumbotron">
<h1><%=quizName%></h1>
<p><%=quizDescription%></p>
<form action="FeedbackServlet" method="post">
<ul class="stripped">

	<%
		int numQuestions = questions.size();
		if (multiplePages) {
			numQuestions = 1;
		}
		for (int i = currQuestion; i < currQuestion + numQuestions; i++) {
			Question toPrint = questions.get(i);
			String type = toPrint.getType();
			int questionID = toPrint.getQuestionID();
	%>

	<li>Question <%=i + 1%></li>
	<%
		if (type.equals("question_response")) {
	%>
	<li><%=toPrint.getQuestion()%>
	<li>
	<li class="form-item">Response: <input type="text"
		class="title-input" name="<%= questionID %>"></li>

	<%
		} else if (type.equals("fill_in_the_blank")) {
				String question = toPrint.getQuestion();
				int indexOfBlank = question.indexOf("//blank//");
				String firstPart = question.substring(0, indexOfBlank);
				String finalPart = question.substring(indexOfBlank + 9,
						question.length());
	%>
	<li class="form-item"><%=firstPart%><input type="text"
		class="title-input" name="<%= questionID %>"> <%= finalPart %></li>
	<%
		} else if (type.equals("multiple_choice")) {
				MultipleChoice theQuestion = (MultipleChoice) toPrint;
				HashMap<String, Boolean> answers = (HashMap<String, Boolean>) theQuestion
						.getAnswers();
	%>
	<li><%=toPrint.getQuestion()%>
	<li>
	<%
		for (Map.Entry<String, Boolean> entry : answers.entrySet()) {
					String answer = entry.getKey();
	%>
	
	<li class="form-item"><%=answer%> <input type="radio"
		name="<%= questionID %>" value="<%= answer %>"></li>
	<%
		}
	%>

	<%
		} else if (type.equals("picture_response")) {
				PictureResponse theQuestion = (PictureResponse) toPrint;
				String img = theQuestion.getImageURL();
	%>

	<li><img id="image" src="<%=img%>" /></li>
	<li class="form-item">Response: <input type="text"
		class="title-input" name="<%= questionID %>"></li>

	<%
		} else if (type.equals("multiple_answer")) {
	%>
	<li><%=toPrint.getQuestion()%>
	<li>
	<li class="form-item">Response: <input type="text"
		class="title-input" name="<%= questionID %>"></li>
	<%
		}
		}
	%>
	<li class="form-item">
	<button class="btn btn-primary" type="submit">Submit</button>
	</li>

</ul>
<input name="id" type="hidden" value="<%= quizID %>" /></form>
</div>
</div>
</body>
</html>