package kr.co.poscoict.card.common.except;

public class CardBusinessException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8234539722850866537L;

	public CardBusinessException() {
		super();
	}

	public CardBusinessException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public CardBusinessException(String message, Throwable cause) {
		super(message, cause);
	}

	public CardBusinessException(String message) {
		super(message);
	}

	public CardBusinessException(Throwable cause) {
		super(cause);
	}
}
