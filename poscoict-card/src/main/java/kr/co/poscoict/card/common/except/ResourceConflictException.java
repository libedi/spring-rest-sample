package kr.co.poscoict.card.common.except;

public class ResourceConflictException extends RuntimeException {
	private static final long serialVersionUID = 363729895242134378L;
	
	public ResourceConflictException() {
        super();
    }
    public ResourceConflictException(String message, Throwable cause) {
        super(message, cause);
    }
    public ResourceConflictException(String message) {
        super(message);
    }
    public ResourceConflictException(Throwable cause) {
        super(cause);
    }
}
