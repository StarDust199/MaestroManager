package com.example.maestro;

import android.content.Context;
import android.view.OrientationEventListener;
import android.view.Surface;
import android.view.WindowManager;

public class MyOrientationEventListener extends OrientationEventListener {
    private Context mContext;
    private int mOrientation = -1;

    public MyOrientationEventListener(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    public void onOrientationChanged(int orientation) {
        if (orientation == ORIENTATION_UNKNOWN) {
            return;
        }
        int rotation = ((WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getRotation();
        if (rotation == Surface.ROTATION_0 || rotation == Surface.ROTATION_180) {
            // Obracanie tylko w orientacjach pionowych
            if (orientation > 350 || orientation < 10) {
                if (mOrientation != 0) {
                    mOrientation = 0;
                    // Obrócenie widoku na poziomie 0 stopni
                    // np. findViewById(R.id.my_view).setRotation(0);
                }
            } else if (orientation > 80 && orientation < 100) {
                if (mOrientation != 90) {
                    mOrientation = 90;
                    // Obrócenie widoku na poziomie 90 stopni
                    // np. findViewById(R.id.my_view).setRotation(90);
                }
            } else if (orientation > 170 && orientation < 190) {
                if (mOrientation != 180) {
                    mOrientation = 180;
                    // Obrócenie widoku na poziomie 180 stopni
                    // np. findViewById(R.id.my_view).setRotation(180);
                }
            } else if (orientation > 260 && orientation < 280) {
                if (mOrientation != 270) {
                    mOrientation = 270;
                    // Obrócenie widoku na poziomie 270 stopni
                    // np. findViewById(R.id.my_view).setRotation(270);
                }
            }
        }
    }
}
