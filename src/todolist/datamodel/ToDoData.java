package todolist.datamodel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.List;

public class ToDoData {

    private static ToDoData instance = new ToDoData();
    private static String fileName = "ToDoListTasks.txt";
    private ObservableList<ToDoItem> toDoItems;
    private DateTimeFormatter formatter; //for Date manipulations

    private ToDoData(){
        formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    }

    public static ToDoData getInstance(){
        return instance;
    }

    public ObservableList<ToDoItem> getToDoItems() {
        return toDoItems;
    }

    public void loadToDoItems() throws IOException{
        toDoItems = FXCollections.observableArrayList();
        Path path = Paths.get(fileName);
        BufferedReader reader = Files.newBufferedReader(path);
        String input;

        try {
            while((input = reader.readLine()) != null){
                String[] itemPieces = input.split("\t");
                String title = itemPieces[0];
                String description = itemPieces[1];
                String dateCreated = itemPieces[2];
                String dueDate = itemPieces[3];

                LocalDate dateCr = LocalDate.parse(dateCreated, formatter);
                LocalDate dateDue = LocalDate.parse(dueDate, formatter);
                ToDoItem newItem = new ToDoItem(title, description, dateCr, dateDue);
                toDoItems.add(newItem);
            }
        }finally{
            if(reader != null){
                reader.close();
            }
        }
    }

    public void addToDoItem(ToDoItem item){
        toDoItems.add(item);
    }

    public void storeToDoItems() throws IOException{
        Path path = Paths.get(fileName);
        BufferedWriter writer = Files.newBufferedWriter(path);
        try{
            Iterator<ToDoItem> iterator = toDoItems.iterator();
            while(iterator.hasNext()){
                ToDoItem item = iterator.next();
                writer.write(String.format("%s\t%s\t%s\t%s",
                        item.getTitle(),
                        item.getDescription(),
                        item.getDateCreated().format(formatter),
                        item.getDueDate().format(formatter)
                ));
                writer.newLine();
            }
        }finally{
            if(writer != null){
                writer.close();
            }
        }
    }
}