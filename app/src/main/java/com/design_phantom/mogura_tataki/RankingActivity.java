package com.design_phantom.mogura_tataki;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import net.arnx.jsonic.JSON;
import net.arnx.jsonic.JSONReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RankingActivity extends AppCompatActivity {

    // JSONからフィールドにListを含むJavaオブジェクトへの変換
    private String jsonStr = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        AsyncTask task = new AsyncTask<Object, Object, String>() {
            @Override
            protected String doInBackground(Object[] object) {
                String json = "";;
                try {
                    URL webApiUrl = new URL("http://mdiz1103.xsrv.jp/ranking.php");
                    InputStream in = webApiUrl.openStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    json = reader.readLine();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return json;
            }

            @Override
            protected void onPostExecute(String json) {
                super.onPostExecute(json);
                System.out.println(json);
                jsonStr = json;

                JSONReader reader = new JSON().getReader(jsonStr);
                try {
                    reader.next();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Map<?, ?> map = null;
                try {
                    map = reader.getMap();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                List<Map> rankingData = (List) map.get("ranking");

                //web上からランキング情報を取得する

                ArrayList<RankingCard> list = new ArrayList<>();

                for (Map<String, Object> ranking : rankingData) {
                    list.add(new RankingCard(
                            ((BigDecimal) ranking.get("rank")).intValue(),
                            ((BigDecimal) ranking.get("hit_count")).intValue(),
                            ranking.get("player_name").toString(),
                            ranking.get("createdate").toString()
                    ));
                }

                RecyclerView recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
                recyclerView.setHasFixedSize(true);
                LinearLayoutManager llManager = new LinearLayoutManager(RankingActivity.this);
                llManager.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(llManager);
                recyclerView.setAdapter(new MyAdapter(list));

            }
        };
        task.execute();

        final Button button_back_to_top = findViewById(R.id.button_back_to_top);
        button_back_to_top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RankingActivity.this, TopActivity.class);
                startActivity(intent);
            }
        });

    }

    private class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

        ArrayList<RankingCard> list;

        MyAdapter(ArrayList<RankingCard> list) {
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


    private class MyViewHolder extends RecyclerView.ViewHolder {

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

        RankingCard(int rank, int score, String name, String regDate) {
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
