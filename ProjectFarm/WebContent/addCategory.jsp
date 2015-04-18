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
		if(UserDB.getEvaluator(login) != null) {
			if(request.getAttribute("error_cat") != null){%>
				<div class="text-danger">
          			<%=request.getAttribute("error_cat")%>
          		</div>
			<%} %>
		<p>Add a new category for users</p>      
		<form name="form1" method="post" action="/ProjectFarm/AddCategory">
			<input type="text" name="category_name" placeholder="Category name">
			<button class="btn btn-default" type="submit">Add new Category</button>
		</form>
	<%} %>
</div>

<jsp:include page="/utils/footer.jsp" />