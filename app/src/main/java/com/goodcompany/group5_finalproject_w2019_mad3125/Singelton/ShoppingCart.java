package com.goodcompany.group5_finalproject_w2019_mad3125.Singelton;

public class ShoppingCart {
    public static final ShoppingCart ourInstance = new ShoppingCart();

    static ShoppingCart getInstance() {
        return ourInstance;
    }

    private ShoppingCart() {
    }


    public void addProductToCart(){

    }

    public  void getItemsInCart(){

    }

    public  void getCartCount(){

    }


    public void getTotalPrice(){

    }

    public  void removeEverythingFromCart(){

    }

    public  void removeProductFromCart(){

    }
}
