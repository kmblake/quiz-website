<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="quiz.*"%>
<%@ page import="java.sql.*"%>
<%@ page import="java.util.*"%>
<%@ page import="java.text.*"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Feedback</title>
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

<%
	DBConnection dbCon = (DBConnection) application
			.getAttribute("connection");
	Connection con = dbCon.getConnection();
	String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
	User homeUser = (User) session.getAttribute("user");
	int homeUserId = homeUser.getId();
	long elapsed = (Long) session.getAttribute("time_elapsed");
	Time time = new Time(elapsed);
	TimeZone timeZone = TimeZone.getDefault();
	int offset = timeZone.getRawOffset();
	time = new Time(elapsed - offset);
	int score = (Integer) session.getAttribute("score");
	Quiz quiz = (Quiz) session.getAttribute("quiz");
	int quizID = quiz.getQuizID();
	QuizHistory qh = new QuizHistory(con, date, homeUserId, time,
			score, quizID);
%>
You got
<%=score%>
correct! Time elapsed:
<%=(int)(elapsed / 1000)%>
seconds
<%
	ArrayList<Question> questions = (ArrayList<Question>) session
			.getAttribute("questions");
	for (Question q : questions) {
		out.println("<p>" + q.getQuestion() + "</p>");
		String questionID = Integer.toString(q.getQuestionID());
		String answer = (String) session.getAttribute(questionID);
		out.println("Your Answer: " + answer);
		out.println("Correct Answer(s): " + q.getAnswer());

	}
%>
</body>
</html>