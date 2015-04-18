package controller;
/*Modification */
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Evaluation;
import model.Evaluator;
import model.Project;
import model.db.EvaluationDB;
import model.db.ProjectDB;
import model.db.UserDB;
import model.db.exception.DatabaseAccessError;
import model.exception.InvalidDataException;

/**
 *  @author Kévin Giroux & Cyril Lefebvre
 */


@WebServlet("/AddEvaluationServlet")

public class AddEvaluationServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		PrintWriter out = resp.getWriter();
		
		HttpSession session = req.getSession(true);
		int id = Integer.parseInt(req.getParameter("id"));
		String login = (String) session.getAttribute("login");
		Project p;
		Evaluator ev;
		try {
			ev = UserDB.getEvaluator(login);
			if( ev!= null){
				try {
					p = ProjectDB.getProject(id);
					if(p != null){
						int attractiveness = Integer.parseInt(req.getParameter("attractiveness"));
						int riskLevel = Integer.parseInt(req.getParameter("riskLevel"));
						try {
							EvaluationDB.saveEvaluation(new Evaluation(UserDB.getEvaluator(login), attractiveness, riskLevel,p.getId()));
						} catch (InvalidDataException | DatabaseAccessError e1) {
							e1.printStackTrace();
						}
						req.setAttribute("project", p);
						System.out.println(p.getDescription());
					}else{
						out.println("Error");
					}
				} catch (DatabaseAccessError e1) {
					e1.printStackTrace();
				} finally{
					out.println("It is the end of this Servlet");
					req.getRequestDispatcher("/projectEvaluatorConsult.jsp").forward(req, resp);
				}
			}else{
				req.getRequestDispatcher("/index.jsp").forward(req, resp);
			}
		} catch (DatabaseAccessError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}