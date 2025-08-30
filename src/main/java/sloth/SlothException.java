package sloth;
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

/**
 * Exception thrown when an unrecognized command is entered by the user.
 * Provides helpful suggestions for valid commands.
 */
class UnknownCommandException extends SlothException {
    /**
     * Constructs a new UnknownCommandException with a helpful error message.
     *
     * @param cmd the unrecognized command that was entered
     */
    public UnknownCommandException(String cmd) {
        super("hmm… I don’t recognise the command: \"" + cmd + "\".\n"
                + "But here is what I know:"
                + "todo <desc> | deadline <desc> /by <when> | event <desc> /from <from> /to <to> " +
                "| list | mark <n> | unmark <n> | bye");
    }
}

/**
 * Exception thrown when a command requiring a description is entered without one.
 * Provides an example of proper usage.
 */
class EmptyDescriptionException extends SlothException {
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

/**
 * Exception thrown when parsing fails due to incorrect command format.
 * Provides examples of correct usage for deadline and event commands.
 */
class ParseException extends SlothException {
    /**
     * Constructs a new ParseException with examples of correct command formats.
     *
     * @param hint additional context about what parsing failed (not currently used)
     */
    public ParseException(String hint) { super("oh nooo… my tiny sloth brain got tangled \uD83E\uDDA5\n" +
            "try eg. deadline Assignment /by Sep 3 2025, 23:59\n" +
            "or  event Career Fair /from Aug 28 2025, 12:00 /to Aug 28 2025, 16:00"); }
}
