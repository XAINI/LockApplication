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
    private Bitmap dragBitmap = null; //拖拽图片
    private int locationY = 0; //bitmap初始绘图位置，足够大，可以认为看不见
    private ImageView wholeRingView = null; //主要是获取相对布局的高度
    private Handler handler = null; //信息传递
    private static int BACK_DURATION = 10 ;   // 回退动画时间间隔值  20ms
    private static float VE_HORIZONTAL = 0.9f ;  // 水平方向前进速率 0.1dip/ms

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
        if(dragBitmap == null){
            dragBitmap = BitmapFactory.decodeResource(context.getResources(),R.drawable.suoping);
        }
    }

    /**
     * 这个方法里可以得到一个其他资源
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        wholeRingView = (ImageView) findViewById(R.id.wholeRing);
    }

    /**
     * 对拖拽图片不同的点击事件处理
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int Y = (int) event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                locationY = (int) event.getY();
                Log.i(TAG, "是否点击到位=" + isActionDown(event));
                return isActionDown(event);//判断是否点击了滑动区域

            case MotionEvent.ACTION_MOVE: //保存Y轴方向，绘制图画
                locationY = Y;
                invalidate(); //重新绘图
                return true;

            case MotionEvent.ACTION_UP: //判断是否解锁成功
                if(!isLocked()){ //没有解锁成功,动画应该回退
                    handleActionUpEvent(event); //动画回退
                }
                return true;
        }
        return super.onTouchEvent(event);
    }

    /**
     * 回退动画
     * @param event
     */
    private void handleActionUpEvent(MotionEvent event) {
        int Y = (int) event.getY();
        int whole = wholeRingView.getHeight();

        locationY = Y - whole;
        if(locationY <= getScreenHeight()/2){
            handler.postDelayed(ImageBack, BACK_DURATION); //回退
        }
    }

    /**
     * 未解锁时，图片回退
     */
    private Runnable ImageBack = new Runnable() {
        @Override
        public void run() {
            locationY = locationY - (int) (VE_HORIZONTAL*BACK_DURATION);
            if(locationY >= 0){
                handler.postDelayed(ImageBack, BACK_DURATION); //回退
                invalidate();
            }
        }
    };
//
    /**
     * 判断是否点击到了滑动区域
     * @param event
     * @return
     */
    private boolean isActionDown(MotionEvent event) {
        Rect rect = new Rect();
        wholeRingView.getHitRect(rect);
        boolean isIn = rect.contains((int)event.getX(), (int)event.getY()-wholeRingView.getHeight());
        if(isIn){
            wholeRingView.setVisibility(View.GONE);
            return true;
        }
        return false;
    }
//
    /**
     * 绘制拖动时的图片
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        invalidateDragImg(canvas);
    }
//
    /**
     * 图片随手势移动
     * @param canvas
     */
    private void invalidateDragImg(Canvas canvas) {
        int drawY = locationY - dragBitmap.getHeight();
        int drawX = wholeRingView.getWidth();

        if(drawY < getScreenHeight()/2){ //向上划到中上位置
            wholeRingView.setVisibility(View.VISIBLE);
            return;
        } else {
            if(isLocked()){ //判断是否成功
                return;
            }
            wholeRingView.setVisibility(View.GONE);
            canvas.drawBitmap(dragBitmap, drawY < 0 ? 5 : drawY,drawX,null);
        }
    }

    /**
     * 判断是否解锁
     */
    private boolean isLocked(){
        if(locationY > (getScreenHeight() - wholeRingView.getHeight())){
            handler.obtainMessage(MainActivity.MSG_LOCK_SUCESS).sendToTarget();
            return true;
        }
        return false;
    }

    /**
     * 获取屏幕高度
     * @return
     */
    private int getScreenHeight(){
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int height = manager.getDefaultDisplay().getHeight();
        return height;
    }

    /**
     * 与主activity通信
     * @param handler
     */
    public void setMainHandler(Handler handler){
        this.handler = handler;
    }
}
