package model.db;
/* Modification */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;

import model.Category;
import model.Evaluation;
import model.Owner;
import model.Project;
import model.db.exception.DatabaseAccessError;
import model.exception.InvalidDataException;
/**
 *  @author Kévin Giroux & Cyril Lefebvre
 */
public class ProjectDB {
	private static String PROJECT_DB_ALL = "Select * from project";
	private static String PROJECT_USER= "Select * from user where id=?";
	private static String PROJECT_CATEGORIE= "Select * from category where id=?";
	private static String PROJECT_DB_OWNER_ID = "Select * from project where owner_id=?";
	private static String PROJECT_ADD = "Insert into project (acronym,description,fundingDuration,budget,date,owner_id,category_id) values(?,?,?,?,?,?,?)";
	private static String PROJECT_ID = "Select * from project where id=?";
	private static Connection con;
	static {
	}

	public static void saveProject(Project project) throws DatabaseAccessError {
		
		
		if(project.getOwner() != null){
			Project p = getProject(project.getId());
			if(p == null){
				try {
					con = DbUtils.getConnection();
					PreparedStatement stmt = con.prepareStatement(PROJECT_ADD);
					stmt.setString(1, project.getAcronym());
					stmt.setString(2, project.getDescription());
					stmt.setInt(3, project.getFundingDuration());
					stmt.setDouble(4, project.getBudget());
					stmt.setString(5, project.getCreated());
					stmt.setInt(6, project.getOwner().getId());
					stmt.setInt(7, project.getCategory().getId());
					stmt.executeUpdate();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
				} catch (NamingException e) {
					e.printStackTrace();
				} finally {
					try {
						DbUtils.dropConnection(con);
						System.out.println("End of adding project");
					} catch (SQLException e) {
						e.printStackTrace();
					}finally{
						System.out.println("Connection closed");
					}
				}
			}
		}
		
	}

	public static Project getProject(int id) throws DatabaseAccessError {
		Project p = null;
		try {
			con = DbUtils.getConnection();
			PreparedStatement stmt = con.prepareStatement(PROJECT_ID);
			stmt.setInt(1, id);
			ResultSet rs =stmt.executeQuery();
			while(rs.next()){
				PreparedStatement stmt1 = con.prepareStatement(PROJECT_CATEGORIE);
				stmt1.setInt(1, rs.getInt("category_id"));
				ResultSet rs1 =stmt1.executeQuery();
				Category c = null;
				while(rs1.next()){
					c = new Category(rs1.getString("description"),rs1.getInt("id"));
				}
				
				stmt1 = con.prepareStatement(PROJECT_USER);
				stmt1.setInt(1, rs.getInt("owner_id"));
				rs1 =stmt1.executeQuery();
				Owner ow = null;
				while(rs1.next()){
					ow = new Owner(rs1.getString("email"),rs1.getString("name"),rs1.getString("password"), rs1.getInt("id"));
				}
				try {
					p = new Project(rs.getString("acronym"),rs.getString("description"),rs.getInt("fundingDuration"),rs.getDouble("budget"),rs.getString("date"),ow,c,rs.getInt("id"));
				} catch (InvalidDataException e) {
					e.printStackTrace();
				} finally{
					System.out.println("End of getting project");
				}
			}
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NamingException e) {
			e.printStackTrace();
		} finally {
			try {
				DbUtils.dropConnection(con);
				System.out.println("End of getting project");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return p;
	}

	public static List<Project> getProjectsOfOwner(Owner owner) throws DatabaseAccessError {

		List<Project> projectsOfOwner = new ArrayList<Project>();
		
		try {
			con = DbUtils.getConnection();
			PreparedStatement stmt = con.prepareStatement(PROJECT_DB_OWNER_ID);
			stmt.setInt(1, owner.getId());
			ResultSet rs =stmt.executeQuery();
			while(rs.next()){
				PreparedStatement stmt1 = con.prepareStatement(PROJECT_CATEGORIE);
				stmt1.setInt(1, rs.getInt("category_id"));
				ResultSet rs1 =stmt1.executeQuery();
				Category c = null;
				while(rs1.next()){
					c = new Category(rs1.getString("description"),rs1.getInt("id"));
				}
				try {
					Project p = new Project(rs.getString("acronym"),rs.getString("description"),rs.getInt("fundingDuration"),rs.getDouble("budget"),rs.getString("date"),owner,c,rs.getInt("id"));
					List<Evaluation> list = EvaluationDB.getEvaluation_id(rs.getInt("id"));
					for(Evaluation e : list){
						p.addEvaluation(e);
					}
					projectsOfOwner.add(p);
				} catch (InvalidDataException e) {
					e.printStackTrace();
				} finally{
					System.out.println("End of adding project list");
				}
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NamingException e) {
			e.printStackTrace();
		} finally {
			try {
				DbUtils.dropConnection(con);
				System.out.println("End of research project");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return projectsOfOwner;

	}
	
	public static List<Project> getAllProjects() throws DatabaseAccessError {
		List<Project> allProjects = new ArrayList<Project>();
		try {
			con = DbUtils.getConnection();
			PreparedStatement stmt = con.prepareStatement(PROJECT_DB_ALL);
			ResultSet rs =stmt.executeQuery();
			PreparedStatement stmt1;
			while(rs.next()){
				stmt1 = con.prepareStatement(PROJECT_USER);
				stmt1.setInt(1, rs.getInt("owner_id"));
				ResultSet rs1 =stmt1.executeQuery();
				Owner ow = null;
				while(rs1.next()){
					ow = new Owner(rs1.getString("email"),rs1.getString("name"),rs1.getString("password"), rs1.getInt("id"));
				}
				stmt1 = con.prepareStatement(PROJECT_CATEGORIE);
				stmt1.setInt(1, rs.getInt("category_id"));
				rs1 = stmt1.executeQuery();
				Category c = null;
				while(rs1.next()){
					c = new Category(rs1.getString("description"),rs1.getInt("id"));
				}
				if(ow != null && c != null){
					try {
						allProjects.add(new Project(rs.getString("acronym"),rs.getString("description"),rs.getInt("fundingDuration"),rs.getDouble("budget"),rs.getString("date"),ow,c,rs.getInt("id")));
					} catch (InvalidDataException e) {
						e.printStackTrace();
					} finally{
						System.out.println("Add project ending");
					}
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
				System.out.println("End of getting all project");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return allProjects;
	}
	
}
