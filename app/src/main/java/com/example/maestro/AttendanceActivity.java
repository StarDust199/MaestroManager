package com.example.maestro;

import static android.content.ContentValues.TAG;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Date;

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
                AlertDialog.Builder builder = new AlertDialog.Builder(AttendanceActivity.this);
                builder.setTitle("Odznacz obecność");
                final EditText input = new EditText(AttendanceActivity.this);
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String name = input.getText().toString();
                        addAttendanceEntry(name);
                    }
                });
                builder.setNegativeButton("Anuluj", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            }
        });
        
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void addAttendanceEntry(String name) {
        // pobranie obecnej daty i czasu
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = dateFormat.format(currentDate);

        // zapisanie wpisu do dziennika aktywności
        String entry = name + " był obecny/a o godzinie " + dateString;
        Log.d(TAG, "Dodano wpis do dziennika aktywności: " + entry);
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