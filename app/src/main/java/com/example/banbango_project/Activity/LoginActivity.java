package com.example.banbango_project.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.banbango_project.API.BaseAPIServer;
import com.example.banbango_project.API.ServerAPI;
import com.example.banbango_project.Model.Login;
import com.example.banbango_project.Model.LoginData;
import com.example.banbango_project.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    EditText EditEnterEmail, EditEnterPass;
    Button btnLogin, btnlogingoogle;
    ProgressDialog loading;
    TextView txtregister;
    String Username, Password;
    SessionManager sessionManager;

    BaseAPIServer apiServer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditEnterEmail = findViewById(R.id.EditEnterEmail);
        EditEnterPass = findViewById(R.id.EditEnterPass);

        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);

        txtregister = findViewById(R.id.txtregister);
        txtregister.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnLogin:
                Username = EditEnterEmail.getText().toString();
                Password = EditEnterPass.getText().toString();
                login(Username, Password);
                break;
            case R.id.txtregister:
                Intent intent = new Intent(this, RegisterActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void login(String username, String password) {

        apiServer = ServerAPI.getClient().create(BaseAPIServer.class);
        Call<Login>loginCall = apiServer.loginresponse(username, password);
        loginCall.enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {

                sessionManager = new SessionManager(LoginActivity.this);
                LoginData loginData = response.body().getData();
                sessionManager.createLoginSession(loginData);

                if (response.body() != null && response.isSuccessful() && response.body().isStatus()){
                    Toast.makeText(LoginActivity.this, response.body().getData().getName(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {
                Toast.makeText(LoginActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }
}



