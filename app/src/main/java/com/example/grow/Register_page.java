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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Register_page extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_page);
        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mAuth = FirebaseAuth.getInstance();

        TextView login = (TextView)findViewById(R.id.log);
        login.setOnClickListener(x -> finish());

        Button register = findViewById(R.id.btn_register);
        register.setOnClickListener(x -> {
            String email = ((EditText)findViewById(R.id.editTextTextEmailAddress2)).getText().toString();
            String password = ((EditText)findViewById(R.id.editTextTextPassword2)).getText().toString();
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, task -> {
                if(task.isSuccessful()) {
                    Toast.makeText(Register_page.this, "Registered successfully", Toast.LENGTH_LONG).show();
                    finish();
                }
                else {
                    Toast.makeText(Register_page.this, "Something went wrong", Toast.LENGTH_LONG).show();
                }
            });
        });
    }
}