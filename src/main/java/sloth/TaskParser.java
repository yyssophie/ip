package sloth;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class TaskParser {
    private static final Pattern TODO =
            Pattern.compile("^\\s*todo\\s+(?<content>.+)\\s*$");
    private static final Pattern DEADLINE =
            Pattern.compile("^\\s*deadline\\s+(?<content>.+?)\\s+/by\\s+(?<by>.+)\\s*$");
    private static final Pattern EVENT =
            Pattern.compile("^\\s*event\\s+(?<content>.+?)\\s+/from\\s+(?<from>.+?)\\s+/to\\s+(?<to>.+)\\s*$");


    public static LocalDateTime parseFlexibleDateTime(String s) throws SlothException {
        DateTimeFormatter[] formats = new DateTimeFormatter[] {
                DateTimeFormatter.ofPattern("MMM dd yyyy, HH:mm"), // Apr 18 2005, 1800
                DateTimeFormatter.ofPattern("MMM d yyyy, HH:mm"),
                DateTimeFormatter.ofPattern("MMM d yyyy HH:mm"),
                DateTimeFormatter.ofPattern("MMM dd yyyy HH:mm"),
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"),   // 2019-10-15 1800
                DateTimeFormatter.ofPattern("d/M/yyyy HH:mm"),     // 2/12/2019 1800
                DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"),   // 02/12/2019 1800
                DateTimeFormatter.ISO_LOCAL_DATE_TIME             // 2019-10-15T18:00
        };

        for (DateTimeFormatter f : formats) {
            try {
                return LocalDateTime.parse(s.trim(), f);
            } catch (DateTimeParseException ignored) {}
        }
        throw new SlothException("nuzzz... nuzzzz..\n" +
                "Little sloth don't understand this date format!\n" +
                "Try e.g. Apr 18 2025, 18:00 or 2/12/2019 18:00");
    }

    // deal with todo, deadline or event
    public static Task parse_input(String input) throws SlothException {
        Matcher m;
        if ((m = TODO.matcher(input)).matches()) {
            return new ToDo(m.group("content"));
        } else if ((m = DEADLINE.matcher(input)).matches()) {
            String date = m.group("by");
            LocalDateTime d = TaskParser.parseFlexibleDateTime(date);
            return new Deadline(m.group("content").trim(), d);
        } else if ((m = EVENT.matcher(input)).matches()) {
            String from = m.group("from"), to = m.group("to");
            LocalDateTime f = TaskParser.parseFlexibleDateTime(from), t = TaskParser.parseFlexibleDateTime(to);
            return new Event(m.group("content").trim(), f, t);
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

    // *** The key part
    public static Command parse(String input) throws SlothException {
        String s = input;
        if (s.equalsIgnoreCase("bye"))  return new ByeCommand();
        if (s.equalsIgnoreCase("list")) return new ListCommand();

        if (s.startsWith("mark ")) {
            int idx = Integer.parseInt(s.split("\\s+")[1]);
            return new MarkCommand(idx);
        }
        if (s.startsWith("unmark ")) {
            int idx = Integer.parseInt(s.split("\\s+")[1]);
            return new UnmarkCommand(idx);
        }
        if (s.startsWith("delete ")) {
            int idx = Integer.parseInt(s.split("\\s+")[1]);
            return new DeleteCommand(idx);
        }

        // everything else is an add: todo / deadline / event
        Task t = TaskParser.parse_input(s);     // your existing parser incl. dates
        return new AddTaskCommand(t);
    }

    /*format of storage line:
     T | 1 | read book
     D | 0 | return book | June 6th
     E | 0 | project meeting | Aug 6th 2-4pm
     T | 1 | join sports club */
    public static Task parse_storage(String line) throws SlothException {
        String[] parts = line.split(" \\| ");
        if (parts.length < 3) throw new ParseException("oops, storage line too short");
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
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd yyyy, HH:mm");
                LocalDateTime dt = LocalDateTime.parse(by, formatter);
                Deadline d = new Deadline(content, dt);
                if (done) d.toggleStatus();
                return d;
            }
            case "E": {
                if (parts.length < 5) throw new ParseException("event missing /from or /to");
                String from = parts[3];
                String to = parts[4];
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd yyyy, HH:mm");
                LocalDateTime dt_from = LocalDateTime.parse(from, formatter);
                LocalDateTime dt_to = LocalDateTime.parse(to, formatter);
                Event e = new Event(content, dt_from, dt_to);
                if (done) e.toggleStatus();
                return e;
            }
            default:
                throw new ParseException("unknown type: " + type);
        }
    }

}

