package se.lexicon.model;

import java.time.LocalDate;
import java.util.Date;

public class Todo {

    private Integer todo_id;
    private String title;
    private String description;
    private LocalDate deadline;
    private boolean done;
    private Integer assignee_id;

    public Todo() {
    }

    public Todo(Integer todo_id, String title, String description, LocalDate deadline, boolean done) {
        this.todo_id = todo_id;
        this.title = title;
        this.description = description;
        this.deadline = deadline;
        this.done = done;
    }

    public Todo(Integer todo_id, String title, String description, LocalDate deadline, boolean done, Integer assignee_id) {
        this.todo_id = todo_id;
        this.title = title;
        this.description = description;
        this.deadline = deadline;
        this.done = done;
        this.assignee_id = assignee_id;
    }

    public Integer getTodo_id() {
        return todo_id;
    }

    public void setTodo_id(Integer todo_id) {
        this.todo_id = todo_id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public boolean isDone() {
        return done;
    }

    public Integer getAssignee_id() {
        return assignee_id;
    }

    @Override
    public String toString() {
        return "Todo{" +
                "todo_id=" + todo_id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", deadline=" + deadline +
                ", done=" + done +
                ", assignee_id=" + assignee_id +
                '}';
    }
}
