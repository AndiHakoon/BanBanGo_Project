package com.example.banbango_project.API;

import com.example.banbango_project.Model.ModelUser;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface SelectUser {
    @GET("select_user.php")
    Call<List<ModelUser>> selectUser();
}
