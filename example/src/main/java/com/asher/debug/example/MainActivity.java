package com.asher.debug.example;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.asher.debug.R;
import com.asher.debug.annotation.Time;
//import com.asher.debug.annotation.Time;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        testTime();
    }

    @Time
    private void testTime() {
        try {
            Thread.sleep(5000);
            Log.i(TAG, "-----------  testTime --------");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
