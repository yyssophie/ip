package sloth.command;

import sloth.Storage;
import sloth.TaskList;
import sloth.UI;
import sloth.task.Task;

/**
 * Command to delete a task from the task list.
 */
public class DeleteCommand extends Command {
    private final int index;

    /**
     * Creates a new DeleteCommand for the task at the specified index.
     *
     * @param oneBasedIndex the 1-based index of the task to delete
     */
    public DeleteCommand(int oneBasedIndex) {
        assert oneBasedIndex > 0 : "Delete index must be 1-based positive";
        this.index = oneBasedIndex;
    }

    /**
     * Executes the delete command by removing the task, saving, and showing confirmation.
     *
     * @param tasks the task list to remove the task from
     * @param ui the user interface for showing the confirmation message
     * @param storage the storage system for persisting the updated task list
     */
    @Override
    public String execute(TaskList tasks, UI ui, Storage storage) {
        assert index >= 1 && index <= tasks.size() : "Delete index out of range: " + index;
        int before = tasks.size();
        Task task = tasks.delete(index);
        storage.save(tasks.asList());
        assert task != null : "Deleted task should not be null";
        assert tasks.size() == before - 1 : "TaskList size should decrease by 1 after delete";
        return ui.showDeleted(task, tasks.size());
    }
}
