package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Evaluator;
import model.Owner;
import model.Project;
import model.db.ProjectDB;
import model.db.UserDB;
import model.db.exception.DatabaseAccessError;
import model.exception.InvalidDataException;
/**
 *  @author Kévin Giroux & Cyril Lefebvre
 */
@WebServlet("/ProjectDetailsServlet")
public class ProjectDetailsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PrintWriter out = resp.getWriter();
		boolean access_auth = false;
		int id = Integer.parseInt(req.getParameter("id"));
		
		HttpSession session = req.getSession(true);
		
		String login = (String) session.getAttribute("login");
		if(login != null){
			try {
				Owner ow = UserDB.getOwner(login);
				Evaluator eval = UserDB.getEvaluator(login);
				Project p = ProjectDB.getProject(id);
				if(ow != null){
					if(p.getOwner().getId() == ow.getId()){
						access_auth = true;
					}
				}else if(eval != null){
					access_auth = true;
				}
				
				
				if(access_auth){ 	
					try {
						p.setDescription(URLDecoder.decode(p.getDescription(),"UTF-8"));
						p.setAcronym(URLDecoder.decode(p.getAcronym(), "UTF-8"));
					} catch (InvalidDataException e) {
						e.printStackTrace();
					}
					if(p != null){
						req.setAttribute("project", p);
						if (UserDB.getOwner(login) != null)
							req.getRequestDispatcher("/projectOwnerConsult.jsp").forward(req, resp);
						else if (UserDB.getEvaluator(login) != null)
							req.getRequestDispatcher("/projectEvaluatorConsult.jsp").forward(req, resp);
						else
							resp.sendError(403, "You are not allowed to access to this page");	
					}
				}else{
					req.getRequestDispatcher("/index.jsp").forward(req, resp);
				}
			} catch (DatabaseAccessError e) {
				out.println("Sorry: " + e.getMessage());
			}
		}else{
			req.getRequestDispatcher("/index.jsp").forward(req, resp);
		}
		
	}
}
