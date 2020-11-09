package com.example.dictionary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class LaunchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
    }

    public void OnClickLaunch(View view){
        Intent mIntent;
        switch (view.getId()){
            case R.id.boggle_solver_button:
                mIntent = new Intent(LaunchActivity.this,BoggleSolverMain.class);
                startActivity(mIntent);
                break;
            case R.id.serach_dict_button:
                mIntent = new Intent(LaunchActivity.this,MainActivity.class);
                startActivity(mIntent);
                break;
        }
    }
}
