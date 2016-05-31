package com.example.shanzhenqiang.lockapplication;

import android.app.Activity;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;

/**
 * Created by shanzhenqiang on 2016/5/30.
 */
public class LockLayer {
    private Activity mActivity;
    private WindowManager mWindowManager;
    private View mLockView;
    private LayoutParams mLockViewLayoutParams;
    private boolean isLocked;

    public LockLayer(Activity act) {
        mActivity = act;
        init();
    }

    private void init() {
        isLocked = false;
        mWindowManager = mActivity.getWindowManager();
        mLockViewLayoutParams = new LayoutParams();
        mLockViewLayoutParams.width = LayoutParams.MATCH_PARENT;
        mLockViewLayoutParams.height = LayoutParams.MATCH_PARENT;
        // 这一行实现屏蔽Home
        mLockViewLayoutParams.type = LayoutParams.TYPE_SYSTEM_ERROR;
        mLockViewLayoutParams.flags = 1280;
    }

    public synchronized void lock() {
        if (mLockView != null && !isLocked) {
            mWindowManager.addView(mLockView, mLockViewLayoutParams);
        }
        isLocked = true;
    }

    public synchronized void unlock() {
        if (mWindowManager != null && isLocked) {
            mWindowManager.removeView(mLockView);
        }
        isLocked = false;
    }

    public synchronized void setLockView(View v) {
        mLockView = v;
    }
}
