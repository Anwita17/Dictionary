package com.example.dictionary;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    ArrayAdapter<String> arrayAdapter;
    Trie trie=new Trie();
    void load(){
        BufferedReader reader ;
        try{
            reader = new BufferedReader(
                    new InputStreamReader(getAssets().open("positive-words.txt")));
            int flag=0,counter=0;
            String mWord;
            while ((mWord = reader.readLine()) != null) {
                trie.insert(mWord);
                mWord="";
            }
            //Toast.makeText(this,mLine,Toast.LENGTH_SHORT).show();

        }catch (IOException e) {
            Toast.makeText(this,"HelloException", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView listView=findViewById(R.id.my_list);
        load();
        String temp=trie.retrieve();
       // String temp1="abl";
        List<String> mylist=trie.suffix(temp);
        //Toast.makeText(MainActivity.this,temp.toString(),Toast.LENGTH_SHORT).show();
       // mylist.add("Erase");
        arrayAdapter=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,mylist);
        listView.setAdapter(arrayAdapter);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu,menu);
        MenuItem menuItem=menu.findItem(R.id.search_icon);
        SearchView searchView=(SearchView)menuItem.getActionView();
        searchView.setQueryHint("Search Words!");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                arrayAdapter.getFilter().filter(s);

                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}
