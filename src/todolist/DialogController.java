package todolist;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import todolist.datamodel.ToDoData;
import todolist.datamodel.ToDoItem;

import java.time.LocalDate;

public class DialogController {

    @FXML
    private TextField taskTitleInput;
    @FXML
    private TextArea descriptionInput;
    @FXML
    private DatePicker dueDateInput;

    public ToDoItem processInput(){
        String title = taskTitleInput.getText().trim();
        String description = descriptionInput.getText().trim();
        LocalDate dueDate = dueDateInput.getValue();

        ToDoItem newItem = (new ToDoItem(title, description, dueDate));
        ToDoData.getInstance().addToDoItem(newItem);
        return newItem;
    }
}
