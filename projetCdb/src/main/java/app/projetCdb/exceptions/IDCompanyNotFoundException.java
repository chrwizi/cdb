package app.projetCdb.exceptions;

public class IDCompanyNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public IDCompanyNotFoundException() {
		super();
	}

	public IDCompanyNotFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public IDCompanyNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public IDCompanyNotFoundException(String message) {
		super(message);
	}

	public IDCompanyNotFoundException(Throwable cause) {
		super(cause);
	}

}
