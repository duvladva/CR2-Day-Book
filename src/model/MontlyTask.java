package model;

import exeption.IncorrectArgumentException;

import java.time.LocalDateTime;

/**
 * Ежемесячная задача
 */

public class MontlyTask extends Task{
    public MontlyTask(String title, String description, TaskType type, LocalDateTime taskTime) throws IncorrectArgumentException {
        super(title, description, type, taskTime);
    }

    @Override
    public LocalDateTime getTaskNextTime(LocalDateTime dateTime){ // для одноразовой задачи метод getTaskNextTime возвращает null
        return dateTime.plusMonths(1);
    }
}
