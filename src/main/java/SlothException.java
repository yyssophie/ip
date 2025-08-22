public class SlothException extends Exception {
    public SlothException(String message) {
        super(message);
    }
}

class UnknownCommandException extends SlothException {
    public UnknownCommandException(String cmd) {
        super("hmm… I don’t recognise the command: \"" + cmd + "\".\n"
                + "Try: todo <desc> | deadline <desc> /by <when> | event <desc> /from <from> /to <to> " +
                "| list | mark <n> | unmark <n> | bye");
    }
}

class EmptyDescriptionException extends SlothException {
    public EmptyDescriptionException(String cmd) {
        super("the " + cmd + " description cannot be empty. e.g., \"" + cmd + " borrow book\"");
    }
}

class ParseException extends SlothException {
    public ParseException(String hint) { super("something wrong with the format\n" +
            "try eg. deadline /by tomorrow   or    event /from today /to tomorrow"); }
}
