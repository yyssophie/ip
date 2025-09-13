package sloth.command;

import sloth.Storage;
import sloth.TaskList;
import sloth.UI;

/**
 * Command to terminate the Sloth application.
 * Saves all tasks before exiting and displays a goodbye message.
 */
public class ByeCommand extends Command {

    /**
     * Indicates that this command will exit the application.
     *
     * @return true, as this command exits the application
     */
    @Override
    public boolean isExit() {
        return true;
    }

    /**
     * Executes the bye command by saving tasks and showing goodbye message.
     *
     * @param tasks the task list to save before exiting
     * @param ui the user interface for displaying the goodbye message
     * @param storage the storage system for saving tasks
     */
    @Override
    public String execute(TaskList tasks, UI ui, Storage storage) {
        storage.save(tasks.asList());
        return ui.showGoodbye();
    }
}
