package com.example.maestro;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    private MyOrientationEventListener mOrientationEventListener;
    private EditText mLoginEditText, mPasswordEditText;
    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mOrientationEventListener = new MyOrientationEventListener(this);
        Button button = findViewById(R.id.btn_login);
        TextView txtView = findViewById(R.id.text_register);
        ImageView mLogoImageView = findViewById(R.id.logo);
        mLoginEditText = findViewById(R.id.login);
        mPasswordEditText = findViewById(R.id.password);


        button.setOnClickListener(v -> {
            String login = mLoginEditText.getText().toString();
            String password = mPasswordEditText.getText().toString();

            DbHelper dbHelper = new DbHelper(LoginActivity.this);
            boolean userExists = dbHelper.checkUser(login, password);
            if (userExists) {
                boolean isAdmin = dbHelper.isAdmin(login, password);
                if (isAdmin) {
                    // Użytkownik jest administratorem
                    // Wykonaj odpowiednie działania dla admina, np. ustaw widoczność przycisków edycji
                    intent = new Intent(LoginActivity.this, MainActivity.class);
                } else {
                    // Użytkownik jest zwykłym użytkownikiem
                    // Wykonaj odpowiednie działania dla użytkownika, np. ukryj przyciski edycji
                    intent = new Intent(LoginActivity.this, MainActivity.class);
                }
            } else {
                Toast.makeText(LoginActivity.this, "Nieprawidłowe dane logowania", Toast.LENGTH_SHORT).show();
                intent = new Intent(LoginActivity.this, LoginActivity.class);
            }
            startActivity(intent);
        });

        txtView.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }
    protected void onResume() {
        super.onResume();

        // Rejestrowanie MyOrientationEventListener
        if (mOrientationEventListener.canDetectOrientation()) {
            mOrientationEventListener.enable();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Wyrejestrowywanie MyOrientationEventListener
        mOrientationEventListener.disable();
    }

}