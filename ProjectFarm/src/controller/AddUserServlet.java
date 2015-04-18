package controller;
/*Modification */
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Owner;
import model.db.UserDB;
import model.db.exception.DatabaseAccessError;
/**
 *  @author Kévin Giroux & Cyril Lefebvre
 */
@WebServlet("/AddUserServlet")
public class AddUserServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		boolean error =false;
		String login = req.getParameter("login");
		
		HttpSession session = req.getSession();
		String login_user = (String) session.getAttribute("login");
		if(login_user != null){
			try {
				if(!UserDB.checkLoginAvailable(login)){
					String name = req.getParameter("name");
					String password = req.getParameter("password1");
					String passwordconf = req.getParameter("password2");
					if(password.equals(passwordconf)){
						Owner ow = new Owner(login, name, passwordconf);
						UserDB.addUser(ow);
					}else{
						error = true;
						req.setAttribute("message_password", "Wrong password");
					}
				}else{
					error = true;
					req.setAttribute("message_login", "login already use");
				}
			} catch (DatabaseAccessError e) {
				e.printStackTrace();
			} finally{
				if(error){
					req.getRequestDispatcher("/signin.jsp").forward(req, resp);
				}else{
					req.getRequestDispatcher("/index.jsp").forward(req, resp);
				}
				
			}
		}else{
			req.getRequestDispatcher("/index.jsp").forward(req, resp);
		}
		
		
		
		
		
		
	}
}