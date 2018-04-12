package todolist.datamodel;

import java.time.LocalDate;

public class ToDoItem {

    private String title;
    private String description;
    private final LocalDate dateCreated;
    private LocalDate dueDate;

    public ToDoItem(String title, String description, LocalDate dateCreated, LocalDate dueDate){
        this.title = title;
        this.description = description;
        this.dateCreated = dateCreated;
        this.dueDate = dueDate;
    }

    public ToDoItem(String title, String description, LocalDate dueDate){
        this.title = title;
        this.description = description;
        this.dateCreated = LocalDate.now();
        this.dueDate = dueDate;
    }

    public String getTitle(){
        return title;
    }

    public String getDescription(){
        return description;
    }

    public LocalDate getDateCreated(){
        return dateCreated;
    }

    public LocalDate getDueDate(){
        return dueDate;
    }

    @Override
    public String toString(){
        return title;
    }
}
