package com.example.security_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    TextInputEditText email,password;
    Button login;
    FirebaseAuth mAuth;
    TextView signUp;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth=FirebaseAuth.getInstance();
        email=findViewById(R.id.email_input_login);
        password=findViewById(R.id.password_input_login);
        login=findViewById(R.id.btnLogin);
        signUp=findViewById(R.id.signup);


        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Login.this,MainActivity.class);
                startActivity(i);
                finish();
            }
        });


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Email=email.getText().toString();
                String Password=password.getText().toString();

                if(Patterns.EMAIL_ADDRESS.matcher(Email).matches()&& Password.length()>=8)
                {
                    mAuth.signInWithEmailAndPassword(Email,Password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful())
                                    {
                                        FirebaseUser user= mAuth.getCurrentUser();
                                        if(user!=null)
                                        {
                                            Intent i=new Intent(Login.this,MainPage.class);
                                            startActivity(i);
                                            finish();
                                        }
                                    }
                                    else {
                                        Toast.makeText(Login.this,"Login Failed",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
                else if (!Patterns.EMAIL_ADDRESS.matcher(Email).matches())
                {
                    email.setError("Enter a valid Email Address");
                    email.requestFocus();
                }
                else
                {
                    password.setError("Password must be at least 8 character long");
                    password.requestFocus();
                }
            }
        });
    }
}