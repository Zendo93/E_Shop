package sk.stuba.fei.uamt.e_shop;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Zendo on 22.11.2017.
 */

public interface APIService {
    @POST("/User.php")
    Call<Credentials> fetchCredentials(@Body User user);

    @POST("/User.php")
    Call<Credentials> registerUser(@Body User user);

    @POST("/User.php")
    Call<Credentials> changeUserData(@Body User user);

    @GET("/product/Product.php")
    Call<List<Product>> getProducts(@Query("action") String action);

    @POST("/product/Order.php")
    Call<Void> createOrder(@Body Order order);

    @POST("/product/Order.php")
    Call<List<Orders>> getMyOrders(@Body Credentials credentials);
}
