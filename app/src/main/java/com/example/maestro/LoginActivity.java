package com.example.maestro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
 private Button button;
 private TextView txtView;
 private MyOrientationEventListener mOrientationEventListener;
    private ImageView mLogoImageView;
    private EditText mLoginEditText, mPasswordEditText;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mOrientationEventListener = new MyOrientationEventListener(this);
        button= (Button) findViewById(R.id.btn_login);
        txtView=(TextView) findViewById(R.id.text_register);
        mLogoImageView = findViewById(R.id.logo);
        mLoginEditText = findViewById(R.id.login);
        mPasswordEditText = findViewById(R.id.password);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String login = mLoginEditText.getText().toString();
                String password = mPasswordEditText.getText().toString();

                DbHelper dbHelper = new DbHelper(LoginActivity.this);
                if (dbHelper.login(login, password)) {
                    intent = new Intent(LoginActivity.this, MainActivity.class);
                } else {
                    Toast.makeText(LoginActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
                startActivity(intent);
            }
        });

        txtView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
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