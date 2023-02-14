package service;

import exeption.TaskNotFoundException;
import model.Task;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Сервис по работе с задачами
 */

public class TaskService {
    private final Map<Integer, Task> taskMap = new HashMap<>(); // создание коллекции taskMap типа "словарь" для хранения задач

    public void addTask(Task task) {

        this.taskMap.put(task.getId(), task);
    }

    private void remove(Integer taskId) throws TaskNotFoundException { // удаление задачи из коллекции taskMap с выбросом исключения
        if (this.taskMap.containsKey(taskId)) {
            this.taskMap.remove(taskId);
        } else {
            throw new TaskNotFoundException(taskId);
        }
    }

    public Collection<Task> getAllByDate(LocalDate date) { // метод для получения всех задач на дату указанную в параметре метода
        Collection<Task> tasksByDay = new ArrayList<>(); // создается список tasksByDay, изначально пустой
//
        for (Task task : taskMap.values()) { // объявление цикла по коллекции taskMap
            LocalDateTime taskTime = task.getTaskTime(); // в переменной taskTime дата задачи для перебираемого циклом элемента из коллекции taskMap
            LocalDateTime taskNextTime = task.getTaskNextTime(taskTime); // в переменной taskNextTime следующая дата задачи для перебираемого циклом элемента из коллекции taskMap. Это значение возвращаемое методом getTaskNextTime - свой для задач разной повторяемости
//
            if (taskNextTime == null || taskTime.toLocalDate().equals(date)) // проверяется условие: следующая дата задачи = null ИЛИ совпадает ли дата задачи с датой указанной в параметре метода
            {
                tasksByDay.add(task); // если условие выполняется, то перебираемый циклом элемент из коллекции taskMap добавляется в список tasksByDay
                continue; // перейти к следующей итерации цикла
            }

            do {
                if (taskNextTime.toLocalDate().equals(date)) { // проверяется условие: совпадает ли следующая дата задачи с датой указанной в параметре метода
                    tasksByDay.add(task); // если условие выполняется, то перебираемый циклом элемент из коллекции taskMap добавляется в список tasksByDay
                    break; //прервать выполнение цикла досрочно
                }

                taskNextTime = task.getTaskNextTime(taskNextTime); // в переменной taskNextTime следующая дата задачи для перебираемого циклом элемента в коллекции taskMap. Параметр метода getTaskNextTime - прежнее значение переменной taskNextTime (это перебор всех повторяющихся задач?)
            } while (taskNextTime.toLocalDate().isBefore(date)); // проверяется условие: меньше ли дата следующей задачи чем дата указанная в параметре метода


        }
        return tasksByDay;
    }

}

