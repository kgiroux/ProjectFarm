<%@page import="model.db.DocumentDB"%>
<%@page import="model.Document"%>
<%@page import="model.Evaluation"%>
<%@page import="model.db.EvaluationDB"%>
<%@page import="model.db.UserDB"%>
<%@page import="model.Project"%>
<%@page import="model.Owner"%>
<%@page import="model.db.ProjectDB"%>
<%@page import="org.apache.commons.lang3.StringEscapeUtils"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<jsp:include page="/utils/header.jsp">
	<jsp:param name="title" value="ProjectFarm" />
	<jsp:param name="page" value="/index.jsp" />
</jsp:include>
<div class="jumbotron">
	<% if(UserDB.getEvaluator((String) session.getAttribute("login")) == null) {%>
		<div>NOK</div>
	<% }else{
		Project p = (Project) request.getAttribute("project"); %>
		<div class="panel panel-default">
			<div class="panel-heading">Project Evaluation</div>
			<div class="panel-body">
				<div class="panel-group">
					<div class="row">
						<label class="col-lg-3">Acronym : <%=StringEscapeUtils.escapeHtml4(p.getAcronym()) %></label>
						<label class="col-lg-3">Created : <%=StringEscapeUtils.escapeHtml4(p.getCreated()) %></label>
					</div>
					<label class="control-label">Description : <%=StringEscapeUtils.escapeHtml4(p.getDescription()) %></label>
					<div class="row">
						<label class="col-lg-6">Category : <%=StringEscapeUtils.escapeHtml4(p.getCategory().getDescription()) %></label>
						<div class="col-lg-6">
                			<label class="control-label" for="budget">Bugdet (EUR) : </label> 
	 						<input type="number" disabled="disabled" step="any" name="budget" value="<%=p.getBudget() %>">
                		</div>
					</div>
				</div>
				<div class="panel-group">
					<label>Documents</label>
					<div class="row">
						<div class="col-lg-3">
							<% for (Document doc : DocumentDB.getDocumentList(p.getId())) {%>
							<a href="/ProjectFarm/DownloadDocumentServlet?document_id=<%=doc.getId()%>"><%=doc.getDocumentPath() %></a>
							<%} %>
						</div>
					</div>
				</div>
				<form role="form" action="/ProjectFarm/AddEvaluationServlet" method="post">
					<div class="panel-group">
						<label>Your evaluation</label>
						<div class="row">
								<div  class="col-lg-6">
									<label for="riskLevel">Risk Level : </label>
									<input type="number" min="0" max="5" step="any" name="riskLevel" value="0">
								</div>
								<div  class="col-lg-6">
									<label for="attractiveness">Attractiveness : </label>
									<input type="number" min="0" max="5" step="any" name="attractiveness" value="0">
								</div>
						</div>
					</div>
					<input type="hidden" name="id" value=<%=p.getId() %>>
					<button type="submit"  class="btn btn-default media-middle col-lg-2">Save</button>
				</form>
				<form name="form2" method="post"  action="/ProjectFarm/AllProjectServlet"><button type="submit" class="btn btn-default media-middle col-lg-2">Discard</button></form>
			</div>
		</div>
	<%} %>
</div>

<jsp:include page="/utils/footer.jsp" />