package com.example.dictionary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Favourite extends AppCompatActivity {
    public static final String TAG="RecyclerViewAdapter";
    private ArrayList<String> favword=new ArrayList<>();
    private RecyclerViewAdapter2 adapter;
    public List<String> word_list;
    public Set<String> set;
    public List<String> newlist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);
        word_list=new ArrayList<String>();
        open_file("Favourite-Words.txt");
        set=new HashSet<String>();
        for(String temp : word_list){
            if(check_occurance(temp)==1){
                set.add(temp);
            }
        }
        newlist= new ArrayList<String>();
        for(String temp : set)
            newlist.add(temp);

        getFavourites();

    }

    private void open_file(String FILE_NAME){
        FileInputStream fis = null;

        try {
            fis = openFileInput(FILE_NAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            String text;

            while ((text = br.readLine()) != null) {
                StringBuilder sb = new StringBuilder();
                sb.append(text);
                word_list.add(sb.toString());
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public  int check_occurance(String word){
        //Toast.makeText(this,"Word count: "+ Collections.frequency(fav_list,word), Toast.LENGTH_SHORT).show();
        if(Collections.frequency(word_list,word)%2==0){
            return 0;
        }
        else
            return 1;
    }

    public void getFavourites(){
        //Toast.makeText(this,, Toast.LENGTH_SHORT).show();
        for(String temp : set)
            favword.add(temp);

        initRecyclerView();
    }

    private void initRecyclerView(){
        Log.d(TAG,"initRecyclerView: init recycler view");
        LinearLayoutManager layoutManager= new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        RecyclerView recyclerView=findViewById(R.id.recyclerview2);
        recyclerView.setLayoutManager(layoutManager);
        adapter= new RecyclerViewAdapter2(favword,this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);
        recyclerView.setAdapter(adapter);
    }
    ItemTouchHelper.SimpleCallback itemTouchHelperCallback=new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            String word=newlist.get(viewHolder.getAdapterPosition());
            set.remove(word);
            newlist.remove(viewHolder.getAdapterPosition());
            favword.remove(viewHolder.getAdapterPosition());
            try {
                FileOutputStream fout = openFileOutput("Favourite-Words.txt", MODE_APPEND);
                String new_word = word + "\n";
                fout.write(new_word.getBytes());
                fout.close();
                //File filedir=new File(getFilesDir(),"Favourite-Words.txt");
                //Toast.makeText(DisplayActivity.this, "File saved at"+filedir, Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                //Toast.makeText(DisplayActivity.this,"Not open", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
            Toast.makeText(getApplicationContext(),word+" removed from favourites!",Toast.LENGTH_SHORT).show();
            adapter.notifyDataSetChanged();
        }
    };

}
