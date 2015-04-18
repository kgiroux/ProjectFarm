package model.exception;

public class InvalidDataException extends Exception {

	private static final long serialVersionUID = 1535492498883065449L;

	public InvalidDataException(String msg) {
		super(msg);
	}
	
	public InvalidDataException(Throwable cause, String msg) {
		super(msg,cause);
	}	

}
