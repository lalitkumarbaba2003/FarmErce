package com.example.farmerce.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.farmerce.R;
import com.example.farmerce.models.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    Button SignUp;
    EditText name, email, password, number, address;
    TextView SignIn;
    FirebaseAuth auth;
    FirebaseDatabase database;
    ProgressBar progressBar;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        toolbar = findViewById(R.id.reg_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        progressBar = findViewById(R.id.progressbar);
        progressBar.setVisibility(View.GONE);

        SignUp = findViewById(R.id.reg_btn);
        name = findViewById(R.id.name);
        email =findViewById(R.id.email_reg);
        password = findViewById(R.id.password_reg);
        SignIn = findViewById(R.id.Sign_In);
        number = findViewById(R.id.number_reg);
        address = findViewById(R.id.address_reg);

        SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });

        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createUser();
                progressBar.setVisibility(View.VISIBLE);
               }
        });
    }

    private void createUser() {

        String userName = name.getText().toString();
        String userEmail = email.getText().toString();
        String userPassword = password.getText().toString();
        String userNumber = number.getText().toString();
        String userAddress = address.getText().toString();

        if (TextUtils.isEmpty(userName)){
            Toast.makeText(this, "Enter your Name!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(userEmail)){
            Toast.makeText(this, "Enter your Email!!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(userPassword)){
            Toast.makeText(this, "Enter Password!!!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (userPassword.length() < 8) {
            Toast.makeText(this,"Minimum password length should be 8", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(userNumber)){
            Toast.makeText(this, "Enter your Number", Toast.LENGTH_SHORT).show();
            return;
        }
        if (userNumber.length() != 10) {
            Toast.makeText(this, "Phone number length should be 10", Toast.LENGTH_SHORT).show();
            return;
        }
        if (userNumber.length() > 10) {
            Toast.makeText(this, "Phone number length should be 10", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(userAddress)){
            Toast.makeText(this, "Enter address", Toast.LENGTH_SHORT).show();
        }

        auth.createUserWithEmailAndPassword(userEmail, userPassword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            UserModel userModel = new UserModel(userName, userEmail, userPassword, userNumber, userAddress);
                            String id = task.getResult().getUser().getUid();
                            database.getReference().child("Users").child(id).setValue(userModel);
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(RegisterActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(RegisterActivity.this, HomeActivity.class));
                        }
                        else {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(RegisterActivity.this, "Error: "+ task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}