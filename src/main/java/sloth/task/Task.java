package sloth.task;

/**
 * Represents a basic task with content and completion status.
 * Base class for all task types in the Sloth task management system.
 */
public class Task {
    private String content;
    private boolean done;
    private int idx; // 1-based index of the task in the task lists. unique identifier for each task.
    private int beforeTaskIdx = -1;
    private int afterTaskIdx = -1;

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

    public Task(String content, int beforeTaskIdx) {
        this.content = content;
        this.done = false;
        this.beforeTaskIdx = beforeTaskIdx;
    }

    /**
     * Set index of a task after it's added to the task list
     * @param idx of the task in list
     */
    public void setIdx(int idx) {
        this.idx = idx;
    }

    public void setBeforeTaskIdx(int beforeTaskIdx) {
        this.beforeTaskIdx = beforeTaskIdx;
    }

    public void setAfterTaskIdx(int afterTaskIdx) {
        this.afterTaskIdx = afterTaskIdx;
    }

    public int getBeforeTaskIdx() {
        return beforeTaskIdx;
    }

    public int getAfterTaskIdx() {
        return afterTaskIdx;
    }

    public int getIdx() {
        return this.idx;
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
        return "T | " + (this.done ? "1" : "0") + " | " + this.content + " | " + this.getBeforeTaskIdx();
    }
}