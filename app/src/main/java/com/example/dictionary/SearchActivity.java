package com.example.dictionary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
import java.util.Set;


public class SearchActivity extends AppCompatActivity {
    ArrayAdapter<String> arrayAdapter;
    ArrayList<String> arrayList,marrayList;
    Trie trie = new Trie();
    ListView listView;
    private String dictionaryEntries(String Word) {
        final String language = "en-gb";
        final String word = Word.toLowerCase();
        final String fields = "definitions";
        final String strictMatch = "false";
        final String word_id = word.toLowerCase();
        return "https://od-api.oxforddictionaries.com:443/api/v2/entries/" + language + "/" + word_id + "?" + "fields=" + fields + "&strictMatch=" + strictMatch;
    }


    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        listView=findViewById(R.id.my_list);
        arrayList=new ArrayList<>();
        marrayList=new ArrayList<>();
        loadData(marrayList);
        //Toast.makeText(this,trie.retrieve(), Toast.LENGTH_SHORT).show();
        arrayAdapter=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,arrayList);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getBaseContext(),DisplayActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("key", arrayList.get(i));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
    }
    void loadData(ArrayList<String> temporary){
        BufferedReader reader ;
        try{
            reader = new BufferedReader(
                    new InputStreamReader(getAssets().open("positive-words.txt")));
            int flag=0,counter=0;
            String mWord;
            while ((mWord = reader.readLine()) != null) {
                mWord=mWord.toLowerCase();
                trie.insert(mWord);
                temporary.add(mWord);
            }

        }catch (IOException e) {
            Toast.makeText(this,"HelloException", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu,menu);

        MenuItem menuItem=menu.findItem(R.id.search_icon);
        SearchView searchView=(SearchView)menuItem.getActionView();

        searchView.setIconified(false);
        //searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Intent intent = new Intent(getApplicationContext(),DisplayActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("key", s);
                intent.putExtras(bundle);
                startActivity(intent);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                arrayList.clear();
                Set<String> setSuggestion=trie.suffix(s.toLowerCase());
                for(String word : setSuggestion){
                    arrayList.add(word.toLowerCase());
                    Log.d(SearchActivity.class.getName(),word.toLowerCase());
                }
                arrayAdapter.notifyDataSetChanged();
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}

