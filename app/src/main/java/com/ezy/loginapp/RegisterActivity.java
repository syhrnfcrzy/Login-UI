package com.ezy.loginapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {
    EditText mFullname,mPassword,mEmail;
    TextView Loginbtn;
    Button Registerbtn;
    FirebaseAuth fAuth;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mFullname=findViewById(R.id.RegName);
        mEmail=findViewById(R.id.RegEmail);
        mPassword=findViewById(R.id.RegPassword);
        Loginbtn=findViewById(R.id.txtLogin);
        Registerbtn=findViewById(R.id.BtnReg);
        progressBar=findViewById(R.id.progressBar);
        fAuth=FirebaseAuth.getInstance();
        if (fAuth.getCurrentUser()!=null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }

        Registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=mEmail.getText().toString().trim();
                String password=mPassword.getText().toString().trim();
                if (TextUtils.isEmpty(email)){
                    mEmail.setError("Email is Required ");
                    return;
                }
                if (TextUtils.isEmpty(password)){
                    mPassword.setError("Email is Required");
                    return;
                }
                if (password.length()<8){
                    mPassword.setError("Password must be Greater than 6");
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                //Register User data
                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()){
                            Toast.makeText(RegisterActivity.this,"User Created",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }
                        else {
                            Toast.makeText(RegisterActivity.this,"error"+task.getException().getMessage(),Toast.LENGTH_SHORT);
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });

        Loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            }
        });
    }

}