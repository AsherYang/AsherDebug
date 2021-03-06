package com.asher.debug.example;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.asher.debug.R;
import com.asher.debug.annotation.Time;

public class MainActivity extends Activity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        testTime("Asher", 20);
//        mThread.start();
        AsherTest asherTest = new AsherTest("Asher");
        asherTest.sayHello();
        hookException();
    }

    @Time
    private void testTime(String name, int age) {
        try {
            Thread.sleep(5000);
            Log.i(TAG, "-----------  name = " + name + " , age = " + age);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    Thread mThread = new Thread(new Runnable() {
        @Override
//        @Time
        public void run() {
            try {
                Thread.sleep(2000);
                Log.i(TAG, "-----------  Thread --------");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    });

//    @Time
    static class AsherTest {
        private final String name;

        AsherTest(String name) {
            this.name = name;
        }

        public String sayHello() {
            return "hello " + name;
        }
    }

    String nullPointer;
    private void hookException() {
        boolean result = nullPointer.equals("nullPoint");
        Log.i(TAG, String.valueOf(result));
    }
}
