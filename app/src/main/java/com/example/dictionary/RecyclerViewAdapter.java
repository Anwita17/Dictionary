package com.example.dictionary;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{
    public static final String TAG="RecyclerViewAdapter";
    private ArrayList<String> word=new ArrayList<>();
    private ArrayList<String> meaning=new ArrayList<>();
    private Context card;

    public RecyclerViewAdapter(ArrayList<String> word, ArrayList<String> meaning, Context card) {
        this.word = word;
        this.meaning = meaning;
        this.card = card;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        Log.d(TAG,"onCreateViewHolder: called");
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_itemlist,parent,false);

        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position){
        holder.cardtitle.setText(word.get(position));
        holder.cardcontent.setText(meaning.get(position));
        holder.cardcontent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(card, "Clicked" + word.get(position), Toast.LENGTH_SHORT).show();
                Intent i1=new Intent(card,DisplayMeaning.class);
                i1.putExtra("word",word.get(position));
                i1.putExtra("meaning",meaning.get(position));
                card.startActivity(i1);
            }
        });


    }
    @Override
    public int getItemCount(){
        return word.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView cardtitle;
        TextView cardcontent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardtitle = itemView.findViewById(R.id.cardtitle);
            cardcontent= itemView.findViewById(R.id.cardcontent);
        }
    }
}
