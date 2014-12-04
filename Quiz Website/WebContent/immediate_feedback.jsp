<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="quiz.*"%>
<%@ page import="java.sql.*"%>
<%@ page import="java.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="css/bootstrap.min.css" rel="stylesheet">
<link href="css/bootstrap-theme.min.css" rel="stylesheet">
<link href="css/stylesheet.css" rel="stylesheet">
<title>Feedback</title>
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
<%
	ArrayList<Question> questions = (ArrayList<Question>) session
			.getAttribute("questions");
	Integer curr_question = (Integer) session
			.getAttribute("current_question");
	Question q = questions.get(curr_question);
	String questionID = Integer.toString(q.getQuestionID());
	Object answer = session.getAttribute(questionID);
	ArrayList<Integer> correctAnswers = (ArrayList<Integer>) session
			.getAttribute("correctAnswers");
	boolean practice = (Boolean)session.getAttribute("practice");
	boolean repeat = (Boolean)session.getAttribute("repeat");


	if (q.isCorrect(answer)) {
		out.println("<h1>Correct!</h1>");
	} else {
		out.println("<h1>Incorrect!</h1>");
	}
	out.println("<dt>" + q.getQuestion() + "</dt>");
	out.println("<dd>Your Answer: " + answer);
	out.println("<br>Correct Answer(s): " + q.getAnswer()
			+ "</span></dd>");

	
	boolean done = false;
	int nextQuestion = -1;
	if (curr_question + 1 == questions.size()) {
		repeat = true;
		session.setAttribute("repeat", false);
	}
	if (practice) {
		if(q.isCorrect(answer)) {
			correctAnswers.set(curr_question, correctAnswers.get(curr_question) + 1);
		}
		Quiz quiz = (Quiz) session.getAttribute("quiz");
		for (int i = 0; i < questions.size(); i++) {
			if (correctAnswers.get(i) < 3) {
				nextQuestion = i;
				break;
			}
		}
		if (nextQuestion == -1) {
			done = true;
			out.println("<p>Congratulations! You have mastered this quiz in practice mode!</p>");
			out.println("<a href='show_quiz.jsp?id=" + quiz.getQuizID() + "' class='btn btn-primary'>Return to Quiz Page</a>");
		} else {
			if (quiz.getIfRandomized() && !repeat) {
				Random random = new Random();
				nextQuestion = random.nextInt(questions.size());
				while (correctAnswers.get(nextQuestion) >= 3) {
					nextQuestion = random.nextInt(questions.size());
				}
			}
			else {
				nextQuestion = (curr_question + 1) % questions.size();
				while (correctAnswers.get(nextQuestion) >= 3) {
					nextQuestion = (nextQuestion + 1) % questions.size();
				}
			}
		}
	} else {
		nextQuestion = curr_question + 1;
	}
	session.setAttribute("current_question", nextQuestion);
	if (!done) {
		out.println("<p> <a href=\"take_quiz.jsp\">Next Question</a></p>");
	}
%>
</div>
</div>
</body>
</html>