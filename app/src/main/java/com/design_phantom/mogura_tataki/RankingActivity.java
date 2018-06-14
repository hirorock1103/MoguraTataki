package com.design_phantom.mogura_tataki;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class RankingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        final Button button_back_to_top = findViewById(R.id.button_back_to_top);
        button_back_to_top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RankingActivity.this, TopActivity.class);
                startActivity(intent);
            }
        });

        //web上からランキング情報を取得する

        ArrayList<RankingCard> list = new ArrayList<>();

        for(int i = 0; i < MyData.ranking.length; i++){
            list.add(new RankingCard(
                    MyData.ranking[i],
                    MyData.score[i],
                    MyData.name[i],
                    "2018/03/02"
            ));
        }

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llManager = new LinearLayoutManager(this);
        llManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llManager);
        recyclerView.setAdapter(new MyAdapter(list));

    }

    public static class MyData{

        public static Integer[] ranking = {
                1,2,3,4,5,6,7,8,9,10,11
        };
        public static Integer[] score = {
                34,31,20,28,25,23,22,18,17,10,9
        };
        public static String[] name = {
                "AAAAAA", "FEREF","FEDDDD","GGGGGG","DFDSDF","REERER","Fdfdee","FFFFFF","Lkd","EDD","FFFFFF",
        };

    }


    private class MyAdapter extends RecyclerView.Adapter<MyViewHolder>{

        ArrayList<RankingCard> list;

        MyAdapter(ArrayList<RankingCard> list){
            this.list = list;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.ranking_card, parent, false);

            return new MyViewHolder(v);

        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {

            holder.name.setText(list.get(position).getName().toString());
            holder.rank.setText(String.valueOf(list.get(position).getRank()));
            holder.regDate.setText(list.get(position).getRegDate().toString());
            holder.score.setText(String.valueOf(list.get(position).getScore()) + "匹");

        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }



    private class MyViewHolder extends RecyclerView.ViewHolder{

        View base;
        TextView rank;
        TextView name;
        TextView score;
        TextView regDate;

        public MyViewHolder(View itemView) {
            super(itemView);

            rank = itemView.findViewById(R.id.my_rank);
            name = itemView.findViewById(R.id.my_rank_name);
            score = itemView.findViewById(R.id.my_rank_score);
            regDate = itemView.findViewById(R.id.my_rank_date);

        }
    }

    private class RankingCard {

        private int rank;
        private int score;
        private String name;
        private String regDate;

        RankingCard(int rank, int score,  String name, String regDate){
            this.rank = rank;
            this.score = score;
            this.name = name;
            this.regDate = regDate;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }

        public int getRank() {
            return rank;
        }

        public void setRank(int rank) {
            this.rank = rank;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getRegDate() {
            return regDate;
        }

        public void setRegDate(String regDate) {
            this.regDate = regDate;
        }
    }






}
