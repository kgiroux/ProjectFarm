<%@page import="org.apache.commons.lang3.StringEscapeUtils"%>
<%@page import="model.db.DocumentDB"%>
<%@page import="model.Document"%>
<%@page import="model.Evaluation"%>
<%@page import="model.db.EvaluationDB"%>
<%@page import="model.Project"%>
<%@page import="model.Owner"%>
<%@page import="model.db.ProjectDB"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<jsp:include page="/utils/header.jsp">
	<jsp:param name="title" value="ProjectFarm" />
	<jsp:param name="page" value="/index.jsp" />
</jsp:include>
<div class="jumbotron">
	<% if(session.getAttribute("login") == null) {%>
		<div>NOK</div>
	<% }else{
		Project p = (Project) request.getAttribute("project");
		if(p.getOwner().getEmail().equals(session.getAttribute("login"))){ %>
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
								<a href="/ProjectFarm/DownloadDocumentServlet?document_id=<%=doc.getId()%>"><%=StringEscapeUtils.escapeHtml4(doc.getDocumentPath()) %></a></br>
								<%} %>
							</div>
							<div class="col-lg-6">
							
							</div>
							<script type="text/javascript">
								function OnclickFunction(){
									document.getElementById('document').click();
									
									document.getElementById('document').onclick = function(){
										this.files = null;
									}
									
									document.getElementById('document').onchange = function(){
										console.log("Hello");
										var input = document.createElement('input');
										input.setAttribute('type',"hidden")
										input.setAttribute('name',"documentPath");
										input.setAttribute('value',this.files[0].name);
										var input2 = document.createElement('input');
										input2.setAttribute('type',"hidden")
										input2.setAttribute('name',"type");
										input2.setAttribute('value',this.files[0].type);
										
										document.form_upload.appendChild(input);
										document.form_upload.appendChild(input2);
										redirect();
									}
									
								}
								function redirect(){
									document.form_upload.submit();
								}
							</script>
							<div class="col-lg-3">		
								<button class="btn btn-default btn-block" onclick="OnclickFunction()">Upload</button>
								<form method="post" name="form_upload" action="/ProjectFarm/UploadDocumentServlet?id=<%=p.getId()%>" enctype="multipart/form-data">
									<input id="document" type="file" class="invisible" name="document">
								</form>
							</div>
							
						</div>
					</div>
					<div class="panel-group">
						<label>Statistics</label>
						<div class="row">
							<% 
								int size = EvaluationDB.getEvaluation_id(p.getId()).size();
								double risk = 0;
								double atract = 0;
								
								for (Evaluation ev : EvaluationDB.getEvaluation_id(p.getId())) {
									risk += ev.getRiskLevel();
									atract += ev.getAttractiveness();
								}
								if (size != 0){
									risk /= size;
									atract /= size;
								}
							%>
							<label class="col-lg-4">Risk Level : <%=risk %></label>
							<label class="col-lg-4">Attractiveness : <%=atract %></label>
							<label class="col-lg-4"># of evaluators : <%=size %></label>
						</div>
					</div>
				</div>
			</div>
		<% }
	} %>
</div>

<jsp:include page="/utils/footer.jsp" />