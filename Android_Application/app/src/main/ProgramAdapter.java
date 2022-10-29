package com.example.pibapp;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

// Create the adapter by extending RecyclerView.Adapter. This custom ViewHolder will give access to your views
public class ProgramAdapter extends RecyclerView.Adapter<ProgramAdapter.ViewHolder> {
    // Declare variables to store data from the constructor
    Context context;
    ArrayList<String> Title;
    ArrayList<String>  PostedBy;
    ArrayList<String>  ids;
    ArrayList<String>  langs;




    // Create a static inner class and provide references to all the Views for each data item.
    // This is particularly useful for caching the Views within the item layout for fast access.
    public static class ViewHolder extends RecyclerView.ViewHolder{
        // Declare member variables for all the Views in a row
        TextView rowName;
        TextView rowDescription;
        TextView idf;
        TextView lang;

        // Create a constructor that accepts the entire row and search the View hierarchy to find each subview
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Store the item subviews in member variables
            rowName = itemView.findViewById(R.id.textView1);
            rowDescription = itemView.findViewById(R.id.textView2);
            idf = itemView.findViewById(R.id.nid);
            lang = itemView.findViewById(R.id.lang);
        }
    }
    // Provide a suitable constructor
    public ProgramAdapter(Context context, List<String> Title, List<String> PostedBy,List<String> nids,List<String> Languages){
        // Initialize the class scope variables with values received from constructor
        this.context = context;
        this.Title = (ArrayList<String>) Title;
        this.PostedBy = (ArrayList<String>) PostedBy;
        this.ids = (ArrayList<String>) nids;
        this.langs = (ArrayList<String>) Languages;

    }

    // Create new views to be invoked by the layout manager
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Create a LayoutInflater object
        LayoutInflater inflater = LayoutInflater.from(context);
        // Inflate the custom layout
        View view = inflater.inflate(R.layout.newcard, parent, false);
        // To attach OnClickListener
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView rowName = v.findViewById(R.id.nid);
                TextView langu = v.findViewById(R.id.lang);
                Intent n = new Intent(context,NewsActivity.class);
                Bundle b = new Bundle();
                b.putString("key", rowName.getText().toString()); //Your id
                b.putString("val", langu.getText().toString()); //Your id
                n.putExtras(b);
                context.startActivity(n);

            }
        });
        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    // Replace the contents of a view to be invoked by the layout manager
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Get element from your dataset at this position and replace the contents of the View with that element
        holder.rowName.setText(Title.get(position));
        holder.rowDescription.setText(PostedBy.get(position));
        holder.idf.setText(ids.get(position));
        holder.lang.setText(langs.get(position));


    }

    // Return the size of your dataset
    @Override
    public int getItemCount() {
        return Title.size();
    }
}

