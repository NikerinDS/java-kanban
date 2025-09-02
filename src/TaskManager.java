import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {
    private HashMap<Integer, Task> simpleTasks;
    private HashMap<Integer, EpicTask> epicTasks;
    private HashMap<Integer, Subtask> subtasks;

    public TaskManager() {
        this.simpleTasks = new HashMap<>();
        this.epicTasks = new HashMap<>();
        this.subtasks = new HashMap<>();
    }

    //for simple tasks
    public void createSimpleTask(Task task) {
        simpleTasks.put(task.getId(), task);
    }

    public void updateSimpleTask(Task task) {
        simpleTasks.put(task.getId(), task);
    }

    public ArrayList<Task> getListOfSimpleTasks() {
        return new ArrayList<>(simpleTasks.values());
    }

    public void deleteAllSimpleTasks() {
        simpleTasks.clear();
    }

    public Task getSimpleTaskById(int id) {
        return simpleTasks.get(id);
    }

    public void deleteSimpleTaskById(int id) {
        simpleTasks.remove(id);
    }

    //for epic tasks
    private TaskStatus calculateEpicTaskStatusBySubtasksId(ArrayList<Integer> tasksId) {
        TaskStatus status;
        boolean allNew = true;
        boolean allDone = true;
        for (Integer taskId : tasksId) {
            TaskStatus subtaskStatus = getSubtaskById(taskId).getStatus();
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

    public void createEpicTask(EpicTask task) {
        task = new EpicTask(task.getId(), task.getName(), task.getDescription(), task.getSubtasksId(),
                calculateEpicTaskStatusBySubtasksId(task.getSubtasksId()));
        epicTasks.put(task.getId(), task);
    }

    public void updateEpicTask(EpicTask task) {
        task = new EpicTask(task.getId(), task.getName(), task.getDescription(), task.getSubtasksId(),
                calculateEpicTaskStatusBySubtasksId(task.getSubtasksId()));
        epicTasks.put(task.getId(), task);
    }

    public ArrayList<EpicTask> getListOfEpicTasks() {
        return new ArrayList<>(epicTasks.values());
    }

    public void deleteAllEpicTasks() {
        epicTasks.clear();
        subtasks.clear();
    }

    public EpicTask getEpicTaskById(int id) {
        return epicTasks.get(id);
    }

    public void deleteEpicTaskById(int id) {
        for (Integer subtaskId : getEpicTaskById(id).getSubtasksId()) {
            deleteSubtaskById(subtaskId);
        }
        epicTasks.remove(id);
    }

    //for subtasks
    public void createSubtask(Subtask task) {
        subtasks.put(task.getId(), task);

        EpicTask epicTask = epicTasks.get(task.getEpicId());
        ArrayList<Integer> subtasksId = epicTask.getSubtasksId();
        subtasksId.add(task.getId());
        TaskStatus status = calculateEpicTaskStatusBySubtasksId(subtasksId);
        epicTasks.put(epicTask.getId(), new EpicTask(epicTask.getId(), epicTask.getName(), epicTask.getDescription(),
                subtasksId, status));
    }

    public void updateSubtask(Subtask task) {
        subtasks.put(task.getId(), task);

        EpicTask epicTask = epicTasks.get(task.getEpicId());
        ArrayList<Integer> subtasksId = epicTask.getSubtasksId();
        TaskStatus status = calculateEpicTaskStatusBySubtasksId(subtasksId);
        epicTasks.put(epicTask.getId(), new EpicTask(epicTask.getId(), epicTask.getName(), epicTask.getDescription(),
                subtasksId, status));
    }

    public ArrayList<Subtask> getListOfSubtasks() {
        return new ArrayList<>(subtasks.values());
    }

    public ArrayList<Subtask> getListOfSubtasksByEpicId(Integer epicId) {
        ArrayList<Subtask> result = new ArrayList<>();
        for (Integer subtaskId : getEpicTaskById(epicId).getSubtasksId()) {
            result.add(getSubtaskById(subtaskId));
        }
        return result;
    }

    public void deleteAllSubtasks() {
        subtasks.clear();

        for (EpicTask task : epicTasks.values()) {
            epicTasks.put(task.getId(), new EpicTask(task.getId(), task.getName(), task.getDescription(),
                    new ArrayList<>(), TaskStatus.NEW));
        }
    }

    public Subtask getSubtaskById(int id) {
        return subtasks.get(id);
    }

    public void deleteSubtaskById(int id) {
        EpicTask epicTask = epicTasks.get(subtasks.get(id).getEpicId());
        ArrayList<Integer> subtasksId = epicTask.getSubtasksId();
        subtasksId.remove(Integer.valueOf(id));
        TaskStatus status = calculateEpicTaskStatusBySubtasksId(subtasksId);
        epicTasks.put(epicTask.getId(), new EpicTask(epicTask.getId(), epicTask.getName(), epicTask.getDescription(),
                subtasksId, status));

        subtasks.remove(id);
    }
}
