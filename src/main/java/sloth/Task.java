package sloth;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a basic task with content and completion status.
 * Base class for all task types in the Sloth task management system.
 */
public class Task {
    private String content;
    private boolean done;

    /**
     * Constructs a new Task with the specified content.
     * The task is initially marked as not done.
     *
     * @param content the description of the task
     */
    public Task(String content) {
        this.content = content;
        this.done = false;
    }

    /**
     * Gets the content/description of this task.
     *
     * @return the task description
     */
    public String getContent() {
        return this.content;
    }

    /**
     * Gets the completion status symbol of this task.
     *
     * @return "X" if the task is done, " " (space) if not done
     */
    public String getStatus() {
        return this.done? "X" : " ";
    }

    /**
     * Checks if this task is completed.
     *
     * @return true if the task is done, false otherwise
     */
    public boolean isDone() {
        return this.done;
    }

    /**
     * Toggles the completion status of this task.
     * Changes done tasks to not done, and not done tasks to done.
     */
    public void toggleStatus() {
        this.done = !this.done;
    }

    /**
     * Returns a string representation of this task for display purposes.
     *
     * @return formatted string with status and content
     */
    @Override
    public String toString() {
        return "[" + getStatus() + "] " + getContent();
    }

    /**
     * Returns a string representation of this task for storage purposes.
     * Uses pipe-separated format for easy parsing.
     *
     * @return formatted string for file storage
     */
    public String to_storage_string() {
        return "T | " + (this.done ? "1" : "0") + " | " + this.content;
    }
}

/**
 * Represents a basic todo task without any time constraints.
 * Extends the base Task class with todo-specific formatting.
 */
class ToDo extends Task {
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

/**
 * Represents a task with a deadline (due date and time).
 * Extends the base Task class to include deadline information.
 */
class Deadline extends Task {
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
        return "D | " + (this.isDone() ? "1" : "0") + " | " + getContent() + " | " + this.endDate.format(formatter);
    }
}

/**
 * Represents an event task with start and end times.
 * Extends the base Task class to include event timing information.
 */
class Event extends Task {
    private final String type = "E";
    private LocalDateTime endDate;
    private LocalDateTime startDate;

    /**
     * Constructs a new Event task with the specified content, start time, and end time.
     *
     * @param content the description of the event
     * @param startDate the date and time when the event starts
     * @param endDate the date and time when the event ends
     */
    public Event(String content, LocalDateTime startDate, LocalDateTime endDate) {
        super(content);
        this.endDate = endDate;
        this.startDate = startDate;
    }

    /**
     * Returns a string representation with event-specific formatting.
     *
     * @return formatted string showing [E] type indicator, status, content, and time range
     */
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd yyyy, HH:mm");
        return "[" + this.type + "] [" + this.getStatus() + "] " + this.getContent()
                + " (from: " + this.startDate.format(formatter) + " to: " + this.endDate.format(formatter) + ")";
    }

    /**
     * Returns a string representation for storage with event timing information.
     *
     * @return formatted string for file storage including start and end times
     */
    @Override
    public String to_storage_string() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd yyyy, HH:mm");
        return "E | " + (this.isDone() ? "1" : "0") + " | " + getContent() + " | " +
                this.startDate.format(formatter) + " | " + this.endDate.format(formatter);
    }
}