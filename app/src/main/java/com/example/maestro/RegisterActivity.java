package com.example.maestro;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {
    private MyOrientationEventListener mOrientationEventListener;
    private EditText mLogin, mPassword, mEmail;
    DbHelper db;
    RegistrationModel model;
    Button btn;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new DbHelper(RegisterActivity.this);
        setContentView(R.layout.activity_register);
        mLogin = findViewById(R.id.login);
        mPassword = findViewById(R.id.password);
        mEmail = findViewById(R.id.email);
        btn= findViewById(R.id.btn_register);
        if (getIntent().getExtras() != null && getIntent().hasExtra("id")) {
            model = db.getModelByID(getIntent().getIntExtra("id", 0));
            if (model != null) {
                mLogin.setText(model.getLogin());
                mPassword.setText(model.getPassword() + "");
                mEmail.setText(model.getEmail());

            } else {
                Toast.makeText(this, "Napotkano problem", Toast.LENGTH_SHORT).show();
            }
        } else {
            model = new RegistrationModel();
        }
        btn.setOnClickListener(this::saveUser);

        mOrientationEventListener = new MyOrientationEventListener(this);
        TextView txtView = findViewById(R.id.txt_login);
        txtView.setOnClickListener(view -> {
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
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
    public void saveUser(View view) {
        String login = mLogin.getText().toString();
        String password = mPassword.getText().toString();

        if (login.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Nazwa użytkownika i hasło są wymagane", Toast.LENGTH_SHORT).show();
            return;
        }

        if (model.getId() == 0)
            model.setLogin(mLogin.getText().toString());

        model.setPassword(mPassword.getText().toString());
        model.setEmail(mEmail.getText().toString());
        if (model.getId() > 0) {
            if (db.updateUsers(model)) {
                Toast.makeText(this, "updated", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
            }
        } else {
            if (db.addUser(model)) {
                Toast.makeText(this, "success", Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
            }
        }
        backLoginAcivity(view);
    }

    public void backLoginAcivity(View v) {
        Intent intent;
        if (model != null && model.getId() > 0) {
            intent = new Intent(this, LoginActivity.class);
        } else {
            intent = new Intent(this, RegisterActivity.class);
        }
        startActivity(intent);
    }
}