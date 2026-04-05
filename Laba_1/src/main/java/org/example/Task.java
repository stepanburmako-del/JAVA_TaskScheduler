package org.example;

public class Task {
    private String title;
    private String date;
    private int priority;

    public Task(String title, String date, int priority){
        this.title = title;
        this.date = date;
        this.priority = priority;
    }

    public int getPriority() {return priority;}
    public String getTitle() {return title;}

    @Override
    public String toString() {
        return String.format("[Приоритет: %d] %s (Дата: %s)", priority, title, date);
    }
}
