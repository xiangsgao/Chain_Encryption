package xgao.com.text_crypt_android.logic;

public class cryptoException extends Exception {
	 
	private static final long serialVersionUID = 1L;

	public cryptoException() {
    }
	
	public cryptoException(String message) {
		super(message);
	}
 
    public cryptoException(String message, Throwable throwable) {
        super(message, throwable);
    }
}