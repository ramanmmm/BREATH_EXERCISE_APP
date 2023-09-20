package com.example.breathingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    EditText edt_email,edt_password;
    Button btn_logIn;
    TextView txt_createAccount;
    ProgressDialog progressDialog;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edt_email = findViewById(R.id.edt_email);
        edt_password = findViewById(R.id.edt_password);
        btn_logIn = findViewById(R.id.btnLogin);
        txt_createAccount = findViewById(R.id.newUser);

        progressDialog = new ProgressDialog(LoginActivity.this);

        getSupportActionBar().hide();

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mAuth = FirebaseAuth.getInstance();

        btn_logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();
            }
        });

        txt_createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private void loginUser() {


        // Take the value of two edit texts in Strings
        String email, password;
        email = edt_email.getText().toString();
        password = edt_password.getText().toString();

        // Validations for input email and password
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(),
                            "Please enter email!!",
                            Toast.LENGTH_LONG)
                    .show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(),
                            "Please enter password!!",
                            Toast.LENGTH_LONG)
                    .show();
            return;
        }

        // login user
        mAuth
                .signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        // show the visibility of progress dialog to show loading
                        progressDialog.setMessage("Login User Account....");
                        progressDialog.setTitle("Loading...");
                        progressDialog.show();

                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(),
                                            "Login successful!",
                                            Toast.LENGTH_LONG)
                                    .show();

                            // hide the progress dialog
                            progressDialog.dismiss();

                            Intent intent
                                    = new Intent(LoginActivity.this,
                                    MainActivity.class);
                            startActivity(intent);
                        }
                        else {

                            // Login failed
                            Toast.makeText(
                                            getApplicationContext(),
                                            "Login failed!!"
                                                    + " Please try again later",
                                            Toast.LENGTH_LONG)
                                    .show();

                            // hide the progress dialog
                            progressDialog.dismiss();
                        }
                    }
                });
    }
}