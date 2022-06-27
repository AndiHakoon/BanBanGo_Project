package com.example.banbango_project.API;

import com.example.banbango_project.Model.Login;
import com.example.banbango_project.Model.Register;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface BaseAPIServer {
    @FormUrlEncoded
    @POST("login.php")
    Call<Login> loginresponse(
      @Field("username") String username,
      @Field("password") String password
    );

    @FormUrlEncoded
    @POST("register.php")
    Call<Register> registerresponse(
            @Field("username") String username,
            @Field("password") String password,
            @Field("name") String name
    );


}
