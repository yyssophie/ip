package sloth.task;

/**
 * Represents a basic todo task without any time constraints.
 * Extends the base Task class with todo-specific formatting.
 */
public class ToDo extends Task {
    private final String type = "T";

    /**
     * Constructs a new ToDo task with the specified content.
     *
     * @param content the description of the todo task
     */
    public ToDo(String content) {
        super(content);
    }

    /**
     * Returns a string representation with todo-specific formatting.
     *
     * @return formatted string showing [T] type indicator, status, and content
     */
    @Override
    public String toString() {
        return "[" + this.type + "] [" + this.getStatus() + "] " + this.getContent();
    }
}
