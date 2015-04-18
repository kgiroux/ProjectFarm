package controller;
/**
 *  @author Kévin Giroux & Cyril Lefebvre
 */
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Owner;
import model.Project;
import model.db.DocumentDB;
import model.db.ProjectDB;
import model.db.UserDB;
import model.db.exception.DatabaseAccessError;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
@WebServlet("/UploadDocumentServlet")
public class UploadDocumentServlet extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	
	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
			HttpSession session = req.getSession();
			String login = (String) session.getAttribute("login");
			
			Owner ow;
			try {
				ow = UserDB.getOwner(login);
				if(ow != null){
					Map<String, Object> items = getMultiPartElements(req);
					
					int id = Integer.parseInt(req.getParameter("id"));
					String path = (String)items.get("documentPath");
					String type = (String)items.get("type");
					byte[] document = (byte []) items.get("document");
					try {
						Project p = ProjectDB.getProject(id);
						if(p != null){
							DocumentDB.saveDocument(p.getId(), document, path,type);
						}
						req.setAttribute("id", id);
						req.getRequestDispatcher("/ProjectDetailsServlet").forward(req, resp);
						
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
	@SuppressWarnings("unchecked")
	private Map<String, Object> getMultiPartElements(HttpServletRequest request) {

		try {
			Map<String, Object> contentMap = new LinkedHashMap<String, Object>();

			// Creates a file-based multipart
			FileItemFactory factory = new DiskFileItemFactory();

			// Create a new file upload handler
			ServletFileUpload upload = new ServletFileUpload(factory);

			List<Object> items;

			items = upload.parseRequest(request);

			@SuppressWarnings("rawtypes")
			Iterator iter = items.iterator();
			while (iter.hasNext()) {
				FileItem item = (FileItem) iter.next();
				if (item.isFormField()) {
					contentMap.put(item.getFieldName(), item.getString());
				} else {
					contentMap.put(item.getFieldName(), item.get());
				}
			}
			return contentMap;
		} catch (FileUploadException e) {
			throw new RuntimeException(e);
		}
	}
}
