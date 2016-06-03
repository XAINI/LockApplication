package com.example.shanzhenqiang.lockapplication;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "LockScreenActivity";
    private SliderRelativeLayout sliderRelativeLayout;
    private ImageView imageView;
    public static int MSG_LOCK_SUCCESS = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /**隐去电池等图标和一切修饰部分（状态栏部分）*/
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        /** 屏蔽back键*/
        this.getWindow().setFlags(WindowManager.LayoutParams.TYPE_KEYGUARD_DIALOG, WindowManager.LayoutParams.TYPE_KEYGUARD_DIALOG);

        sliderRelativeLayout = (SliderRelativeLayout) findViewById(R.id.sliderLayout);
        sliderRelativeLayout.setMainHandler(handler);

        startService(new Intent(MainActivity.this, LockScreenService.class)); //这里要显示的调用服务
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (MSG_LOCK_SUCCESS == msg.what){
                finish();
            }
        }
    };
}
