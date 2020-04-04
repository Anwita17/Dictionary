package com.example.dictionary;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;


public class DisplayActivity extends AppCompatActivity {
    TextView wordMeaning;
    TextView wordPOP;
    TextView wordName;
    ListView wordMeaningList;
    ArrayAdapter<String> wordArrayAdapter;
    String word;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        Bundle bundle = getIntent().getExtras();
        word = bundle.getString("key");
        //wordMeaning = findViewById(R.id.WordMeaning);
        wordName = findViewById(R.id.WordName);
        wordPOP = findViewById(R.id.WordPOP);
        wordMeaningList=findViewById(R.id.WordMeaningList);
        wordName.setText(word);
        /*wordArrayAdapter=new ArrayAdapter<String>(this,R.layout.list_item_word,wordArray);
        wordMeaningList.setAdapter(wordArrayAdapter);*/
        new CallbackTask(DisplayActivity.this,wordPOP,wordMeaningList).execute(dictionaryEntries(word));
    }
    private String dictionaryEntries(String Word) {
        final String language = "en-gb";
        final String word = Word;
        final String fields = "definitions";
        final String strictMatch = "false";
        final String word_id = word.toLowerCase();
        return "https://od-api.oxforddictionaries.com:443/api/v2/entries/" + language + "/" + word_id + "?" + "fields=" + fields + "&strictMatch=" + strictMatch;
    }
}
