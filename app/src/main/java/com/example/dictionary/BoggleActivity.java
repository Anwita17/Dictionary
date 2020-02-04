package com.example.dictionary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class BoggleActivity extends AppCompatActivity {

    Trie trie;
    List<String> results;

    TextView matrix,output;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boggle);

        matrix=findViewById(R.id.bogglematrix);
        output=findViewById(R.id.boggleOutput);

        int n=5;
        char boggle[][]=new char[n][n];

        trie=new Trie();

        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                boggle[i][j]=(char)(Math.random()*26+97);
                matrix.append(boggle[i][j]+"  ");
            }
            matrix.append("\n");
        }
        load();

        Boggle boggle1=new Boggle();
        results=boggle1.findWords(boggle,trie);

        for(int i=0;i<results.size();i++){
            //Toast.makeText(this,results.get(i),Toast.LENGTH_SHORT).show();
            output.append(results.get(i)+"\n");
        }

    }

    void load(){

        BufferedReader reader ;
        try{
            reader = new BufferedReader(
                    new InputStreamReader(getAssets().open("positive-words.txt")));

            String mWord;
            while ((mWord = reader.readLine()) != null) {
                trie.insert(mWord);
                //Toast.makeText(this,mLine,Toast.LENGTH_SHORT).show();
            }
        }catch (IOException e) {
            Toast.makeText(this,"HelloException",Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
}
