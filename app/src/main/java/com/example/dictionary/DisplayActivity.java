package com.example.dictionary;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class DisplayActivity extends AppCompatActivity {
    TextView wordMeaning;
    TextView wordPOP;
    TextView wordName;
    ListView wordMeaningList;
    ArrayAdapter<String> wordArrayAdapter;
    String word;
    List<String> fav_list = new ArrayList<>();
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
        wordName.setText(word.substring(0,1).toUpperCase() + word.substring(1).toLowerCase());
        /*wordArrayAdapter=new ArrayAdapter<String>(this,R.layout.list_item_word,wordArray);
        wordMeaningList.setAdapter(wordArrayAdapter);*/
        new CallbackTask(DisplayActivity.this,wordPOP,wordMeaningList).execute(dictionaryEntries(word));
        check_favourite(word);
    }
    public  int check_occurance(String word){
        //Toast.makeText(this,"Word count: "+ Collections.frequency(fav_list,word), Toast.LENGTH_SHORT).show();
        if(Collections.frequency(fav_list,word)%2==0){
            ArrayList<String> temp =new ArrayList<>();
            temp.add(word);
            fav_list.removeAll(temp);
            return 0;
        }
        else
            return 1;
    }
    public void check_favourite(final String word){
        open_file("Favourite-Words.txt");
        //Toast.makeText(this, fav_list.toString(), Toast.LENGTH_SHORT).show();
        int i = check_occurance(word);
        final ImageButton button1 =(ImageButton)findViewById(R.id.imageButton);
        if(i==1){
            button1.setColorFilter(Color.parseColor("#e3552d"));
        }
        else{
            button1.setColorFilter(Color.parseColor("#a09beb"));
        }
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  Toast.makeText(DisplayActivity.this,word, Toast.LENGTH_SHORT).show();
                if (check_occurance(word)== 1) {
                    button1.setColorFilter(Color.parseColor("#a09beb"));
                    //Toast.makeText(DisplayActivity.this, "Here", Toast.LENGTH_SHORT).show();
                    //fav_list.remove(new String(word));
                }
                else{
                    button1.setColorFilter(Color.parseColor("#e3552d"));
                }
                    fav_list.add(word);

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
            }
        });
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
                fav_list.add(sb.toString());
            }

           // Toast.makeText(this, sb.toString(), Toast.LENGTH_SHORT).show();

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
