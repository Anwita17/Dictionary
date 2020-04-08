package com.example.dictionary;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;


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
        check_favourite(word);
    }

    public void check_favourite(final String word){
        final int i = 0;
        ImageButton button1 =(ImageButton)findViewById(R.id.imageButton);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(DisplayActivity.this,word, Toast.LENGTH_SHORT).show();
                try {
                    FileOutputStream fout=openFileOutput("Favourite-Words.txt",MODE_APPEND);
                    String new_word=word+"\n";
                    fout.write(new_word.getBytes());
                    fout.close();
                    //File filedir=new File(getFilesDir(),"Favourite-Words.txt");
                    //Toast.makeText(DisplayActivity.this, "File saved at"+filedir, Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    //Toast.makeText(DisplayActivity.this,"Not open", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });
        open_file("Favourite-Words.txt");
    }
    private void open_file(String FILE_NAME){
        FileInputStream fis = null;

        try {
            fis = openFileInput(FILE_NAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String text;

            while ((text = br.readLine()) != null) {
                sb.append(text).append("\n");
            }

            Toast.makeText(this, sb.toString(), Toast.LENGTH_SHORT).show();

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

    private String dictionaryEntries(String Word) {
        final String language = "en-gb";
        final String word = Word;
        final String fields = "definitions";
        final String strictMatch = "false";
        final String word_id = word.toLowerCase();
        return "https://od-api.oxforddictionaries.com:443/api/v2/entries/" + language + "/" + word_id + "?" + "fields=" + fields + "&strictMatch=" + strictMatch;
    }
}
