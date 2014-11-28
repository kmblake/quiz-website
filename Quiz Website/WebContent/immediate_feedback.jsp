<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="quiz.*"%>
<%@ page import="java.sql.*"%>
<%@ page import="java.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Feedback</title>
</head>
<body>
<%
	ArrayList<Question> questions = (ArrayList<Question>) session
			.getAttribute("questions");
	Integer curr_question = (Integer) session
			.getAttribute("current_question");
	Question q = questions.get(curr_question);
	String questionID = Integer.toString(q.getQuestionID());
	String answer = (String) session.getAttribute(questionID);
	
	if (q.isCorrect(answer)) {
		out.println("<p>Correct!</p>");
	} else {
		out.println("<p>Incorrect!</p>");
	}
	out.println("<p>" + q.getQuestion() + "</p>");
	out.println("<p>Your Answer: " + answer);
	out.println("Correct Answer(s): " + q.getAnswer() + "</p>");
	session.setAttribute("current_question", curr_question + 1);
	out.println("<p> <a href=\"take_quiz.jsp\">Next Question</a></p>");	
%>
</body>
</html>