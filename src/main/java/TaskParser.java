import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TaskParser {
    private static final Pattern TODO =
            Pattern.compile("^\\s*todo\\s+(?<content>.+)\\s*$");
    private static final Pattern DEADLINE =
            Pattern.compile("^\\s*deadline\\s+(?<content>.+?)\\s+/by\\s+(?<by>.+)\\s*$");
    private static final Pattern EVENT =
            Pattern.compile("^\\s*event\\s+(?<content>.+?)\\s+/from\\s+(?<from>.+?)\\s+/to\\s+(?<to>.+)\\s*$");

    public static Task parse(String input) {
        Matcher m;
        if ((m = TODO.matcher(input)).matches()) {
            return new ToDo(m.group("content").trim());
        }
        if ((m = DEADLINE.matcher(input)).matches()) {
            return new Deadline(m.group("content").trim(), m.group("by").trim());
        }
        if ((m = EVENT.matcher(input)).matches()) {
            return new Event(m.group("content").trim(), m.group("from").trim(), m.group("to").trim());
        }
        throw new IllegalArgumentException("Invalid input");
    }
}