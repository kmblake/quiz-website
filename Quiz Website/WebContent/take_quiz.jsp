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
	int currQuestion = (Integer) session
			.getAttribute("current_question");
	String practiceMode = (String) request
			.getParameter("practice_mode");
	if (request.getParameter("start") != null) {
		session.setAttribute("time_started", System.currentTimeMillis());
		boolean practice = false;
		if (practiceMode != null)
			practice = practiceMode.equals("on");
		session.setAttribute("practice", practice);
		if (practice) {
			ArrayList<Integer> correctAnswers = new ArrayList<Integer>();
			for (int i = 0; i < questions.size(); i++) {
				correctAnswers.add(0);
			}
			session.setAttribute("correctAnswers", correctAnswers);
		}
		session.setAttribute("repeat", false);
	}
%>

<title><%=quizName%></title>
</head>
<body>

<div class="navbar navbar-inverse navbar-static-top">
<div class="container"><a class="navbar-brand" href="home.jsp">Let's Get Quizzical!</a>
<div id="navbar" class="navbar-collapse collapse">
<ul class="nav navbar-nav navbar-right">
	<li><a href="/Quiz_Website/LogoutServlet">Logout</a></li>
	<li><a href="show_messages.jsp">Messages</a></li>
	<li><a href="home.jsp">Home</a></li>
</ul>
<form action="SearchServlet" method="post"
	class="navbar-form navbar-right"><input type="text"
	class="navbar-search form-control" name="query"
	placeholder="Search for quiz or user..."></form>
</div>
</div>
</div>

<div class="container">
<div class="jumbotron">
<h1><%=quizName%></h1>
<p><%=quizDescription%></p>
<form action="FeedbackServlet" method="post">
</div>
<ul class="list-unstyled">
	<div>
	<%
		int numQuestions = questions.size();
		if (multiplePages) {
			numQuestions = 1;
		}
		for (int i = currQuestion; i < currQuestion + numQuestions; i++) {
			Question toPrint = questions.get(i);
			String type = toPrint.getType();
			System.out.println(type);
			System.out.println("Hello");
			int questionID = toPrint.getQuestionID();
	%>

	<li>Question <%=i + 1%></li>
	<%
		if (type.equals("question_response")) {
	%>
	<li><%=toPrint.getQuestion()%>
	<li>
	<li class="form-item">Response: <input type="text"
		class="title-input" name="<%=questionID%>"></li>

	<%
		} else if (type.equals("fill_in_the_blank")) {
				String question = toPrint.getQuestion();
				int indexOfBlank = question.indexOf("//blank//");
				String firstPart = question.substring(0, indexOfBlank);
				String finalPart = question.substring(indexOfBlank + 9,
						question.length());
	%>
	<li class="form-item"><%=firstPart%><input type="text"
		class="title-input" name="<%=questionID%>"> <%=finalPart%></li>
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
		name="<%=questionID%>" value="<%=answer%>"></li>

	<%
		}
	%> 
	<%
		} else if (type.equals("multiple_choice_multiple_answer")) {
			System.out.println("working");
				MultipleChoiceMultipleAnswer theQuestion = (MultipleChoiceMultipleAnswer) toPrint;
				HashMap<String, Boolean> answers = (HashMap<String, Boolean>) theQuestion
						.getAnswers();
	%>
	<li><%=toPrint.getQuestion()%>
	<li>
	<%
		int num=0;
		for (Map.Entry<String, Boolean> entry : answers.entrySet()) {
					String answer = entry.getKey();
	%>
	
	<li class="form-item"><%=answer%> <input type="checkbox"
		name="<%=questionID%>-<%=num%>" value="<%=answer%>"></li>

	<%
			num++;
		}
	%>
	
	<%
 	} else if (type.equals("picture_response")) {
 			PictureResponse theQuestion = (PictureResponse) toPrint;
 			String img = theQuestion.getImageURL();
 %>

	<li><img id="image" src="<%=img%>" /></li>
	<li class="form-item">Response: <input type="text"
		class="title-input" name="<%=questionID%>"></li>

	<%
		} else if (type.equals("multiple_answer")) {
	%>
	<li><%=toPrint.getQuestion()%>
	<li>
	<li class="form-item">Response: <input type="text"
		class="title-input" name="<%=questionID%>"></li>
	<%
		}
		}
	%>
	</div>
	<li class="form-item">
	<button class="btn btn-primary" type="submit">Submit</button>
	</li>

</ul>
<input name="id" type="hidden" value="<%=quizID%>" />
</form>


</div>
</body>
</html>