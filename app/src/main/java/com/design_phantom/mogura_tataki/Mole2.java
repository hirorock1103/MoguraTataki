package com.design_phantom.mogura_tataki;

import android.media.SoundPool;
import android.util.Log;

import java.util.Random;

/**
 * Created by amb01 on 2018/06/01.
 */

public class Mole2 {

    private final static int speed = 10;
    private final static int stopTime = 1000;
    private final static int stopTimeInTop = 100;

    //移動用に使用する
    private int x = 0;
    private int y = 0;

    //最初に設置された場所
    private int f_x = 0;
    private int f_y = 0;

    //幅と高さ ※画像セット時に幅と高さを取得する。
    private int width;
    private int height;

    //次のアクションまでの設定時間（休憩→moveUp）
    long nextMoveTime;

    //↑向き動作の上限値
    int moveUpLimit = 100;

    //↑向き動作か↓向き動作を制御
    boolean onMoveUp = true;

    //穴の中で休憩中
    boolean restInHole = false;

    //リアクション
    boolean reaction = false;
    //name
    String reactionName = "";
    //リアクション終了時間
    long reactionTime;
    //身震い時の方向
    boolean trembleDiretionSwitch = false;


    Mole2(int x, int y, int width, int height){

        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        this.f_x = x;
        this.f_y = y;

        //
        setNextMoveTime(stopTime);
    }

    //幅をセットする（X地点から右向きの距離）
    public void setWidth(int width){
        this.width = width;
    }
    //高さをセットする（Y地点から右向きの距離）
    public void setHeight(int height){
        this.height = height;
    }
    //x位置を取得
    public int getLocationX(){
        return this.x;
    }
    //ｙ位置を取得
    public int getLocationY(){
        return this.y;
    }
    //最初のx位置を取得
    public int getFirstLocationX(){
        return this.f_x;
    }
    //最初のy位置を取得
    public int getFirstLocationY(){
        return this.f_y;
    }
    //自身の幅を取得
    public int getWidth(){
        return width;
    }
    //自身の高さを取得
    public int getHeight(){
        return height;
    }
    //上↑向きに動く
    public void moveUp(int speed){
        this.y -= speed;
    }
    //下↓向きに動く
    public void moveDown(int speed){
        this.y += speed;
    }
    //詳細
    public String toString(){

        StringBuilder builder = new StringBuilder();
        builder.append("f_x : " + this.f_x + "\n");
        builder.append("f_y : " + this.f_y + "\n");
        builder.append("imageWidth : " + this.width + "\n");
        builder.append("imageHeight : " + this.height + "\n");

        return builder.toString();
    }

    //休憩中かを確認
    public boolean isRest(){
        long current = System.currentTimeMillis();
        //次の動作時間より小さい場合は休憩中
        if(current < nextMoveTime){
            return true;
        }
        //休憩おわり
        if(restInHole == true){
            restInHole = false;
        }

        return false;
    }
    //穴で休憩中かどうか
    public boolean isRestInHole(){
        return restInHole;
    }

    //もぐらの動作
    public void moleAction(){

        //リアクションが有効な時
        if(reaction == true){

            if(System.currentTimeMillis() < reactionTime){

                if(this.getReaction() == "tremble"){
                    this.tremble();
                }

            }else{
                //this.x = this.f_x;
                reaction = false;
                setReaction("");
                this.x = this.f_x;
            }

        }else if( onMoveUp == true ){
            //上に移動する
            this.moveUp(speed);
            if( (this.f_y - this.y) > moveUpLimit){
                //初期位置-現在の位置が移動上限距離を超えた場合、移動の方向を切り替える
                onMoveUp = false;
                //次のアクションの時間を設定する。
                setNextMoveTime(stopTimeInTop);
            }
        }else{
            //下に移動する
            this.moveDown(speed);
            if( (this.f_y - this.y) < 0 ){
                //初期位置-現在の位置が０を下回ると、移動の方向を切り替える
                onMoveUp = true;
                //次のアクションの時間を設定する。
                setNextMoveTime(stopTime);
                //しばらく穴の中で休憩
                restInHole = true;
            }
        }
    }

    //次のアクションまでの時間をきめる
    public void setNextMoveTime(int stopTime){
        Random rand = new Random();
        nextMoveTime = System.currentTimeMillis() + ( stopTime * (rand.nextInt(5 ) + 1) );
    }

    //あたり判定
    public boolean isTouched( int touch_x, int touch_y){

        if( (touch_x >= this.x && touch_x <= (this.x + this.width))
                && (touch_y > this.y && touch_y <= (this.y + this.height)) ){

                //時間解除
                nextMoveTime = 0;
                //身震いする
                this.setReaction("tremble");
                return true;
        }
        return false;
    }

    //叫ぶ
    public void shout(SoundPool soundPool, int shoutId){
        soundPool.play(shoutId,1.0F, 1.0F, 0, 0, 1);
    }

    //震える
    public void setReaction(String action){

        reactionName = action;

        if(action == "tremble"){
            this.reaction = true;
            reactionTime = System.currentTimeMillis() + 4000;
        }

    }

    //身震い
    public void tremble(){

        int pos =  6;

        if(trembleDiretionSwitch == false){//右動き
            this.x += 5;
            if(this.x > (this.f_x + pos)){
                //方向逆に
                trembleDiretionSwitch = true;
            }
        }else{//左動き
            this.x -= 5;
            if(this.x < (this.f_x - pos)){
                //方向逆に
                trembleDiretionSwitch = false;
            }

        }

    }

    public String getReaction(){
        return reactionName;
    }

    //震える時間
    public boolean isReaction(){
        return reaction;
    }




}
