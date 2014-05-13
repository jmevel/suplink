<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="../css/bootstrap.min.css" rel="stylesheet" media="screen">
<jsp:include page="../bootstrapInit.html" flush="true"/>
<link href="../css/bootstrap-responsive.css" rel="stylesheet">
<title>SupLink - Home</title>
</head>
<body>
	<jsp:include page="../connectedNavBar.html" flush="true"/>
	<div class="container">
	<h2 style="padding-bottom: 30px; padding-top: 30px; text-align: center">SupLink - Another URL shortener</h2>
		<form class="form-signin" style="max-width: 600px; margin-left: 200px" method="POST" action="home">
		 	<h3 class="form-signin-heading" style="text-align: left">New URL</h3>
			<input type="text" placeholder="Name" name="linkName"/>
			<input type="text" placeholder="URL" name="Url"/>
			 <button class="btn btn-large btn-primary" type="submit">Generate</button>
		</form>
		<table class="table table-striped" style="border-width: 2px;!important">
			<thead>
				<tr>
					<td>Name</td>
					<td>Original Url</td>
					<td>Shortened Url</td>
					<td>Clicks</td>
					<td>Date created</td>
					<td>Enable</td>
					<td>Remove</td>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${links}" var="link">
					<tr>
						<td>
							<c:out value="${link.getName()}"/>
						</td>
						<td>
							<c:out value="${link.getOriginalURL()}"/>
						</td>
						<td>
							<c:out value="${link.getShortenedURL()}"/>
						</td>
						<td>
							<c:out value="${link.getClicks().size()}"/>
						</td>
						<td>
							<c:out value="${link.getCreationDate()}"/>
						</td>
						<td>
							<FORM method="POST" action="../auth/enable"> 
        						<input type="hidden" name="linkId" value="${link.getId()}" />
								<c:if test="${link.getIsEnable()==true}">
									<INPUT type="checkbox" name="isEnable"  checked/>
								</c:if> 
								<c:if test="${link.getIsEnable()==false}">
									<INPUT type="checkbox" name="isEnable" />
								</c:if> 
								<input type="submit" value="OK">
							</FORM>
						</td>
						<td>
							<form method="post" action="../auth/removelink">
								<input src="../img/trash.png" style="width: 25px" type=image Value=submit/>
								<input type="hidden" name="removeLinkId" value="${link.getId()}"/>
							</form>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	<script src="../js/jquery-1.9.0.js"></script>
    <script src="../js/bootstrap.js"></script>
</body>
</html>