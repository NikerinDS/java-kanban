package tasktracker.manager;

import tasktracker.data.EpicTask;
import tasktracker.data.Subtask;
import tasktracker.data.Task;

import java.util.List;

public interface TaskManager {
    void createSimpleTask(Task task);

    void updateSimpleTask(Task task);

    List<Task> getListOfSimpleTasks();

    void deleteAllSimpleTasks();

    Task getSimpleTaskById(int id);

    void deleteSimpleTaskById(int id);

    void createEpicTask(EpicTask task);

    void updateEpicTask(EpicTask task);

    List<EpicTask> getListOfEpicTasks();

    void deleteAllEpicTasks();

    EpicTask getEpicTaskById(int id);

    void deleteEpicTaskById(int id);

    void createSubtask(Subtask task);

    void updateSubtask(Subtask task);

    List<Subtask> getListOfSubtasks();

    List<Subtask> getListOfSubtasksByEpicId(Integer epicId);

    void deleteAllSubtasks();

    Subtask getSubtaskById(int id);

    void deleteSubtaskById(int id);

    List<Task> getHistory();
}
