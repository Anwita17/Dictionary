package com.example.dictionary;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewAdapter3 extends RecyclerView.Adapter<RecyclerViewAdapter3.ViewHolder>{
    public static final String TAG="RecyclerViewAdapter";
    private ArrayList<String> word=new ArrayList<>();
   // private ArrayList<String> meaning=new ArrayList<>();
    private Context context;

    public RecyclerViewAdapter3(ArrayList<String> word, Context context) {
        this.word = word;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        Log.d(TAG,"onCreateViewHolder: called");
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.fav_word_list,parent,false);

        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position){
        holder.recent_word.setText(word.get(position));
        holder.recent_word.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  Toast.makeText(context, "Clicked" + word.get(position), Toast.LENGTH_SHORT).show();
                /*Intent i1=new Intent(card,DisplayMeaning.class);
                i1.putExtra("word",word.get(position));
                i1.putExtra("meaning",meaning.get(position));
                card.startActivity(i1);*/
                Intent intent = new Intent(context,DisplayActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("key", word.get(position));
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });


    }
    @Override
    public int getItemCount(){
        return word.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView recent_word;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            recent_word = itemView.findViewById(R.id.favwordlist);
        }
    }
}
