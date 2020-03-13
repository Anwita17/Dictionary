package com.example.dictionary;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ArrayAdapter<String> arrayAdapter;
    Trie trie=new Trie();
    private String dictionaryEntries(String Word) {
        final String language = "en-gb";
        final String word = Word;
        final String fields = "definitions";
        final String strictMatch = "false";
        final String word_id = word.toLowerCase();
        return "https://od-api.oxforddictionaries.com:443/api/v2/entries/" + language + "/" + word_id + "?" + "fields=" + fields + "&strictMatch=" + strictMatch;
    }
    void loadData(ArrayList<String> temporary){
        BufferedReader reader ;
        try{
            reader = new BufferedReader(
                    new InputStreamReader(getAssets().open("positive-words.txt")));
            int flag=0,counter=0;
            String mWord;
            while ((mWord = reader.readLine()) != null) {
                //trie.insert(mWord);
                temporary.add(mWord);
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
        ArrayList<String> temporary=new ArrayList<>();
        loadData(temporary);
        final List<String> mylist=temporary;
        arrayAdapter=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,mylist);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                new CallbackTask(MainActivity.this).execute(dictionaryEntries(mylist.get(i)));
                //Toast.makeText(MainActivity.this,mylist.get(i),Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu,menu);

        MenuItem menuItem=menu.findItem(R.id.search_icon);
        SearchView searchView=(SearchView)menuItem.getActionView();



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

