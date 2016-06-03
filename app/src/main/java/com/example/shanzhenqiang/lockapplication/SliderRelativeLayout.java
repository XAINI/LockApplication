package com.example.shanzhenqiang.lockapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * Created by shanzhenqiang on 2016/5/30.
 */
public class SliderRelativeLayout extends RelativeLayout {
    private final static String TAG = "SliderRelativeLayout";
    private Context context;
    private Handler handler = null; //信息传递
    private Bitmap dragBitmap = null; //拖拽图片

    public SliderRelativeLayout(Context context) {
        super(context);
        SliderRelativeLayout.this.context = context;
        intiDragBitmap();
    }

    public SliderRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
        SliderRelativeLayout.this.context = context;
        intiDragBitmap();
    }

    public SliderRelativeLayout(Context context, AttributeSet attrs,
                                int defStyle) {
        super(context, attrs, defStyle);
        SliderRelativeLayout.this.context = context;
        intiDragBitmap();
    }

    /**
     * 得到拖拽图片
     */
    private void intiDragBitmap() {
        if (dragBitmap == null) {
            dragBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.suoping);
        }
    }

    /**
     * 对拖拽图片不同的点击事件处理
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:/**初次触摸屏幕*/
                Log.i(TAG, "您已触摸屏幕");
                return true;

            case MotionEvent.ACTION_MOVE: /**滑动*/
                invalidate(); /**重新绘图*/
                return true;

            case MotionEvent.ACTION_UP: /**抬起*/
                return true;
        }
        return super.onTouchEvent(event);
    }

    /**
     * 与主activity通信
     * @param handler
     */
    public void setMainHandler(Handler handler){
        this.handler = handler;
    }

}
