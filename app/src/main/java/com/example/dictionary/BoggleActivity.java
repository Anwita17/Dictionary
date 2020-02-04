package com.example.dictionary;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class BoggleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boggle);

        int n=5;
        char boggle[][]=new char[n][n];

        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++)
                boggle[i][j]=(char)(Math.random()*26+97);
        }
    }
}
