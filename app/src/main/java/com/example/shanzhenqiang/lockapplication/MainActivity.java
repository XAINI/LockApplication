package com.example.shanzhenqiang.lockapplication;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "LockScreenActivity";
    private SliderRelativeLayout sliderRelativeLayout;
    private ImageView imageView;
    public static int MSG_LOCK_SUCESS = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //隐去电池等图标和一切修饰部分（状态栏部分）
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // 屏蔽back键
        this.getWindow().setFlags(WindowManager.LayoutParams.TYPE_KEYGUARD_DIALOG,WindowManager.LayoutParams.TYPE_KEYGUARD_DIALOG);


        setContentView(R.layout.activity_main);

        registerIntentReceivers();

//        imageView = (ImageView) findViewById(R.id.wholeRing);
//        imageView.getBackground().setAlpha(180); //设置背景的透明度
//        startService(new Intent(MainActivity.this, AdminReceiver.class)); //这里要显示的调用服务

        //  killMyself ，锁屏之后就立即kill掉我们的Activity，避免资源的浪费;
        //  android.os.Process.killProcess(android.os.Process.myPid());
    }

    private void registerIntentReceivers()
    {
        Log.d(TAG, "***registerIntentReceivers");
        IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_OFF);
        filter.addAction("android.intent.action.SCREEN_ON");
        AdminReceiver receiver = new AdminReceiver(); //用于侦听
        registerReceiver(receiver, filter);
    }
}
