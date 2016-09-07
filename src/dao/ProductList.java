/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import domain.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author mowca765
 */
public class ProductList implements DaoInterface {
    
    private static Map<Integer, Product> productMap = new HashMap<>();
    private static Map<String, Set<Product>> categoryMap = new HashMap<>();
    
    /**
     *
     * @param newProduct
     */
    @Override
    public void addProduct(domain.Product newProduct){
        productMap.put(newProduct.getProductID(), newProduct);
        
        String category = newProduct.getCategory();
        if (categoryMap.containsKey(category)){
            categoryMap.get(category).add(newProduct);
        } else {
            Set<Product> categorySet = new TreeSet<>();
            categorySet.add(newProduct);
            categoryMap.put(category, categorySet);
        }
        
    }
    
    /**
     *
     * @param rmProduct
     */
    @Override
    public void removeProduct(domain.Product rmProduct){
        productMap.remove(rmProduct.getProductID());
        categoryMap.remove(rmProduct.getCategory());
    }
    
    /**
     *
     * @return
     */
    @Override
    public Collection<Product> getProducts(){
        return productMap.values();
    }
    
    /**
     *
     * @return
     */
    @Override
    public Collection<String> getCategories(){
        return categoryMap.keySet();
    }
    
    /**
     *
     * @param id
     * @return
     */
    @Override
    public Product searchMap(Integer id){
        Product p = productMap.get(id);
        return p;
    }
    
    /**
     *
     * @param category
     * @return
     */
    @Override
    public Collection<Product> categorySearch(String category){    
        return categoryMap.get(category);
    }
    
}
