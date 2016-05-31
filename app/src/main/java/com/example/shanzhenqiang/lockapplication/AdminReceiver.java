package com.example.shanzhenqiang.lockapplication;

import android.app.KeyguardManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AdminReceiver extends BroadcastReceiver {
    private final static String TAG = "LockScreenReceiver";
    private KeyguardManager keyguardManager = null;
    private KeyguardManager.KeyguardLock keyguardLock = null;
    public AdminReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.ACTION_SCREEN_OFF.equals(Intent.ACTION_BOOT_COMPLETED)){
            Intent mIntent = new Intent(context, MainActivity.class);
            mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            keyguardManager = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
            keyguardLock = keyguardManager.newKeyguardLock("");
            keyguardLock.disableKeyguard();

            context.startActivity(mIntent);
        }
    }
}
