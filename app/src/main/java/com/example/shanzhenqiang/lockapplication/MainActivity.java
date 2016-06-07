package com.example.shanzhenqiang.lockapplication;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "MainActivity";
    private Intent service_intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        service_intent = new Intent(MainActivity.this, LockScreenService.class); //这里要显示的调用服务
    }

    public void start_service(View view){
        Toast.makeText(getApplicationContext(), "启动锁屏监听服务", Toast.LENGTH_SHORT).show();
        this.startService(service_intent);
    }

    public void stop_service(View view){
        Toast.makeText(getApplicationContext(), "停止锁屏监听服务", Toast.LENGTH_SHORT).show();
        this.stopService(service_intent);
    }
}
