package kr.co.poscoict.push.common.exception;

public class EmailFailException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5938253984743988762L;

	public EmailFailException() {
		super();
	}

	public EmailFailException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public EmailFailException(String message, Throwable cause) {
		super(message, cause);
	}

	public EmailFailException(String message) {
		super(message);
	}

	public EmailFailException(Throwable cause) {
		super(cause);
	}
}
