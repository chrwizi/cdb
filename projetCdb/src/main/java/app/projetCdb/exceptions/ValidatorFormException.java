package app.projetCdb.exceptions;

public class ValidatorFormException  extends Exception{
	private ValidatorCause invalidityCause;
	private static final long serialVersionUID = 1L;

	public ValidatorFormException(String message,ValidatorCause cause) {
		super(message);
		this.setInvalidityCause(cause);
	}


	public ValidatorCause getInvalidityCause() {
		return invalidityCause; 
	}

	public void setInvalidityCause(ValidatorCause invalidityCause) {
		this.invalidityCause = invalidityCause;
	}

}
