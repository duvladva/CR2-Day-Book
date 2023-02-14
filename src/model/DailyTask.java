package model;

import exeption.IncorrectArgumentException;

import java.time.LocalDateTime;

/**
 * Ежедневная задача
 */

public class DailyTask extends Task{

    public DailyTask(String title, String description, TaskType type, LocalDateTime taskTime) throws IncorrectArgumentException {
        super(title, description, type, taskTime);
    }

    @Override
    public LocalDateTime getTaskNextTime(LocalDateTime dateTime){ // для одноразовой задачи метод getTaskNextTime возвращает null
        return dateTime.plusDays(1);
    }
}
