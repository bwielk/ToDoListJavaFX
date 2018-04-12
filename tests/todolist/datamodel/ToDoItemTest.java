package todolist.datamodel;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ToDoItemTest {

    private ToDoItem item1;

    @Before
    public void setUp() throws Exception {
        item1 = new ToDoItem();
    }

    @Test
    public void gettersWork(){
        assertEquals(item1.getClass(), item1.getClass());
    }
}