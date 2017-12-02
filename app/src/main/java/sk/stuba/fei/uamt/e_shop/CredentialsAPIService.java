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
    @POST("/User.php")
    Call<Credentials> fetchCredentials(@Body User user);

    @POST("/User.php")
    Call<Credentials> registerUser(@Body User user);
}
