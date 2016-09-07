/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import dao.DaoInterface;
import domain.Product;
import java.util.Collection;
import java.util.TreeSet;
import org.fest.swing.fixture.DialogFixture;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.mockito.ArgumentCaptor;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 *
 * @author Janine
 */
public class ProductEditorTest {
    
    private DaoInterface productDao;
    private DialogFixture fest;
    
    public ProductEditorTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        Collection<String> categories = new TreeSet<>();
        categories.add("CategoryOne");
	categories.add("CategoryTwo");

        productDao = mock(DaoInterface.class);

		
	when(productDao.getCategories()).thenReturn(categories);
    }
    
    @After
    public void tearDown() {
        fest.cleanUp();
    }

    @Test
    public void testSave(){
        ProductEditor dialog = new ProductEditor(null, true, productDao);

	fest = new DialogFixture(dialog);
	fest.show();

	fest.robot.settings().delayBetweenEvents(25);

	fest.textBox("txtId").enterText("1234");
	fest.textBox("txtName").enterText("ProductOne");
        fest.textBox("txtDesc").enterText("This is productOne");
	fest.comboBox("cmbCategory").selectItem("CategoryOne");
        fest.textBox("txtPrice").enterText("10.0");
        fest.textBox("txtQuantity").enterText("100");

	fest.button("btnSave").click();

	ArgumentCaptor<Product> argument = ArgumentCaptor.forClass(Product.class);

	verify(productDao).addProduct(argument.capture());

	Product savedProduct = argument.getValue();

	assertEquals("Ensure the ID was saved", new Integer(1234), savedProduct.getProductID());
	assertEquals("Ensure the name was saved", "ProductOne", savedProduct.getName());
        assertEquals("Ensure the description was saved", "This is productOne", savedProduct.getDescription());
	assertEquals("Ensure the category was saved", "CategoryOne", savedProduct.getCategory());
        assertEquals("Ensure the price was saved", new Double(10.0), savedProduct.getPrice());
        assertEquals("Ensure the quantity was saved", new Integer(100), savedProduct.getQuantityInStock());
    }
}
