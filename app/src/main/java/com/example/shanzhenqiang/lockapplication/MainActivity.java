package com.example.shanzhenqiang.lockapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "LockScreenActivity";
    private SliderRelativeLayout sliderRelativeLayout;
    public static int MSG_LOCK_SUCESS = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        //去头
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //隐去电池等图标和一切修饰部分（状态栏部分）
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
//
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);

        setContentView(R.layout.activity_main);
        LockLayer layer = new LockLayer(this);
        Log.v("this","IWantKnowKeyTHIS'sValue==="+this+"===IWantKnowLayer'sValueToo==="+layer);
        sliderRelativeLayout = (SliderRelativeLayout) findViewById(R.id.sliderLayout);
        sliderRelativeLayout.getBackground().setAlpha(180); //设置背景的透明度

        startService(new Intent(MainActivity.this, AdminReceiver.class)); //这里要显示的调用服务

        //  killMyself ，锁屏之后就立即kill掉我们的Activity，避免资源的浪费;
        //  android.os.Process.killProcess(android.os.Process.myPid());
    }
}
