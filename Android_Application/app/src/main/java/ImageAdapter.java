package com.example.pibapp;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

// Create the adapter by extending RecyclerView.Adapter. This custom ViewHolder will give access to your views
public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {
    // Declare variables to store data from the constructor
    Context context;
    ArrayList<String> Title;
    ArrayList<String>  urls;



    // Create a static inner class and provide references to all the Views for each data item.
    // This is particularly useful for caching the Views within the item layout for fast access.
    public static class ViewHolder extends RecyclerView.ViewHolder{
        // Declare member variables for all the Views in a row
        ImageView img;
        TextView title;

        // Create a constructor that accepts the entire row and search the View hierarchy to find each subview
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Store the item subviews in member variables


            img  = itemView.findViewById(R.id.image);
            title = itemView.findViewById(R.id.imagealt);
        }
    }
    // Provide a suitable constructor
    public ImageAdapter(Context context, List<String> Title, List<String> PostedBy){
        // Initialize the class scope variables with values received from constructor
        this.context = context;
        this.Title = (ArrayList<String>) Title;
        this.urls = (ArrayList<String>) PostedBy;


    }

    // Create new views to be invoked by the layout manager
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Create a LayoutInflater object
        LayoutInflater inflater = LayoutInflater.from(context);
        // Inflate the custom layout
        View view = inflater.inflate(R.layout.imagecard, parent, false);



        // To attach OnClickListener
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                TextView rowName = v.findViewById(R.id.nid);
//                TextView langu = v.findViewById(R.id.lang);
//                Intent n = new Intent(context,NewsActivity.class);
//                Bundle b = new Bundle();
//                b.putString("key", rowName.getText().toString()); //Your id
//                b.putString("val", langu.getText().toString()); //Your id
//                n.putExtras(b);
//                context.startActivity(n);
//                Toast.makeText(context, "Clicked Item: " + rowName.getText().toString(), Toast.LENGTH_SHORT).show();
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



        try {
            Bitmap mIcon_val;
            URL newurl = new URL(urls.get(position));
            Uri imgUri=Uri.parse(urls.get(position));

//            mIcon_val = BitmapFactory.decodeStream(newurl.openConnection().getInputStream());
//            holder.img.setImageURI(imgUri);
            // For a simple view:
            Glide.with(context).load(urls.get(position)).into(holder.img);

//           holder.img.setImageResource(R.drawable.azadi);

        } catch (Exception e) {
            e.printStackTrace();
        }

        holder.title.setText(Title.get(position));
        System.out.println("Done");
    }

    // Return the size of your dataset
    @Override
    public int getItemCount() {
        return Title.size();
    }
}

