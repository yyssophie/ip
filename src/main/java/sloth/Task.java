package sloth;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Task {
    private String content;
    private boolean done;

    public Task(String content) {
        this.content = content;
        this.done = false;
    }

    public String getContent() {
        return this.content;
    }

    public String getStatus() {
        return this.done? "X" : " ";
    }

    public boolean isDone() {
        return this.done;
    }

    public void toggleStatus() {
        this.done = !this.done;
    }

    @Override
    public String toString() {
        return "[" + getStatus() + "] " + getContent();
    }

    public String to_storage_string() {
        return "T | " + (this.done ? "1" : "0") + " | " + this.content;
    }

}

class ToDo extends Task {
    private final String type = "T";

    public ToDo(String content) {
        super(content);
    }
    @Override
    public String toString() {
        return "[" + this.type + "] [" + this.getStatus() + "] " + this.getContent();
    }

}

class Deadline extends Task {
    private final String type = "D";
    private LocalDateTime endDate;

    public Deadline(String content,  LocalDateTime endDate) {
        super(content);
        this.endDate = endDate;
    }
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd yyyy, HH:mm");
        return "[" + this.type + "] [" + this.getStatus() + "] " + this.getContent()
                + " (by: " + this.endDate.format(formatter) + ")";
    }

    @Override
    public String to_storage_string() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd yyyy, HH:mm");
        return "D | " + (this.isDone() ? "1" : "0") + " | " + getContent() + " | " + this.endDate.format(formatter);
    }
}

class Event extends Task {
    private final String type = "E";
    private LocalDateTime endDate;
    private LocalDateTime startDate;

    public Event(String content, LocalDateTime startDate, LocalDateTime endDate) {
        super(content);
        this.endDate = endDate;
        this.startDate = startDate;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd yyyy, HH:mm");
        return "[" + this.type + "] [" + this.getStatus() + "] " + this.getContent()
                + " (from: " + this.startDate.format(formatter) + " to: " + this.endDate.format(formatter) + ")";
    }

    @Override
    public String to_storage_string() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd yyyy, HH:mm");
        return "E | " + (this.isDone() ? "1" : "0") + " | " + getContent() + " | " +
                this.startDate.format(formatter) + " | " + this.endDate.format(formatter);
    }
}


