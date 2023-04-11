package com.example.maestro;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class InstrumentActivity extends AppCompatActivity {
    private MyOrientationEventListener mOrientationEventListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instrument);
        mOrientationEventListener = new MyOrientationEventListener(this);
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