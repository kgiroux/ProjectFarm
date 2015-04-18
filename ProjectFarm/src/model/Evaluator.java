package model;
/* Modification */
/**
 *  @author Kévin Giroux & Cyril Lefebvre
 */
public class Evaluator extends User {

	private static final long serialVersionUID = 5349999513714780361L;

	public Evaluator(String email, String name, String password, int id) {
		super(email, name, password,id);
	}
	
	public Evaluator(String email, String name, String password) {
		super(email, name, password);
	}
}
