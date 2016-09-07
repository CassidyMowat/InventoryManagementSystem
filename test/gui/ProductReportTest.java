/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import dao.DaoInterface;
import domain.Product;
import gui.helpers.SimpleListModel;
import java.util.Collection;
import java.util.TreeSet;
import static org.fest.swing.core.matcher.DialogMatcher.withTitle;
import static org.fest.swing.core.matcher.JButtonMatcher.withText;
import org.fest.swing.fixture.DialogFixture;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 *
 * @author mowca765
 */
public class ProductReportTest {
    
    private DaoInterface productDao;
    private DialogFixture fest;
    
    private Product prodOne;
    private Product prodTwo;
    private Product prodThree;
    private Product prodFour;
    
    public ProductReportTest() {
    }
    
    @Before
    public void setUp() {
        
        Collection<Product> products = new TreeSet<>();
        Collection<String> categories = new TreeSet<>();
        Collection<Product> c1 = new TreeSet<>();
        
        categories.add("cat1");
	categories.add("cat2");
   
        this.prodOne = new Product(1, "name1", "desc1", "cat1", 11.0, 22);
        this.prodTwo = new Product(2, "name2", "desc2", "cat2", 33.0, 44);
        this.prodThree = new Product(3, "name3", "desc3", "cat1", 55.0, 66);
        this.prodFour = new Product(4, "name4", "desc4", "cat2", 77.0, 88);
        
        products.add(prodOne);
        products.add(prodTwo);
        products.add(prodThree);
        products.add(prodFour);
        
        c1.add(prodOne);
        c1.add(prodThree);
        
        productDao = mock(DaoInterface.class);
        
        when(productDao.getProducts()).thenReturn(products);
        //when(productDao.removeProduct(prodOne)).donothing()
     
        when(productDao.searchMap(1)).thenReturn(prodOne);
        when(productDao.searchMap(2)).thenReturn(prodTwo);
        when(productDao.searchMap(3)).thenReturn(prodThree);
        when(productDao.searchMap(4)).thenReturn(prodFour);
        
        when(productDao.categorySearch("cat1")).thenReturn(c1);
        
        when(productDao.getCategories()).thenReturn(categories);

    }
    
    @After
    public void tearDown() { 
        fest.cleanUp();
    }

    @Test
    public void testViewAll() {
        ProductReport dialog = new ProductReport(null, true, productDao);
        
        fest = new DialogFixture(dialog);
	fest.show();

	fest.robot.settings().delayBetweenEvents(25);
        
        SimpleListModel model = (SimpleListModel) fest.list("lstProd").component().getModel();
        assertEquals("list contains all products", 4, model.getSize());
    }
    
    @Test
    public void testSearchById() {
        ProductReport dialog = new ProductReport(null, true, productDao);
        
        fest = new DialogFixture(dialog);
	fest.show();

	fest.robot.settings().delayBetweenEvents(300);
        
        fest.textBox("txtSearch").enterText("1");
        fest.button("btnSearch").click();
        
        SimpleListModel model = (SimpleListModel) fest.list("lstProd").component().getModel();
        assertEquals("list Only contains one products", 1, model.getSize());
        assertTrue("list contains the correct product", model.contains(prodOne));
        
        fest.textBox("txtSearch").setText("");
        fest.button("btnSearch").click();
        
        assertEquals("list Only contains one products", 4, model.getSize());
        assertTrue("list contains the correct product", model.contains(prodOne));
        assertTrue("list contains the correct product", model.contains(prodTwo));
        assertTrue("list contains the correct product", model.contains(prodThree));
        assertTrue("list contains the correct product", model.contains(prodFour));
    }
    
    @Test
    public void testFilterByCategory(){
        ProductReport dialog = new ProductReport(null, true, productDao);
        
        fest = new DialogFixture(dialog);
	fest.show();

	fest.robot.settings().delayBetweenEvents(300);
        
        fest.comboBox("cmbCategory").selectItem("cat1");
        
        SimpleListModel model = (SimpleListModel) fest.list("lstProd").component().getModel();
        assertEquals("list Only contains two products", 2, model.getSize());
        assertTrue("list contains the correct product", model.contains(prodOne));
        assertTrue("list contains the correct product", model.contains(prodThree));
        
        fest.comboBox("cmbCategory").selectItem("");
        
        assertEquals("list contains all products", 4, model.getSize());
        assertTrue("list contains the correct product", model.contains(prodOne));
        assertTrue("list contains the correct product", model.contains(prodTwo));
        assertTrue("list contains the correct product", model.contains(prodThree));
        assertTrue("list contains the correct product", model.contains(prodFour));
    }
    
    @Test
    public void testEdit(){
        ProductReport dialog = new ProductReport(null, true, productDao);
        
        fest = new DialogFixture(dialog);
	fest.show();

	fest.robot.settings().delayBetweenEvents(300);
        
        fest.list("lstProd").selectItem(prodOne.toString());

        fest.button("btnEdit").click();

        DialogFixture editDialog = fest.dialog("productEditor");

        editDialog.textBox("txtId").requireText(prodOne.getProductID().toString());
        
        fest.textBox("txtName").setText("ProductOne");
        
        fest.button("btnSave").click();
        
        assertEquals("Product name has been changed", "ProductOne", prodOne.getName());
    }
    
//    @Test
//    public void testDelete(){
//        ProductReport dialog = new ProductReport(null, true, productDao);
//        
//        fest = new DialogFixture(dialog);
//        fest.show();
//
//	  fest.robot.settings().delayBetweenEvents(1000);
//        
//        fest.list("lstProd").selectItem(prodOne.toString());
//
//        fest.button("btnDelete").click();
//        
//        DialogFixture confirmDialog = fest.dialog(withTitle("Select an Option").andShowing()).requireVisible();
//        
//        confirmDialog.button(withText("Yes")).click();
//        
//        SimpleListModel model = (SimpleListModel) fest.list("lstProd").component().getModel();
//        
//        assertEquals("list contains all products", 3, model.getSize());
//        assertTrue("list contains the correct product", model.contains(prodTwo));
//        assertTrue("list contains the correct product", model.contains(prodThree));
//        assertTrue("list contains the correct product", model.contains(prodFour));
//    }

}
