import exeption.IncorrectArgumentException;
import exeption.TaskNotFoundException;
import model.*;
import service.TaskService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Main {


    private static final Pattern DATE_TIME_PATTERN = Pattern.compile("\\d{2}\\.\\d{2}\\.\\d{4} \\d{2}\\:\\d{2}"); //шаблон для ввода даты и времени
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    private static final Pattern DATE_PATTERN = Pattern.compile("\\d{2}\\.\\d{2}\\.\\d{4}"); //шаблон для ввода даты и времени
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    private static final TaskService taskService = new TaskService();


    public static void main(String[] args) {

        try (Scanner scanner = new Scanner(System.in)) {
            label:
            while (true) {
                printMenu();
                System.out.print("Выберите пункт меню: ");
                if (scanner.hasNextInt()) {
                    int menu = scanner.nextInt();
                    switch (menu) {
                        case 1:
                            inputTask(scanner);
                            break;
                        case 2:
                            removeTask(scanner);
                            break;
                        case 3:
                            printTaskByDay(scanner);
                            break;
                        case 0:
                            break label;
                    }
                } else {
                    scanner.next();
                    System.out.println("Выберите пункт меню из списка!");

                }
            }
        }
    }


    private static void inputTask(Scanner scanner) {
        scanner.useDelimiter("\n"); // метод useDelimiter() — задает шаблон регулярного выражения, который будет использоваться для разделения фрагментов.


        String title = inputTaskTitle(scanner);
        String description = inputTaskDescription(scanner);
        TaskType type = inputTaskType(scanner);
        LocalDateTime taskTime = inputTaskTime(scanner);
        int repeatability = inputRepeatability(scanner);

        createTask(title, description, type, taskTime, repeatability);


    }

    private static void removeTask(Scanner scanner) {
        System.out.println("Введите id удаляемой задачи");

        while (!scanner.hasNextInt()) {
            System.out.println("Введите id удаляемой задачи");
            scanner.next();
        }

        int id = scanner.nextInt();

        try {
            taskService.remove(id);
        } catch (TaskNotFoundException e) {
            System.out.println(e.getMessage());
        }

    }

    //    private static void printTaskByDay(Scanner scanner) {
//        System.out.println("Введите дату задачи в формате DD.MM.YYYY");
//
//        if (scanner.hasNext(DATE_PATTERN)) {
//            String dateTime = scanner.next(DATE_PATTERN);
//            LocalDate inputDate = LocalDate.parse(dateTime, DATE_FORMATTER);
//
//            Collection<Task> tasksByDayA = taskService.getAllByDate(inputDate);
//
//            for (Task task : tasksByDayA) {
//                System.out.println(task);
//            }
//
//        } else {
//            System.out.println("Дата задачи указана НЕ в формате DD.MM.YYYY");
//            scanner.close();
//
//        }
//    }
    private static void printTaskByDay(Scanner scanner) {
        System.out.println("Для получения списка задач укажите конкретную дату в формате DD.MM.YYYY");
        String dateTime;

        while (!scanner.hasNext(DATE_PATTERN)) {
            System.out.println("Укажите дату в формате DD.MM.YYYY");
            scanner.next();
        }
        dateTime = scanner.next(DATE_PATTERN);
        LocalDate inputDate = LocalDate.parse(dateTime, DATE_FORMATTER);
        Collection<Task> tasksByDay = taskService.getAllByDate(inputDate);

        if (tasksByDay.size() == 0) {
            System.out.println("Не найдены задачи на указанную дату");
        }
            for (Task task : tasksByDay) {
                System.out.println(task);
            }

    }


    private static String inputTaskTitle(Scanner scanner) {

        System.out.println("Введите заголовок задачи: ");
        String title = scanner.next();

        while (title.isBlank()) {
            System.out.println("Заголовок отсутствует, введите вновь: ");
            title = scanner.next();
        }
        return title;
    }


    private static String inputTaskDescription(Scanner scanner) {
        System.out.print("Введите описание задачи: ");
        String description = scanner.next();

        while (description.isBlank()) {
            System.out.println("Описание отсутствует, введите вновь: ");
            description = scanner.next();
        }
        return description;
    }

    private static TaskType inputTaskType(Scanner scanner) {

        TaskType type = null;

        while (type == null) {
            System.out.println("Введите тип задачи (1 - личная, 2 - рабочая) ");

            if (scanner.hasNextInt()) {
                int taskTypeChoice = scanner.nextInt();
                switch (taskTypeChoice) {
                    case 1:
                        type = TaskType.PERSONAL;
                        break;
                    case 2:
                        type = TaskType.WORK;
                        break;
                    default:
                        System.out.println("Выберите тип задачи из списка!");
                }
            } else {
                scanner.next();
                System.out.println("Выберите тип задачи из списка!");
            }
        }
        return type;
    }


    private static LocalDateTime inputTaskTime(Scanner scanner) {
        System.out.println("Введите дату и время задачи в формате DD.MM.YYYY HH:MM");
        String dateTime;

        while (!scanner.hasNext(DATE_TIME_PATTERN)) {
            System.out.println("Дата и время задачи указаны НЕ в формате DD.MM.YYYY HH:MM");
            scanner.next();
        }

        dateTime = scanner.next(DATE_TIME_PATTERN);
        return LocalDateTime.parse(dateTime, DATE_TIME_FORMATTER);

    }


    private static int inputRepeatability(Scanner scanner) {
        System.out.println("Введите повторяемость задачи: 1 - однократная, 2 - ежедневная, " +
                "3 - каждую неделю, 4 - каждый месяц, 5 - каждый год");

        while (!scanner.hasNextInt()) {
            System.out.println("Введите числом повторяемость задачи");
            scanner.next();
        }
        int repeatability = scanner.nextInt();
        while (repeatability < 1 || repeatability > 5) {
            System.out.println("Введите повторяемость числом от 1 до 5");
            repeatability = scanner.nextInt();
        }

        return repeatability;
    }


    private static void createTask(String title, String description, TaskType type, LocalDateTime taskTime,
                                   int repeatability) {

        Task task = null;
        try {
            switch (repeatability) {
                case 1:
                    task = new OneTimeTask(title, description, type, taskTime);
                    break;
                case 2:
                    task = new DailyTask(title, description, type, taskTime);
                    break;
                case 3:
                    task = new WeeklyTask(title, description, type, taskTime);
                    break;
                case 4:
                    task = new MontlyTask(title, description, type, taskTime);
                    break;
                case 5:
                    task = new YearlyTask(title, description, type, taskTime);
                    break;
                default:
                    System.out.println("Повторяемость задачи введена некорректно");
            }
        } catch (IncorrectArgumentException e) {
            System.out.println(e.getMessage());
        }

        if (task != null) {
            taskService.addTask(task);
            System.out.println("Задача добавлена");
            System.out.println(task);
        } else {
            System.out.println("Введены некорректные данные по задаче");
        }
    }


    private static void printMenu() {
        System.out.println();
        System.out.println("1. Добавить задачу\n2. Удалить задачу\n3. Получить задачу на указанный день\n0. Выход");
    }
}