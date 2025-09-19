package sloth.task;

/**
 * Represents a basic task with content and completion status.
 * Base class for all task types in the Sloth task management system.
 */
public class Task implements Comparable<Task> {
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

    /**
     * Compares this task with another task for chronological ordering.
     * Sorting priority:
     * 1. Todo tasks come last (sorted alphabetically among themselves)
     * 2. Deadline tasks are sorted by their due date
     * 3. Event tasks are sorted by their start time
     * 4. When comparing different task types, time-based tasks come before todos,
     *    and deadlines vs events are compared by their respective time constraints
     *
     * @param o the other task to compare with
     * @return negative integer if this task comes before o, positive if after,
     *         zero if they are considered equal in ordering
     */
    @Override
    public int compareTo(Task o) {
        if (this instanceof ToDo) {
            if (o instanceof ToDo) {
                return this.getContent().compareTo(o.getContent());
            } else {
                return 1;
            }
        } else if (this instanceof Deadline) {
            if (o instanceof ToDo) {
                return -1;
            } else if (o instanceof Deadline) {
                return ((Deadline) this).getEndDate().compareTo(((Deadline) o).getEndDate());
            } else {
                return ((Deadline) this).getEndDate().compareTo(((Event) o).getStartDate());
            }
        } else {
            if (o instanceof ToDo) {
                return -1;
            } else if (o instanceof Deadline) {
                return ((Event) this).getStartDate().compareTo(((Deadline) o).getEndDate());
            } else {
                return ((Event) this).getStartDate().compareTo(((Event) o).getStartDate());
            }
        }
    }
}