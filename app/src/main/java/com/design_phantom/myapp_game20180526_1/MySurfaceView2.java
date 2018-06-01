package com.design_phantom.myapp_game20180526_1;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by amb01 on 2018/05/30.
 */

public class MySurfaceView2 extends SurfaceView implements SurfaceHolder.Callback,Runnable {

    private SurfaceHolder holder;
    private Thread thread;
    ArrayList<Mole> charas = new ArrayList<>();
    private int characterAmount = 4;
    private Random random;
    private Bitmap droid;

    public MySurfaceView2(Context context) {

        super(context);
        holder = getHolder();
        holder.addCallback(this);
        random = new Random();
    }


    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {

        Random r = new Random();
        r.nextInt();

        Resources rcs = getResources();
        //droid = BitmapFactory.decodeResource(rcs, R.drawable.blink);
        droid = BitmapFactory.decodeResource(rcs, R.drawable.mini_mogura);

        int width = this.getWidth();
        int height = this.getHeight();

        Log.i("INFO", "width : " + width);
        Log.i("INFO", "height : " + height);



        //キャラクターを作成、自身のポジションへセット
        int radius = 150;

        for(int i = 0; i <  characterAmount; i++){
            if(i == 0 ){
                Mole circle = new Mole(
                        100,
                        250,
                        radius);
                charas.add(circle);
            }else if(i == 1){
                Mole circle = new Mole(
                        500,
                        250,
                        radius);
                charas.add(circle);
            }else if(i == 2){
                Mole circle = new Mole(
                        100,
                        600,
                        radius);
                charas.add(circle);
            }else if(i == 3){
                Mole circle = new Mole(
                        500,
                        600,
                        radius);
                charas.add(circle);
            }

        }

        thread = new Thread(this);
        thread.start();

    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        thread = null;
    }

    @Override
    public void run() {

        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.FILL);


        Paint paint_hide = new Paint();
        paint_hide.setColor(Color.WHITE);
        paint_hide.setStyle(Paint.Style.FILL);



        while(thread != null){

            Canvas canvas = holder.lockCanvas();
            canvas.drawColor(Color.WHITE);
            //canvas.drawCircle(x,y,r+=2, paint);
            for(Mole mole : charas){

                //自身のポジションへ移動
                //canvas.drawCircle(circle.getLocationX(),circle.getLocationY(),circle.getRadius(),paint);
                //canvas.drawCircle(circle.getFirstLocationX(),circle.getFirstLocationY(),circle.getRadius(),paint);
                //oval

                int x = mole.getRadius() - (mole.getRadius() * 2) ;
                int y = mole.getRadius() - (mole.getRadius() * 2) ;

                x += 100;
                y += -30;

                RectF rect = new RectF(
                        mole.getFirstLocationX() + x,
                        mole.getFirstLocationY() + y ,
                        mole.getFirstLocationX() + x + (mole.getRadius() * 2),
                        mole.getFirstLocationY() + y + mole.getRadius());

                //canvas.drawOval(rect, paint);

                canvas.drawRect(rect,paint);


                canvas.drawBitmap(droid, mole.getLocationX(), mole.getLocationY(),null);

                //hide用
                canvas.drawRect(
                        mole.getFirstLocationX() -30,
                        mole.getFirstLocationY() -30,
                        mole.getFirstLocationX() + 230,
                        mole.getFirstLocationY() + 230,
                        paint_hide);

                //Log.i("INFO", "x:" + circle.getFirstLocationX());
                //Log.i("INFO", "y:" + circle.getFirstLocationY());


                if (mole.wakeUp()) {
                    mole.action();
                }
            }

            holder.unlockCanvasAndPost(canvas);

//            try {
//                Thread.sleep(100 * random.nextInt(6));
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }


        }
    }
}
