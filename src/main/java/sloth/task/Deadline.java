package sloth.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a task with a deadline (due date and time).
 * Extends the base Task class to include deadline information.
 */
public class Deadline extends Task {
    private final String type = "D";
    private LocalDateTime endDate;

    /**
     * Constructs a new Deadline task with the specified content and due date.
     *
     * @param content the description of the deadline task
     * @param endDate the date and time when this task is due
     */
    public Deadline(String content, LocalDateTime endDate) {
        super(content);
        this.endDate = endDate;
    }


    public Deadline(String content, LocalDateTime endDate, int beforeTaskIdx) {
        super(content,  beforeTaskIdx);
        this.endDate = endDate;
    }

    /**
     * Returns a string representation with deadline-specific formatting.
     *
     * @return formatted string showing [D] type indicator, status, content, and due date
     */
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd yyyy, HH:mm");
        return "[" + this.type + "] [" + this.getStatus() + "] " + this.getContent()
                + " (by: " + this.endDate.format(formatter) + ")";
    }

    /**
     * Returns a string representation for storage with deadline information.
     *
     * @return formatted string for file storage including due date
     */
    @Override
    public String to_storage_string() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd yyyy, HH:mm");

        return "D | " + (this.isDone() ? "1" : "0") + " | " + getContent() + " | " + this.endDate.format(formatter)
                + " | " + this.getBeforeTaskIdx();
    }
}