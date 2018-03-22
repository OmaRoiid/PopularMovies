package com.example.omar_salem.popularmovies.Model;

import java.util.List;

/**
 TODO: Add class header
 */

public class MovieResponse {
        public Integer voteCount;
        public Integer id;
        public Boolean video;
        public String voteAverage;
        public String title;
        public Float popularity;
        public String posterPath;
      public String originalLanguage;
        public String originalTitle;
        public List<Integer> genreIds = null;
        public String backdropPath;
        public Boolean adult;
        public String overview;
        public String releaseDate;



    public MovieResponse(String posterPath, String originalTitle, String overview, Integer id, String voteAverage, String releaseDate) {

        this.posterPath = posterPath;
        this.id = id;
        this.voteAverage = voteAverage;
        this.originalTitle = originalTitle;
        this.overview = overview;
        this.releaseDate = releaseDate;
    }
    public String getVoteAverage() {
        return voteAverage;
    }

    public Float getPopularity() {
        return popularity;
    }

    public  String getPosterPath() {
        return "https://image.tmdb.org/t/p/w500"+posterPath;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getOverview() {
        return overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

}
