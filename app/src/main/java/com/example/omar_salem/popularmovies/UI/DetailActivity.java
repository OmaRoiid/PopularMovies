package com.example.omar_salem.popularmovies.UI;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.omar_salem.popularmovies.R;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {
    ImageView poster;
    TextView MovieTittle,MovieOverView,MovieReleaseDate,MovieVoteAvg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
       setUpUI();
       setMovieDetailIntoScreen();
    }
    void setMovieDetailIntoScreen()
    {

      String MoviePoster = getIntent().getExtras().getString("poster_path");
        Picasso.get()
                .load(MoviePoster)
                .placeholder(R.drawable.loading)
                .into(poster);
        MovieTittle.setText(getIntent().getStringExtra("original_title"));
        MovieOverView.setText(getIntent().getStringExtra("overview"));
        MovieVoteAvg.setText(getIntent().getStringExtra("vote_average"));
        MovieReleaseDate.setText(getIntent().getStringExtra("release_date"));
    }
    void setUpUI()
    {
        poster=findViewById(R.id.poster_iv);
        MovieTittle=findViewById(R.id.movie_tittle_tv);
        MovieOverView=findViewById(R.id.overView_tv);
        MovieReleaseDate=findViewById(R.id.release_date_tv);
        MovieVoteAvg=findViewById(R.id.user_rate_tv);
    }
}
