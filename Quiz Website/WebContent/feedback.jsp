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
You got <%=request.getAttribute("score") %> correct!
<%
ArrayList<Question> questions = (ArrayList<Question>)request.getAttribute("questions");
for (Question q : questions) {
	out.println("<p>" + q.getQuestion() + "</p>");
	String questionID = Integer.toString(q.getQuestionID());
	String answer = (String)request.getAttribute(questionID);
	out.println("Your Answer: " + answer);
	out.println("Correct Answer: " + q.getAnswer());
}

%>
</body>
</html>