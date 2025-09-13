package sloth;

import sloth.command.AddTaskCommand;
import sloth.command.ByeCommand;
import sloth.command.Command;
import sloth.command.DeleteCommand;
import sloth.command.FindCommand;
import sloth.command.ListCommand;
import sloth.command.MarkCommand;
import sloth.command.UnmarkCommand;
import sloth.task.Task;
import sloth.task.Deadline;
import sloth.task.ToDo;
import sloth.task.Event;
import sloth.exception.SlothException;
import sloth.exception.EmptyDescriptionException;
import sloth.exception.UnknownCommandException;
import sloth.exception.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utility class for parsing user input and storage data into Task objects and Commands.
 * Handles flexible date/time parsing and command interpretation.
 */
public class TaskParser {
    private static final Pattern TODO =
            Pattern.compile("^\\s*todo\\s+(?<content>.+)\\s*$");
    private static final Pattern DEADLINE =
            Pattern.compile("^\\s*deadline\\s+(?<content>.+?)\\s+/by\\s+(?<by>.+)\\s*$");
    private static final Pattern EVENT =
            Pattern.compile("^\\s*event\\s+(?<content>.+?)\\s+/from\\s+(?<from>.+?)\\s+/to\\s+(?<to>.+)\\s*$");

    /**
     * Parses a date/time string using multiple flexible formats.
     * Tries various common date formats until one succeeds.
     *
     * @param dateTimeString the date/time string to parse
     * @return LocalDateTime object representing the parsed date/time
     * @throws SlothException if the string cannot be parsed with any supported format
     */
    public static LocalDateTime parseFlexibleDateTime(String dateTimeString) throws SlothException {
        DateTimeFormatter[] formats = new DateTimeFormatter[] {
                DateTimeFormatter.ofPattern("MMM dd yyyy, HH:mm"), // Apr 18 2005, 18:00
                DateTimeFormatter.ofPattern("MMM d yyyy, HH:mm"),
                DateTimeFormatter.ofPattern("MMM d yyyy HH:mm"),
                DateTimeFormatter.ofPattern("MMM dd yyyy HH:mm"),
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"),   // 2019-10-15 18:00
                DateTimeFormatter.ofPattern("d/M/yyyy HH:mm"),     // 2/12/2019 18:00
                DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"),   // 02/12/2019 18:00
                DateTimeFormatter.ISO_LOCAL_DATE_TIME             // 2019-10-15T18:00
        };

        for (DateTimeFormatter formatter : formats) {
            try {
                return LocalDateTime.parse(dateTimeString.trim(), formatter);
            } catch (DateTimeParseException ignored) {
                // Continue to next format
            }
        }
        throw new SlothException("nuzzz... nuzzzz..\n"
                + "Little sloth don't understand this date format!\n"
                + "Try e.g. Apr 18 2025, 18:00 or 2/12/2019 18:00");
    }

    /**
     * Parses user input to create the appropriate Task object.
     * Handles todo, deadline, and event task creation with appropriate validation.
     *
     * @param input the user input string to parse
     * @return Task object of the appropriate type (ToDo, Deadline, or Event)
     * @throws SlothException if the input format is invalid or required information is missing
     */
    public static Task parseInput(String input) throws SlothException {
        Matcher matcher;
        if ((matcher = TODO.matcher(input)).matches()) {
            return new ToDo(matcher.group("content"));
        } else if ((matcher = DEADLINE.matcher(input)).matches()) {
            String date = matcher.group("by");
            LocalDateTime dueDate = TaskParser.parseFlexibleDateTime(date);
            return new Deadline(matcher.group("content").trim(), dueDate);
        } else if ((matcher = EVENT.matcher(input)).matches()) {
            String from = matcher.group("from");
            String to = matcher.group("to");
            LocalDateTime startTime = TaskParser.parseFlexibleDateTime(from);
            LocalDateTime endTime = TaskParser.parseFlexibleDateTime(to);
            assert endTime.isAfter(startTime) : "Event '/to' must be >= '/from'";
            return new Event(matcher.group("content").trim(), startTime, endTime);
        } else {
            String command = input.split("\\s+", 2)[0].toLowerCase();
            if (command.equals("todo")) {
                throw new EmptyDescriptionException("todo");
            } else if (command.equals("deadline")) {
                throw new ParseException("deadline");
            } else if (command.equals("event")) {
                throw new ParseException("event");
            } else {
                throw new UnknownCommandException(input);
            }
        }
    }

