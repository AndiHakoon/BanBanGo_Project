package com.example.banbango_project.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.banbango_project.API.BaseAPIServer;
import com.example.banbango_project.API.ServerAPI;
import com.example.banbango_project.Model.Register;
import com.example.banbango_project.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{
    ImageView btnback;
    EditText edit_nama, edit_email, edit_password;
    TextView sudahpunyakakun;
    Button btnregister;
    String Username, Password, Name;
    ProgressDialog loading;
    BaseAPIServer apiServer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edit_email = findViewById(R.id.edit_email);
        edit_nama = findViewById(R.id.edit_nama);
        edit_password = findViewById(R.id.edit_password);

        btnregister = findViewById(R.id.btnregister);
        btnregister.setOnClickListener(this);

        sudahpunyakakun = findViewById(R.id.sudahpunyaakun);
        sudahpunyakakun.setOnClickListener(this);

        btnback = (ImageView) findViewById(R.id.btnback);
        btnback.setOnClickListener(this);


        //initComponents();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.sudahpunyaakun:
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.btnback:
                intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.btnregister:
                Username = edit_email.getText().toString();
                Name = edit_nama.getText().toString();
                Password = edit_password.getText().toString();
                register(Username,Name,Password);
                break;
        }
    }

    private void register(String username, String name, String password) {

        apiServer = ServerAPI.getClient().create(BaseAPIServer.class);
        Call<Register> call = apiServer.registerresponse(username, name, password);
        call.enqueue(new Callback<Register>() {
            @Override
            public void onResponse(Call<Register> call, Response<Register> response) {
                if (response.body() != null && response.isSuccessful() && response.body().isStatus()){
                    Toast.makeText(RegisterActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(RegisterActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Register> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

//    private void initComponents() {
//        edit_nama = (EditText) findViewById(R.id.edit_nama);
//        edit_email = (EditText) findViewById(R.id.edit_email);
//        edit_password = (EditText) findViewById(R.id.edit_password);
//        btn1 = (Button) findViewById(R.id.btn1);
//
//        btn1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                loading = ProgressDialog.show(context, null, "Tungggu Bentar Ya!", true, false);
//                requestRegister();
//            }
//
//            private void requestRegister() {
//                apiServer.registerRequest(edit_nama.getText().toString(),
//                        edit_email.getText().toString(),
//                        edit_password.getText().toString()).enqueue(new Callback<ResponseBody>() {
//                    @Override
//                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                        if (response.isSuccessful()){
//                            Log.i("debug", "onResponse: Berhasil");
//                            loading.dismiss();
//                            try {
//                                JSONObject jsonObject = new JSONObject(response.body().string());
//                                if (jsonObject.getString("error").equals("false")){
//                                    Toast.makeText(context, "Registrasi Berhasil", Toast.LENGTH_SHORT).show();
//                                    Intent intent = new Intent(context, LoginActivity.class);
//                                    startActivity(intent);
//                                } else {
//                                    String error_message = jsonObject.getString("error_msg");
//                                    Toast.makeText(context, error_message, Toast.LENGTH_SHORT).show();
//                                }
//                            }catch (JSONException e) {
//                                e.printStackTrace();
//                            }catch (IOException e) {
//                                e.printStackTrace();
//                        }
//                    } else {
//                            Log.i("debug", "onResponse: Gagal");
//                            loading.dismiss();
//                        }
//
//                }
//                    @Override
//                    public void onFailure(Call<ResponseBody> call, Throwable t) {
//                        Log.e("debug", "onFailure: ERROR > " + t.getMessage());
//                        Toast.makeText(context, "Koneksi Internet Bermasalah", Toast.LENGTH_SHORT).show();
//                    }
//                    });
//            }
//        });
//    }
}