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

    public void processInput(){
        String title = taskTitleInput.getText().trim();
        String description = descriptionInput.getText().trim();
        LocalDate dueDate = dueDateInput.getValue();

        ToDoData.getInstance().addToDoItem(new ToDoItem(title, description, dueDate));
    }
}
