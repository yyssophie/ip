package sloth;
// Command.java
public abstract class Command {
    public boolean isExit() { return false; }
    public abstract void execute(TaskList tasks, UI ui, Storage storage) throws SlothException;
}

// ByeCommand.java
class ByeCommand extends Command {
    @Override
    public boolean isExit() { return true; }
    @Override
    public void execute(TaskList tasks, UI ui, Storage storage) {
        storage.save(tasks.asList());
        ui.showGoodbye();
    }
}

// ListCommand.java
class ListCommand extends Command {
    @Override
    public void execute(TaskList tasks, UI ui, Storage storage) {
        ui.showList(tasks.asList());
    }
}

// AddTaskCommand.java
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

// MarkCommand.java
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

// UnmarkCommand.java
class UnmarkCommand extends Command {
    private final int idx;
    public UnmarkCommand(int oneBasedIdx) { this.idx = oneBasedIdx; }
    @Override public void execute(TaskList tasks, UI ui, Storage storage) {
        Task t = tasks.unmark(idx);
        storage.save(tasks.asList());
        ui.showMarked(t, false);
    }
}

// DeleteCommand.java
class DeleteCommand extends Command {
    private final int idx;
    public DeleteCommand(int oneBasedIdx) { this.idx = oneBasedIdx; }
    @Override public void execute(TaskList tasks, UI ui, Storage storage) {
        Task t = tasks.delete(idx);
        storage.save(tasks.asList());
        ui.showDeleted(t, tasks.size());
    }
}
