package sloth;
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
    public abstract void execute(TaskList tasks, UI ui, Storage storage) throws SlothException;
}

/**
 * Command to terminate the Sloth application.
 * Saves all tasks before exiting and displays a goodbye message.
 */
class ByeCommand extends Command {
    @Override
    public boolean isExit() {
        return true;
    }
    @Override
    public void execute(TaskList tasks, UI ui, Storage storage) {
        storage.save(tasks.asList());
        ui.showGoodbye();
    }
}

/**
 * Command to display all tasks in the current task list.
 */
class ListCommand extends Command {
    @Override
    public void execute(TaskList tasks, UI ui, Storage storage) {
        ui.showList(tasks.asList());
    }
}

/**
 * Command to add a new task to the task list.
 */
class AddTaskCommand extends Command {
    private final Task task;
    public AddTaskCommand(Task t) { this.task = t; }

    @Override
    public void execute(TaskList tasks, UI ui, Storage storage) {
        tasks.add(task);
        storage.save(tasks.asList());
        ui.showAdded(task, tasks.size());
    }
}

/**
 * Command to mark a task as completed.
 */
class MarkCommand extends Command {
    private final int idx;
    public MarkCommand(int oneBasedIdx) {
        this.idx = oneBasedIdx;
    }
    @Override
    public void execute(TaskList tasks, UI ui, Storage storage) {
        Task t = tasks.mark(idx);
        storage.save(tasks.asList());
        ui.showMarked(t, true);
    }
}

/**
 * Command to unmark a task (mark as incomplete).
 */
class UnmarkCommand extends Command {
    private final int idx;
    public UnmarkCommand(int oneBasedIdx) { this.idx = oneBasedIdx; }
    @Override public void execute(TaskList tasks, UI ui, Storage storage) {
        Task t = tasks.unmark(idx);
        storage.save(tasks.asList());
        ui.showMarked(t, false);
    }
}

/**
 * Command to delete a task from the task list.
 */
class DeleteCommand extends Command {
    private final int idx;
    public DeleteCommand(int oneBasedIdx) { this.idx = oneBasedIdx; }
    @Override public void execute(TaskList tasks, UI ui, Storage storage) {
        Task t = tasks.delete(idx);
        storage.save(tasks.asList());
        ui.showDeleted(t, tasks.size());
    }
}
