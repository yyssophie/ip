package sloth.exception;

/**
 * Exception thrown when an unrecognized command is entered by the user.
 * Provides helpful suggestions for valid commands.
 */
public class UnknownCommandException extends SlothException {
    /**
     * Constructs a new UnknownCommandException with a helpful error message.
     *
     * @param cmd the unrecognized command that was entered
     */
    public UnknownCommandException(String cmd) {
        super("hmm… I don’t recognise the command: \"" + cmd + "\".\n"
                + "But here is what I know:\n"
                + "todo <something>\ndeadline <something> /by <when>\nevent <something> /from <when> /to <when>\n" +
                "list\nmark <n>\nunmark <n>\n" + "delete <index>\n" +  "find <key word>\n" + "bye");
    }
}