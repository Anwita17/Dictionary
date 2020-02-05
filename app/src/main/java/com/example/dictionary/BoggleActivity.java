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
import java.util.Random;

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
                //matrix.append(boggle[i][j]+"  ");
            }
            //matrix.append("\n");
        }
        load(boggle,n);
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                //boggle[i][j]=(char)(Math.random()*26+97);
                matrix.append(boggle[i][j]+"  ");
            }
            matrix.append("\n");
        }
        Boggle boggle1=new Boggle();
        results=boggle1.findWords(boggle,trie);

        for(int i=0;i<results.size();i++){
            //Toast.makeText(this,results.get(i),Toast.LENGTH_SHORT).show();
            output.append(results.get(i)+"\n");
        }

    }

    void load(char boggle[][],int n){

        BufferedReader reader ;
        Random rand = new Random();


        try{
            reader = new BufferedReader(
                    new InputStreamReader(getAssets().open("positive-words.txt")));
            int flag=0,row=0;
            String mWord;
            while ((mWord = reader.readLine()) != null) {
                trie.insert(mWord);
                flag=rand.nextInt(5);
                //System.out.println(flag);
                if(flag==rand.nextInt(5)){
                    int column=0,pos=0,check=1;
                    while(true){
                        if(mWord.length()>=n){
                            check=0;
                            break;
                        }
                        boggle[row][column]=mWord.charAt(pos);
                        column++;
                        pos++;
                        if(pos==mWord.length()) break;

                    }
                    if(check==1){
                        row++;
                        column=0;
                    }
                    if(row==n){
                        row=0;
                    }
                }
                mWord="";
            }
                //Toast.makeText(this,mLine,Toast.LENGTH_SHORT).show();

        }catch (IOException e) {
            Toast.makeText(this,"HelloException",Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
}
