/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import dungeoncrawler.Items.Item;
import dungeoncrawler.Items.Item.ItemType;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author jy
 */
public class ItemTest {

    Item item;

    @Before
    public void setUp() {
        item = new Item("test", ItemType.ITEM);
    }

    @Test
    public void getName() {
        assertEquals("test", item.getName());
    }

    @Test
    public void getType() {
        assertEquals(ItemType.ITEM, item.getType());
    }

    @Test
    public void getAction() {
        assertEquals("", item.getAction());
    }
}
