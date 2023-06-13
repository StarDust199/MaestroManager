package com.example.maestro;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private MyOrientationEventListener mOrientationEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mOrientationEventListener = new MyOrientationEventListener(this);
        String userRole = getIntent().getStringExtra("userRole");
        boolean isAdmin = userRole != null && userRole.equals("Kapelmistrz");
        Button btnAddInstr = findViewById(R.id.btn_addInstr);
        Button btnAddNotes = findViewById(R.id.btn_addNotes);

        if (isAdmin) {
            btnAddInstr.setVisibility(View.VISIBLE);
            btnAddNotes.setVisibility(View.VISIBLE);
        } else {
            btnAddInstr.setVisibility(View.GONE);
            btnAddNotes.setVisibility(View.GONE);
        }
        Button button = findViewById(R.id.logout);
        button.setOnClickListener(view -> {
            SharedPreferences sharedPreferences = getSharedPreferences("SESSION_DATA", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.apply();

            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });


        ImageButton imageButton = findViewById(R.id.btn_attendance);
        imageButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AttendanceActivity.class);
            startActivity(intent);
        });



       ImageButton imageButton_c = findViewById(R.id.btn_calendar);
        imageButton_c.setOnClickListener(this::openAcivity);


        imageButton = findViewById(R.id.btn_instrument);
        imageButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, InstrumentActivity.class);
            startActivity(intent);
        });
        imageButton = findViewById(R.id.btn_notes);
        imageButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, NotesActivity.class);
            startActivity(intent);
        });
    }
    @Override
    public void onBackPressed() {
        // Nie wywołuj super metody, aby zablokować przycisk cofania
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

    public void openAcivity(View v) {
        Intent intent;
        intent = new Intent(MainActivity.this, CalendarActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();

    }
}