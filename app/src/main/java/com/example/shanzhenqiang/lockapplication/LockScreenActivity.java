package com.example.shanzhenqiang.lockapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

public class LockScreenActivity extends AppCompatActivity {
    private final String TAG = "LockScreenActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**隐去电池等图标和一切修饰部分（状态栏部分）*/
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        /** 屏蔽back键*/
        this.getWindow().setFlags(WindowManager.LayoutParams.TYPE_KEYGUARD_DIALOG, WindowManager.LayoutParams.TYPE_KEYGUARD_DIALOG);
        setContentView(R.layout.activity_lock_screen);
    }

    public void close(View view){
        finish();
    }
}
