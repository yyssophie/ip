package sloth.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents an event task with start and end times.
 * Extends the base Task class to include event timing information.
 */
public class Event extends Task {
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