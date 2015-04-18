package model;
/* Modification */
import java.io.Serializable;
/**
 *  @author K�vin Giroux & Cyril Lefebvre
 */
public abstract class User implements Serializable {

	private static final long serialVersionUID = -5822209320482420372L;

	private String email;
	private String name;
	private int id; 
	private String password;

	public User(String email, String name, String password, int id) {
		setEmail(email);
		setName(name);
		setPassword(password);
		setId(id);
	}

	public User(String email, String name, String password) {
		setEmail(email);
		setName(name);
		setPassword(password);
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		return true;
	}

}
