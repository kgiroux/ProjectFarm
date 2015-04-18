package model.db;
/* Modification */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.naming.NamingException;

import model.Evaluator;
import model.Owner;
import model.User;
import model.db.exception.DatabaseAccessError;
/**
 *  @author Kévin Giroux & Cyril Lefebvre
 */
public class UserDB {
	
	private static Map<String,User> users;
	private static String USER_ACCESS_DB = "Select * from user where email=? and password=?";
	private static String USER_CHECK_AVAILABLE = "Select * from user where email=?";
	private static String USER_GET_OWNER = "Select * from user where email=?";
	private static String USER_GET_EVAL = "Select * from user where email=?";
	private static String USER_GET_EVAL_BY_ID = "Select * from user where id=?";
	private static String USER_ADD_BDD = "Insert into user (email,name,password,type) values(?,?,?,?)";
	private static Connection con;
	static {
		users = new LinkedHashMap<String, User>();
	}
	
	public static boolean checkLogin(String login,String password) throws DatabaseAccessError{
		try {
			con = DbUtils.getConnection();
			PreparedStatement stmt = con.prepareStatement(USER_ACCESS_DB);
			stmt.setString(1, login);
			stmt.setString(2, password);
			ResultSet rs =stmt.executeQuery();
			while(rs.next()){
				if(!rs.getString("email").equals("") && rs.getString("email") !=null){
					if(rs.getString("type").equals("Owner")){
						users.put(rs.getString("email"), new Owner(login, rs.getString("name"), password,rs.getInt("id")));
					}else{
						users.put(rs.getString("email"), new Evaluator(login, rs.getString("name"), password,rs.getInt("id")));
					}
					System.out.println("User ok");
					return true;
				}else{
					return false;
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
				System.out.println("End of checking user");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
		
	}
	
	public static boolean checkLoginAvailable(String login) throws DatabaseAccessError{
		try {
			con = DbUtils.getConnection();
			PreparedStatement stmt = con.prepareStatement(USER_CHECK_AVAILABLE);
			stmt.setString(1, login);
			ResultSet rs =stmt.executeQuery();
			while(rs.next()){
				if(!rs.getString("email").equals("") && rs.getString("email") !=null){
					System.out.println("User ok");
					return true;
				}else{
					return false;
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
				System.out.println("End of checking user");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
		
	}
	
	
	public static User getUser(String login) throws DatabaseAccessError {
		User u = getOwner(login);
		if(u == null) {
			u = getEvaluator(login);
		}
		return u;
	}
	
	public static Owner getOwner(String login) throws DatabaseAccessError{
		Owner ow = null;
		try {
			con = DbUtils.getConnection();
			PreparedStatement stmt = con.prepareStatement(USER_GET_OWNER);
			stmt.setString(1, login);
			ResultSet rs =stmt.executeQuery();
			while(rs.next()){
				if(!rs.getString("email").equals("") && rs.getString("email") !=null){
					if(rs.getString("type").equals("Owner")){
						ow = new Owner(login, rs.getString("name"), rs.getString("password"),rs.getInt("id"));
					}
				}else{
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
				System.out.println("End of getting owner");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return ow;
	}
	
	public static Evaluator getEvaluator(String login) throws DatabaseAccessError {
		
		Evaluator ev = null;
		try {
			con = DbUtils.getConnection();
			PreparedStatement stmt = con.prepareStatement(USER_GET_EVAL);
			stmt.setString(1, login);
			ResultSet rs =stmt.executeQuery();
			while(rs.next()){
				if(!rs.getString("email").equals("") && rs.getString("email") !=null){
					if(rs.getString("type").equals("Evaluator")){
						ev = new Evaluator(login, rs.getString("name"), rs.getString("password"),rs.getInt("id"));
					}
				}else{
				}			
			}
			System.out.println("Getting evaluator");
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
				System.out.println("End of getting evaluator");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return ev;		
	}
	
	public static Evaluator getEvaluator(int id) throws DatabaseAccessError {
		
		Evaluator ev = null;
		try {
			con = DbUtils.getConnection();
			PreparedStatement stmt = con.prepareStatement(USER_GET_EVAL_BY_ID);
			stmt.setInt(1, id);
			ResultSet rs =stmt.executeQuery();
			while(rs.next()){
				if(!rs.getString("email").equals("") && rs.getString("email") !=null){
					if(rs.getString("type").equals("Evaluator")){
						ev = new Evaluator(rs.getString("email"), rs.getString("name"), rs.getString("password"),id);
					}
				}else{
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
				System.out.println("End of getting evaluator");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return ev;		
	}
	
	public static void addUser(User u) throws DatabaseAccessError {
		try {
			con = DbUtils.getConnection();
			PreparedStatement stmt = con.prepareStatement(USER_ADD_BDD);
			stmt.setString(1, u.getEmail());
			stmt.setString(2, u.getName());
			stmt.setString(3, u.getPassword());
			stmt.setString(4, "Owner");
			stmt.executeUpdate();	
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
				System.out.println("End of adding owner");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void addEvaluator(User u) throws DatabaseAccessError {
		try {
			con = DbUtils.getConnection();
			PreparedStatement stmt = con.prepareStatement(USER_ADD_BDD);
			stmt.setString(1, u.getEmail());
			stmt.setString(2, u.getName());
			stmt.setString(3, u.getPassword());
			stmt.setString(4, "Evaluator");
			stmt.executeUpdate();	
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
				System.out.println("End of adding evaluator");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
