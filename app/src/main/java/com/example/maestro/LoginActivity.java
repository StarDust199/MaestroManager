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

public class LoginActivity extends AppCompatActivity {
 private Button button;
 private TextView txtView;
 private MyOrientationEventListener mOrientationEventListener;
    private ImageView mLogoImageView;
    private EditText mLoginEditText, mPasswordEditText;
    private String mLoginText,mPasswordText,mRegisterText;

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

        if(savedInstanceState != null) {
            // Przywróć stan ekranu po zmianie orientacji
            mLoginText = savedInstanceState.getString("login_text");
            mPasswordText = savedInstanceState.getString("password_text");
            mRegisterText = savedInstanceState.getString("register_text");

            mLoginEditText.setText(mLoginText);
            mPasswordEditText.setText(mPasswordText);
            txtView.setText(mRegisterText);
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
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
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Zapisz stan ekranu przed zmianą orientacji
        mLoginText = mLoginEditText.getText().toString();
        mPasswordText = mPasswordEditText.getText().toString();
        mRegisterText = txtView.getText().toString();

        outState.putString("login_text", mLoginText);
        outState.putString("password_text", mPasswordText);
        outState.putString("register_text", mRegisterText);
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Zablokuj zmianę orientacji ekranu dla elementów ImageView i Button
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            mLogoImageView.getLayoutParams().height = 120;
            mLogoImageView.getLayoutParams().width = 120;
            button.getLayoutParams().width = 400;
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            mLogoImageView.getLayoutParams().height = 188;
            mLogoImageView.getLayoutParams().width = 196;
            button.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
        }
    }
}