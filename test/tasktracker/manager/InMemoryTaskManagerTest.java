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

class InMemoryTaskManagerTest {
    private InMemoryTaskManager taskManager;

    @BeforeEach
    void beforeEach() {
        taskManager = new InMemoryTaskManager();
    }

    @Test
    void createSimpleTaskTest() {
        Task task = new Task(0, "1", "1", TaskStatus.NEW);
        taskManager.createSimpleTask(task);
        int id = task.getId();
        Task taskInManager = taskManager.getSimpleTaskById(id);
        assertNotEquals(0, id, "При создание задачи в менеджере должен присваиваться id");
        assertNotNull(taskInManager, "После создания задачи  по сгенерированному id из менеджера должна возвращаться задача");
    }

    @Test
    void updateSimpleTaskTest() {
        Task task = new Task(0, "1", "1", TaskStatus.NEW);
        taskManager.createSimpleTask(task);
        int id = task.getId();
        task.setStatus(TaskStatus.DONE);
        Task taskInManager = taskManager.getSimpleTaskById(id);
        assertEquals(TaskStatus.NEW, taskInManager.getStatus(), "Задача в менеджере должна изменятся только при вызове метода обновления");

        taskManager.updateSimpleTask(task);
        taskInManager = taskManager.getSimpleTaskById(id);
        assertEquals(TaskStatus.DONE, taskInManager.getStatus(), "Задача в менеджере должна изменятся только при вызове метода обновления");
    }

    @Test
    void deleteSimpleTaskTest() {
        Task task = new Task(0, "1", "1", TaskStatus.NEW);
        taskManager.createSimpleTask(task);
        int id = task.getId();
        taskManager.deleteSimpleTaskById(id);
        Task taskInManager = taskManager.getSimpleTaskById(id);
        assertNull(taskInManager, "После удаления в менеджере не должно остаться задачи с заданным id");
    }

    @Test
    void deleteAllSimpleTaskTest() {
        taskManager.createSimpleTask(new Task(0, "1", "1", TaskStatus.NEW));
        taskManager.createSimpleTask(new Task(0, "2", "2", TaskStatus.NEW));
        taskManager.createSimpleTask(new Task(0, "3", "3", TaskStatus.NEW));
        taskManager.deleteAllSimpleTasks();
        List<Task> list = taskManager.getListOfSimpleTasks();
        assertEquals(0, list.size(), "После удаления всех задач список должен быть пустым");
    }

    @Test
    void createEpicTaskTest() {
        EpicTask epic = new EpicTask(0, "1", "1", new ArrayList<>(), TaskStatus.IN_PROGRESS);
        taskManager.createEpicTask(epic);
        int id = epic.getId();
        EpicTask taskInManager = taskManager.getEpicTaskById(id);
        assertNotEquals(0, id, "При создание задачи в менеджере должен присваиваться id");
        assertNotNull(taskInManager, "После создания задачи  по сгенерированному id из менеджера должна возвращаться задача");
        assertEquals(TaskStatus.NEW, taskInManager.getStatus(), "Статус эпика должен рассчитываться");
    }

    @Test
    void updateEpicTaskTest() {
        EpicTask task = new EpicTask("1", "1");
        taskManager.createEpicTask(task);
        int id = task.getId();
        task.setName("name");
        task.setStatus(TaskStatus.DONE);
        EpicTask taskInManager = taskManager.getEpicTaskById(id);
        assertEquals("1", taskInManager.getName(), "Задача в менеджере должна изменятся только при вызове метода обновления");

        taskManager.updateEpicTask(task);
        taskInManager = taskManager.getEpicTaskById(id);
        assertEquals("name", taskInManager.getName(), "Задача в менеджере должна изменятся только при вызове метода обновления");
        assertEquals(TaskStatus.NEW, taskInManager.getStatus(), "Статус эпика должен рассчитываться");
    }

