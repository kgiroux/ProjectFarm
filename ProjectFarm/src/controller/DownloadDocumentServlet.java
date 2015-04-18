package controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Document;
import model.db.DocumentDB;
/**
 *  @author Kévin Giroux & Cyril Lefebvre
 */
/**
 * Servlet implementation class DownloadDocumentServlet
 */
@WebServlet("/DownloadDocumentServlet")
public class DownloadDocumentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Document d = DocumentDB.getDocument(Integer.parseInt(request.getParameter("document_id")));

		HttpSession session = request.getSession();
		String login = (String) session.getAttribute("login");
		
		if(login != null){
			InputStream input;
			int fileLenght = 0;
			try {
				input = d.getFile().getBinaryStream();
				fileLenght = input.available();
				
				response.setContentType(d.getType());
				response.setContentLength(fileLenght);
				String headerKey = "Content-Disposition";
				String headerValue = String.format("attachment; filename=\"%s\"", d.getDocumentPath());
				
				response.setHeader(headerKey, headerValue);
				
				OutputStream outStream = response.getOutputStream();
				
				byte[] buffer = new byte[4096];
				int bytesRead = -1;
				
				while ((bytesRead = input.read(buffer)) != -1) {
					outStream.write(buffer, 0, bytesRead);
				}
				
				input.close();
				outStream.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}else{
			request.getRequestDispatcher("/index.jsp").forward(request, response);
		}
		
		
	}
}
