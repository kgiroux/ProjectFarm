<%@page import="model.db.UserDB"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<jsp:include page="/utils/header.jsp">
	<jsp:param name="title" value="ProjectFarm" />
	<jsp:param name="page" value="/index.jsp" />
</jsp:include>
<div class="jumbotron text-center">
	<% 
		String login = (String) session.getAttribute("login");
		if(login == null) {
	%>
		<p>Project ideas are seeds to change the world</p>      
		<a class="btn btn-default" href="#">Learn More</a>
	<%} else if(UserDB.getOwner(login) != null) {%>
		<p>Project ideas are seeds to change the world</p>      
		<a class="btn btn-default" href="/ProjectFarm/ListOfCategory">Add Project Idea</a>
	<%} else if(UserDB.getEvaluator(login) != null) {%>
		<p>Project ideas are seeds to change the world</p>      
		<form name="form1" method="post" action="/ProjectFarm/AllProjectServlet">
			<button class="btn btn-default" type="submit">All Project</button>
		</form>
	<%} %>
</div>

<jsp:include page="/utils/footer.jsp" />