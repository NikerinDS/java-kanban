package tasktracker.manager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasktracker.data.EpicTask;
import tasktracker.data.Subtask;
import tasktracker.data.Task;
import tasktracker.data.TaskStatus;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class InMemoryHistoryManagerTest {
    private static InMemoryHistoryManager historyManager;

    @BeforeEach
    void beforeEach() {
        historyManager = new InMemoryHistoryManager();
    }

    @Test
    void addSingleTask() {
        Task task = new Task(1, "1", "1", TaskStatus.NEW);
        historyManager.add(task);
        List<Task> taskList = historyManager.getHistory();
        assertEquals(1, taskList.size(), "После добавления одной задачи в истории должна хранится одна задача");
        assertEquals(task, taskList.getFirst(), "После добавления одной задачи в истории должна храгится неизменная задача");
    }

    @Test
    void addSingleChangedTask() {
        Task task = new Task(1, "1", "1", TaskStatus.NEW);
        historyManager.add(task);
        task.setStatus(TaskStatus.DONE);
        historyManager.add(task);
        List<Task> taskList = historyManager.getHistory();
        assertEquals(TaskStatus.NEW, taskList.get(0).getStatus(), "История должна хранить независимые версии задач");
        assertEquals(TaskStatus.DONE, taskList.get(1).getStatus(), "История должна хранить независимые версии задач");
    }

    @Test
    void addDifferentTasks() {
        Task task = new Task(1, "1", "1", TaskStatus.NEW);
        EpicTask epicTask = new EpicTask(2, "2", "2", new ArrayList<>(), TaskStatus.NEW);
        Subtask subtask = new Subtask(3, "3", "3", 2, TaskStatus.NEW);
        historyManager.add(task);
        historyManager.add(epicTask);
        historyManager.add(subtask);
        List<Task> taskList = historyManager.getHistory();
        assertInstanceOf(Task.class, taskList.get(0), "В истории должны хранится разные типы задач");
        assertInstanceOf(EpicTask.class, taskList.get(1), "В истории должны хранится разные типы задач");
        assertInstanceOf(Subtask.class, taskList.get(2), "В истории должны хранится разные типы задач");
    }

    @Test
    void shouldBe10LatestElementsInHistory() {
        for (int i = 0; i < 15; i++) {
            historyManager.add(new Task(i, "", "", TaskStatus.NEW));
        }
        List<Task> taskList = historyManager.getHistory();
        assertEquals(10, taskList.size(), "В истории должно хранится максимум 10 элементов");
        assertEquals(5, taskList.getFirst().getId(), "В истории должны хранится последние добавленные задачи");
        assertEquals(14, taskList.getLast().getId(), "В истории должны хранится последние добавленные задачи");
    }
}