    @Test
    void createSubtaskTest() {
        EpicTask epic = new EpicTask("1", "1");
        taskManager.createEpicTask(epic);
        int epicId = epic.getId();
        Subtask sub = new Subtask(0, "2", "2", epicId, TaskStatus.NEW);
        taskManager.createSubtask(sub);
        int subId = sub.getId();
        List<Integer> listOfSubtasksInEpic = taskManager.getEpicTaskById(epicId).getSubtasksId();
        Subtask subtaskInManager = taskManager.getSubtaskById(subId);
        assertNotEquals(0, subId, "При создание задачи в менеджере должен присваиваться id");
        assertNotNull(subtaskInManager, "После создания задачи  по сгенерированному id из менеджера должна возвращаться задача");
        assertEquals(1, listOfSubtasksInEpic.size(), "В эпик должен добавляться id поздадачи");
        assertEquals(subId, listOfSubtasksInEpic.getFirst(), "В эпик должен добавляться id поздадачи");
    }

    @Test
    void updateSubtaskTest() {
        EpicTask epic = new EpicTask("1", "1");
        taskManager.createEpicTask(epic);
        int epicId = epic.getId();
        Subtask sub = new Subtask(0, "2", "2", epicId, TaskStatus.NEW);
        taskManager.createSubtask(sub);
        int subId = sub.getId();
        sub.setStatus(TaskStatus.DONE);
        Subtask subtaskInManager = taskManager.getSubtaskById(subId);
        assertEquals(TaskStatus.NEW, subtaskInManager.getStatus(), "Задача в менеджере должна изменятся только при вызове метода обновления");

        taskManager.updateSubtask(sub);
        subtaskInManager = taskManager.getSubtaskById(subId);
        assertEquals(TaskStatus.DONE, subtaskInManager.getStatus(), "Задача в менеджере должна изменятся только при вызове метода обновления");
    }

    @Test
    void deleteSubtaskTest() {
        EpicTask epic = new EpicTask("1", "1");
        taskManager.createEpicTask(epic);
        int epicId = epic.getId();
        Subtask sub = new Subtask("2", "2", epicId);
        taskManager.createSubtask(sub);
        int subId = sub.getId();
        taskManager.deleteSubtaskById(subId);
        assertNull(taskManager.getSubtaskById(subId), "После удаления в менеджере не должно остаться задачи с заданным id");
        List<Integer> listOfSubtasks = taskManager.getEpicTaskById(epicId).getSubtasksId();
        assertEquals(0, listOfSubtasks.size(), "После удаления подзадачи в эпике не должно остаться задачи с заданным id");
    }

    @Test
    void deleteEpicTaskTest() {
        EpicTask epic = new EpicTask("1", "1");
        taskManager.createEpicTask(epic);
        int epicId = epic.getId();
        Subtask sub = new Subtask("2", "2", epicId);
        taskManager.createSubtask(sub);
        int subId = sub.getId();
        taskManager.deleteEpicTaskById(epicId);
        assertNull(taskManager.getEpicTaskById(epicId), "После удаления эпика в менеджере не должно остаться задачи с заданным id");
        assertNull(taskManager.getSubtaskById(subId), "После удаления эпика в менеджере не должно остаться его подзадач");
    }

    @Test
    void calculateEpicStatusTest() {
        EpicTask epic = new EpicTask("epic", "epic");
        taskManager.createEpicTask(epic);
        int epicId = epic.getId();
        EpicTask epicInManager = taskManager.getEpicTaskById(epicId);
        assertEquals(TaskStatus.NEW, epicInManager.getStatus(), "У созданного эпика должен быть статус NEW");

        Subtask sub1 = new Subtask("1", "1", epicId);
        Subtask sub2 = new Subtask("2", "2", epicId);
        Subtask sub3 = new Subtask("3", "3", epicId);
        taskManager.createSubtask(sub1);
        taskManager.createSubtask(sub2);
        taskManager.createSubtask(sub3);
        epicInManager = taskManager.getEpicTaskById(epicId);
        assertEquals(TaskStatus.NEW, epicInManager.getStatus(),
                "Если у всех подзадач эпика статус NEW то у эпика должен быть статус NEW");

        sub1.setStatus(TaskStatus.DONE);
        sub2.setStatus(TaskStatus.IN_PROGRESS);
        taskManager.updateSubtask(sub1);
        taskManager.updateSubtask(sub2);
        epicInManager = taskManager.getEpicTaskById(epicId);
        assertEquals(TaskStatus.IN_PROGRESS, epicInManager.getStatus(),
                "Если не все подзадачи эпика выполнены то у эпика должен быть статус IN_PROGRESS");

        sub2.setStatus(TaskStatus.DONE);
        sub3.setStatus(TaskStatus.DONE);
        taskManager.updateSubtask(sub2);
        taskManager.updateSubtask(sub3);
        epicInManager = taskManager.getEpicTaskById(epicId);
        assertEquals(TaskStatus.DONE, epicInManager.getStatus(),
                "Если все подзадачи эпика выполнены то у эпика должен быть статус DONE");
    }

