package todolist.datamodel;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.*;

public class ToDoItemTest {

    private ToDoItem item1;
    private ToDoItem item2;

    @Before
    public void before() {
        item1 = new ToDoItem("Do it tomorrow", "Take the rubbish out and water the plants, please", LocalDate.of(2018, 04, 12), LocalDate.of(2018, 04, 14));
        item2 = new ToDoItem("Do it in three days", "Chill out :)", LocalDate.of(2018, 04, 15));
    }

    @Test
    public void gettersWorkForConstructorWithoutAutomaticCreationDate(){
        assertEquals(ToDoItem.class, item1.getClass());
        assertEquals("Do it tomorrow", item1.getTitle());
        assertEquals("Take the rubbish out and water the plants, please", item1.getDescription());
        assertEquals("2018-04-12", item1.getDateCreated().toString());
        assertEquals("2018-04-14", item1.getDueDate().toString());
    }

    @Test
    public void gettersWorkForConstructorWithAutomaticCreationDate(){
        assertEquals("Do it in three days", item2.getTitle());
        assertEquals("Chill out :)", item2.getDescription());
        assertEquals("2018-04-12", item2.getDateCreated().toString());
        assertEquals("2018-04-15", item2.getDueDate().toString());
    }

    @Test
    public void taskGeneratesTheNumberOfDaysLeftToItsCompletion(){
        assertEquals(2, item1.daysToDueDate());
        ToDoItem tempItem = new ToDoItem("Task", "Task Description", LocalDate.of(2018, 10, 20), LocalDate.of(2018, 10, 30));
        assertEquals(10, tempItem.daysToDueDate());
    }
}