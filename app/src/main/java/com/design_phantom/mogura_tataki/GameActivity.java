package com.design_phantom.mogura_tataki;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SurfaceView;

import com.design_phantom.myapp_game20180526_1.R;

public class GameActivity extends AppCompatActivity {

    private SurfaceView surfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_game);
        setContentView(new MySurfaceView2(this));

    }





}
