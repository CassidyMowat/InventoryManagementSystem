/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import domain.Product;
import java.util.Arrays;
import java.util.Collection;
import java.util.TreeSet;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

/**
 *
 * @author mowca765
 */
@RunWith(Parameterized.class)
public class DaoTest {
   
    private DaoInterface productDao;
    
    private Product prodOne;
    private Product prodTwo;
    
    @Parameterized.Parameters
    public static Collection<?> productDao(){
        return Arrays.asList(new Object[][]{
            {new ProductList()},
            {new ProductDBM(
                    "jdbc:h2:tcp://localhost/project-testing;IFEXISTS=TRUE")}
        });
    }
        
    public DaoTest(DaoInterface dao) {
        this.productDao = dao;
    }

    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        this.prodOne = new Product(1, "name1", "desc1", "cat1", 11.0, 22);
        this.prodTwo = new Product(2, "name2", "desc2", "cat2", 33.0, 44);
        
        productDao.addProduct(prodOne);
        productDao.addProduct(prodTwo);
    }
    
    @After
    public void tearDown() {
        productDao.removeProduct(prodOne);
        productDao.removeProduct(prodTwo);
    }

    @Test
    public void testDaoSaveAndDelete(){
        Product savedProd = new Product(3, "name", "cat", "desc", 1.0, 2);
        
        productDao.addProduct(savedProd);
        
        Product retrieved = productDao.searchMap(3);
        
        assertEquals("Retrieved product should be the same as the saved one", savedProd, retrieved);
        
        productDao.removeProduct(savedProd);
        
        retrieved = productDao.searchMap(3);
        
        assertNull("Product should no longer exist", retrieved);
    }
    
    @Test
    public void testDaoGetAll(){
        Collection<Product> products = productDao.getProducts();
        
        assertTrue("prodOne should exist", products.contains(prodOne));
        assertTrue("prodTwo should exist", products.contains(prodTwo));
        
        assertEquals("Only 2 products in result", 2, products.size());
        
        for (Product p : products){
            if (p.equals(prodOne)){
                assertEquals(prodOne.getProductID(), p.getProductID());
                assertEquals(prodOne.getName(), p.getName());
                assertEquals(prodOne.getDescription(), p.getDescription());
                assertEquals(prodOne.getCategory(), p.getCategory());
                assertEquals(prodOne.getPrice(), p.getPrice());
                assertEquals(prodOne.getQuantityInStock(), p.getQuantityInStock());
            }
        }
    }
    
    @Test
    public void testDaoFindById(){
        Product product = productDao.searchMap(1);
        
        assertEquals("Retrieved product should be prodOne", product, prodOne);
        
        if (product.equals(prodOne)){
            assertEquals(prodOne.getProductID(), product.getProductID());
            assertEquals(prodOne.getName(), product.getName());
            assertEquals(prodOne.getDescription(), product.getDescription());
            assertEquals(prodOne.getCategory(), product.getCategory());
            assertEquals(prodOne.getPrice(), product.getPrice());
            assertEquals(prodOne.getQuantityInStock(), product.getQuantityInStock());
        }
        
        Product product2 = productDao.searchMap(5);
        
        assertEquals("Retrieved product should not exist", product2, null);
    }
    
    @Test
    public void testGetCategories(){
        Collection<String> test = new TreeSet<>();
        
        test.add(prodOne.getCategory());
        test.add(prodTwo.getCategory());
        
        assertEquals("Categories were retrieved correctly", test, productDao.getCategories());
        assertEquals("Correct number of categories", 2, test.size());
    }
    
    @Test
    public void testCategorySearch(){
        Collection<Product> test = new TreeSet<>();
        
        test.add(prodOne);
        
        assertEquals("Correct product is returned", test, productDao.categorySearch("cat1"));
        assertEquals("Nothing will be returned", null, productDao.categorySearch("cat5"));
        
    }
    
    @Test
    public void testEdit(){
        prodOne.setName("EditedName");

        productDao.addProduct(prodOne);
        
        Product test = productDao.searchMap(1);
        
        assertEquals("ProdOne has the changed name", "EditedName", test.getName());
        assertEquals(prodOne.getProductID(), test.getProductID());
        assertEquals(prodOne.getDescription(), test.getDescription());
        assertEquals(prodOne.getCategory(), test.getCategory());
        assertEquals(prodOne.getPrice(), test.getPrice());
        assertEquals(prodOne.getQuantityInStock(), test.getQuantityInStock());
    }

}
