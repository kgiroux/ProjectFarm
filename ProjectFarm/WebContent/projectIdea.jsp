<%@page import="java.util.List"%>
<%@page import="model.Category"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<jsp:include page="/utils/header.jsp">
	<jsp:param name="title" value="ProjectFarm" />
	<jsp:param name="page" value="/index.jsp" />
</jsp:include>
<div class="jumbotron">
	<% if(session.getAttribute("login") == null) { 
	   		response.sendError(403, "You are not allowed to access to this page");	
	   }else{  %>
	   <form role="form" action="/ProjectFarm/AddProjectServlet" method="post">
		    <div class="panel panel-default">
				<div class="panel-heading">New Project Idea</div>
				<div class="panel-body">
					 <div class="panel-group">
	                <div class="form-group">
	 						<label class="control-label" for="acronym">Title : </label>   
							<input type="text" name="acronym" placeholder="Your title">
					</div>
					<div class="form-group">
	 						<label class="control-label" for="acronym">Description : </label>   
							<textarea class="form-control pre-scrollable" rows="5" cols="130" name="description"></textarea>
					</div>
	           </div>
	           <div class="panel-group">
	                <div class="form-group">
	                	<div class="row">
	                		<div class="col-lg-4">
		                		<label class="control-label" for="catName">Category : </label> 
		 						<select name="catName">
		 							<% 	for(Category c : (List<Category>) request.getAttribute("category")) { %>
		 								<option value="<%=c.getDescription() %>"><%=c.getDescription() %></option>
		 								
		 							<% }%>
		 						</select> 
	                		</div>
	                		<div class="col-lg-4">
	                			<label class="control-label" for="budget">Bugdet (EUR) : </label> 
		 						<input type="number" step="any" name="budget" placeholder="Your Budget" value="0">
	                		</div>
	                		<div class="col-lg-4">
	                			<label class="control-label" for="fundingDuration">Funding Duration</label> 
		 						<input type="number" name="fundingDuration" placeholder="Your Budget" value="0">
	                		</div>
	                	</div>
	 						  
					</div>
	           </div>
	           <div class="row">
	           		<div class="col-lg-2">
	           			<button type="submit"  class="btn btn-default btn-lg btn-block">Save</button>
	           		</div>
	           		<div class="col-lg-2">
	           			<a class="btn btn-default btn-block btn-lg" href="./index.jsp">Discard</a>
	           		</div>
	           </div>
				</div>
			</div>
	   </form>
	<%} %>
</div>

<jsp:include page="/utils/footer.jsp" />