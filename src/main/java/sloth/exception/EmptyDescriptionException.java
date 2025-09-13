package sloth.exception;

/**
 * Exception thrown when a command requiring a description is entered without one.
 * Provides an example of proper usage.
 */
public class EmptyDescriptionException extends SlothException {
    /**
     * Constructs a new EmptyDescriptionException with a descriptive error message.
     *
     * @param cmd the command that was missing a description
     */
    public EmptyDescriptionException(String cmd) {
        super("eep! I slipped off my branch trying to read that 🦥🌿\n" +
                "the " + cmd + " description cannot be empty. e.g., \"" + cmd + " borrow book\"");
    }
}