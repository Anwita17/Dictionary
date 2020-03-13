package com.example.dictionary;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;


import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView listView=findViewById(R.id.my_list);
        List<String> mylist=new ArrayList<>();
        mylist.add("Eraser");mylist.add("Pen");
        mylist.add("Ink");mylist.add("Pencil");
        mylist.add("Copy");mylist.add("Book");
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
