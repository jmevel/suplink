<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Registration</title>
</head>
<body>
	<form method="POST" action="adduser">
	  Email: <input type="email" name="email" id="email" size="15" /><br />
	  Password: <input type="password" name="password1" id="password1" size="15" /><br />
	  Password confirmation: <input type="password" name="password2" id="password2" size="15" /><br />
	  <div align="center">
	    <p><input type="submit"  /></p>
	  </div>
	</form>
</body>
</html>