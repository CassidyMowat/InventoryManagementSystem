/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.util.Objects;
import net.sf.oval.constraint.Length;
import net.sf.oval.constraint.NotBlank;
import net.sf.oval.constraint.NotNegative;
import net.sf.oval.constraint.NotNull;

/**
 *
 * @author mowca765
 */
public class Product implements Comparable<Product>{
    
    @NotNull(message = "ProductID must be Provided.")
    @NotBlank(message = "ProductID must be Provided.")
    @NotNegative(message = "ProductID must be a positive number.")
    private Integer productID;
    
    @NotNull(message = "Name must be Provided.")
    @NotBlank(message = "Name must be Provided.")
    @Length(min=2, message = "Name must contain at least two characters.")
    private String name;
    
    private String description;
    
    @NotNull(message = "Category must be Provided.")
    @NotBlank(message = "Category must be Provided.")
    private String category;
    
    @NotNull(message = "Price must be Provided.")
    @NotBlank(message = "Price must be Provided.")
    @NotNegative(message = "Price must be a positive number.")
    private Double price;
    
    @NotNull(message = "Quantity must be Provided.")
    @NotBlank(message = "Quantity must be Provided.")
    @NotNegative(message = "Quantity must be a positive number.")
    private Integer quantityInStock;

    @Override
    public String toString() {
        return productID + ", " + name;
    }

    public Product() {
    }
   
    public Product(Integer productID, String name, String description, String category, Double price, Integer quantityInStock) {
        this.productID = productID;
        this.name = name;
        this.description = description;
        this.category = category;
        this.price = price;
        this.quantityInStock = quantityInStock;
    } 

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getProductID() {
        return productID;
    }

    public void setProductID(Integer productID) {
        this.productID = productID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getQuantityInStock() {
        return quantityInStock;
    }

    public void setQuantityInStock(Integer quantityInStock) {
        this.quantityInStock = quantityInStock;
    }

    @Override
    public int compareTo(Product o) {
        Integer myId = this.getProductID();
        Integer otherId = o.getProductID();
        return myId.compareTo(otherId);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + Objects.hashCode(this.productID);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Product other = (Product) obj;
        if (!Objects.equals(this.productID, other.productID)) {
            return false;
        }
        return true;
    }
    
    
}
