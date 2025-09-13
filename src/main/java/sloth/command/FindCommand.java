package sloth.command;

import sloth.Storage;
import sloth.TaskList;
import sloth.UI;
import sloth.task.Task;

/**
 * Command to find  a task
 * by searching for a keyword in the task description
 */
public class FindCommand extends Command {
    private final String keyword;

    public FindCommand(String keyword) {
        assert keyword != null && !keyword.isBlank() : "Find keyword must be non-empty";
        this.keyword = keyword;
    }

    /**
     * Executes find command by looping through all tasks in task list
     * to find tasks that match the key word
     * @param tasks the task list to operate on
     * @param ui the user interface for displaying messages
     * @param storage the storage system for persisting tasks
     */
    @Override
    public String execute(TaskList tasks, UI ui, Storage storage) {
        java.util.ArrayList<Task> allTasks = tasks.asList();
        java.util.ArrayList<Task> matchingTasks = new java.util.ArrayList<>();

        for (Task task : allTasks) {
            if (task.getContent().toLowerCase().contains(keyword.toLowerCase())) {
                matchingTasks.add(task);
            }
        }

        return ui.showFoundTasks(matchingTasks);
    }
}