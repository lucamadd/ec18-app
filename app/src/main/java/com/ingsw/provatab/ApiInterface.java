package com.ingsw.provatab;

import com.ingsw.provatab.com.ingsw.model.Customer;
import com.ingsw.provatab.com.ingsw.model.Item;
import com.ingsw.provatab.com.ingsw.model.Order;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiInterface {

    @GET("/AndroidServlet")
    Call<List<Customer>> getList();


    @POST("/AndroidAllProducts")
    Call<List<Item>> getItems();

    @Headers({
            "Content-Type: application/x-www-form-urlencoded; charset=UTF-8"
    })
    @FormUrlEncoded
    @POST("/AndroidLogin")
    Call<Boolean> login(@Field("mail") String mail, @Field("password") String password);

    @Headers({
            "Content-Type: application/x-www-form-urlencoded; charset=UTF-8"
    })
    @FormUrlEncoded
    @POST("/AndroidRegistration")
    Call<Boolean> registration(@Field("name") String  name, @Field("surname") String  surname,@Field("address") String  address,@Field("city") String  city,@Field("province") String  province, @Field("mail") String  mail,  @Field("password") String  password);

    @Headers({
            "Content-Type: application/x-www-form-urlencoded; charset=UTF-8"
    })
    @POST("/AndroidLatestItems")
    Call<List<Item>> getLatestItems();

    @Headers({
            "Content-Type: application/x-www-form-urlencoded; charset=UTF-8"
    })
    @POST("/AndroidBestSellers")
    Call<List<Item>> getBestSellers();

    @Headers({
            "Content-Type: application/x-www-form-urlencoded; charset=UTF-8"
    })
    @FormUrlEncoded
    @POST("/AndroidFilters")
    Call<List<Item>> getFilters(@Field("filterType") String filterType, @Field("subFilter") String subFilter);


    @Headers({
            "Content-Type: application/x-www-form-urlencoded; charset=UTF-8"
    })
    @FormUrlEncoded
    @POST("/AndroidItemSearch")
    Call<List<Item>> getItemSearchResult(@Field("itemSearch") String itemSearch);


    @Headers({
            "Content-Type: application/x-www-form-urlencoded; charset=UTF-8"
    })
    @POST("/AndroidGetBrands")
    Call<List<String>> getBrands();


    @Headers({
            "Content-Type: application/x-www-form-urlencoded; charset=UTF-8"
    })
    @FormUrlEncoded
    @POST("/AndroidGetUserByMail")
    Call<Customer> getUserByMail(@Field("mail") String mail);


    @Headers({
            "Content-Type: application/x-www-form-urlencoded; charset=UTF-8"
    })
    @FormUrlEncoded
    @POST("/AndroidGetOrdersByUser")
    Call<List<Order>> getOrderByUser(@Field("mail") String mail);


    @Headers({
            "Content-Type: application/x-www-form-urlencoded; charset=UTF-8"
    })
    @POST("/AndroidGetNextOrderN")
    Call<Integer> getNextOrderN();


    @Headers({
            "Content-Type: application/x-www-form-urlencoded; charset=UTF-8"
    })
    @FormUrlEncoded
    @POST("/AndroidRecoveryPassword")
    Call<Boolean> recoveryPassword(@Field("mail") String mail);

    @Headers({
            "Content-Type: application/x-www-form-urlencoded; charset=UTF-8"
    })
    @FormUrlEncoded
    @POST("/AndroidUpdateUser")
    Call<Customer> editUser(@Field("mail") String mail,@Field("name") String name,@Field("surname") String surname,@Field("address") String address,@Field("city") String city,@Field("province") String province);


    @Headers({
            "Content-Type: application/x-www-form-urlencoded; charset=UTF-8"
    })
    @FormUrlEncoded
    @POST("/AndroidModifyPassword")
    Call<Boolean> modifyPassword(@Field("mail") String mail,@Field("pass") String pass, @Field("newPass") String newPass);

    @Headers({
            "Content-Type: application/x-www-form-urlencoded; charset=UTF-8"
    })

    @FormUrlEncoded
    @POST("/AndroidGetOrderByID")
    Call<Order> getOrderByID(@Field("id") int id);


    @Headers({
            "Content-Type: application/x-www-form-urlencoded; charset=UTF-8"
    })
    @FormUrlEncoded
    @POST("/AndroidCheckFeedback")
    Call<Boolean> checkFeedback(@Field("orderN")int orderN,@Field("index")int index, @Field("rating")int rating);

    @Headers({
            "Content-Type: application/x-www-form-urlencoded; charset=UTF-8"
    })
    @POST("/AndroidPayment")
    Call<Boolean> payment(@Body Order order);

    @Headers({
            "Content-Type: application/x-www-form-urlencoded; charset=UTF-8"
    })
    @POST("/AndroidBrainTreeGetToken")
    Call<String> brainTreeGetToken();

    @Headers({
            "Content-Type: application/x-www-form-urlencoded; charset=UTF-8"
    })
    @FormUrlEncoded
    @POST("/AndroidBrainTreePayment")
    Call<Boolean> brainTreePayment(@Field("nonce")String nonce, @Field("paymentAmount")String paymentAmount);

    @Headers({
            "Content-Type: application/x-www-form-urlencoded; charset=UTF-8"
    })
    @FormUrlEncoded
    @POST("/AndroidAddClick")
    Call<Boolean> addClick(@Field("ID") int ID);

}



