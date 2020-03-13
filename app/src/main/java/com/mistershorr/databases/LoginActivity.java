package com.mistershorr.databases;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

public class LoginActivity extends AppCompatActivity {

    public static final String TAG = LoginActivity.class.getSimpleName();
    public static final String EXTRA_USERNAME = "login username";
    public static final int REQUEST_CREATE_ACCOUNT = 1;

    private TextView textViewCreateAccount;
    private Button buttonLogin;
    private EditText editTextUsername;
    private EditText editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Backendless.initApp(this,Credentials.APP_ID,Credentials.API_KEY);


        wireWidgets();
        setListeners();
    }

    private void setListeners() {
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginToBackendless();
            }
        });

        textViewCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO replace create account startActivity with startAcitivtyForResult
                Intent createAccountIntent = new Intent(LoginActivity.this, RegistrationActivity.class);
                createAccountIntent.putExtra(EXTRA_USERNAME, editTextUsername.getText().toString());

                startActivityForResult(createAccountIntent,REQUEST_CREATE_ACCOUNT);
            }
        });
    }
    private void loginToBackendless()
    {
        final String username = editTextUsername.getText().toString();
        String password = editTextPassword.getText().toString();
        if(!username.isEmpty() && !password.isEmpty()){
        Backendless.UserService.login(username, password, new AsyncCallback<BackendlessUser>() {
            @Override
            public void handleResponse(BackendlessUser response) {
            Toast.makeText(LoginActivity.this,"Welcome",Toast.LENGTH_LONG).show();
            Intent loggedInIntent = new Intent(LoginActivity.this, FriendListActivity.class);
            startActivity(loggedInIntent);
            finish();
            }

            @Override
            public void handleFault(BackendlessFault fault) {
            Toast.makeText(LoginActivity.this,""+fault.getDetail(), Toast.LENGTH_SHORT).show();
            }
        });}
        else{
        Toast.makeText(this,"Enter password",Toast.LENGTH_LONG).show();
        }


    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CREATE_ACCOUNT){
            if(resultCode == RESULT_OK){
                String username = data.getStringExtra(RegistrationActivity.EXTRA_USERNAME);
                String password = data.getStringExtra(RegistrationActivity.EXTRA_PASSWORD);
                editTextPassword.setText(password);
                editTextUsername.setText(username);
            }

        }
    }



    private void wireWidgets() {
        textViewCreateAccount = findViewById(R.id.textview_login_create_account);
        buttonLogin = findViewById(R.id.button_login_login);
        editTextUsername = findViewById(R.id.edit_text_login_username);
        editTextPassword = findViewById(R.id.edit_text_login_password);
    }
}
