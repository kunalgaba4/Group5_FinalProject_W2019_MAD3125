package com.goodcompany.group5_finalproject_w2019_mad3125.Modals;

import java.util.ArrayList;

public class OrdersModal {
    public String orderId;
    public ArrayList<ProductsModal> productsModals = new ArrayList<>();

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public ArrayList<ProductsModal> getProductsModals() {
        return productsModals;
    }

    public void setProductsModals(ArrayList<ProductsModal> productsModals) {
        this.productsModals = productsModals;
    }
}
