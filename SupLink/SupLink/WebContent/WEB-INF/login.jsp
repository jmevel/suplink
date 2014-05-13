<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="css/bootstrap.min.css" rel="stylesheet" media="screen">
<jsp:include page="bootstrapInit.html" flush="true"/>
<link href="css/bootstrap-responsive.css" rel="stylesheet">
<title>SupLink - Login</title>
</head>
<body>
	
	<jsp:include page="anonymousNavBar.html" flush="true"/>
	<div class="container" style="padding-top: 60px">

      <form class="form-signin" method="POST" action="login">
        <h2 class="form-signin-heading">Please sign in</h2>
        <input type="email" class="input-block-level" placeholder="Email address" name="email">
        <input type="password" class="input-block-level" placeholder="Password" name="password">
       <!-- <label class="checkbox">
          <input type="checkbox" value="remember-me"> Remember me
        </label>-->
        <button class="btn btn-large btn-primary" type="submit">Sign in</button>
      </form>

    </div>
	<script src="js/jquery-1.9.0.js"></script>
    <script src="js/bootstrap.js"></script>
</body>
</html>