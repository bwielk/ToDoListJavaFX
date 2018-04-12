package todolist.datamodel;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ToDoItemTest {

    private ToDoItem item1;

    @Before
    public void before() {
        item1 = new ToDoItem("Do it tomorrow", "Take the rubbish out and water the plants, please");
    }

    @Test
    public void gettersWork(){
        assertEquals(ToDoItem.class, item1.getClass());
        assertEquals("Do it tomorrow", item1.getTitle());
        assertEquals("Take the rubbish out and water the plants, please", item1.getDescription());
    }
}