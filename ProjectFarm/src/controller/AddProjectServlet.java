package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Owner;
import model.Project;
import model.db.CategoryDB;
import model.db.ProjectDB;
import model.db.UserDB;
import model.db.exception.DatabaseAccessError;
import model.exception.InvalidDataException;

import org.apache.commons.lang3.StringEscapeUtils;
/**
 *  @author Kévin Giroux & Cyril Lefebvre
 */
/**
 * Servlet implementation class AddProjectServlet
 */
@WebServlet("/AddProjectServlet")
public class AddProjectServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	/**
	 * @see HttpServlet#doPost(HttpServletRequest req, HttpServletResponse resp)
	 */
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		PrintWriter out = resp.getWriter();
		
		HttpSession session = req.getSession(true);
		
		String login = (String) session.getAttribute("login");
		
		String acronym = req.getParameter("acronym");
		acronym = StringEscapeUtils.escapeHtml4(acronym);
		String description = req.getParameter("description");
		description = StringEscapeUtils.escapeHtml4(description);
		int fundingDuration = Integer.parseInt(req.getParameter("fundingDuration"));
		double budget = Double.parseDouble(req.getParameter("budget"));
		String catName = req.getParameter("catName");
		
		try {
			Owner ow = UserDB.getOwner(login);
			if(ow != null){
				try {
					if(login != null) {
						Date date = new Date();
						SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm");
						ProjectDB.saveProject(new Project(acronym, description, fundingDuration, budget,sdf.format(date), UserDB.getOwner(login), CategoryDB.getCategory(catName)));
						out.println("ok");
						req.getRequestDispatcher("/OwnerProjectServlet").forward(req, resp);
					}
					else
						out.println("Any user connected !");
				} catch (DatabaseAccessError | InvalidDataException e) {
					out.println("Sorry: " + e.getMessage());
				}
			}else{
				req.getRequestDispatcher("/index.jsp").forward(req, resp);
			}
		} catch (DatabaseAccessError e1) {
			e1.printStackTrace();
		}
		
	}
}
