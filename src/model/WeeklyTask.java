package model;

import exeption.IncorrectArgumentException;

import java.time.LocalDateTime;

/**
 * Еженедельная задача
 */

public class WeeklyTask extends Task{
    public WeeklyTask(String title, String description, TaskType type, LocalDateTime taskTime) throws IncorrectArgumentException {
        super(title, description, type, taskTime);
    }

    @Override
    public LocalDateTime getTaskNextTime(LocalDateTime dateTime){ // для одноразовой задачи метод getTaskNextTime возвращает null
        return dateTime.plusWeeks(1);
    }
}
