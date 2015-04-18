<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<jsp:include page="/utils/header.jsp">
	<jsp:param name="title" value="ProjectFarm" />
	<jsp:param name="page" value="/index.jsp" />
</jsp:include>
<div class="jumbotron">
	<% if(session.getAttribute("login") == null){ %>
	<div class="row">
		<div class="col-lg-3">
		
		</div>
		<div class="col-lg-6">
			<form role="form" method="post" action="/ProjectFarm/AddUserServlet">
			<%if(request.getAttribute("message_password") != null){%>
          			<div class="text-danger">
          				<%=request.getAttribute("message_password")%>
          			</div>
          		<%} else if(request.getAttribute("message_login") != null){%>
          				<div class="text-danger">
          				<%=request.getAttribute("message_login")%>
          			</div>
          		<% } %>	
				<div class="row">
					<div class="col-lg-5">
							<label for="login">Login :</label>
					</div>
					<div class="col-lg-6">
							<input type="email" name="login" placeholder="email">
					</div>
				</div>
				<div class="row">
						<div class="col-lg-5">
							<label for="name">Name : </label>
						</div>	
						<div class="col-lg-6">
							<input type="text" name="name" placeholder="Your name">
						</div>
				</div>
				<div class="row">
						<div class="col-lg-5">
							<label for="password1">Password : </label>
						</div>	
						<div class="col-lg-6">
							<input type="password" name="password1" placeholder="Password">
						</div>
				</div>
				<div class="row">
						<div class="col-lg-5">
							<label for="password2">Confirm your Password : </label>
						</div>	
						<div class="col-lg-6">
							<input type="password" name="password2" placeholder="Confirm your Password">
						</div>
				</div>
				<button type="submit"  class="btn btn-default btn-lg">Save</button>
			</form>
		</div>
	</div>
	<%} else { %>
	
	<%} %>
</div>

<jsp:include page="/utils/footer.jsp" />