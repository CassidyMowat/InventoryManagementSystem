/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import domain.Product;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.TreeSet;

/**
 *
 * @author mowca765
 */
public class ProductDBM implements DaoInterface{
    
    private String url = "jdbc:h2:tcp://localhost/project;IFEXISTS=TRUE";

    public ProductDBM() {
        this.url = "jdbc:h2:tcp://localhost/project;IFEXISTS=TRUE";

    }

    public ProductDBM(String url) {
        this.url = url;
    }
    
    /**
     *
     * @param newProduct
     */
    @Override
    public void addProduct(domain.Product newProduct){
        String sql = "merge into products (id, name, description, "
                + "category, price, quantity) values (?, ?, ?, ?, ?, ?)";
        
        try (
            // get connection to database
            Connection dbCon = JdbcConnection.getConnection(url);
            // create the statement
            PreparedStatement stmt = dbCon.prepareStatement(sql);
        ) {
            // copy the data from the student domain object in the SQL parameters
            stmt.setInt(1, newProduct.getProductID());
            stmt.setString(2, newProduct.getName());
            stmt.setString(3, newProduct.getDescription());
            stmt.setString(4, newProduct.getCategory());
            stmt.setDouble(5, newProduct.getPrice());
            stmt.setInt(6, newProduct.getQuantityInStock());
            
            stmt.executeUpdate(); // execute the statement
        } catch (SQLException ex){ // we are forced to catch SQLException
            // don't let the SQLException leak from our DAO encapsulation
            throw new DAOException(ex.getMessage(), ex);
        }
    }

    @Override
    public void removeProduct(Product rmProduct) {
        String sql = "delete from products where id=?";
        
        try (
            // get connection to database
            Connection dbCon = JdbcConnection.getConnection(url);
            // create the statement
            PreparedStatement stmt = dbCon.prepareStatement(sql);
        ) {
            stmt.setInt(1, rmProduct.getProductID());
 
            stmt.executeUpdate(); // execute the statement
        } catch (SQLException ex){ 
             throw new DAOException(ex.getMessage(), ex);
        }
    }

    @Override
    public Collection<Product> getProducts() {
        String sql = "select * from products order by id";
        
        try (
            // get connection to database
            Connection dbCon = JdbcConnection.getConnection(url);
            // create the statement
            PreparedStatement stmt = dbCon.prepareStatement(sql);
        ) {
        // execute the query
        ResultSet rs = stmt.executeQuery();
        // Create a collection for holding the product we get from the query.
        // We are using a List in order to preserve the order in which
        // the data was returned from the query.
        Collection<Product> products = new ArrayList<>();
        // iterate through the query results
        while (rs.next()) {
            // get the data out of the query
            Integer productID = rs.getInt("id");
            String name = rs.getString("name");
            String description = rs.getString("description");
            String category = rs.getString("category");
            Double price = rs.getDouble("price");
            Integer quantity = rs.getInt("quantity");
            
            // use the data to create a new product object
            Product p = new Product(productID, name, description, category, 
                                    price, quantity);
            // add to collection
            products.add(p);
        }
        return products;
        } catch (SQLException ex){
            throw new DAOException(ex.getMessage(), ex);
        }
    }

    @Override
    public Collection<String> getCategories() {
        String sql = "select distinct category from products"
                + " order by category";
        
        try (
            // get connection to database
            Connection dbCon = JdbcConnection.getConnection(url);
            // create the statement
            PreparedStatement stmt = dbCon.prepareStatement(sql);
        ) {
        // execute the query
        ResultSet rs = stmt.executeQuery();
        // Create a collection for holding the product we get from the query.
        // We are using a List in order to preserve the order in which
        // the data was returned from the query.
        Collection<String> categories = new TreeSet<>();
        // iterate through the query results
        while (rs.next()) {
            // get the data out of the query
            String category = rs.getString("category");

            // add to collection
            categories.add(category);
        }
        return categories;
        } catch (SQLException ex){
            throw new DAOException(ex.getMessage(), ex);
        }
    }

    @Override
    public Product searchMap(Integer id) {
        String sql = "select * from products where id=?";
        
        try (
            // get connection to database
            Connection dbCon = JdbcConnection.getConnection(url);
            // create the statement
            PreparedStatement stmt = dbCon.prepareStatement(sql);
        ) {
        
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();
    
        
       if (rs.next()) {
            Integer productID = rs.getInt("id");
            String name = rs.getString("name");
            String description = rs.getString("description");
            String category = rs.getString("category");
            Double price = rs.getDouble("price");
            Integer quantity = rs.getInt("quantity");
            
            // use the data to create a new product object
            Product p = new Product(productID, name, description, category, 
                                    price, quantity);
            return p;
        } else {
           return null;
       }
        
        } catch (SQLException ex){
            throw new DAOException(ex.getMessage(), ex);
        }
    }

    @Override
    public Collection<Product> categorySearch(String category) {
        String sql = "select * from products where category =?";
        
        try (
            // get connection to database
            Connection dbCon = JdbcConnection.getConnection(url);
            // create the statement
            PreparedStatement stmt = dbCon.prepareStatement(sql);
        ) {
        // execute the query
        stmt.setString(1, category);
        ResultSet rs = stmt.executeQuery();
       
        Collection<Product> products = new TreeSet<>();
        
        while (rs.next()) {
            // get the data out of the query
            Integer productID = rs.getInt("id");
            String name = rs.getString("name");
            String description = rs.getString("description");
            Double price = rs.getDouble("price");
            Integer quantity = rs.getInt("quantity");
            
            // use the data to create a new product object
            Product p = new Product(productID, name, description, category, 
                                    price, quantity);
            // add to collection
            products.add(p);
        }
        if (products.isEmpty()){
            return null;
        }
        return products;
        } catch (SQLException ex){
            throw new DAOException(ex.getMessage(), ex);
        }
    }
    
    
    
}
