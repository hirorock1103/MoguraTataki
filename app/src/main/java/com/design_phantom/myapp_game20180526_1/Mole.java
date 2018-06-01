package com.design_phantom.myapp_game20180526_1;

import android.util.Log;

import java.util.Random;

/**
 * Created by amb01 on 2018/05/30.
 */

public class Mole {

    //移動用に使用する
    private int x = 0;
    private int y = 0;
    private int r = 0;

    //最初に設置された場所
    private int f_x = 0;
    private int f_y = 0;
    private int f_r = 0;

    //指定位置までの距離
    private int moveLimit = 200;

    //アクションの速度
    private int moveSpeed = 15;

    //アクションを制御 moveがtrueの時に指定位置へ動く falseはもとに戻る
    boolean move = true;

    //引っ込んだあと次のアクションの開始時間
    private long nextmoveTime;

    //次のアクションへのタイミング
    Random random = new Random();


    Mole(int x, int y, int r) {

        this.x = x;
        this.y = y;
        this.r = r;

        this.f_x = x;
        this.f_y = y;
        this.f_r = r;

        //開始時間をセット
        long currentTime = System.currentTimeMillis();
        nextmoveTime = currentTime + (1000 * (random.nextInt(8) + 2));

    }

    public int getLocationX() {
        return this.x;
    }

    public int getLocationY() {
        return this.y;
    }

    public int getFirstLocationX() {
        return this.f_x;
    }

    public int getFirstLocationY() {
        return this.f_y;
    }

    public int getRadius() {
        return this.r;
    }

    public void setLocationX(int x) {
        this.x = x;
    }

    public void setLocationY(int y) {
        this.y = y;
    }

    public void moveRight(int right) {
        this.x += right;
    }

    public void moveLeft(int left) {
        this.x -= left;
    }

    public void moveTop(int top) {
        long currentTime = System.currentTimeMillis();

        // 上移動
        if (move == true) {
            this.y -= top;
            if ((this.f_y - this.y) >= moveLimit) {
                move = false;
                nextmoveTime = currentTime + (50 * (random.nextInt(8) + 2));
            }
        } else {
            this.y += top;
            if ((this.f_y - this.y) <= 0) {
                move = true;
                //nextMoveTimeをセット
                nextmoveTime = currentTime + (1000 * (random.nextInt(8) + 2));
            }
        }

    }

    public void action() {
        moveTop(moveSpeed);
    }

    public void moveBottom(int bottom) {
        this.y += bottom;
    }

    public void setRadius(int r) {
        this.r = r;
    }

    public boolean wakeUp() {
        long currentTime = System.currentTimeMillis();

        return currentTime > nextmoveTime;
    }

    //待機時間を超えた場合に
}
