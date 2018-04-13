package todolist;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;
import todolist.datamodel.ToDoItem;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Controller {

    private List<ToDoItem> todoItems;

    @FXML
    private ListView listViewPane;
    @FXML
    private TextArea toDoItemView;
    @FXML
    private Label taskTitle;
    @FXML
    private Label taskCreated;
    @FXML
    private Label taskDeadline;

    public void initialize(){
        todoItems = new ArrayList<>();
        todoItems.add(new ToDoItem("Do it in three days", "Chill out :)", LocalDate.of(2018, Month.APRIL, 15)));
        todoItems.add(new ToDoItem("See your aunt", "Bring her some cake", LocalDate.of(2018, Month.FEBRUARY, 1)));
        todoItems.add(new ToDoItem("John's birthday", "Make that day special", LocalDate.of(2018, Month.DECEMBER, 5)));
        todoItems.add(new ToDoItem("Finish the prototypes", "Use Photoshop for that", LocalDate.of(2018, Month.NOVEMBER, 25)));
        populateListView();
        listViewPane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                if(newValue != null){
                    ToDoItem selectedItem = (ToDoItem) listViewPane.getSelectionModel().getSelectedItem();
                    toDoItemView.setText(selectedItem.getDescription());
                    StringBuilder content = new StringBuilder();
                    content.append("DESCRIPTION: \n" + selectedItem.getDescription() + "\n\n");
                    toDoItemView.setText(content.toString());
                    taskTitle.setText(selectedItem.getTitle());
                    taskCreated.setText("Created on " + selectedItem.getDateCreated().toString());
                    taskDeadline.setText("\nThe task deadline is on " + selectedItem.getDueDate() + " \nand you have " + selectedItem.daysToDueDate() + "  days left to complete it!");
                }
            }
        });
        listViewPane.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        listViewPane.getSelectionModel().selectFirst();
    }

   private void populateListView(){
        for(int i=0; i<todoItems.size(); i++){
           listViewPane.getItems().add(todoItems.get(i));
        }
    }
}
