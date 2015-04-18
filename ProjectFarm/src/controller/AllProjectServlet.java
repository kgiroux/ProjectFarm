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

import model.Evaluator;
import model.Project;
import model.db.EvaluationDB;
import model.db.ProjectDB;
import model.db.UserDB;
import model.db.exception.DatabaseAccessError;
import fr.esigelec.utils.HTMLUtils;
/** 
 * 
 *  @author kevin et Cyril
 * 
 */
/**
 * Servlet implementation class AllProjectServlet
 */
@WebServlet("/AllProjectServlet")
public class AllProjectServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest req, HttpServletResponse resp)
	 */
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		PrintWriter out = resp.getWriter();
		
		HttpSession session = req.getSession(true);
		
		String login = (String) session.getAttribute("login");
		
		try {
			Evaluator ev = UserDB.getEvaluator(login);

			if (ev != null) {
				List<Project> projectList = (List<Project>) ProjectDB.getAllProjects();
				StringBuilder sb = new StringBuilder();
				HTMLUtils.buildHeader(req,resp);
				sb.append("<table class=\"table table-striped\">");
				sb.append("<thead>");
				sb.append("<tr>");
				sb.append("<th>Acronym</th>");
				sb.append("<th>Category</th>");
				sb.append("<th># of incubation days</th>");
				sb.append("<th>Budget</th>");
				sb.append("<th># of evaluator</th>");
				sb.append("<th>Action</th>");
				sb.append("</thead>");
				sb.append("<tbody>");
				for(Project p : projectList) {
					sb.append("<tr>");
					sb.append("<td><a href=\"/ProjectFarm/ProjectDetailsServlet?id=" + p.getId() + "\">" + URLDecoder.decode(p.getAcronym(), "UTF-8") + "</a></td>");
					sb.append("<td>" + p.getCategory().getDescription() + "</td>");
					sb.append("<td>" + p.getFundingDuration() + "</td>");
					sb.append("<td>" + p.getBudget() + "</td>");
					sb.append("<td>" + EvaluationDB.getEvaluation_id(p.getId()).size() + "</td>");
					sb.append("<td><a href=\"/ProjectFarm/ProjectDetailsServlet?id=" + p.getId()  + "\">Evaluate</a></td>");
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
				out.println("Any evaluator connected !");
				req.getRequestDispatcher("/index.jsp").forward(req, resp);
			}
				
		} catch (DatabaseAccessError e) {
			out.println("Sorry: " + e.getMessage());
		}
	}
}
