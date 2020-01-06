package com.example.movieapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class ApiAdapter extends RecyclerView.Adapter<ApiAdapter.MyViewHolder> {

    private Context mContext;
    private List<MovieDetails> movieDetailsList;

    public ApiAdapter(Context mContext, List<MovieDetails> movieDetailsList){
        this.mContext = mContext;
        this.movieDetailsList = movieDetailsList;
    }

    @Override
    public ApiAdapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i){
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.example_movie_card, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ApiAdapter.MyViewHolder viewHolder, int i){
        viewHolder.title.setText(movieDetailsList.get(i).getOriginalTitle());
        String vote = Double.toString(movieDetailsList.get(i).getVoteAverage());
        viewHolder.userrating.setText(vote);

        String poster = "https://image.tmdb.org/t/p/w500" + movieDetailsList.get(i).getPosterPath();

        Glide.with(mContext)
                .load(poster)
                .placeholder(R.drawable.load)
                .into(viewHolder.thumbnail);
    }

    @Override
    public int getItemCount(){
        return movieDetailsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView title, userrating;
        public ImageView thumbnail;

        public MyViewHolder(View view){
            super(view);
            title = (TextView)view.findViewById(R.id.title);
            userrating = (TextView) view.findViewById(R.id.userrating);
            thumbnail = (ImageView)view.findViewById(R.id.thumbnail);

            view.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION){
                        MovieDetails clickedDataItem = movieDetailsList.get(pos);
                        Intent intent = new Intent(mContext, RegisterActivity.class);
                        intent.putExtra("movies", clickedDataItem );
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(intent);
                        Toast.makeText(v.getContext(), "You clicked " + clickedDataItem.getOriginalTitle(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}