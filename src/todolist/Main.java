package todolist;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import todolist.datamodel.ToDoData;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("mainView.fxml"));
        primaryStage.setTitle("Your To Do List");
        primaryStage.setScene(new Scene(root, 800, 475));
        primaryStage.show();
    }

    @Override
    public void init(){
        try{
            ToDoData.getInstance().loadToDoItems();
        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void stop(){
        try{
            ToDoData.getInstance().storeToDoItems();

        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}