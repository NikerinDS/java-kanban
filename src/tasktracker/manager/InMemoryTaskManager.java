package tasktracker.manager;

import tasktracker.data.EpicTask;
import tasktracker.data.Subtask;
import tasktracker.data.Task;
import tasktracker.data.TaskStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryTaskManager implements TaskManager {
    private int newId = 1;
    private final HashMap<Integer, Task> simpleTasks;
    private final HashMap<Integer, EpicTask> epicTasks;
    private final HashMap<Integer, Subtask> subtasks;
    private final HistoryManager history;

    public InMemoryTaskManager() {
        this.simpleTasks = new HashMap<>();
        this.epicTasks = new HashMap<>();
        this.subtasks = new HashMap<>();
        history = Managers.getDefaultHistoryManager();
    }

    @Override
    public void createSimpleTask(Task task) {
        task.setId(getNewId());
        simpleTasks.put(task.getId(), new Task(task));
    }

    @Override
    public void updateSimpleTask(Task task) {
        simpleTasks.put(task.getId(), new Task(task));
    }

    @Override
    public List<Task> getListOfSimpleTasks() {
        ArrayList<Task> listOfTasks = new ArrayList<>();
        for (Task task : simpleTasks.values()) {
            listOfTasks.add(new Task(task));
        }
        return listOfTasks;
    }

    @Override
    public void deleteAllSimpleTasks() {
        for (Task task : simpleTasks.values()) {
            history.remove(task.getId());
        }
        simpleTasks.clear();
    }

    @Override
    public Task getSimpleTaskById(int id) {
        Task task = simpleTasks.get(id);
        if (task == null) return null;
        task = new Task(task);
        history.add(task);
        return task;
    }

    @Override
    public void deleteSimpleTaskById(int id) {
        simpleTasks.remove(id);
        history.remove(id);
    }

    @Override
    public void createEpicTask(EpicTask task) {
        task.setId(getNewId());
        task.setStatus(calculateEpicTaskStatusBySubtasksId(task.getSubtasksId()));
        epicTasks.put(task.getId(), new EpicTask(task));
    }

    @Override
    public void updateEpicTask(EpicTask task) {
        task.setStatus(calculateEpicTaskStatusBySubtasksId(task.getSubtasksId()));
        epicTasks.put(task.getId(), new EpicTask(task));
    }

    @Override
    public List<EpicTask> getListOfEpicTasks() {
        ArrayList<EpicTask> listOfTasks = new ArrayList<>();
        for (EpicTask task : epicTasks.values()) {
            listOfTasks.add(new EpicTask(task));
        }
        return listOfTasks;
    }

    @Override
    public void deleteAllEpicTasks() {
        for (EpicTask task : epicTasks.values()) {
            history.remove(task.getId());
        }
        for (Subtask task : subtasks.values()) {
            history.remove(task.getId());
        }
        epicTasks.clear();
        subtasks.clear();
    }

    @Override
    public EpicTask getEpicTaskById(int id) {
        EpicTask epicTask = epicTasks.get(id);
        if (epicTask == null) return null;
        epicTask = new EpicTask(epicTask);
        history.add(epicTask);
        return epicTask;
    }

    @Override
    public void deleteEpicTaskById(int id) {
        for (Integer subtaskId : epicTasks.get(id).getSubtasksId()) {
            subtasks.remove(subtaskId);
            history.remove(subtaskId);
        }
        epicTasks.remove(id);
        history.remove(id);
    }

    @Override
    public void createSubtask(Subtask task) {
        task.setId(getNewId());
        subtasks.put(task.getId(), new Subtask(task));

        EpicTask epicTask = epicTasks.get(task.getEpicId());
        List<Integer> subtasksId = epicTask.getSubtasksId();
        subtasksId.add(task.getId());
        updateEpicTask(new EpicTask(epicTask.getId(), epicTask.getName(), epicTask.getDescription(),
                subtasksId, epicTask.getStatus()));
    }

    @Override
    public void updateSubtask(Subtask task) {
        subtasks.put(task.getId(), new Subtask(task));
        updateEpicTask(epicTasks.get(task.getEpicId()));
    }

    @Override
    public List<Subtask> getListOfSubtasks() {
        ArrayList<Subtask> listOfTasks = new ArrayList<>();
        for (Subtask task : subtasks.values()) {
            listOfTasks.add(new Subtask(task));
        }
        return listOfTasks;
    }

    @Override
    public List<Subtask> getListOfSubtasksByEpicId(Integer epicId) {
        ArrayList<Subtask> listOfTasks = new ArrayList<>();
        for (Integer subtaskId : epicTasks.get(epicId).getSubtasksId()) {
            listOfTasks.add(subtasks.get(subtaskId));
        }
        return listOfTasks;
    }

    @Override
    public void deleteAllSubtasks() {
        for (Subtask task : subtasks.values()) {
            history.remove(task.getId());
        }
        subtasks.clear();

        for (EpicTask task : epicTasks.values()) {
            epicTasks.put(task.getId(), new EpicTask(task.getId(), task.getName(), task.getDescription(),
                    new ArrayList<>(), TaskStatus.NEW));
        }
    }

    @Override
    public Subtask getSubtaskById(int id) {
        Subtask subtask = subtasks.get(id);
        if (subtask == null) return null;
        subtask = new Subtask(subtask);
        history.add(subtask);
        return subtask;
    }

    @Override
    public void deleteSubtaskById(int id) {
        EpicTask epicTask = epicTasks.get(subtasks.get(id).getEpicId());
        List<Integer> subtasksId = epicTask.getSubtasksId();
        subtasksId.remove(Integer.valueOf(id));
        updateEpicTask(new EpicTask(epicTask.getId(), epicTask.getName(), epicTask.getDescription(),
                subtasksId, epicTask.getStatus()));

        subtasks.remove(id);
        history.remove(id);
    }

    @Override
    public List<Task> getHistory() {
        return history.getHistory();
    }


    private int getNewId() {
        return newId++;
    }

    private TaskStatus calculateEpicTaskStatusBySubtasksId(List<Integer> tasksId) {
        TaskStatus status;
        boolean allNew = true;
        boolean allDone = true;
        for (Integer taskId : tasksId) {
            TaskStatus subtaskStatus = subtasks.get(taskId).getStatus();
            allNew = allNew && (subtaskStatus == TaskStatus.NEW);
            allDone = allDone && (subtaskStatus == TaskStatus.DONE);
        }
        if (allNew) {
            status = TaskStatus.NEW;
        } else if (allDone) {
            status = TaskStatus.DONE;
        } else {
            status = TaskStatus.IN_PROGRESS;
        }
        return status;
    }
}
