package sloth.exception;

/**
 * Base exception class for all Sloth application-specific errors.
 * Extends Exception to provide custom error handling for task management operations.
 */
public class SlothException extends Exception {
    /**
     * Constructs a new SlothException with a helpful error message.
     *
     * @param message of error
     */
    public SlothException(String message) {
        super(message);
    }
}