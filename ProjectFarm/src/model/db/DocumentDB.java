package model.db;

import java.io.ByteArrayInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;

import model.Document;
import model.exception.InvalidDataException;
/**
 *  @author Kévin Giroux & Cyril Lefebvre
 */
public class DocumentDB {
	private static Connection con = null;
	
	private static String ADD_DOCUMENT_PROJECT ="Insert into documents (project_id,files,documentPath,added,type) values (?,?,?,?,?)";
	private static String SELECT_DOCUMENT ="Select * from documents where id = ?";
	private static String SELECT_DOCUMENT_PROJECT ="Select * from documents where project_id = ?";
	
	public static void saveDocument(int id_project, byte[] document,String path,String type){
		
		try {
			con = DbUtils.getConnection();
			ByteArrayInputStream bis = new ByteArrayInputStream(document);
			
			PreparedStatement stmt = con.prepareStatement(ADD_DOCUMENT_PROJECT);
			stmt.setInt(1,id_project);
			stmt.setBinaryStream(2, bis, document.length);
			stmt.setString(3, path);
			stmt.setDate(4, new java.sql.Date(new java.util.Date().getTime()));
			stmt.setString(5, type);
			stmt.executeUpdate();
			
		} catch (ClassNotFoundException | SQLException | NamingException e) {
			e.printStackTrace();
		} finally {
			try {
				DbUtils.dropConnection(con);
				System.out.println("End of saving document");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static Document getDocument(int id_doc){
		Document doc = null;
		try {
			con = DbUtils.getConnection();
			PreparedStatement stmt = con.prepareStatement(SELECT_DOCUMENT);
			stmt.setInt(1, id_doc);
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()){
				try {
					doc = new Document(rs.getInt("id"), rs.getBlob("files"), rs.getString("documentPath"), rs.getString("type"), rs.getDate("added"));
				} catch (InvalidDataException e) {
					e.printStackTrace();
				}
			}
		
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NamingException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			System.out.println(e.getExplanation());
			
		} finally {
			try {
				DbUtils.dropConnection(con);
				System.out.println("End of getting document");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return doc;
	}
	
	public static List<Document> getDocumentList(int id_project){
		List<Document> docs = new ArrayList<Document>();
		try {
			con = DbUtils.getConnection();
			PreparedStatement stmt = con.prepareStatement(SELECT_DOCUMENT_PROJECT);
			stmt.setInt(1, id_project);
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()){
				try {
					docs.add(new Document(rs.getInt("id"), rs.getBlob("files"), rs.getString("documentPath"), rs.getString("type"), rs.getDate("added")));
				} catch (InvalidDataException e) {
					e.printStackTrace();
				} finally{
					System.out.println("End of getting document");
				}
			}
		
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NamingException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			System.out.println(e.getExplanation());
			
		} finally {
			try {
				DbUtils.dropConnection(con);
				System.out.println("End of getting Docs");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return docs;
	}
}
