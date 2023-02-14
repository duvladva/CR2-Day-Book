package model;

import exeption.IncorrectArgumentException;

import java.time.LocalDateTime;

/**
 * Одноразовая задача
 */
public class OneTimeTask extends Task {

    public OneTimeTask(String title, String description, TaskType type, LocalDateTime taskTime) throws IncorrectArgumentException {
        super(title, description, type, taskTime);
    }

    @Override
    public LocalDateTime getTaskNextTime(LocalDateTime dateTime){ // для одноразовой задачи метод getTaskNextTime возвращает null
        return null;
    }
}
