package sk.stuba.fei.uamt.e_shop;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Zendo on 22.11.2017.
 */

public interface CredentialsAPIService {
    @GET("/User.php")
    Call<Credentials> fetchCredentials(@Query("action") String login, @Query("email") String email, @Query("pass") String password);

    @POST("/User.php")
    Call<Credentials> registerUser(@Query("action") String registration, @Body User user);
}
