package com.example.maestro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AttendanceActivity extends AppCompatActivity {
    private MyOrientationEventListener mOrientationEventListener;
    private RecyclerView activityList;
    private Button markAttendanceButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);
        mOrientationEventListener = new MyOrientationEventListener(this);

        activityList = findViewById(R.id.activity_list);
        markAttendanceButton = findViewById(R.id.mark_attendance_button);

        markAttendanceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // code for marking attendance

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