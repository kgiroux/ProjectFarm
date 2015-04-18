package model;
/* Modification */
/**
 *  @author Kévin Giroux & Cyril Lefebvre
 */
public class Owner extends User {

	private static final long serialVersionUID = 5349999513714780361L;

	public Owner(String email, String name, String password, int id) {
		super(email, name, password,id);
	}
	public Owner(String email, String name, String password) {
		super(email, name, password);
	}
}
