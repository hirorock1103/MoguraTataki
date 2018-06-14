package com.design_phantom.mogura_tataki;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class TopActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_top);

        final Button buttonGameStart = this.findViewById(R.id.buttonGameStart);

        buttonGameStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TopActivity.this, GameActivity.class);
                startActivity(intent);
            }
        });

        final Button buttonRanking = this.findViewById(R.id.buttonRanking);
        buttonRanking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TopActivity.this, RankingActivity.class);
                startActivity(intent);
            }
        });

    }
}
