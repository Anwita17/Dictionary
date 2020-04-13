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

public class RecyclerViewAdapter2 extends RecyclerView.Adapter<RecyclerViewAdapter2.ViewHolder>{
    public static final String TAG="RecyclerViewAdapter";
    private ArrayList<String> favword=new ArrayList<>();
    private Context context;

    public RecyclerViewAdapter2(ArrayList<String> favword, Context context) {
        this.favword = favword;
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
        holder.favwordlist.setText(favword.get(position));
        holder.favwordlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,DisplayActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("key", favword.get(position));
                intent.putExtras(bundle);
                context.startActivity(intent);

            }
        });


    }
    @Override
    public int getItemCount(){
        return favword.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView favwordlist;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            favwordlist = itemView.findViewById(R.id.favwordlist);
        }
    }
}
