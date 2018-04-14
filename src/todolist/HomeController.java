package todolist;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import todolist.datamodel.ToDoData;
import todolist.datamodel.ToDoItem;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.Optional;


public class HomeController {

    @FXML
    private BorderPane homePanel;
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
        listViewPane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                if(newValue != null){
                    ToDoItem selectedItem = (ToDoItem) listViewPane.getSelectionModel().getSelectedItem();
                    toDoItemView.setText(selectedItem.getDescription());
                    displayToDoItemDetails(selectedItem);
                }
            }

        });
        listViewPane.getItems().setAll(ToDoData.getInstance().getToDoItems());
        listViewPane.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        listViewPane.getSelectionModel().selectFirst();
    }

    private void displayToDoItemDetails(ToDoItem selectedItem){
        StringBuilder content = new StringBuilder();
        content.append("DESCRIPTION: \n" + selectedItem.getDescription() + "\n\n");
        toDoItemView.setText(content.toString());
        taskTitle.setText(selectedItem.getTitle());
        DateTimeFormatter formattedDate = DateTimeFormatter.ofPattern("MMMM d, YYYY");
        taskCreated.setText("Created on " + formattedDate.format(selectedItem.getDateCreated()));
        taskDeadline.setText("\nThe task deadline is on " + formattedDate.format(selectedItem.getDueDate()) + "\nand you have " + selectedItem.daysToDueDate() + " days left to complete it!");
    }

    @FXML
    public void showAddNewItemDialog(){
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(homePanel.getScene().getWindow());
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("newFileDialog.fxml"));
        try{
            dialog.getDialogPane().setContent(fxmlLoader.load());
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK){
            DialogController controller = fxmlLoader.getController();
            controller.processInput();
            System.out.println("OK pressed");
        }else{
            System.out.println("Cancel pressed");
        }
    }
}