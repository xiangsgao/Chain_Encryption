package Application.logic;

public class cryptoException extends Exception {
	 
	private static final long serialVersionUID = 1L;

	public cryptoException() {
    }
 
    public cryptoException(String message, Throwable throwable) {
        super(message, throwable);
    }
}