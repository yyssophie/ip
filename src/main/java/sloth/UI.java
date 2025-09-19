package sloth;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import sloth.task.Task;

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
    public String showWelcome() {
        String logo = "   🌿─────\n  ( - . - )\n  /(   づ )\n  ╯ ╯ ╯ ╯\n";
        String word = logo + "Hello, I am ... Sloth\n" + "\nHow can I help you today?🦥";
        println(line);
        println(line);
        println(word);
        return word;
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
    public String showGoodbye() {
        String word = "Bye. Hope to see you soon~\nI'm gonna...go back to...sleep  zZZ 🦥";
        println(line);
        println(word);
        println(line);
        return word;
    }

    /**
     * Displays the complete list of tasks with numbering.
     * Shows each task with its 1-based index and formatted representation.
     *
     * @param tasks the list of tasks to display
     */
    public String showList(List<Task> tasks) {
        String list = "";
        println(line);
        for (int i = 0; i < tasks.size(); i++) {
            list += (i+1) + ". " + tasks.get(i) + "\n";
            println("\t" + (i+1) + ". " + tasks.get(i));
        }
        println(line);
        return list;
    }

    /**
     * Displays a confirmation message when a task is successfully added.
     * Shows the added task and the updated total count of tasks.
     *
     * @param t the task that was added
     * @param size the new total number of tasks
     */
    public String showAdded(Task t, int size) {
        String word = "okayy ... I've added " + t + "\n" + "Now you have " + size + " tasks 🦥";
        println("okayy ... I've added " + t);
        println("Now you have " + size + " tasks 🦥");
        return word;
    }

    /**
     * Displays a confirmation message when a task's completion status is changed.
     * Shows different messages for marking as done vs marking as undone.
     *
     * @param t the task whose status was changed
     * @param done true if the task was marked as done, false if marked as undone
     */
    public String showMarked(Task t, boolean done) {
        String word = done ? "sniff sniff! I've marked as done:\n" + t
                : "okay... I've marked as undone:\n" + t;
        println(word);
        return word;
    }

    /**
     * Displays a confirmation message when a task is successfully deleted.
     * Shows the deleted task and the updated total count of tasks.
     *
     * @param t the task that was deleted
     * @param size the new total number of tasks after deletion
     */
    public String showDeleted(Task t, int size) {
        String word = "Got it ..... I removed this task\n" + t + "Now you have " + size + " tasks 🦥";
        println(word);
        return word;
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

    /**
     * Displays the results of a find operation.
     * Shows all tasks that contain the searched keyword with their original numbering.
     *
     * @param matchingTasks the list of tasks that match the search keyword
     */
    public String showFoundTasks(ArrayList<Task> matchingTasks) {
        println(line);
        String word;
        if (matchingTasks.isEmpty()) {
            word = "Oops, there is no matching tasks found in your list 🦥\n";
            println("Oops, there is no matching tasks found in your list 🦥");
        } else {
            word = "aha ~ Here are the matching tasks in your list:\n";
            println("aha ~ Here are the matching tasks in your list:");
            for (int i = 0; i < matchingTasks.size(); i++) {
                println("\t" + (i + 1) + "." + matchingTasks.get(i));
                word += "\t" + (i + 1) + "." + matchingTasks.get(i) + "\n";
            }
        }
        println(line);
        return word;
    }

    /**
     * Displays the sorted list of tasks with their current numbering.
     * Shows a confirmation message followed by all tasks in their sorted order.
     *
     * @param tasks the TaskList containing the sorted tasks to display
     * @return the formatted string representation of the sorted task list
     */
    public String showSorted(TaskList tasks) {
        String word = "Ok, I've sorted all tasks in the list\n";
        for (int i = 1; i <= tasks.size(); i++) {
            word += i + ". " + tasks.get(i) + "\n";
        }
        println(word);
        return word;
    }
}
