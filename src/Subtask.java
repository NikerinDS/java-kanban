public class Subtask extends Task {
    private int epicId;

    public Subtask(int id, String name, String description, int epicId, TaskStatus status) {
        super(id, name, description, status);
        this.epicId = epicId;
    }

    public Subtask(Subtask task) {
        this(task.getId(), task.getName(), task.getDescription(), task.epicId, task.getStatus());
    }

    public Subtask(String name, String description, int epicId) {
        this(0, name, description, epicId, TaskStatus.NEW);
    }

    public int getEpicId() {
        return epicId;
    }

    @Override
    public String toString() {
        return "Subtask{" +
                "id=" + getId() +
                ", epicId=" + epicId +
                ", name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", status=" + getStatus() +
                '}';
    }
}
