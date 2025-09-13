package sloth.command;

import sloth.exception.SlothException;
import sloth.Storage;
import sloth.TaskList;
import sloth.UI;

/**
 * Abstract base class for all commands in the Sloth task management system.
 * Implements the Command pattern to encapsulate user actions.
 */
public abstract class Command {

    /**
     * Determines if this command should cause the application to exit.
     *
     * @return true if the command should exit the application, false otherwise
     */
    public boolean isExit() {
        return false;
    }

    /**
     * Executes the command with the given task list, UI, and storage.
     *
     * @param tasks the task list to operate on
     * @param ui the user interface for displaying messages
     * @param storage the storage system for persisting tasks
     * @throws SlothException if an error occurs during command execution
     */
    public abstract String execute(TaskList tasks, UI ui, Storage storage) throws SlothException;
}

/**
 * Command to terminate the Sloth application.
 * Saves all tasks before exiting and displays a goodbye message.
 */