package tasktracker.manager;

import tasktracker.data.EpicTask;
import tasktracker.data.Subtask;
import tasktracker.data.Task;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    private final List<Task> taskHistory = new ArrayList<>();
    private static final int MAX_HISTORY_ELEMENTS = 10;

    @Override
    public void add(Task task) {
        taskHistory.add(task);
        if (taskHistory.size() > MAX_HISTORY_ELEMENTS) {
            taskHistory.removeFirst();
        }
    }

    @Override
    public List<Task> getHistory() {
        List<Task> historyCopy = new ArrayList<>();
        for (Task task : taskHistory) {
            Task taskCopy;
            switch (task) {
                case EpicTask epicTask -> taskCopy = new EpicTask(epicTask);
                case Subtask subtask -> taskCopy = new Subtask(subtask);
                default -> taskCopy = new Task(task);
            }
            historyCopy.add(taskCopy);
        }
        return historyCopy;
    }
}
