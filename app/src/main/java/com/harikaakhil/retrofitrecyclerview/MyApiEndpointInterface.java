package com.harikaakhil.retrofitrecyclerview;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by ashok.kumar on 17/06/16.
 */
public interface MyApiEndpointInterface {

    @GET("/search/users")
    Call<User> getUsersNamedTom(@Query("q") String name);

}
