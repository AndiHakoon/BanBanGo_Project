package com.example.banbango_project.API;

public class UtilsApi {
    private static final String base_URL = "https://banbangoofficial.000webhostapp.com/";

    public static BaseAPIServer getApiService(){
        return ServerAPI.getClient(base_URL).create(BaseAPIServer.class);
    }
}
