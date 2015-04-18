package model.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;

import model.Evaluation;
import model.Evaluator;
import model.db.exception.DatabaseAccessError;
import model.exception.InvalidDataException;
/**
 *  @author Kévin Giroux & Cyril Lefebvre
 */
public class EvaluationDB {
	private static Connection con = null;
	private static String EVALUATION_BY_PROJECT_ID = "Select * from evaluation where project_id = ?";
	private static String ADD_EVALUATION = "Insert into evaluation (project_id,user_id,attractiveness,riskLevel) values (?,?,?,?)";
	
	public static void saveEvaluation(Evaluation eval) throws DatabaseAccessError {
		try {
			con = DbUtils.getConnection();
			PreparedStatement stmt = con.prepareStatement(ADD_EVALUATION);
			stmt.setInt(1, eval.getProject_id());
			stmt.setInt(2, eval.getEvaluator().getId());
			stmt.setInt(3, eval.getAttractiveness());
			stmt.setInt(4, eval.getRiskLevel());
			stmt.executeUpdate();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NamingException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			System.out.println(e.getExplanation());
			
		}  finally {
			try {
				DbUtils.dropConnection(con);
				System.out.println("End of adding Eval BDD");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static List<Evaluation> getEvaluation_id(int id) throws DatabaseAccessError{
		List<Evaluation> eval = new ArrayList<Evaluation>();
		try {
			con = DbUtils.getConnection();
			PreparedStatement stmt = con.prepareStatement(EVALUATION_BY_PROJECT_ID);
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()){
				Evaluator evaluator = UserDB.getEvaluator(rs.getInt("user_id"));
				if(evaluator != null){
					try {
						eval.add(new Evaluation(evaluator, rs.getInt("attractiveness"), rs.getInt("riskLevel"),id));
					} catch (InvalidDataException e) {
						e.printStackTrace();
					} finally{
						System.out.println("End of adding evaluator list");
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
				System.out.println("End of getting Eval");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return eval;
	}

}
