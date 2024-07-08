package authserver.exceptions;

public class BadInfoException extends Exception {

	private static final long serialVersionUID = 237717497227445643L;
	
	public BadInfoException() {
		super();
	}
	
	public BadInfoException(String msg) {
		super(msg);
	}
}
