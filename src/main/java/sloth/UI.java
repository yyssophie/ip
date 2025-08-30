package sloth;

import java.util.*;

/**
 * Handles all user interface operations for the Sloth task management application.
 * Manages input/output operations including displaying messages, reading commands,
 * and formatting output with consistent styling.
 */
public class UI {
    private final String line = "_".repeat(50);
    private final Scanner sc = new Scanner(System.in);

    /**
     * Displays the welcome message with the Sloth logo when the application starts.
     * Shows the application greeting and ASCII art logo.
     */
    public void showWelcome() {
        String logo = "   🌿─────\n  ( - . - )\n  /(   づ )\n  ╯ ╯ ╯ ╯\n";
        println(line);
        println("Hello, I am ... Sloth\n" + logo + "\nHow can I help you today?🦥");
        println(line);
    }

    /**
     * Reads a command from the user input.
     *
     * @return the command string entered by the user
     */
    public String readCommand() {
        return sc.nextLine();
    }

    /**
     * Displays a horizontal line separator for visual formatting.
     */
    public void showLine() {
        println(line);
    }

    /**
     * Displays an error message to the user.
     *
     * @param msg the error message to display
     */
    public void showError(String msg) {
        println(msg);
    }

    /**
     * Displays the goodbye message when the application is exiting.
     * Shows a farewell message with sloth-themed text.
     */
    public void showGoodbye() {
        println(line);
        println("Bye. Hope to see you soon~\nI'm gonna...go back to...sleep  zZZ 🦥");
        println(line);
    }

    /**
     * Displays the complete list of tasks with numbering.
     * Shows each task with its 1-based index and formatted representation.
     *
     * @param tasks the list of tasks to display
     */
    public void showList(List<Task> tasks) {
        println(line);
        for (int i = 0; i < tasks.size(); i++) println("\t" + (i+1) + ". " + tasks.get(i));
        println(line);
    }

    /**
     * Displays a confirmation message when a task is successfully added.
     * Shows the added task and the updated total count of tasks.
     *
     * @param t the task that was added
     * @param size the new total number of tasks
     */
    public void showAdded(Task t, int size) {
        println("okayy ... I've added " + t);
        println("Now you have " + size + " tasks 🦥");
    }

    /**
     * Displays a confirmation message when a task's completion status is changed.
     * Shows different messages for marking as done vs marking as undone.
     *
     * @param t the task whose status was changed
     * @param done true if the task was marked as done, false if marked as undone
     */
    public void showMarked(Task t, boolean done) {
        println(done ? "sniff sniff! I've marked as done:\n" + t
                : "okay... I've marked as undone:\n" + t);
    }

    /**
     * Displays a confirmation message when a task is successfully deleted.
     * Shows the deleted task and the updated total count of tasks.
     *
     * @param t the task that was deleted
     * @param size the new total number of tasks after deletion
     */
    public void showDeleted(Task t, int size) {
        println("Got it ..... I removed this task\n" + t);
        println("Now you have " + size + " tasks 🦥");
    }

    /**
     * Helper method to print a line to standard output.
     * Centralizes output operations for consistent formatting.
     *
     * @param s the string to print
     */
    private void println(String s){
        System.out.println(s);
    }
}
