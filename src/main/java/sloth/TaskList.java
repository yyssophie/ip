package sloth;

import java.util.ArrayList;
import sloth.task.Task;

/**
 * Manages a collection of tasks with operations for adding, removing, and modifying tasks.
 * Provides a convenient interface for task list operations with 1-based indexing for user interaction.
 */
public class TaskList {
    private final ArrayList<Task> tasks;
    /**
     * Constructs a new empty TaskList.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Constructs a new TaskList with the given list of tasks.
     * Creates a copy of the provided list to ensure encapsulation.
     *
     * @param list the initial list of tasks to populate this TaskList with
     */
    public TaskList(ArrayList<Task> list) {
        this.tasks = new ArrayList<>(list);
    }

    /**
     * Gets the number of tasks in this list.
     *
     * @return the size of the task list
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Returns the internal ArrayList of tasks.
     * Note: This returns a reference to the internal list, not a copy.
     *
     * @return the ArrayList containing all tasks
     */
    public ArrayList<Task> asList() {

        return this.tasks;
    }

    /**
     * Adds a new task to the end of the task list.
     *
     * @param t the task to add
     * @return the added task (same as input parameter)
     */
    public Task add(Task t){
        tasks.add(t);
        return t;
    }

    /**
     * Gets the task at the specified 1-based index.
     *
     * @param i the 1-based index of the task to retrieve
     * @return the task at the specified position
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    public Task get(int i){
        return tasks.get(i - 1);
    }

    /**
     * Removes and returns the task at the specified 1-based index.
     *
     * @param i the 1-based index of the task to remove
     * @return the removed task
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    public Task delete(int i){
        return tasks.remove(i - 1);
    }

    /**
     * Marks the task at the specified 1-based index as completed.
     * Only changes the status if the task is not already marked as done.
     *
     * @param i the 1-based index of the task to mark
     * @return the marked task
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    public Task mark(int i){
        Task t = get(i);
        if (t.getStatus().equals(" "))
            t.toggleStatus();
        return t;
    }

    /**
     * Unmarks the task at the specified 1-based index (marks as incomplete).
     * Only changes the status if the task is currently marked as done.
     *
     * @param i the 1-based index of the task to unmark
     * @return the unmarked task
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    public Task unmark(int i){
        Task t = get(i);
        if (t.getStatus().equals("X"))
            t.toggleStatus();
        return t;
    }
}
