package com.example.banbango_project.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.identity.Identity;
import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.android.gms.auth.api.identity.SignInCredential;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "";
    EditText EditEnterEmail, EditEnterPass;
    Button btnLogin;
    ProgressDialog loading;

    Context context;
    BaseAPIServer apiServer;

    private SignInClient oneTapClient;
    private BeginSignInRequest.Builder signInRequest;
    private FirebaseAuth auth;

    private static final int REQ_ONE_TAP = 2;
    private boolean showOneTapUI = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();
        oneTapClient = Identity.getSignInClient(this);
        signInRequest = BeginSignInRequest.builder().setGoogleIdTokenRequestOptions(BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                .setSupported(true)
                .setServerClientId(getString(R.string.web_client_id))
                .setFilterByAuthorizedAccounts(true)
                .build());

        context = this;
        apiServer = UtilsApi.getApiService();
        initComponenets();
    }
    public void signOut() {
        // [START auth_sign_out]
        FirebaseAuth.getInstance().signOut();
        // [END auth_sign_out]
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
                                if (response.isSuccessful()) {
                                    loading.dismiss();
                                    try {
                                        JSONObject jsonObject = new JSONObject(response.body().string());
                                        if (jsonObject.getString("error").equals("false")) {
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
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                } else {
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_ONE_TAP:
                try {
                    SignInCredential credential = oneTapClient.getSignInCredentialFromIntent(data);
                    String idToken = credential.getGoogleIdToken();
                    if (idToken != null) {
                        // Got an ID token from Google. Use it to authenticate
                        // with Firebase.
                        AuthCredential firebaseCredential = GoogleAuthProvider.getCredential(idToken, null);
                        auth.signInWithCredential(firebaseCredential)
                                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            // Sign in success, update UI with the signed-in user's information
                                            Log.d(TAG, "signInWithCredential:success");
                                            FirebaseUser user = auth.getCurrentUser();
                                            updateUI(user);
                                        } else {
                                            // If sign in fails, display a message to the user.
                                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                                            updateUI(null);
                                        }
                                    }
                                });
                    }
                } catch (ApiException e) {
                    // ...
                }
                break;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = auth.getCurrentUser();
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser user) {

    }
}

