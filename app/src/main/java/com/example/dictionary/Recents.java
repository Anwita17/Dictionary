package com.example.dictionary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Recents extends AppCompatActivity {
    public static final String TAG="RecyclerViewAdapter";
    //String name;
    private ArrayList<String> recentword=new ArrayList<>();
    private ArrayList<String> recentword1=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recents);
        //Shared Preferences
        SharedPreferences mPreferences= getSharedPreferences("shared preferences",MODE_PRIVATE);
        Gson gson=new Gson();
        String json=mPreferences.getString("key",null);
        Type type=new TypeToken<ArrayList<String>>(){}.getType();
        recentword1=gson.fromJson(json,type);
        if(recentword1 == null){
            recentword1=new ArrayList<String>();
        }
        getRecent();
    }



    public void getRecent(){
        //Toast.makeText(this,, Toast.LENGTH_SHORT).show();
//        recentword.add("Amiable");
//
//        recentword.add("Courageous");
//
//        recentword.add("Empathetic");
//
//        recentword.add("Generous");
//
//        recentword.add("Intuitive");
            for(String temp : recentword1)
                recentword.add(temp);


        initRecyclerView();
    }


    private void initRecyclerView(){
        Log.d(TAG,"initRecyclerView: init recycler view");
        LinearLayoutManager layoutManager= new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        RecyclerView recyclerView=findViewById(R.id.recyclerview3);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerViewAdapter3 adapter= new RecyclerViewAdapter3(recentword,this);
        recyclerView.setAdapter(adapter);
    }
}
