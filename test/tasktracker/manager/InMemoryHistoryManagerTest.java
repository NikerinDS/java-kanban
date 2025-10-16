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
    void removeTask() {
        Task task = new Task(1, "1", "1", TaskStatus.NEW);
        historyManager.add(task);
        historyManager.remove(task.getId());
        List<Task> taskList = historyManager.getHistory();
        assertTrue(taskList.isEmpty(), "После удаления задачи история долна быть пустой");
    }

    @Test
    void addAndUpdateTasks() {
        Task task1 = new Task(1, "1", "1", TaskStatus.NEW);
        Task task2 = new Task(2, "2", "2", TaskStatus.NEW);
        Task task3 = new Task(3, "3", "3", TaskStatus.NEW);
        historyManager.add(task1);
        historyManager.add(task2);
        historyManager.add(task3);
        task1.setStatus(TaskStatus.DONE);
        task2.setStatus(TaskStatus.IN_PROGRESS);
        historyManager.add(task1);
        historyManager.add(task2);
        List<Task> taskList = historyManager.getHistory();
        assertEquals(3, taskList.size(), "В истории должны храниться только последние версии задач");
        List<Task> expected = new ArrayList<>();
        expected.add(task3);
        expected.add(task1);
        expected.add(task2);
        assertIterableEquals(expected, taskList, "Задачи в истории должны храниться в порядке добавления или обновления");
    }

}