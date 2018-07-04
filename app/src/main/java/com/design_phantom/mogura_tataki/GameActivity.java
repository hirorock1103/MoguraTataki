package com.design_phantom.mogura_tataki;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.design_phantom.mogura_tataki.R;

public class GameActivity extends AppCompatActivity {

    private SurfaceView surfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        LinearLayout layout = findViewById(R.id.my_surfaceview);
        //SurfaceView view = new MySurfaceView2(this);
        layout.addView(new MySurfaceView2(this));
        //setContentView(new MySurfaceView2(this));

        Button bt = findViewById(R.id.my_bt_finish_game);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //アラートdialog
                AlertDialog.Builder builder = new AlertDialog.Builder(GameActivity.this);

                builder.setTitle(getResources().getString(R.string.app_name))
                        .setMessage("ゲームを終了しますか？")
                        .setCancelable(false)
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                //やめる場合の処理 -- surfaceviewを終了
                                //スレッドを終了する

                                Intent intent = new Intent(GameActivity.this, TopActivity.class);
                                GameActivity.this.startActivity(intent);


                            }
                        })
                        .setNegativeButton("NG", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // do nothing
                            }
                        });

                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });


    }


}
