package model;

import exeption.IncorrectArgumentException;

import java.time.LocalDateTime;
import java.util.Objects;

public abstract class Task implements Repeatable { //абстрактный клас, находящийся во главе иерархии всех задач

    private static int idGenerator = 1;

    private int id;
    private String title;

    private String description;
    private TaskType type;
    private LocalDateTime taskTime;

    public Task(String title, String description, TaskType type, LocalDateTime taskTime) throws IncorrectArgumentException {
        this.id = idGenerator++;
        setTitle(title);
        setDescription(description);
        setType(type);
        setTaskTime(taskTime);
    }


    public int getId() {
        return id;
    }

//    public void setId(int id) {
//        this.id = id;
//    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) throws IncorrectArgumentException { // сеттер с выбросом исключения
        if (title != null && !title.isEmpty()) {
            this.title = title;
        } else {
            throw new IncorrectArgumentException("Заголовок задачи введён некорректно");
        }

    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) throws IncorrectArgumentException {
        if (description != null && !description.isEmpty()) {
            this.description = description;
        } else {
            throw new IncorrectArgumentException("Описание задачи введено некорректно");
        }
    }

    public TaskType getType() {
        return type;
    }

    public void setType(TaskType type) throws IncorrectArgumentException {
        if (type != null) {
            this.type = type;
        } else {
            throw new IncorrectArgumentException("Тип задачи введён некорректно");
        }
    }

    public LocalDateTime getTaskTime() {
        return taskTime;
    }

    public void setTaskTime(LocalDateTime taskTime) throws IncorrectArgumentException {
        if (taskTime != null) {
            this.taskTime = taskTime;
        } else {
            throw new IncorrectArgumentException("Дата и время задачи введены некорректно");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id && title.equals(task.title) && description.equals(task.description) && type == task.type && taskTime.equals(task.taskTime);
//        return title.equals(task.title) && description.equals(task.description) && type == task.type && taskTime.equals(task.taskTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, type, taskTime);
    }

    @Override
    public String toString() {
        return "Задача: " +
                "id = " + id +
                ", Заголовок = '" + title + '\'' +
                ", Описание = '" + description + '\'' +
                ", Тип = " + type +
                ", Дата = " + taskTime;
    }
}
