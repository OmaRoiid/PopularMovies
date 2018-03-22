package com.example.omar_salem.popularmovies.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.omar_salem.popularmovies.Model.MovieResponse;
import com.example.omar_salem.popularmovies.R;
import com.example.omar_salem.popularmovies.UI.DetailActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 TODO: Add class header
 */

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MyViewHolder> {
 private   ArrayList<MovieResponse> movieResponses;
 private Context mContxt;

    public MoviesAdapter(ArrayList<MovieResponse> movieResponses, Context mContxt) {
        this.movieResponses = movieResponses;
        this.mContxt = mContxt;
    }

    @Override
    public MoviesAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View moviesCard;
        moviesCard= LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_card,parent,false);
        return new MyViewHolder(moviesCard);
    }

    @Override
    public void onBindViewHolder(MoviesAdapter.MyViewHolder holder, int position) {
        Picasso.get()
                .load(movieResponses.get(position).getPosterPath())
                .placeholder(R.drawable.loading)
                .into(holder.thumbnailImageView);

    }

    @Override
    public int getItemCount() {
        return movieResponses.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
       private ImageView thumbnailImageView;
         MyViewHolder(final View itemView) {
            super(itemView);
            thumbnailImageView=itemView.findViewById(R.id.iv_movie_thumbnail);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int Position=getAdapterPosition();
                    if(getAdapterPosition()!= RecyclerView.NO_POSITION){

                    Intent Details= new Intent(mContxt, DetailActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    Details.putExtra("poster_path",movieResponses.get(Position).getPosterPath());
                    Details.putExtra("original_title",movieResponses.get(Position).getOriginalTitle());
                    Details.putExtra("overview",movieResponses.get(Position).getOverview());
                    Details.putExtra("release_date",movieResponses.get(Position).getReleaseDate());
                    Details.putExtra("vote_average",movieResponses.get(Position).getVoteAverage());
                       mContxt.startActivity(Details);
                    }
                }
            });

        }
    }
}
