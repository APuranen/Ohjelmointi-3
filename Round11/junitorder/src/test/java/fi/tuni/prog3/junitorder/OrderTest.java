package fi.tuni.prog3.junitorder;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;

public class OrderTest{

    Order instance;

    @BeforeEach
    public void setup(){
        instance = new Order();

    }

    // ORDER
    @Test
    public void testOrder(){
        
    }

    @Test
    public void testAddItemsItem(){
        Order.Item item = new Order.Item("Jalkapallo", 3.5);
        assertThrows(IllegalArgumentException.class, () -> {
            instance.addItems(item, -3);
        });
    }

    @Test
    public void testAddItemsPrice(){
        Order.Item item = new Order.Item("Jalkapallo", 3.5);
        Order.Item item2 = new Order.Item("Jalkapallo", 6.0);
        instance.addItems(item, 1);

        assertThrows(IllegalStateException.class, () -> {
            instance.addItems(item2, 1);
        });
    }

    @Test
    public void testAddItemsString(){
        Order.Item item = new Order.Item("Jalkapallo", 3.5);
        instance.addItems(item, 1);

        assertThrows(IllegalArgumentException.class, () -> {
            instance.addItems("Jalkapallo", -5);
        });

        assertThrows(NoSuchElementException.class, () -> {
            instance.addItems("jokutavara", 1);
        });
    }

    @Test
    public void testGetEntryCount(){ //correct
        Order.Item item1 = new Order.Item("Jalkapallo", 3.5);
        Order.Item item2 = new Order.Item("Kahvikuppi", 3.5);
        Order.Item item3 = new Order.Item("Sukka", 3.5);

        instance.addItems(item1, 2);
        instance.addItems(item2, 1);
        instance.addItems(item3, 5);

        assertEquals(3, instance.getEntryCount());
    }

    @Test
    public void testGetItemCount(){ //correct
        Order.Item item = new Order.Item("Jalkapallo", 3.5);
        instance.addItems(item, 1);
        assertEquals(1, instance.getItemCount());
    }

    @Test
    public void testGetTotalPrice(){ //correct

        Order.Item item = new Order.Item("auto", 2.5);
        Order.Item item2 = new Order.Item("rekka", 3.5);

        instance.addItems(item, 10);
        instance.addItems(item2, 10);

        assertTrue( instance.getTotalPrice() == 60 );
    }

    @Test
    public void testIsEmpty(){ //correct
        assertEquals(true, instance.isEmpty());
    }

    @Test
    public void testIsEmptyEntriesNotEmpty(){
        Order.Item item = new Order.Item("Jalkapallo", 3.5);
        instance.addItems(item, 2);
        assertEquals(false, instance.isEmpty());
    }

    @Test
    public void testRemoveItems(){ //correct
        Order.Item item = new Order.Item("Jalkapallo", 3.5);
        instance.addItems(item, 5);
        instance.removeItems("Jalkapallo", 2);

        assertEquals(3, instance.getItemCount());
    }
    @Test
    public void testRemoveItemsWrongAmount(){
        Order.Item item = new Order.Item("Jalkapallo", 3.5);
        instance.addItems(item, 3);

        assertThrows(IllegalArgumentException.class, ()-> instance.removeItems("Jalkapallo", 4));
        assertThrows(IllegalArgumentException.class, ()-> instance.removeItems("Jalkapallo", -4));
    }

    @Test
    public void testRemoveItemsNoItemName(){
        assertThrows(NoSuchElementException.class, ()-> instance.removeItems("kukkuluuruu", 1));
    }

    // ENTRY
    @Test
    public void testEntry(){//correct
        Order.Item item = new Order.Item("Jalkapallo", 3.5);
        assertThrows(IllegalArgumentException.class, ()->new Order.Entry(item, -5));
    }

    @Test
    public void testGetCount(){//correct
        Order.Item item = new Order.Item("Jalkapallo", 3.5);
        Order.Entry entry = new Order.Entry(item, 5);
        assertEquals(5, entry.getCount());
    }

    @Test
    public void testGetItem(){
        Order.Item item = new Order.Item("Jalkapallo", 3.5);
        Order.Entry entry = new Order.Entry(item, 5);
        assertEquals(item, entry.getItem());
        assertNotNull(entry.getItem());
        assertSame(item, entry.getItem());
    }

    @Test
    public void testItemGetMethods(){
        Order.Item item = new Order.Item("Jalkapallo", 3.5);
        instance.addItems(item, 2);

        Order.Entry entry = instance.getEntries().get(0);

        assertTrue(entry.getItemName() == "Jalkapallo");
        assertTrue(entry.getUnitPrice() == 3.5);
        assertEquals(entry.getItem(), item);
        assertTrue(entry.getCount() == 2);
    }

    @Test
    public void testGetUnitPrice(){
        Double price = 3.5;
        Order.Item item = new Order.Item("Jalkapallo", price);
        Order.Entry entry = new Order.Entry(item, 5);
        assertTrue(entry.getUnitPrice() == price);
    }

    @Test
    public void testEntryToString(){//correct
        Order.Item item = new Order.Item("Jalkapallo", 3.5);
        Order.Entry entry = new Order.Entry(item, 5);
        assertEquals("5 units of Item(Jalkapallo, 3.50)", entry.toString());
    }

    // ITEM

    @Test
    public void testItem(){ assertThrows(IllegalArgumentException.class, () -> new Order.Item("name", -5)); }

    @Test
    public void testItemName(){ assertThrows(IllegalArgumentException.class, () -> new Order.Item(null, 4)); }

    @Test
    public void testEquals(){
        Order.Item item = new Order.Item("Jalkapallo", 3.5);
        Order.Item item2 = new Order.Item("Jalkapallo", 6.0);
        assertTrue(item.equals(item2));
    }

    @Test
    public void testGetName(){
        Order.Item item = new Order.Item("Jalkapallo", 3.5);
        instance.addItems(item, 2);
        Order.Item gotItem = instance.getEntries().get(0).getItem();

        assertEquals("Jalkapallo", gotItem.getName());
        assertTrue("Jalkapallo" == gotItem.getName());
    }

    @Test
    public void testGetPrice(){
        Order.Item item = new Order.Item("Jalkapallo", 3.5);
        instance.addItems(item, 2);
        Order.Item gotItem = instance.getEntries().get(0).getItem();

        assertTrue(3.5 == gotItem.getPrice());
    }

    @Test
    public void testItemToString(){ // correct
        Order.Item item = new Order.Item("Jalkapallo", 3.5);
        assertEquals("Item(Jalkapallo, 3.50)", item.toString());
    }
    
}