    /**
     * Parses user input to create the appropriate Command object.
     * Handles all supported commands: bye, list, mark, unmark, delete, and task creation commands.
     *
     * @param input the user input string to parse
     * @return Command object that can be executed
     * @throws SlothException if the input cannot be parsed into a valid command
     */
    public static Command parse(String input) throws SlothException {
        String command = input;
        if (command.equalsIgnoreCase("bye")) {
            return new ByeCommand();
        }
        if (command.equalsIgnoreCase("list")) {
            return new ListCommand();
        }

        if (command.startsWith("mark ")) {
            try {
                String[] parts = command.split("\\s+");
                if (parts.length < 2) {
                    throw new SlothException("eep! I need a task number after 'mark'. Try: mark 1");
                }
                int index = Integer.parseInt(parts[1]);
                return new MarkCommand(index);
            } catch (NumberFormatException e) {
                throw new SlothException("eep! This is not a valid number. Try: mark 1");
            }
        }
        if (command.startsWith("unmark ")) {
            try {
                String[] parts = command.split("\\s+");
                if (parts.length < 2) {
                    throw new SlothException("eep! I need a task number after 'unmark'. Try: unmark 1");
                }
                int index = Integer.parseInt(parts[1]);
                return new UnmarkCommand(index);
            } catch (NumberFormatException e) {
                throw new SlothException("eep! This is not a valid number. Try: unmark 1");
            }
        }
        if (command.startsWith("delete ")) {
            try {
                String[] parts = command.split("\\s+");
                if (parts.length < 2) {
                    throw new SlothException("eep! I need a task number after 'delete'. Try: delete 1");
                }
                int index = Integer.parseInt(parts[1]);
                return new DeleteCommand(index);
            } catch (NumberFormatException e) {
                throw new SlothException("eep! This is not a valid number. Try: delete 1");
            }
        }
        if (command.startsWith("find ")) {
            String[] parts = command.split("\\s+", 2);
            if (parts.length < 2) {
                throw new SlothException("eep! I need a keyword to search for. Try: find book");
            }
            String keyword = parts[1].trim();
            if (keyword.isEmpty()) {
                throw new SlothException("eep! Little sloth need a keyword to search for. Try: find book");
            }
            return new FindCommand(keyword);
        }

        // everything else is an add: todo / deadline / event
        Task task = TaskParser.parseInput(command);
        return new AddTaskCommand(task);
    }

    /**
     * Parses a line from storage file to recreate a Task object.
     * Expected format: "TYPE | DONE_FLAG | CONTENT | [DATE_INFO]"
     * where TYPE is T/D/E, DONE_FLAG is 0/1, and DATE_INFO varies by task type.
     *
     * @param line the storage line to parse
     * @return Task object recreated from the storage format
     * @throws SlothException if the line format is invalid or cannot be parsed
     */
    public static Task parseStorage(String line) throws SlothException {
        String[] parts = line.split(" \\| ");
        if (parts.length < 3) {
            throw new ParseException("oops, storage line too short");
        }
        String type = parts[0];
        String doneFlag = parts[1];
        String content = parts[2];

        boolean isDone = doneFlag.equals("1");
        switch (type) {
            case "T":
                ToDo todo = new ToDo(content);
                if (isDone) {
                    todo.toggleStatus();
                }
                return todo;
            case "D":
                if (parts.length < 4) {
                    throw new ParseException("deadline missing /by");
                }
                String by = parts[3];
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd yyyy, HH:mm");
                LocalDateTime dueDateTime = LocalDateTime.parse(by, formatter);
                Deadline deadline = new Deadline(content, dueDateTime);
                if (isDone) {
                    deadline.toggleStatus();
                }
                return deadline;
            case "E":
                if (parts.length < 5) {
                    throw new ParseException("event missing /from or /to");
                }
                String from = parts[3];
                String to = parts[4];
                DateTimeFormatter eventFormatter = DateTimeFormatter.ofPattern("MMM dd yyyy, HH:mm");
                LocalDateTime startDateTime = LocalDateTime.parse(from, eventFormatter);
                LocalDateTime endDateTime = LocalDateTime.parse(to, eventFormatter);
                Event event = new Event(content, startDateTime, endDateTime);
                if (isDone) {
                    event.toggleStatus();
                }
                return event;
            default:
                throw new ParseException("unknown type: " + type);
        }
    }
}