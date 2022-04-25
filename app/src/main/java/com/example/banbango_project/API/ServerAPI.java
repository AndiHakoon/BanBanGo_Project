package com.example.banbango_project.API;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServerAPI {
    private static final String base_URL = "https://banbangoofficial.000webhostapp.com/";
    private static Retrofit retrofit = null;

    public static SelectUser getSelectAPI(){
        if (retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(base_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(SelectUser.class);
    }
}
