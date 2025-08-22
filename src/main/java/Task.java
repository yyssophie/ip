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

    public void toggleStatus() {
        this.done = !this.done;
    }

    @Override
    public String toString() {
        return "[" + getStatus() + "] " + getContent();
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
    private String endDate;

    public Deadline(String content,  String endDate) {
        super(content);
        this.endDate = endDate;
    }
    @Override
    public String toString() {
        return "[" + this.type + "] [" + this.getStatus() + "] " + this.getContent()
                + " (by: " + this.endDate + ")";
    }
}

class Event extends Task {
    private final String type = "E";
    private String endDate;
    private String startDate;

    public Event(String content, String startDate, String endDate) {
        super(content);
        this.endDate = endDate;
        this.startDate = startDate;
    }

    @Override
    public String toString() {
        return "[" + this.type + "] [" + this.getStatus() + "] " + this.getContent()
                + " (from: " + this.startDate + " to: " + this.endDate + ")";
    }
}


