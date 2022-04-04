package com.example.grow;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Register_page extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_page);
        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        TextView login = (TextView)findViewById(R.id.log);

        TextView register = (TextView) findViewById(R.id.log);
        register.setOnClickListener(view -> {
            finish();
            //startActivity(new Intent(Register_page.this, Login_page.class));
        });
    }
}