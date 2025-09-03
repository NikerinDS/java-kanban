import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {
    private int newId = 1;
    private HashMap<Integer, Task> simpleTasks;
    private HashMap<Integer, EpicTask> epicTasks;
    private HashMap<Integer, Subtask> subtasks;

    public TaskManager() {
        this.simpleTasks = new HashMap<>();
        this.epicTasks = new HashMap<>();
        this.subtasks = new HashMap<>();
    }

    public void createSimpleTask(Task task) {
        task.setId(getNewId());
        simpleTasks.put(task.getId(), new Task(task));
    }

    public void updateSimpleTask(Task task) {
        simpleTasks.put(task.getId(), new Task(task));
    }

    public ArrayList<Task> getListOfSimpleTasks() {
        ArrayList<Task> listOfTasks = new ArrayList<>();
        for (Task task : simpleTasks.values()) {
            listOfTasks.add(new Task(task));
        }
        return listOfTasks;
    }

    public void deleteAllSimpleTasks() {
        simpleTasks.clear();
    }

    public Task getSimpleTaskById(int id) {
        return new Task(simpleTasks.get(id));
    }

    public void deleteSimpleTaskById(int id) {
        simpleTasks.remove(id);
    }

    public void createEpicTask(EpicTask task) {
        task.setId(getNewId());
        task.setStatus(calculateEpicTaskStatusBySubtasksId(task.getSubtasksId()));
        epicTasks.put(task.getId(), new EpicTask(task));
    }

    public void updateEpicTask(EpicTask task) {
        task.setStatus(calculateEpicTaskStatusBySubtasksId(task.getSubtasksId()));
        epicTasks.put(task.getId(), new EpicTask(task));
    }

    public ArrayList<EpicTask> getListOfEpicTasks() {
        ArrayList<EpicTask> listOfTasks = new ArrayList<>();
        for (EpicTask task : epicTasks.values()) {
            listOfTasks.add(new EpicTask(task));
        }
        return listOfTasks;
    }

    public void deleteAllEpicTasks() {
        epicTasks.clear();
        subtasks.clear();
    }

    public EpicTask getEpicTaskById(int id) {
        return new EpicTask(epicTasks.get(id));
    }

    public void deleteEpicTaskById(int id) {
        for (Integer subtaskId : getEpicTaskById(id).getSubtasksId()) {
            subtasks.remove(subtaskId);
        }
        epicTasks.remove(id);
    }

    public void createSubtask(Subtask task) {
        task.setId(getNewId());
        subtasks.put(task.getId(), new Subtask(task));

        EpicTask epicTask = epicTasks.get(task.getEpicId());
        ArrayList<Integer> subtasksId = epicTask.getSubtasksId();
        subtasksId.add(task.getId());
        updateEpicTask(new EpicTask(epicTask.getId(), epicTask.getName(), epicTask.getDescription(),
                subtasksId, epicTask.getStatus()));
    }

    public void updateSubtask(Subtask task) {
        subtasks.put(task.getId(), new Subtask(task));
        updateEpicTask(getEpicTaskById(task.getEpicId()));
    }

    public ArrayList<Subtask> getListOfSubtasks() {
        ArrayList<Subtask> listOfTasks = new ArrayList<>();
        for (Subtask task : subtasks.values()) {
            listOfTasks.add(new Subtask(task));
        }
        return listOfTasks;
    }

    public ArrayList<Subtask> getListOfSubtasksByEpicId(Integer epicId) {
        ArrayList<Subtask> listOfTasks = new ArrayList<>();
        for (Integer subtaskId : getEpicTaskById(epicId).getSubtasksId()) {
            listOfTasks.add(getSubtaskById(subtaskId));
        }
        return listOfTasks;
    }

    public void deleteAllSubtasks() {
        subtasks.clear();

        for (EpicTask task : epicTasks.values()) {
            epicTasks.put(task.getId(), new EpicTask(task.getId(), task.getName(), task.getDescription(),
                    new ArrayList<>(), TaskStatus.NEW));
        }
    }

    public Subtask getSubtaskById(int id) {
        return new Subtask(subtasks.get(id));
    }

    public void deleteSubtaskById(int id) {
        EpicTask epicTask = epicTasks.get(subtasks.get(id).getEpicId());
        ArrayList<Integer> subtasksId = epicTask.getSubtasksId();
        subtasksId.remove(Integer.valueOf(id));
        updateEpicTask(new EpicTask(epicTask.getId(), epicTask.getName(), epicTask.getDescription(),
                subtasksId, epicTask.getStatus()));

        subtasks.remove(id);
    }

    private int getNewId() {
        return newId++;
    }

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
}
