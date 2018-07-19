package com.design_phantom.mogura_tataki;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Toast;

import com.design_phantom.mogura_tataki.R;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by amb01 on 2018/06/01.
 */

public class MySurfaceView2 extends SurfaceView implements SurfaceHolder.Callback, Runnable, View.OnTouchListener {

    private final static int MOLENUMBER = 10;
    private final static int GAMEMILLITIME = 10000;
    private SurfaceHolder holder;
    private Thread thread;
    private ArrayList<Mole> moleList;
    private Bitmap moleImage;
    private SoundPool soundPool;
    private int attackSoundId;
    private int shoutSoundId;
    private int hitMoleCount = 0;
    private int fontStartSize = 600;


    public MySurfaceView2(Context context) {

        super(context);
        holder = getHolder();
        holder.addCallback(this);
        setOnTouchListener(this);

        soundPool = new SoundPool(2, AudioManager.STREAM_MUSIC, 0);
        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                if(status == 0){
                    Toast.makeText(getContext(),"LoadComplete",Toast.LENGTH_SHORT).show();
                }
            }
        });

        attackSoundId = soundPool.load(getContext(),R.raw.attack,1);
        shoutSoundId = soundPool.load(getContext(),R.raw.shout,1);

    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {

        moleList = new ArrayList<>();

        //set Image
        moleImage = BitmapFactory.decodeResource(getResources(), R.drawable.mini_mogura);

        //画面サイズ(px)
        int width = getWidth();
        int height = getHeight();

        //画像大きさ
        int imageWidth = moleImage.getWidth();
        int imageHeight = moleImage.getHeight();

        //SCORE部分の高さ
        int scoreHeight = 200;

        //もぐらを何匹セットできるか　
        int widthSattable = width / imageWidth;
        int heightSattable = (height-scoreHeight) / imageHeight;

        //セットできる最大数
        int maxCount = widthSattable * heightSattable;

        //余白
        int spaceWidth = getWidth() - (widthSattable * imageWidth);
        int spaceHeight = getHeight() - (heightSattable * imageHeight);



        if(MOLENUMBER > maxCount){
            //上限を超えています
        }else{
            Random rand = new Random();
            //もぐらをセット
            int roopCount = 0;
            int setCount = 0;
            for(int i = 0; i < heightSattable; i++){

                //１行あたりの余白
                int marginLeft = spaceWidth / widthSattable;

                for(int j = 0; j < widthSattable; j++){

                    roopCount++;

                    //設定値に達した
                    if(setCount == MOLENUMBER){
                        continue;
                    }

                    //残数に余裕がある場合ランダムで処理抜ける
                    if(setCount < (maxCount - roopCount)){
                        if(rand.nextInt(3) != 1){
                            continue;
                        }
                    }

                    //もぐらを作成
                    Mole mole = new Mole(
                            (j * imageWidth) + marginLeft,
                            i * imageHeight + 250,
                            imageWidth,
                            imageHeight
                    );

                    Log.i("INFO" , "mole : " + mole.toString());

                    moleList.add(mole);

                    //作成カウント
                    setCount++;

                }
            }
        }

        thread = new Thread(this);
        thread.start();


    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        Log.i("INFO","surfaceChanged");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        Log.i("INFO","surfaceDestroyed");
        thread = null;
    }

    @Override
    public void run() {

        long start = System.currentTimeMillis();

        //穴用
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.FILL);

        //もぐら隠す用
        Paint paint_hide = new Paint();
        paint_hide.setColor(Color.WHITE);
        paint_hide.setStyle(Paint.Style.FILL);

        int i = GAMEMILLITIME;
//        while(thread != null){
//        int secondsOfGameTime = 30;
        int secondsOfGameTime = 5;
        while(System.currentTimeMillis() - start < secondsOfGameTime * 1000){

            try{


                Canvas canvas = holder.lockCanvas();

                //キャンバス背景色
                canvas.drawColor(Color.WHITE);
                paint.setTextSize(100);
                canvas.drawText("Hit : " , 10.0f, 80.0f, paint);

                fontStartSize = (fontStartSize-40) > 100 ? fontStartSize-40 : 100;
                paint.setTextSize(fontStartSize);
                canvas.drawText(String.valueOf(hitMoleCount), 250.0f, 80.0f, paint);



                for(Mole mole : moleList){

                    //穴用
                    RectF rect = new RectF(
                            mole.getFirstLocationX(),
                            mole.getFirstLocationY(),
                            mole.getFirstLocationX() + mole.getWidth(),
                            mole.getFirstLocationY() + mole.getHeight() / 4
                    );

                    //canvas.drawOval(rect, paint);
                    canvas.drawRect(rect,paint);

                    canvas.drawBitmap(moleImage, mole.getLocationX(), mole.getLocationY(),null);

                    //hide用
                    canvas.drawRect(
                            mole.getFirstLocationX() ,
                            mole.getFirstLocationY() + mole.getHeight()/4,
                            mole.getFirstLocationX() + mole.getWidth(),
                            mole.getFirstLocationY() + mole.getHeight() + 10,
                            paint_hide);

                    //もぐらが休憩中じゃないとき、もぐらは動く (身震い中は例外)
                    if(mole.isRest() == false ){
                        mole.moleAction();
                    }

                }

                holder.unlockCanvasAndPost(canvas);
            }catch(Exception e){
                Log.i("INFO", e.getMessage());
            }
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {

        //攻撃音
        //soundPool.play(attackSoundId,1.0F, 1.0F, 0, 0, 1);

        for(Mole mole : moleList){

            if(mole.isRestInHole() == false && mole.isReaction() == false){
                //もぐらHit判定
                if(mole.isTouched((int)motionEvent.getX(), (int)motionEvent.getY())){
                    //もぐら鳴き声
                    mole.shout(soundPool, shoutSoundId);
                    //もぐらたたいたカウントを保存
                    hitMoleCount++;
                    //
                    fontStartSize = 600;
                }
            }

        }

        return false;
    }
}
