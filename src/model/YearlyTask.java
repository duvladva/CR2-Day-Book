package model;

import exeption.IncorrectArgumentException;

import java.time.LocalDateTime;

/**
 * Ежегодная задача
 */

public class YearlyTask extends Task{

    public YearlyTask(String title, String description, TaskType type, LocalDateTime taskTime) throws IncorrectArgumentException {
        super(title, description, type, taskTime);
    }

    @Override
    public LocalDateTime getTaskNextTime(LocalDateTime dateTime){
        return dateTime.plusYears(1);
    }
}
