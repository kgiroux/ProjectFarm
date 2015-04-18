package controller;
/**
 *  @author Kévin Giroux & Cyril Lefebvre
 */

/* Modification */
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.db.UserDB;
import model.db.exception.DatabaseAccessError;


@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

	private static final long serialVersionUID = 3311297485258766639L;
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		PrintWriter out = resp.getWriter();
		String login = req.getParameter("login");
		String password = req.getParameter("password");
		//password = URLEncoder.encode(password, "UTF-8");
		boolean loginOk;
		try {
			loginOk = UserDB.checkLogin(login, password);
			if (loginOk) {
				out.println("ok");
				HttpSession session = req.getSession(true);
				session.setAttribute("login", login);
			} else
				out.println("nok");
				req.setAttribute("message", "Wrong login/password");
		} catch (DatabaseAccessError e) {
			out.println("Sorry: " + e.getMessage());
		} finally{
			req.getRequestDispatcher("/index.jsp").forward(req, resp);
		}
	}
}
