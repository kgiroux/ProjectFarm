<%@page import="model.db.UserDB"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet"href="/ProjectFarm/ext/bootstrap/3.2.2/css/bootstrap.css">
<script src="/ProjectFarm/ext/jquery/1.11.2/jquery-1.11.2.js"></script>
<script src="/ProjectFarm/ext/bootstrap/3.2.2/js/bootstrap.min.js"></script>
</head>
<body>
<script language="javascript">
	function Go()
	{
		document.monForm.submit();
	}
</script>
	<div class="navbar navbar-default">
		<div class="navbar-header">
			<a class="navbar-brand" href="<%= request.getContextPath()%>/index.jsp"><%= request.getParameter("title") %></a>
		</div>
		<% String login = (String) session.getAttribute("login"); 
		if(login == null){%>
          	<div class="login_form text-right">
          		<%if(request.getAttribute("message") != null){%>
          			<div class="text-danger">
          				<%=request.getAttribute("message")%>
          			</div>
          		<%} %>	
          		<div class="navbar-form form-inline pull-right">
					<a href="signin.jsp" class="btn btn-default media-middle">Sign in</a>
				</div>
				<form class ="navbar-form form-inline pull-right" role="form" action="/ProjectFarm/LoginServlet" method="post">
						<input type="email" name="login" placeholder="Login">
						<input type="password" name="password" placeholder="password">
						<button type="submit"  class="btn btn-default media-middle">Login</button>
				</form>
				
			</div>
		<%}else if (UserDB.getOwner(login) != null){ %>
			<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
		    	<ul class="nav navbar-nav navbar-right">
		        	<li class="dropdown">
		          		<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false"><%=session.getAttribute("login") %><span class="caret"></span></a>
		          		<ul class="dropdown-menu" role="menu">
			            	<li><form name="form1" method="post"  action="/ProjectFarm/OwnerProjectServlet"><input type="hidden"><a href="javascript:document.form1.submit()">My Projects</a></form></li>
			            	<li><a href="/ProjectFarm/ListOfCategory">New project</a></li>
			            	<li><a href="/ProjectFarm/LogoutServlet">Logout</a></li>
		          		</ul>
		        	</li>
	      		</ul>
		    </div>
		<%} else if (UserDB.getEvaluator(login) != null){ %>
			<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
		    	<ul class="nav navbar-nav navbar-right">
		        	<li class="dropdown">
		          		<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false"><%=session.getAttribute("login") %><span class="caret"></span></a>
		          		<ul class="dropdown-menu" role="menu">
			            	<li><form name="form_all_project" method="post"  action="/ProjectFarm/AllProjectServlet"><input type="hidden"><a href="javascript:document.form_all_project.submit()">All Projects</a></form></li>
			            	<li><a href="signinEvaluator.jsp">Add Evaluator</a></li>
			            	<li><a href="addCategory.jsp">Add Category</a></li>
			            	<li><a href="/ProjectFarm/LogoutServlet">Logout</a></li>
		          		</ul>
		        	</li>
	      		</ul>
		    </div>
		<%} %>
	</div>
	
<div class="container">

<!-- container, body and HTML tags are still opened -->