package com.design_phantom.myapp_game20180526_1;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by amb01 on 2018/05/26.
 */

public class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback, Runnable {
    static final long FPS = 20;
    static final long FRAME_TIME = 1000 / FPS;
    static final int BALL_R = 30;
    SurfaceHolder surfaceHolder;
    Thread thread;
    int cx = BALL_R, cy = BALL_R;
    int screen_width, screen_height;


    public MySurfaceView(Context context) {
        super(context);
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {

//        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
//        paint.setColor(Color.BLUE);
//        paint.setStyle(Paint.Style.FILL);
//
//        Canvas canvas = surfaceHolder.lockCanvas();
//        canvas.drawColor(Color.BLACK);
//        canvas.drawCircle(100, 300, 50, paint);
//
//        surfaceHolder.unlockCanvasAndPost(canvas);

        thread = new Thread(this);
        thread.start();

    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
            screen_width = i1;
            screen_height = i2;
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        thread = null;
    }

    @Override
    public void run() {

        Canvas canvas = null;
        Paint paint = new Paint();
        Paint bgPaint = new Paint();


        // Background
        bgPaint.setStyle(Paint.Style.FILL);
        bgPaint.setColor(Color.WHITE);
        // Ball
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.BLUE);



        long loopCount = 0;
        long waitTime = 0;
        long startTime = System.currentTimeMillis();


        while(thread != null){

            try{
                loopCount++;
                canvas = surfaceHolder.lockCanvas();

                canvas.drawRect(
                        0, 0,
                        screen_width, screen_height,
                        bgPaint);
                canvas.drawCircle(
                        cx++, cy++, BALL_R,
                        paint);

                surfaceHolder.unlockCanvasAndPost(canvas);

                waitTime = (loopCount * FRAME_TIME)
                        - (System.currentTimeMillis() - startTime);

                if( waitTime > 0 ){
                    Thread.sleep(waitTime);
                }
            }
            catch(Exception e){}
        }

    }
}
