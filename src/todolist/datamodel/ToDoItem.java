package todolist.datamodel;

import java.time.LocalDate;

public class ToDoItem {

    private String title;
    private String description;

    ToDoItem(String title, String description){
        this.title = title;
        this.description = description;
    }

    /*
    ToDoItem(String title, String description){
        this.title = title;
        this.description = description;

    }*/

    public String getTitle(){
        return title;
    }

    public String getDescription(){
        return description;
    }
}
