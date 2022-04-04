package com.example.grow;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login_page extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);
        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = firebaseAuth -> {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            if (user != null) {
                startActivity(new Intent(Login_page.this, Main.class));
            }
        };

        TextView register = findViewById(R.id.register);
        register.setOnClickListener(x -> startActivity(new Intent(Login_page.this, Register_page.class)));

        Button login = findViewById(R.id.btn_login);
        login.setOnClickListener(x -> {
            String email = ((EditText)findViewById(R.id.editTextTextEmailAddress)).getText().toString();
            String password = ((EditText)findViewById(R.id.editTextTextPassword)).getText().toString();
            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, task -> {
                if(task.isSuccessful()) {
                    Toast.makeText(Login_page.this, "Authorized successfully", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(Login_page.this, Main.class));
                } else {
                    Toast.makeText(Login_page.this, "Wrong e-mail or password", Toast.LENGTH_LONG).show();
                }
            });
        });
    }
}