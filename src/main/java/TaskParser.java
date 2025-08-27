import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TaskParser {
    private static final Pattern TODO =
            Pattern.compile("^\\s*todo\\s+(?<content>.+)\\s*$");
    private static final Pattern DEADLINE =
            Pattern.compile("^\\s*deadline\\s+(?<content>.+?)\\s+/by\\s+(?<by>.+)\\s*$");
    private static final Pattern EVENT =
            Pattern.compile("^\\s*event\\s+(?<content>.+?)\\s+/from\\s+(?<from>.+?)\\s+/to\\s+(?<to>.+)\\s*$");

    public static Task parse_input(String input) throws SlothException {
        Matcher m;
        if ((m = TODO.matcher(input)).matches()) {
            return new ToDo(m.group("content").trim());
        } else if ((m = DEADLINE.matcher(input)).matches()) {
            return new Deadline(m.group("content").trim(), m.group("by").trim());
        } else if ((m = EVENT.matcher(input)).matches()) {
            return new Event(m.group("content").trim(), m.group("from").trim(), m.group("to").trim());
        } else {
            String cmd = input.split("\\s+", 2)[0].toLowerCase();
            if (cmd.equals("todo")) {
                throw new EmptyDescriptionException("todo");
            } else if (cmd.equals("deadline")) {
                throw new ParseException("deadline");
            } else if (cmd.equals("event")) {
                throw new ParseException("event");
            } else {
                throw new UnknownCommandException(input);
            }
        }
    }

    /*format of storage line:
     T | 1 | read book
     D | 0 | return book | June 6th
     E | 0 | project meeting | Aug 6th 2-4pm
     T | 1 | join sports club */
    public static Task parse_storage(String line) throws SlothException {
        String[] parts = line.split(" \\| ");
        if (parts.length < 3) throw new ParseException("storage line too short");
        String type = parts[0];
        String done_flag = parts[1];
        String content = parts[2];

        boolean done = done_flag.equals("1");
        switch (type) {
            case "T": {
                ToDo t = new ToDo(content);
                if (done) t.toggleStatus();
                return t;
            }
            case "D": {
                if (parts.length < 4) throw new ParseException("deadline missing /by");
                String by = parts[3];
                Deadline d = new Deadline(content, by);
                if (done) d.toggleStatus();
                return d;
            }
            case "E": {
                if (parts.length < 5) throw new ParseException("event missing /from or /to");
                String from = parts[3];
                String to = parts[4];
                Event e = new Event(content, from, to);
                if (done) e.toggleStatus();
                return e;
            }
            default:
                throw new ParseException("unknown type: " + type);
        }
    }

}

