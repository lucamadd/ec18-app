package com.ingsw.provatab.com.ingsw.model;

import com.ingsw.provatab.ApiClient;
import com.ingsw.provatab.ApiInterface;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//Singleton
public final class Cart {

    private static Cart aCart=null;

    private boolean isEmpty;
    private List<Item> products;
    private List<Integer> shipping;
    private List<String> colors;
    private Customer customer;
    private ApiInterface apiInterface= ApiClient.getClient().create(ApiInterface.class);

    public static Cart getCart() {
        if (aCart==null)
            aCart=new Cart();
        return aCart;
    }

    public Cart() {}



    public Cart(Item product,  int shipping, String color,Customer customer) {
        setIsEmpty(true);
        setProducts(product);
        setShipping(shipping);
        setColors(color);
        setCustomer(customer);
    }

    public void setIsEmpty(boolean isEmpty) {
        this.isEmpty=isEmpty;
    }
    public void setProducts(Item product) {
        if (products==null)
            products=new ArrayList<>();
        this.products.add(product);
        setIsEmpty(false);
    }

    public void setShipping(int shipping) {
        if (this.shipping==null)
            this.shipping=new ArrayList<>();
        this.shipping.add(shipping);
    }
    public void setColors(String color) {
        if (this.colors==null)
            colors=new ArrayList<>();
        this.colors.add(color);
    }
    public void setCustomer(Customer customer) {
        if (this.customer==null)
            this.customer=new Customer();
        this.customer=customer;
    }

    public boolean getIsEmpty() {
        return isEmpty;
    }
    public List<Item> getProducts(){
        return products;
    }
    public List<Integer> getShipping(){
        return shipping;
    }
    public List<String> getColors(){
        return colors;
    }
    public static void deleteCart() {
        aCart=null;
    }

    public String getTotalPrice() {
        double totalPrice=0;
        if (!products.isEmpty()) {
            for (Item i:products) {
                if (i.getOnSale())
                    totalPrice+=i.getPrice()-(0.2*i.getPrice());
                else
                    totalPrice+=i.getPrice();
            }
            totalPrice+=Double.parseDouble(getTotalShipping());
        }
        return Double.toString(Cart.round(totalPrice, 2));
    }

    public String getTotalShipping() {
        double totalShipping=0;
        if (!shipping.isEmpty()) {
            for (Integer i:shipping) {
                if (i==0) {
                    totalShipping+=0;
                }
                else if (i==1) {
                    totalShipping+=4;
                }
                else if (i==2) {
                    totalShipping+=8;
                }
            }
        }
        return Double.toString(totalShipping);
    }
    public Customer getCustomer() {
        return customer;
    }


    public static Double round(Double d, int precise)
    {
        BigDecimal bigDecimal = BigDecimal.valueOf(d);
        bigDecimal = bigDecimal.setScale(precise, RoundingMode.HALF_UP);
        return bigDecimal.doubleValue();
    }

    public void addProductToCart(Item item, String colorSelected, String shippingSelected, String mail) {
        final com.ingsw.provatab.com.ingsw.model.Cart cart = com.ingsw.provatab.com.ingsw.model.Cart.getCart();
        cart.setProducts(item);
        cart.setColors(colorSelected);


        switch (shippingSelected){
            case "Standard (4/5 gg) Gratis": cart.setShipping(0); break;
            case "Espressa (48/72 ore) €4.00": cart.setShipping(1); break;
            case "Espressa (24 ore) €8.00": cart.setShipping(2); break;
        }


        Call<Customer> call=apiInterface.getUserByMail(mail);
        call.enqueue(new Callback<Customer>() {
            @Override
            public void onResponse(Call<Customer> call, Response<Customer> response) {
                cart.setCustomer(response.body());
            }

            @Override
            public void onFailure(Call<Customer> call, Throwable t) {

            }
        });

    }
}