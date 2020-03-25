package com.example.dictionary;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class DisplayMeaning extends AppCompatActivity {
    View view;
    TextView cardword=findViewById(R.id.word1);
    TextView cardmeaning=findViewById(R.id.meaning1);
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_meaning);
        getIncomingIntent();
    }
    private void getIncomingIntent(){
        if((getIntent().hasExtra("word")) && (getIntent().hasExtra("meaning"))){
            String word=getIntent().getStringExtra("word");
            String meaning=getIntent().getStringExtra("meaning");
            setContent(word,meaning);
        }
    }
    private void setContent(String word,String meaning){
        TextView word1=findViewById(R.id.word1);
        TextView meaning1=findViewById(R.id.meaning1);
        word1.setText(word);
        meaning1.setText(meaning);
    }
}
