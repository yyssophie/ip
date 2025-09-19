package sloth.command;

import sloth.Storage;
import sloth.TaskList;
import sloth.UI;
import sloth.task.Task;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Command to sort all the tasks chronologically.
 * Compare deadline and event by deadline's start time and event's end time
 * By default, todo comes last
 */
public class SortCommand extends Command {

    /**
     * Executes the list command by displaying all tasks.
     *
     * @param tasks the task list to display
     * @param ui the user interface for showing the task list
     * @param storage the storage system (not used in this command)
     */
    @Override
    public String execute(TaskList tasks, UI ui, Storage storage) {
        ArrayList<Task> lst = tasks.asList();
        Collections.sort(lst);
        Storage.save(lst);
        return ui.showSorted(tasks);
    }
}

