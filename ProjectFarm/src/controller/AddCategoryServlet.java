package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Evaluator;
import model.db.CategoryDB;
import model.db.UserDB;
import model.db.exception.DatabaseAccessError;

/**
 *  @author Kévin Giroux & Cyril Lefebvre
 */

@WebServlet("/AddCategory")
public class AddCategoryServlet extends HttpServlet {

	
	private static final long serialVersionUID = 1L;
	
	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
			String category = req.getParameter("category_name");
			HttpSession session = req.getSession();
			String login = (String) session.getAttribute("login");
			Evaluator ev;
			try {
				ev = UserDB.getEvaluator(login);
				if(ev != null){
					boolean b = CategoryDB.checkCategory(category);
					if(b == false){
						req.setAttribute("error_cat","Wrong name category");
					}
					req.getRequestDispatcher("/addCategory.jsp").forward(req, resp);
				}else{
					req.getRequestDispatcher("/index.jsp").forward(req, resp);
				}
			} catch (DatabaseAccessError e) {
				e.printStackTrace();
			} finally{
				System.out.println("End of add category");
			}
			
			
			
		
	}

}
