import tasktracker.data.EpicTask;
import tasktracker.data.Subtask;
import tasktracker.data.Task;
import tasktracker.data.TaskStatus;
import tasktracker.manager.TaskManager;

public class Main {
    public static void main(String[] args) {
        TaskManager tm = new TaskManager();

        Task task1 = new Task("1", "помыть полы");
        Task task2 = new Task("2", "");

        tm.createSimpleTask(task1);
        tm.createSimpleTask(task2);
        System.out.println(tm.getListOfSimpleTasks());

        task1.setStatus(TaskStatus.DONE);
        task2.setDescription("выкинуть мусор");
        task2.setStatus(TaskStatus.IN_PROGRESS);

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

        sub1.setStatus(TaskStatus.DONE);
        sub2.setStatus(TaskStatus.IN_PROGRESS);

        System.out.println(tm.getListOfEpicTasks());
        System.out.println(tm.getListOfSubtasks());
        System.out.println();

        tm.updateSubtask(sub1);
        tm.updateSubtask(sub2);

        System.out.println(tm.getListOfEpicTasks());
        System.out.println(tm.getListOfSubtasks());
        System.out.println();

        EpicTask epic2 = new EpicTask("Ремонт", "Провести ремонт дома");
        tm.createEpicTask(epic2);
        Subtask sub4 = new Subtask("Заказ", "Заказать ремонт", epic2.getId());
        tm.createSubtask(sub4);
        sub4.setStatus(TaskStatus.DONE);
        tm.updateSubtask(sub4);

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
