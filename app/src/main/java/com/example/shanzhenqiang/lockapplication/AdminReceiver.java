package com.example.shanzhenqiang.lockapplication;

import android.app.KeyguardManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AdminReceiver extends BroadcastReceiver {
    private final static String TAG = "LockScreenReceiver";
    private KeyguardManager keyguardManager = null;
    private KeyguardManager.KeyguardLock keyguardLock = null;

    public AdminReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "----------------- Hello Receive ------");
        if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF) || intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
            Log.i(TAG, "-----------------This is "+intent.getAction()+" --------------");
            Intent mIntent = new Intent(context, LockScreenActivity.class);
            mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            keyguardManager = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
            keyguardLock = keyguardManager.newKeyguardLock("");
            keyguardLock.disableKeyguard();

            context.startActivity(mIntent);
        }
    }
}
