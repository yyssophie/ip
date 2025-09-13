package sloth.command;

import sloth.Storage;
import sloth.TaskList;
import sloth.UI;
import sloth.task.Task;

/**
 * Command to add a new task to the task list.
 */
public class AddTaskCommand extends Command {
    private final Task task;

    /**
     * Creates a new AddTaskCommand with the specified task.
     *
     * @param task the task to be added
     */
    public AddTaskCommand(Task task) {
        this.task = task;
    }

    /**
     * Executes the add command by adding the task to the list, saving, and showing confirmation.
     *
     * @param tasks the task list to add the task to
     * @param ui the user interface for showing the confirmation message
     * @param storage the storage system for persisting the updated task list
     */
    @Override
    public String execute(TaskList tasks, UI ui, Storage storage) {
        int before = tasks.size();
        tasks.add(task);
        int idx = before + 1; // index of this newly added task
        task.setIdx(idx);
        int beforeTaskIdx = task.getIdx();
        if (beforeTaskIdx != -1) {
            Task beforeTask = tasks.get(beforeTaskIdx);
            beforeTask.setAfterTaskIdx(idx);
        }
        assert tasks.size() == idx : "TaskList size should increase by 1 after add";
        storage.save(tasks.asList());
        return ui.showAdded(task, tasks.size());
    }
}
