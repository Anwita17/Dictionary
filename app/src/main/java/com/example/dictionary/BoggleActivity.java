package com.example.dictionary;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class BoggleActivity extends AppCompatActivity {
    public static final String TAG="RecyclerViewAdapter";
    private ArrayList<String> word=new ArrayList<>();
    private ArrayList<String> meaning=new ArrayList<>();
    DisplayMetrics displayMetrics = new DisplayMetrics();
    EditText search_bar;
    TextView wordofday;
    RecyclerView rv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boggle);
        getWordAndMeaning();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        search_bar=(EditText)findViewById(R.id.search_bar);
        wordofday=(TextView)findViewById(R.id.word1);
        rv=(RecyclerView)findViewById(R.id.recyclerview);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, height/3, 0, 0);
        params1.setMargins(0,height/2+120,0,40);
        search_bar.setLayoutParams(params);
        wordofday.setLayoutParams(params1);
        params2.setMargins(0,height/2+280,0,0);
        rv.setLayoutParams(params2);
    }

    //get words through API
    public void getWordAndMeaning(){
        word.add("\"apple\"");
        meaning.add("the round fruit of a tree of the rose family");

        word.add("\"apple\"");
        meaning.add("the round fruit of a tree of the rose family");

        word.add("\"apple\"");
        meaning.add("the round fruit of a tree of the rose family");

        word.add("\"apple\"");
        meaning.add("the round fruit of a tree of the rose family");

        word.add("\"apple\"");
        meaning.add("the round fruit of a tree of the rose family");

        word.add("\"apple\"");
        meaning.add("the round fruit of a tree of the rose family");

        word.add("\"apple\"");
        meaning.add("the round fruit of a tree of the rose family");

        word.add("\"apple\"");
        meaning.add("the round fruit of a tree of the rose family");

        word.add("\"apple\"");
        meaning.add("the round fruit of a tree of the rose family");

        word.add("\"apple\"");
        meaning.add("the round fruit of a tree of the rose family");

        word.add("\"apple\"");
        meaning.add("the round fruit of a tree of the rose family");

        word.add("\"apple\"");
        meaning.add("the round fruit of a tree of the rose family");

        word.add("\"apple\"");
        meaning.add("the round fruit of a tree of the rose family");

        word.add("\"apple\"");
        meaning.add("the round fruit of a tree of the rose family");

        word.add("\"apple\"");
        meaning.add("the round fruit of a tree of the rose family");

        word.add("\"apple\"");
        meaning.add("the round fruit of a tree of the rose family");

        initRecyclerView();
    }
    private void initRecyclerView(){
        Log.d(TAG,"initRecyclerView: init recycler view");
        LinearLayoutManager layoutManager= new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        RecyclerView recyclerView=findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerViewAdapter adapter= new RecyclerViewAdapter(word,meaning,this);
        recyclerView.setAdapter(adapter);
    }
}

