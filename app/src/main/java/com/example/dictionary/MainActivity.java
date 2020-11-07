package com.example.dictionary;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {
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
        setContentView(R.layout.activity_main);
        getWordAndMeaning();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        search_bar=findViewById(R.id.search_bar);
        wordofday=findViewById(R.id.word1);
        rv=findViewById(R.id.recyclerview);

        RelativeLayout.LayoutParams param=new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
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
        param.setMargins(width/3-30,0,0,0);
        params.setMargins(0, height/3, 0, 0);
        params1.setMargins(0,height/2+120,0,40);
        params2.setMargins(0,height/2+280,0,0);

        search_bar.setLayoutParams(params);
        wordofday.setLayoutParams(params1);
        rv.setLayoutParams(params2);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.item1:
                Intent intent=new Intent(this,Favourite.class);
                startActivity(intent);
                break;
            case R.id.item2:
                Intent intent1=new Intent(this,Recents.class);
                startActivity(intent1);
                break;
        }
        return super.onOptionsItemSelected(item);
    }



    //get words through API
    public void getWordAndMeaning(){
        //Toast.makeText(this,, Toast.LENGTH_SHORT).show();
        word.add("Amiable");
        meaning.add("diffusing warmth and friendliness");

        word.add("Courageous");
        meaning.add("able to face and deal with danger or fear without flinching\n");

        word.add("Empathetic");
        meaning.add("showing ready comprehension of others' states\n");

        word.add("Generous");
        meaning.add("willing to give and share unstintingly\n");

        word.add("Intuitive");
        meaning.add("obtained through instinctive knowledge");

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
    public void OnclickSearchBar(View view){
        Intent intent = new Intent(this,SearchActivity.class);
        startActivity(intent);
    }
}

