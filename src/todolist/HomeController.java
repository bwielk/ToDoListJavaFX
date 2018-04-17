package todolist;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import todolist.datamodel.ToDoData;
import todolist.datamodel.ToDoItem;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.Optional;
import java.util.function.Predicate;


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
    @FXML
    private ContextMenu listContextMenu;
    @FXML
    private ToggleButton filterToggle;

    private FilteredList<ToDoItem> filteredList;
    private Predicate<ToDoItem> allItems;
    private Predicate<ToDoItem> todaysItems;

    public void initialize(){
        listContextMenu = new ContextMenu();
        MenuItem deleteMenuItem = new MenuItem("Delete this item");
        deleteMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ToDoItem item = (ToDoItem) listViewPane.getSelectionModel().getSelectedItem();
                deleteItem(item);
            }
        });
        listContextMenu.getItems().addAll(deleteMenuItem);

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

        allItems = new Predicate<ToDoItem>() {
            @Override
            public boolean test(ToDoItem toDoItem) {
                return true;
            }
        };

        todaysItems = new Predicate<ToDoItem>() {
            @Override
            public boolean test(ToDoItem toDoItem) {
                return (toDoItem.getDueDate().equals(LocalDate.now()));
            }
        };

        SortedList<ToDoItem> sortedList = new SortedList<>(ToDoData.getInstance().getToDoItems(),
                new Comparator<ToDoItem>() {
                @Override
                public int compare(ToDoItem o1, ToDoItem o2) {
                    return o2.getDueDate().compareTo(o1.getDueDate());
            }
        });

        //listViewPane.setItems(ToDoData.getInstance().getToDoItems());
        filteredList = new FilteredList<ToDoItem>(ToDoData.getInstance().getToDoItems(), allItems);

        listViewPane.setItems(filteredList);
        listViewPane.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        listViewPane.getSelectionModel().selectFirst();
        listViewPane.setCellFactory(new Callback<ListView, ListCell>() {
            @Override
            public ListCell call(ListView param){
                ListCell<ToDoItem> cell = new ListCell<ToDoItem>(){
                    @Override
                    protected void updateItem(ToDoItem item, boolean empty){
                        super.updateItem(item, empty);
                        if(empty){
                            setText(null);
                        }else{
                            setText(item.getTitle());
                            if(item.getDueDate().equals(LocalDate.now())){
                                setTextFill(Color.RED);
                            }else if(item.daysToDueDate()==1 || item.daysToDueDate()==2){
                                setTextFill(Color.CHOCOLATE);
                            }else if(item.daysToDueDate()<=10 && item.daysToDueDate() > 2){
                                setTextFill(Color.DARKORANGE);
                            }else if(item.daysToDueDate() < 0){
                                setTextFill(Color.BLUEVIOLET);
                            }
                        }
                    }
                };
                cell.emptyProperty().addListener(
                        (obs, wasEmpty, isNowEmpty) ->{
                            if(isNowEmpty){
                                cell.setContextMenu(null);
                            }else{
                                cell.setContextMenu(listContextMenu);}
                        });
                return cell;
            };
        });
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
        dialog.setTitle("Add a new to do item");
        dialog.setHeaderText("To add a new item, type in its name(or title), description and also choose its due date");
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
            ToDoItem newItem = controller.processInput();
            //after adding a new item, the focus goes into it
            listViewPane.getSelectionModel().select(newItem);
        }
    }

    public void deleteItem(ToDoItem itemToDelete){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete an item");
        alert.setHeaderText("Are you sure you would like to delete this item?\n" + "To do item name: " + itemToDelete.getTitle() + "\nItem created on :" + itemToDelete.getDateCreated());
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK){
            ToDoData.getInstance().deleteToDoItem(itemToDelete);
        }
    }

    public void handleKeyPressed(KeyEvent e){
        ToDoItem selectedItem = (ToDoItem) listViewPane.getSelectionModel().getSelectedItem();
        if(selectedItem != null){
            if(e.getCode().equals(KeyCode.DELETE)){
                deleteItem(selectedItem);
            }
        }
    }

    public void handleFilterToggle(){
        ToDoItem selectedItem = (ToDoItem) listViewPane.getSelectionModel().getSelectedItem();
        if(filterToggle.isSelected()){
            filteredList.setPredicate(todaysItems);
            if(filteredList.isEmpty()){
                toDoItemView.clear();
                taskCreated.setText("");
                taskDeadline.setText("");
                taskTitle.setText("");
            }else if(filteredList.contains(selectedItem)){
                listViewPane.getSelectionModel().select(selectedItem);
            }else{
                listViewPane.getSelectionModel().selectFirst();
            }
        }else{
            filteredList.setPredicate(allItems);
            listViewPane.getSelectionModel().select(selectedItem);
        }
    }
}