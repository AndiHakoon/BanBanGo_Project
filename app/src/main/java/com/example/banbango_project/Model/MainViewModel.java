package com.example.banbango_project.Model;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.banbango_project.API.ApiInterface;
import com.example.banbango_project.API.ServerAPI;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainViewModel extends ViewModel {
    private final MutableLiveData<ArrayList<ModelResults>> modelResultMutableLiveData = new MutableLiveData<>();
    public static String strApiKey = "AIzaSyC7PIFPfhiuxbRYY9N09JWzoUFSAjI5XcU";

    public void setMarkerLocation(String strLocation, String strKeyword){
        ApiInterface apiService = ServerAPI.getClient(ServerAPI.base_URL).create(ApiInterface.class);

        Call<ModelResultNearby> call = apiService.getDataResult(strApiKey, strKeyword, strLocation, "distance");
        call.enqueue(new Callback<ModelResultNearby>() {
            @Override
            public void onResponse(Call<ModelResultNearby> call, Response<ModelResultNearby> response) {
                if (!response.isSuccessful()){
                    Log.e("response", response.toString());
                } else if (response.body() != null){
                    ArrayList<ModelResults> items = new ArrayList<>(response.body().getModelResults());
                    modelResultMutableLiveData.postValue(items);
                }
            }

            @Override
            public void onFailure(Call<ModelResultNearby> call, Throwable t) {
                Log.e("failure", t.toString());
            }
        });
    }

    public LiveData<ArrayList<ModelResults>> getMarkerLocation(){
        return modelResultMutableLiveData;
    }
}
