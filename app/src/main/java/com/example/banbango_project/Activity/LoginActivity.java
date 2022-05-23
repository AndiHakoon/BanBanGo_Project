package com.example.banbango_project.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.banbango_project.API.BaseAPIServer;
import com.example.banbango_project.API.UtilsApi;
import com.example.banbango_project.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    EditText EditEnterEmail, EditEnterPass;
    Button btnLogin;
    ProgressDialog loading;

    Context context;
    BaseAPIServer apiServer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        context = this;
        apiServer = UtilsApi.getApiService();
        initComponenets();
    }

    private void initComponenets() {
        EditEnterEmail = (EditText) findViewById(R.id.EditEnterEmail);
        EditEnterPass = (EditText) findViewById(R.id.EditEnterPass);
        btnLogin = (Button) findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loading = ProgressDialog.show(context, null, "Tuggu bentar Ya!", true, false);
                RequestLogin();
            }

            private void RequestLogin() {
                apiServer.loginRequest(EditEnterEmail.getText().toString(), EditEnterPass.getText().toString())
                        .enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                if (response.isSuccessful()){
                                    loading.dismiss();
                                    try {
                                        JSONObject jsonObject = new JSONObject(response.body().string());
                                        if (jsonObject.getString("error").equals("false")){
                                            // jika login berhasil maka data nama yang ada di response API
                                            // akan diparsing ke activing selanjutnya
                                            Toast.makeText(context, "Succsesfully Login", Toast.LENGTH_SHORT).show();
                                            String nama = jsonObject.getJSONObject("user").getString("nama");
                                            Intent intent = new Intent(context, MainActivity.class);
                                            intent.putExtra("result_nama", nama);
                                            startActivity(intent);
                                        } else {
                                            // Jika login gagal
                                            String error_message = jsonObject.getString("error_msg");
                                            Toast.makeText(context, error_message, Toast.LENGTH_SHORT).show();
                                        }
                                    } catch (JSONException e){
                                        e.printStackTrace();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }else {
                                    loading.dismiss();
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                Log.e("debug", "onFailure: ERROR > " + t.toString());
                                loading.dismiss();
                            }
                        });
            }
        });
    }
}