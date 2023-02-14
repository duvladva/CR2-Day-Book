import exeption.IncorrectArgumentException;
import model.*;
import service.TaskService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Main {



    private static final Pattern DATE_TIME_PATTERN = Pattern.compile("\\d{2}\\.\\d{2}\\.\\d{4} \\d{2}\\:\\d{2}"); //шаблон для ввода даты и времени
//
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy HH.mm");

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
                            // to do:  обрабатываем пункт меню 2
                            break;
                        case 3:
                            // to do:  обрабатываем пункт меню 3
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
//        scanner.useDelimiter("\n");


        String title = inputTaskTitle(scanner);
        String description = inputTaskDescription(scanner);
        TaskType type = inputTaskType(scanner);
        LocalDateTime taskTime = inputTaskTime(scanner);
        int repeatability = inputRepeatability(scanner);

        createTask(title, description, type, taskTime, repeatability);


    }

    private static String inputTaskTitle(Scanner scanner) {
        System.out.println("Введите название задачи: ");
        String title = scanner.next();

        if (title.isBlank()) {
            System.out.println("Введено некорректное название задачи");
            scanner.close();
        }
        return title;
    }

    private static String inputTaskDescription(Scanner scanner) {
        System.out.print("Введите описание задачи: ");
        String description = scanner.next();

        if (description.isBlank()) {
            System.out.println("Введено некорректное описание задачи");
            scanner.close();
        }
        return description;
    }

    private static TaskType inputTaskType(Scanner scanner) {
        System.out.println("Введите тип задачи (1 - личная, 2 - рабочая) ");
        TaskType type = null;

        int taskTypeChoice = scanner.nextInt();

        switch (taskTypeChoice) {
            case 1:
                type = TaskType.PERSONAL;
                break;
            case 2:
                type = TaskType.WORK;
                break;
            default:
                System.out.println("Тип задачи указан неверно");
                scanner.close();
        }
        return type;
    }

    private static LocalDateTime inputTaskTime(Scanner scanner) {
        System.out.println("Введите дату и время задачи в формате DD.MM.YYYY HH:MM");

        if (scanner.hasNext(DATE_TIME_PATTERN)) {
            String dateTime = scanner.next(DATE_TIME_PATTERN);
            return LocalDateTime.parse(dateTime, DATE_TIME_FORMATTER);
        } else {
            System.out.println("Дата и время задачи указаны НЕ в формате DD.MM.YYYY HH:MM");
            scanner.close();
            return null;
        }
    }


    private static int inputRepeatability(Scanner scanner) {
        System.out.println("Введите повторяемость задачи: 1 - однократная, 2 - ежедневная, " +
                "3 - каждую неделю, 4 - каждый месяц, 5 - каждый год");

        if (scanner.hasNextInt()) {
            return scanner.nextInt();
        } else {
            System.out.println("Введите числом повторяемость задачи");
            scanner.close();
        }

        return -1;
    }

    private static void createTask(String title, String description, TaskType type, LocalDateTime taskTime, int repeatability) {

        Task task = null;
        try {
            switch (repeatability) {
                case 1:
                    task = new OneTimeTask(title, description, type, taskTime);
                case 2:
                    task = new DailyTask(title, description, type, taskTime);
                case 3:
                    task = new WeeklyTask(title, description, type, taskTime);
                case 4:
                    task = new MontlyTask(title, description, type, taskTime);
                case 5:
                    task = new YearlyTask(title,  description, type, taskTime);
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
        } else {
            System.out.println("Введены некорректные данные по задаче");
        }
    }


    private static void printMenu() {
        System.out.println();
        System.out.println("1. Добавить задачу\n2. Удалить задачу\n3. Получить задачу на указанный день\n0. Выход");
    }
}