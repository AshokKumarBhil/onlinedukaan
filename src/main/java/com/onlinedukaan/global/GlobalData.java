package com.onlinedukaan.global;

import com.onlinedukaan.model.Product;

import java.util.ArrayList;
import java.util.List;

public class GlobalData {
    public static List<Product> cart;

    static {
        cart = new ArrayList<Product>();
    }
    public static int totalPrice(){
        int total = 0;
        for (Product product : GlobalData.cart) {
            total = total + product.getPrice();
        }
        return total;
    }
}
