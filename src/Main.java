public class Main {
    public static void main(String[] args) {
        TaskManager tm = new TaskManager();

        Task task1 = new Task("1", "помыть полы");
        Task task2 = new Task("2", "");

        tm.createSimpleTask(task1);
        tm.createSimpleTask(task2);
        System.out.println(tm.getListOfSimpleTasks());

        task2 = new Task(task2.getId(), task2.getName(), "выкинуть мусор", TaskStatus.IN_PROGRESS);
        task1 = new Task(task1.getId(), task1.getName(), task1.getDescription(), TaskStatus.DONE);

        tm.updateSimpleTask(task1);
        tm.updateSimpleTask(task2);
        System.out.println(tm.getListOfSimpleTasks());

        tm.deleteAllSimpleTasks();
        System.out.println(tm.getListOfSimpleTasks());

        System.out.println();
        EpicTask epic1 = new EpicTask("Поход", "Подготовка к пешему походу");
        tm.createEpicTask(epic1);
        Subtask sub1 = new Subtask("Маршрут", "Проработать маршут", epic1.getId());
        Subtask sub2 = new Subtask("Продукты", "Закупит продукты", epic1.getId());
        Subtask sub3 = new Subtask("Снаряга", "Проверить снаряжение", epic1.getId());
        tm.createSubtask(sub1);
        tm.createSubtask(sub2);
        tm.createSubtask(sub3);
        System.out.println(tm.getListOfEpicTasks());
        System.out.println(tm.getListOfSubtasks());
        System.out.println();

        tm.updateSubtask(new Subtask(sub1.getId(),sub1.getName(),sub1.getDescription(),sub1.getEpicId(),TaskStatus.DONE));
        tm.updateSubtask(new Subtask(sub2.getId(),sub2.getName(),sub2.getDescription(),sub2.getEpicId(),TaskStatus.IN_PROGRESS));
        System.out.println(tm.getListOfEpicTasks());
        System.out.println(tm.getListOfSubtasks());
        System.out.println();

        EpicTask epic2 = new EpicTask("Ремонт", "Провести ремонт дома");
        tm.createEpicTask(epic2);
        Subtask sub4 = new Subtask("Заказ", "Заказать ремонт", epic2.getId());
        tm.createSubtask(sub4);
        tm.updateSubtask(new Subtask(sub4.getId(),sub4.getName(),sub4.getDescription(),sub4.getEpicId(),TaskStatus.DONE));

        System.out.println(tm.getListOfEpicTasks());
        System.out.println(tm.getListOfSubtasks());
        System.out.println();

        tm.deleteSubtaskById(sub4.getId());
        tm.deleteEpicTaskById(epic1.getId());
        System.out.println(tm.getListOfEpicTasks());
        System.out.println(tm.getListOfSubtasks());
        System.out.println();
    }
}
