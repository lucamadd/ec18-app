package com.ingsw.provatab.com.ingsw.model;

import android.os.StrictMode;

import com.ingsw.provatab.ApiClient;
import com.ingsw.provatab.ApiInterface;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public class Order implements Serializable {
    private int orderN;
    private String  date;
    private Double totalPrice;
    private Customer customer;
    private List<Item> products;
    private List<String> colors;
    private List<Integer> shipping;
    private List<Integer> feedback;

    public Order() {}

    public Order(Cart cart) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);
        ApiInterface apiInterface;
        apiInterface= ApiClient.getClient().create(ApiInterface.class);

        Call<Integer> call=apiInterface.getNextOrderN();

        try {
            orderN=call.execute().body() + 1;
        }catch (IOException e){

        }





        date=new String();
        setTotalPrice(Double.parseDouble(cart.getTotalPrice()));
        setProducts(cart.getProducts());
        setColors(cart.getColors());
        setShipping(cart.getShipping());
        setCustomer(cart.getCustomer());
        setFeedback(cart.getProducts().size());
    }
    public Order(int orderN, String date, Double totalPrice, Customer customer, List<Item> products, List<String> colors, List<Integer> shipping, List<Integer> feedback) {
        this.orderN=orderN;
        this.date=date;
        setTotalPrice(totalPrice);
        setProducts(products);
        setColors(colors);
        setShipping(shipping);
        setCustomer(customer);
        setFeedback(feedback);

    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice=totalPrice;
    }
    public void setProducts(List<Item> products) {
        if (this.products==null)
            this.products=new ArrayList<Item>();
        this.products.addAll(products);
    }
    public void setColors(List<String> colors) {
        if (this.colors==null)
            this.colors=new ArrayList<String>();
        this.colors.addAll(colors);
    }
    public void setShipping(List<Integer> shipping) {
        if (this.shipping==null)
            this.shipping=new ArrayList<Integer>();
        this.shipping.addAll(shipping);
    }
    public void setCustomer(Customer customer) {
        if (this.customer==null)
            this.customer=new Customer();
        this.customer=customer;
    }
    public void setFeedback() {
        this.feedback=new ArrayList<>();
    }

    public void setFeedback(int size) {
        if (this.feedback==null) {
            feedback=new ArrayList<Integer>();
            for (int i=0;i<size;i++) {
                feedback.add(0);
            }
        }
    }

    public void setFeedback(List<Integer> feedback) {
        if (this.feedback==null)
            this.feedback=new ArrayList<Integer>();
        this.feedback.addAll(feedback);
    }

    public int getOrderN() {
        return orderN;
    }
    public String getDate() {
        return date;
    }
    public String setDate(String date) {
        return this.date=date;
    }
    public double getTotalPrice() {
        return totalPrice;
    }
    public List<Item> getProducts(){
        return products;
    }
    public List<String> getColors(){
        return colors;
    }
    public List<Integer> getShipping(){
        return shipping;
    }
    public Customer getCustomer() {
        return customer;
    }
    public int getTotalShipping() {
        int totale=0;
        for (Integer i:shipping) {
            totale+=i;
        }
        return totale;
    }
    public List<Integer> getFeedback(){
        return feedback;
    }

    public void showDetails(List<Item> itemList, List<Integer> feedback, List<String> colors, List<Integer> shipping){
        Order order1=new Order();
        order1.setProducts(itemList);
        order1.setShipping(shipping);
        order1.setColors(colors);
        order1.setFeedback(feedback);

    }

}