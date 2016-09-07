/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import domain.Product;
import java.util.Collection;

/**
 *
 * @author mowca765
 */
public interface DaoInterface {
    public void addProduct(domain.Product newProduct);
    public void removeProduct(domain.Product rmProduct);
    public Collection<Product> getProducts();
    public Collection<String> getCategories();
    public Product searchMap(Integer id);
    public Collection<Product> categorySearch(String category); 
}
