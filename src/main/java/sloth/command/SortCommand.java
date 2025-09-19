package sloth.command;

import sloth.Storage;
import sloth.TaskList;
import sloth.UI;
import sloth.task.Task;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Command to sort all tasks chronologically.
 * Compares deadline and event tasks by their respective time constraints:
 * - Deadline tasks are sorted by their due date
 * - Event tasks are sorted by their start time
 * - Todo tasks come last in the sorted order as they have no time constraints
 */
public class SortCommand extends Command {

    /**
     * Executes the sort command by arranging all tasks in chronological order.
     * Sorts the task list using the natural ordering defined in Task.compareTo(),
     * saves the updated list to storage, and displays the sorted tasks to the user.
     *
     * @param tasks the task list to sort
     * @param ui the user interface for displaying the sorted task list
     * @param storage the storage system for persisting the sorted task order
     * @return the formatted string representation of the sorted task list
     */
    @Override
    public String execute(TaskList tasks, UI ui, Storage storage) {
        ArrayList<Task> lst = tasks.asList();
        Collections.sort(lst);
        Storage.save(lst);
        return ui.showSorted(tasks);
    }
}

