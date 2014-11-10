<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>Create Account</title>
</head>
<body>
<h1> The Name <%=request.getParameter("user") %> is Already In Use</h1>
Please enter another name and password.

<form action="NewAccountServlet" method="post">
<p>User Name: <input type="text" name="user" /> </p>
<p>Password:  <input type="text" name="password" />
<input type="submit" value="Login"/></p>
</form>

</body>
</html>