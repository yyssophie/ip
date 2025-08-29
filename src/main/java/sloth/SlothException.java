package sloth;
public class SlothException extends Exception {
    public SlothException(String message) {
        super(message);
    }
}

class UnknownCommandException extends SlothException {
    public UnknownCommandException(String cmd) {
        super("hmm… I don’t recognise the command: \"" + cmd + "\".\n"
                + "But here is what I know:"
                + "todo <desc> | deadline <desc> /by <when> | event <desc> /from <from> /to <to> " +
                "| list | mark <n> | unmark <n> | bye");
    }
}

class EmptyDescriptionException extends SlothException {
    public EmptyDescriptionException(String cmd) {
        super("eep! I slipped off my branch trying to read that 🦥🌿\n" +
                "the " + cmd + " description cannot be empty. e.g., \"" + cmd + " borrow book\"");
    }
}

class ParseException extends SlothException {
    public ParseException(String hint) { super("oh nooo… my tiny sloth brain got tangled \uD83E\uDDA5\n" +
            "try eg. deadline Assignment /by Sep 3 2025, 23:59\n" +
            "or  event Career Fair /from Aug 28 2025, 12:00 /to Aug 28 2025, 16:00"); }
}
