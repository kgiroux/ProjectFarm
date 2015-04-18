package model.db.exception;

public class DatabaseAccessError extends Exception {

	private static final long serialVersionUID = 9068396024331602041L;
	
	public DatabaseAccessError(String error) {
		super(error);
	}
	
	public DatabaseAccessError(String error, Throwable cause) {
		super(error,cause);
	}	

}
