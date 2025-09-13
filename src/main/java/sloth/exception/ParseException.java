package sloth.exception;

/**
 * Exception thrown when parsing fails due to incorrect command format.
 * Provides examples of correct usage for deadline and event commands.
 */
public class ParseException extends SlothException {
    /**
     * Constructs a new ParseException with examples of correct command formats.
     *
     * @param hint additional context about what parsing failed (not currently used)
     */
    public ParseException(String hint) { super("oh nooo… my tiny sloth brain got tangled \uD83E\uDDA5\n" +
            "try eg. deadline Assignment /by Sep 3 2025, 23:59\n" +
            "or  event Career Fair /from Aug 28 2025, 12:00 /to Aug 28 2025, 16:00"); }
}