    @Test
    void deleteAllEpicTaskTest() {
        EpicTask epic1 = new EpicTask("epic1", "epic1");
        EpicTask epic2 = new EpicTask("epic2", "epic2");
        taskManager.createEpicTask(epic1);
        taskManager.createEpicTask(epic2);
        Subtask sub1 = new Subtask("sub1", "sub1", epic1.getId());
        Subtask sub2 = new Subtask("sub2", "sub2", epic1.getId());
        Subtask sub3 = new Subtask("sub3", "sub3", epic2.getId());
        taskManager.createSubtask(sub1);
        taskManager.createSubtask(sub2);
        taskManager.createSubtask(sub3);

        taskManager.deleteAllEpicTasks();
        List<EpicTask> listOfEpicTasks = taskManager.getListOfEpicTasks();
        List<Subtask> listOFSubtasks = taskManager.getListOfSubtasks();
        assertEquals(0, listOfEpicTasks.size(), "После удаления всех эпиков список эпиков должен быть пустым");
        assertEquals(0, listOFSubtasks.size(), "После удаления всех эпиков список подзадач должен быть пустым");
    }

    @Test
    void deleteAllSubtaskTest() {
        EpicTask epic1 = new EpicTask("epic1", "epic1");
        EpicTask epic2 = new EpicTask("epic2", "epic2");
        taskManager.createEpicTask(epic1);
        taskManager.createEpicTask(epic2);
        Subtask sub1 = new Subtask("sub1", "sub1", epic1.getId());
        Subtask sub2 = new Subtask("sub2", "sub2", epic1.getId());
        Subtask sub3 = new Subtask("sub3", "sub3", epic2.getId());
        taskManager.createSubtask(sub1);
        taskManager.createSubtask(sub2);
        taskManager.createSubtask(sub3);

        taskManager.deleteAllSubtasks();
        List<Subtask> listOFSubtasks = taskManager.getListOfSubtasks();
        List<Subtask> listOFSubtasksInEpic1 = taskManager.getListOfSubtasksByEpicId(epic1.getId());
        List<Subtask> listOFSubtasksInEpic2 = taskManager.getListOfSubtasksByEpicId(epic2.getId());
        assertEquals(0, listOFSubtasks.size(), "После удаления всех подзадач список подзадач должен быть пустым");
        assertEquals(0, listOFSubtasksInEpic1.size(), "После удаления всех подзадач список подзадач в эпике должен быть пустым");
        assertEquals(0, listOFSubtasksInEpic2.size(), "После удаления всех подзадач список подзадач в эпике должен быть пустым");
    }

    @Test
    void getHistoryTest() {
        Task task = new Task("task1", "task1");
        taskManager.createSimpleTask(task);
        EpicTask epicTask = new EpicTask("epic1", "epic1");
        taskManager.createEpicTask(epicTask);
        Subtask subtask = new Subtask("sub1", "sub1", epicTask.getId());
        taskManager.createSubtask(subtask);
        taskManager.getSimpleTaskById(task.getId());
        taskManager.getEpicTaskById(epicTask.getId());
        taskManager.getSubtaskById(subtask.getId());
        List<Task> listOfTasks = taskManager.getHistory();
        List<Task> expected = new ArrayList<>();
        expected.add(task);
        expected.add(epicTask);
        expected.add(subtask);
        assertIterableEquals(expected, listOfTasks, "История должна хранить обращение к задачам");

        taskManager.deleteSimpleTaskById(task.getId());
        taskManager.deleteAllEpicTasks();
        listOfTasks = taskManager.getHistory();
        assertTrue(listOfTasks.isEmpty(), "После удаления задач в менеджере история должна быть пустой");
    }
}