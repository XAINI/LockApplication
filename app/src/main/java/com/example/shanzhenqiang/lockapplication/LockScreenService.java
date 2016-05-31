package com.example.shanzhenqiang.lockapplication;

import android.app.KeyguardManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;

public class LockScreenService extends Service {

    private final static String TAG = "LockScreenService";
    private Intent lockIntent;
    private KeyguardManager keyguardManager = null;
    private KeyguardManager.KeyguardLock keyguardLock = null;

    public LockScreenService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        lockIntent = new Intent(LockScreenService.this, MainActivity.class);
        lockIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        /* 注册广播 */
        IntentFilter mScreenOnFilter = new IntentFilter("android.intent.action.SCREEN_ON");
        LockScreenService.this.registerReceiver(mScreenOnReceiver, mScreenOnFilter);

        //注册广播
        IntentFilter mScreenOffFilter = new IntentFilter(Intent.ACTION_SCREEN_OFF);
        LockScreenService.this.registerReceiver(mScreenOffReceiver, mScreenOffFilter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LockScreenService.this.unregisterReceiver(mScreenOnReceiver);
        LockScreenService.this.unregisterReceiver(mScreenOffReceiver);
        //重新启动activity
        startService(new Intent(LockScreenService.this, LockScreenService.class));
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return Service.START_STICKY;
    }

    /**
     * 屏幕变亮的广播,我们要隐藏默认的锁屏界面
     */
    private BroadcastReceiver mScreenOnReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
                Log.i(TAG, "----------------- android.intent.action.SCREEN_ON------");
            }
        }
    };

    /**
     * 屏幕变暗/变亮的广播 ， 我们要调用KeyguardManager类相应方法去解除屏幕锁定
     */
    private BroadcastReceiver mScreenOffReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i(TAG, intent.getAction());
            if(intent.getAction().equals(Intent.ACTION_SCREEN_OFF)
                    || intent.getAction().equals(Intent.ACTION_SCREEN_ON)){

                keyguardManager = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
                keyguardLock = keyguardManager.newKeyguardLock("");
                keyguardLock.disableKeyguard(); //这里就是取消系统默认的锁屏

                startActivity(lockIntent); //注意这里跳转的意图
            }
        }
    };
}
