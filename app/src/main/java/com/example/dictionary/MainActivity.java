package com.example.dictionary;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        Trie trie=new Trie();
        trie.insert("rahul");
        trie.insert("rothi");
        trie.insert("anwita");
        trie.insert("snigdha");
        trie.search("apple");

    }
}
