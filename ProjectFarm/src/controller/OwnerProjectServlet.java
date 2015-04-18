package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.esigelec.utils.HTMLUtils;
import model.Evaluation;
import model.Owner;
import model.Project;
import model.db.EvaluationDB;
import model.db.ProjectDB;
import model.db.UserDB;
import model.db.exception.DatabaseAccessError;
/**
 *  @author Kévin Giroux & Cyril Lefebvre
 */
/**
 * Servlet implementation class ListProjectServlet
 */
@WebServlet("/OwnerProjectServlet")
public class OwnerProjectServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		PrintWriter out = resp.getWriter();
		
		HttpSession session = req.getSession(true);
		
		String login = (String) session.getAttribute("login");
		
		try {
			Owner ow = UserDB.getOwner(login);
			if (ow != null) {
				List<Project> projectList = (List<Project>) ProjectDB.getProjectsOfOwner(ow);
				StringBuilder sb = new StringBuilder();
				HTMLUtils.buildHeader(req,resp);
				sb.append("<table class=\"table table-striped\">");
				sb.append("<thead>");
				sb.append("<tr>");
				sb.append("<th>Acronym</th>");
				sb.append("<th>Category</th>");
				sb.append("<th># of incubation days</th>");
				sb.append("<th>Budget</th>");
				sb.append("<th>Risk level</th>");
				sb.append("<th>Atractiveness</th>");
				sb.append("<th># of evaluator</th>");
				sb.append("</thead>");
				sb.append("<tbody>");
				for(Project p : projectList) {
					sb.append("<tr>");
					sb.append("<td><a href=\"/ProjectFarm/ProjectDetailsServlet?id=" + p.getId() + "\">" + URLDecoder.decode(p.getAcronym(), "UTF-8") + "</a></td>");
					sb.append("<td>" + p.getCategory().getDescription() + "</td>");
					sb.append("<td>" + p.getFundingDuration() + "</td>");
					sb.append("<td>" + p.getBudget() + "</td>");
					
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
					if(size> 0){
						if(0<=risk && risk<=2)
							sb.append("<td class=\"success\">" + risk + "</td>");
						else if(2<risk && risk<=4)
							sb.append("<td class=\"warning\">" + risk + "</td>");
						else
							sb.append("<td class=\"danger\">" + risk + "</td>");
						
						if(0<=atract && atract<=2)
							sb.append("<td class=\"danger\">" + atract + "</td>");
						else if(2<atract && atract<=4)
							sb.append("<td class=\"warning\">" + atract + "</td>");
						else 
							sb.append("<td class=\"success\">" + atract + "</td>");
					}else{
						sb.append("<td>" + risk + "</td>");
						sb.append("<td>" + atract + "</td>");
					}

					sb.append("<td>" + size + "</td>");
					sb.append("</tr>");
				}
				sb.append("</tbody>");
				sb.append("</table>");
				
				resp.setContentType("text/html");
				out.println(sb.toString());
				HTMLUtils.buildFooter(req,resp);
				System.out.println("Well done !");
			}
			else{
				out.println("Any owner connected !");
				req.getRequestDispatcher("/index.jsp").forward(req, resp);
			}
				
		} catch (DatabaseAccessError e) {
			out.println("Sorry: " + e.getMessage());
		}
	}
}
