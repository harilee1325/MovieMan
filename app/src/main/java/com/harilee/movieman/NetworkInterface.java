package com.harilee.movieman;

import io.reactivex.Observable;
import com.harilee.movieman.Login.LoginResponse;
import com.harilee.movieman.SignUp.SignupResponse;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;


public interface NetworkInterface {

    @GET("/login/{username}/{password}")
    Observable<LoginResponse> loginUser(@Path("username") String username, @Path("password") String password);


    @FormUrlEncoded
    @POST("/sign-up")
    Observable<SignupResponse> signupUser(@Field("username") String username, @Field("password") String password
                                          ,@Field("email") String email);
}
