package fr.esigelec.utils;
/**
 *  @author Kévin Giroux & Cyril Lefebvre
 */
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HTMLUtils {

	public static void buildHeader(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		RequestDispatcher disp = req.getRequestDispatcher("/utils/header.jsp" + "?title=ProjectFarm");
		disp.include(req, resp);
	}

	public static void buildFooter(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RequestDispatcher disp = req.getRequestDispatcher("/utils/footer.jsp");
		disp.include(req, resp);
	}
}
