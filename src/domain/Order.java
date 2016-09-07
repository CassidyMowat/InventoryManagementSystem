/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author mowca765
 */
public class Order {
    private Integer orderId;
    private String date;
    
    private static Collection<OrderItem> orderItems = new ArrayList<>();
    
    public Double getTotal(){
        Double total = 0.0;
        
        for(OrderItem o: orderItems){
            total += o.getItemTotal();
        }
        return total;
    }
    
    public void addItem(OrderItem item){
        orderItems.add(item);
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    
    
}
