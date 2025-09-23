package tasktracker.manager;

import tasktracker.data.EpicTask;
import tasktracker.data.Subtask;
import tasktracker.data.Task;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    private static final int MAX_HISTORY_ELEMENTS = 10;

    private final List<Task> taskHistory = new ArrayList<>();

    @Override
    public void add(Task task) {
        taskHistory.add(copy(task));
        if (taskHistory.size() > MAX_HISTORY_ELEMENTS) {
            taskHistory.removeFirst();
        }
    }

    @Override
    public List<Task> getHistory() {
        List<Task> historyCopy = new ArrayList<>();
        for (Task task : taskHistory) {
            historyCopy.add(copy(task));
        }
        return historyCopy;
    }

    private Task copy(Task task) {
        return switch (task) {
            case EpicTask epicTask -> new EpicTask(epicTask);
            case Subtask subtask -> new Subtask(subtask);
            default -> new Task(task);
        };
    }
}
