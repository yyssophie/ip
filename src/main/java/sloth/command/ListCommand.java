package sloth.command;

import sloth.Storage;
import sloth.TaskList;
import sloth.UI;

/**
 * Command to display all tasks in the current task list.
 */
public class ListCommand extends Command {

    /**
     * Executes the list command by displaying all tasks.
     *
     * @param tasks the task list to display
     * @param ui the user interface for showing the task list
     * @param storage the storage system (not used in this command)
     */
    @Override
    public String execute(TaskList tasks, UI ui, Storage storage) {
        return ui.showList(tasks.asList());
    }
}