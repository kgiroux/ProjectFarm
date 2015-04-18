package controller;


import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Category;
import model.Owner;
import model.db.CategoryDB;
import model.db.UserDB;
import model.db.exception.DatabaseAccessError;
@WebServlet("/ListOfCategory")
/**
 *  @author Kévin Giroux & Cyril Lefebvre
 */
public class ListOfCategory extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		HttpSession session = req.getSession();
		String login = (String) session.getAttribute("login");
		
		try {
			Owner ow = UserDB.getOwner(login);
			if(ow != null){
				List<Category> category_list;
				try {
					category_list = (List<Category>) CategoryDB.getCategories();
					req.setAttribute("category", category_list);
					req.getRequestDispatcher("/projectIdea.jsp").forward(req, resp);
				} catch (DatabaseAccessError e) {
					e.printStackTrace();
				}
			}else{
				req.getRequestDispatcher("/index.jsp").forward(req, resp);
			}
			
			
		} catch (DatabaseAccessError e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		
	}
}
