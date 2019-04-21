package com.goodcompany.group5_finalproject_w2019_mad3125.Singelton;

import com.goodcompany.group5_finalproject_w2019_mad3125.Modals.OrdersModal;
import com.goodcompany.group5_finalproject_w2019_mad3125.Modals.ProductsModal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class ShoppingCart {

    public static final ShoppingCart ourInstance = new ShoppingCart();
    public HashMap<String, ProductsModal> cartItems =  new HashMap<>();
    public HashMap<String, ProductsModal> orderdItems =  new HashMap<>();
    public HashMap<String, ArrayList<ProductsModal>> order =  new HashMap<>();
    public ArrayList<OrdersModal> ordersModals = new ArrayList<>();


    static ShoppingCart getInstance() {
        return ourInstance;
    }

    private ShoppingCart() {
    }


    public void addProductToCart(String id, ProductsModal productsModal){
        if (cartItems.containsKey(id)){
            cartItems.get(id).setQuantity(cartItems.get(id).getQuantity()+productsModal.getQuantity());
        }else{
            cartItems.put(id,productsModal);
        }
    }

    public ArrayList<ProductsModal> getItemsInCart(){
        ArrayList<ProductsModal> productsModals = new ArrayList<>();
        for (Map.Entry<String, ProductsModal> entry : cartItems.entrySet()) {
            productsModals.add(entry.getValue());
        }
        return  productsModals;
    }

    public ArrayList<ProductsModal> getOrderdItems(){
        ArrayList<ProductsModal> productsModals = new ArrayList<>();
        for (Map.Entry<String, ProductsModal> entry : orderdItems.entrySet()) {
            productsModals.add(entry.getValue());
        }
        return  productsModals;
    }


    public ProductsModal getProductById(String id){
        if (cartItems.containsKey(id)){
           return cartItems.get(id);
        }
        return null;
    }

    public  int getCartCount(){
        return cartItems.size();

    }


    public Double getTotalPrice(){
        Double totalprice = 0.00;
        for (Map.Entry<String, ProductsModal> entry : cartItems.entrySet()) {
            totalprice = totalprice+ (entry.getValue().getPrice()* entry.getValue().getQuantity());
        }
        return totalprice;
    }


    public void checkout(){
        orderdItems.putAll(cartItems);

        ArrayList<ProductsModal> productsModals = new ArrayList<>();
        for (Map.Entry<String, ProductsModal> entry : orderdItems.entrySet()) {
            productsModals.add(entry.getValue());
        }
        final int random = new Random().nextInt(61) + 20; // [0, 60] + 20 => [20, 80]
        order.put(String.valueOf(random),productsModals);


        //Assing to the orders
        OrdersModal ordersModal = new OrdersModal();
        ordersModal.orderId = String.valueOf(random);
        ordersModal.productsModals.addAll(productsModals);
        ordersModals.add(ordersModal);

    }


    public ArrayList<ProductsModal> getOrderdById(int pos){
        return ordersModals.get(pos).getProductsModals();
    }


    public ArrayList<OrdersModal> getOrder() {
        return ordersModals;
    }




    public  void removeEverythingFromCart(){
        if (cartItems != null){
            cartItems.clear();
        }

    }

    public  void removeProductFromCart(String id){
        if (cartItems.containsKey(id)) {
            cartItems.remove(id);
        }
    }
}
