package com.example.shanzhenqiang.lockapplication;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class LockScreenService extends Service {

    private final static String TAG = "LockScreenService";
    private Intent lockIntent;
    public AdminReceiver lockReceiver = new AdminReceiver();

    public LockScreenService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new LockBinder();
    }

    public class LockBinder extends Binder {

        public LockScreenService getService() {
            return LockScreenService.this;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();

        lockIntent = new Intent(this, LockScreenActivity.class);
        lockIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        /* 注册广播 */
        IntentFilter mScreenOnFilter = new IntentFilter("android.intent.action.SCREEN_ON");
        LockScreenService.this.registerReceiver(lockReceiver, mScreenOnFilter);

        IntentFilter mScreenOffFilter = new IntentFilter("android.intent.action.SCREEN_OFF");
        LockScreenService.this.registerReceiver(lockReceiver, mScreenOffFilter);

    }

    @Override
    public void onDestroy() {
        Log.i(TAG, "----------------- onDestroy------");
        super.onDestroy();
        LockScreenService.this.unregisterReceiver(lockReceiver);
    }
}
