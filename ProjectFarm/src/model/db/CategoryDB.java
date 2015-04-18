package model.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.naming.NamingException;

import model.Category;
import model.db.exception.DatabaseAccessError;
/**
 *  @author Kévin Giroux & Cyril Lefebvre
 */
public class CategoryDB {
	
	private static Map<String,Category> categories;
	private static Connection con = null;
	private static String SELECT_CAT_BY_NAME = "SELECT * FROM category where description=?";
	private static String SELECT_ALL_CAT = "SELECT * FROM category";
	private static String ADD_CAT = "Insert into category (description) values (?)";
	
	static {
		categories = new LinkedHashMap<>();
	}
	
	public static List<Category> getCategories() throws DatabaseAccessError {
		categories = new LinkedHashMap<>();
		try {
			con = DbUtils.getConnection();
			PreparedStatement stmt = con.prepareStatement(SELECT_ALL_CAT);
			ResultSet rs =stmt.executeQuery();
			while(rs.next()){
				System.out.println(rs.getString("description"));
				categories.put(rs.getString("description"), new Category(rs.getString("description"),rs.getInt("id")));
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
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		return new LinkedList<Category>(categories.values());
	}
	
	public static Category getCategory(String name) {
		Category c = null;
		try {
			con = DbUtils.getConnection();
			PreparedStatement stmt = con.prepareStatement(SELECT_CAT_BY_NAME);
			stmt.setString(1, name);
			ResultSet rs =stmt.executeQuery();
			while(rs.next()){
				c = new Category(rs.getString("description"),rs.getInt("id"));
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
				System.out.println("End of getting Category");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return c;
	}
	
	public static boolean checkCategory(String category){
		boolean return_bool = false;
		Category c = getCategory(category);
		if(c== null){
			try {
				con = DbUtils.getConnection();
				PreparedStatement stmt = con.prepareStatement(ADD_CAT);
				stmt.setString(1, category);
				stmt.executeUpdate();
				return_bool = true;
			} catch (ClassNotFoundException | SQLException | NamingException e) {
				e.printStackTrace();
			}finally{
				try {
					DbUtils.dropConnection(con);
				} catch (SQLException e) {
					e.printStackTrace();
				} finally{
					System.out.println("End of adding category");
				}
			}
		}
		
		return return_bool;
		
	}
}
