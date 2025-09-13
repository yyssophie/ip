package sloth.exception;

import sloth.task.Task;

/** Thrown when a task is marked before its prerequisite is done. */
public class PrerequisiteNotDoneException extends SlothException {
    public PrerequisiteNotDoneException(Task currentTask, Task beforeTask) {
        super("Little sloth cannot mark " + currentTask.getIdx() + " " + currentTask
                + " before prerequisite " + beforeTask.getIdx() + " " + beforeTask +  " is done.");
    }

}
