package com.example.movieapp.Api;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.movieapp.Movie.DetailsActivity;
import com.example.movieapp.Movie.MovieDetails;
import com.example.movieapp.R;

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
    public int getItemCount(){
        return movieDetailsList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView titleOfMovie, userRating;
        private ImageView moviePoster;

        private MyViewHolder(View view){
            super(view);

            titleOfMovie = view.findViewById(R.id.title);
            userRating =  view.findViewById(R.id.mTextViewShortDescription);
            moviePoster = view.findViewById(R.id.thumbnail);

            view.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){

                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION){
                        MovieDetails clickedDataItem = movieDetailsList.get(position);
                        Intent intent = new Intent(mContext, DetailsActivity.class);
                        intent.putExtra("movies", clickedDataItem );
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(intent);
                    }
                }
            });
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ApiAdapter.MyViewHolder viewHolder, int i){

        viewHolder.titleOfMovie.setText(movieDetailsList.get(i).getOriginalTitle());
        String vote = Double.toString(movieDetailsList.get(i).getVoteAverage());
        viewHolder.userRating.setText(vote);

        String posterOfMovie = "https://image.tmdb.org/t/p/w500" + movieDetailsList.get(i).getPosterPath();

        Glide.with(mContext)
                .load(posterOfMovie)
                .placeholder(R.drawable.load)
                .into(viewHolder.moviePoster);
    }
}