package authserver.exceptions;

public class NoBasicAuthenticationException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public NoBasicAuthenticationException() {
		super();
	}
	
	public NoBasicAuthenticationException(String msg) {
		super(msg);
	}
}
