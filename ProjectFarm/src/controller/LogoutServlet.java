package controller;
/**
 *  @author Kévin Giroux & Cyril Lefebvre
 */
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/LogoutServlet")
public class LogoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		PrintWriter out = resp.getWriter();
		
		HttpSession session = req.getSession(true);
		
		String login = (String) session.getAttribute("login");
		
		if (login != null) {
			session.invalidate();
			out.println("Session closed !");
		}
		else
			out.println("There is no session to close !");
		
		req.getRequestDispatcher("/index.jsp").forward(req, resp);
	}
}
