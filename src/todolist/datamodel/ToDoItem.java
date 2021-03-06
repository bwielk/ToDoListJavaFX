package todolist.datamodel;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class ToDoItem implements Comparable{

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

    public long daysToDueDate(){
       return ChronoUnit.DAYS.between(dateCreated, dueDate);
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }
}
