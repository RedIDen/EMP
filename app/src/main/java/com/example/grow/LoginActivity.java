package com.example.grow;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mAuth = FirebaseAuth.getInstance();

        TextView register = findViewById(R.id.register);
        register.setOnClickListener(x -> startActivity(new Intent(LoginActivity.this, RegisterActivity.class)));

        Button login = findViewById(R.id.btn_login);
        login.setOnClickListener(x -> {
            String email = ((EditText)findViewById(R.id.editTextTextEmailAddress)).getText().toString();
            String password = ((EditText)findViewById(R.id.editTextTextPassword)).getText().toString();
            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, task -> {
                if(task.isSuccessful()) {
                    Toast.makeText(LoginActivity.this, "Authorized successfully", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                } else {
                    Toast.makeText(LoginActivity.this, "Wrong e-mail or password", Toast.LENGTH_LONG).show();
                }
            });
        });
    }
}