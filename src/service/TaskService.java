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
    public Map<Integer, Task> taskMap = new HashMap<>(); // создание коллекции taskMap типа "мапа" (словарь) для хранения задач

    public void addTask(Task task) {
        this.taskMap.put(task.getId(), task);
//        taskMap.put(task.getId(), task);
    }

    public void remove(Integer taskId) throws TaskNotFoundException { // удаление задачи из коллекции taskMap с выбросом исключения
        if (this.taskMap.containsKey(taskId)) {
            this.taskMap.remove(taskId);
            System.out.println("Задача с id = " + taskId + " удалена");
        } else {
            throw new TaskNotFoundException(taskId);
        }
    }

//    public Collection<Task> getAllByDate(LocalDate date) { // метод для получения всех задач на дату указанную в параметре метода
//        Collection<Task> tasksByDay = new ArrayList<>(); // создается список tasksByDay, изначально пустой
////
//        for (Task task : taskMap.values()) { // объявление цикла по коллекции taskMap
//            LocalDateTime taskTime = task.getTaskTime(); // в переменной taskTime дата задачи для перебираемого циклом элемента из коллекции taskMap
//            LocalDateTime taskNextTime = task.getTaskNextTime(taskTime); // в переменной taskNextTime следующая дата задачи для перебираемого циклом элемента из коллекции taskMap.
//            Это значение возвращаемое методом getTaskNextTime - свой для задач разной повторяемости
////
//            if (taskNextTime == null || taskTime.toLocalDate().equals(date)) // проверяется условие: следующая дата задачи = null ИЛИ совпадает ли дата задачи с датой,
//            указанной в параметре метода
//            {
//                tasksByDay.add(task); // если условие выполняется, то перебираемый циклом элемент из коллекции taskMap добавляется в список tasksByDay
//                continue; // перейти к следующей итерации цикла
//            }
//
//            do {
//                if (taskNextTime.toLocalDate().equals(date)) { // проверяется условие: совпадает ли следующая дата задачи с датой указанной в параметре метода
//                    tasksByDay.add(task); // если условие выполняется, то перебираемый циклом элемент из коллекции taskMap добавляется в список tasksByDay
//                    break; //прервать выполнение цикла досрочно
//                }
//
//                taskNextTime = task.getTaskNextTime(taskNextTime); // в переменной taskNextTime следующая дата задачи для перебираемого циклом элемента в коллекции taskMap.
//                Параметр метода getTaskNextTime - прежнее значение переменной taskNextTime (это перебор всех повторяющихся задач?)
//            } while (taskNextTime.toLocalDate().isBefore(date)); // проверяется условие: меньше ли дата следующей задачи чем дата указанная в параметре метода
//
//
//        }
//        return tasksByDay;
//    }

    public Collection<Task> getAllByDate(LocalDate date) {

        Collection<Task> tasksByDay = new ArrayList<>(); // возвращаемый методом массив
        Collection<Task> allTasks = taskMap.values();

        for (Task task : allTasks) {
            LocalDateTime currentDateTime = task.getTaskTime(); // метод task.getTaskTime() возвращает значение поля taskTime из задачи - это дата и время присвоенные при создании задачи

            if (currentDateTime.toLocalDate().equals(date)) { // метод toLocalDate() возвращает объект класса LocalDate. Здесь дата и время присвоенные при создании задачи сравниваются
                // с датой для которой нужен список задач
                tasksByDay.add(task); // если даты совпадают, то задача записывается в возвращаемый этим методом массив tasksByDay
//                break;
            }

            LocalDateTime taskNextTime = currentDateTime; // в переменную taskNextTime записывается дата и время присвоенные при создании задачи

            do {
                taskNextTime = task.getTaskNextTime(taskNextTime); // на основании даты и времени присвоенных при создании задачи вычисляем дату И ВРЕМЯ следующей задачи

//                if (taskNextTime !== null) { // если дата И ВРЕМЯ следующей задачи не существует, то выход из цикла

                if (taskNextTime.toLocalDate().equals(date)) { //дата И ВРЕМЯ следующей задачи приводятся к типу даты, для которой нужен список задач и сравнивается с ней
                    tasksByDay.add(task); // если даты совпадают, то задача записывается в возвращаемый этим методом массив tasksByDay и выход из цикла
                    break;
                }

            } while (taskNextTime.toLocalDate().isBefore(date)); // условие, при котором выполняется цикл do: если дата и время следующей задачи
            // меньше даты, для которой нужен список задач. Метод isBefore() — возвращает значение true, если текущий объект меньше указанного в скобках,

        }
        return tasksByDay;
    }

}

