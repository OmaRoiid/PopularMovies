package com.example.omar_salem.popularmovies.UI;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.omar_salem.popularmovies.Adapters.MoviesAdapter;
import com.example.omar_salem.popularmovies.Model.MovieResponse;
import com.example.omar_salem.popularmovies.R;
import com.example.omar_salem.popularmovies.Utils.DetectConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Float.valueOf;

public class MainActivity extends AppCompatActivity {
    private RecyclerView moviesRecyclerView;
    private MoviesAdapter moviesAdapter;
    private ProgressDialog dialog;
    private ArrayList <MovieResponse> movieResponses = new ArrayList<>();
    private  String BASE_URL="https://api.themoviedb.org/3/movie/";
    private static final String Populer_Quary="popular?api_key=254ef4875714b785ce16c48c8c873b49";
    private static final String Top_Quary="top_rated?api_key=254ef4875714b785ce16c48c8c873b49";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(!DetectConnection.checkInternetConnection(this)){
            Toast.makeText(getApplicationContext(), "No Internet!,please connect to Network", Toast.LENGTH_SHORT).show();
        }
        moviesRecyclerView=findViewById(R.id.rv_movies);
        setUpDailog();
        new GetMoviesResponse().execute(BASE_URL+Populer_Quary);
        setUpRecyclerView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.movie_sorted, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_popular:
               ReFetchApi(BASE_URL+Populer_Quary);
               break;
            case R.id.action_top_rated:
              ReFetchApi(BASE_URL+Top_Quary);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    void setUpRecyclerView()
    {
        //Check the orientation of Screen
        if(getResources().getConfiguration().orientation== Configuration.ORIENTATION_PORTRAIT)
        {
            moviesRecyclerView.setLayoutManager(new GridLayoutManager(this,2));
        }
        else{
            moviesRecyclerView.setLayoutManager(new GridLayoutManager(this,4));
        }

    }
    void setUpDailog()
    {
        dialog=new ProgressDialog(this);
        dialog.setTitle("Loading ");
        dialog.setMessage("Please wait...");
        dialog.setCancelable(false);
        dialog.show();
    }

    void ReFetchApi(String Url)
    {
        movieResponses.clear();
        new GetMoviesResponse().execute(Url);
        moviesAdapter.notifyDataSetChanged();
    }
   /* private void  CheckSorted ()
    {
        SharedPreferences Preferences = PreferenceManager.getDefaultSharedPreferences(this);
        @SuppressLint("ResourceType")
        String Sort=Preferences.getString(
                this.getString(R.id.action_popular),
               this.getString(R.id.action_top_rated)
        );
        if(Sort.equals(this.getString(R.id.action_popular)))
        {
            Log.d(LOG_TAG,"Sorting by Poupelar ");
            LoadFromService();
        }
        else {Log.d(LOG_TAG,"Sorting by Rating");
            LoadFromService2();}
    }*/
    private class GetMoviesResponse extends AsyncTask<String,Void,String>{


        @Override
        protected String doInBackground(String... prams) {
           String stringUrl= prams[0];
           String result = null,inputLine;

           try {
               URL myUrl= new URL(stringUrl);
               HttpURLConnection connection =(HttpURLConnection)
                       myUrl.openConnection();
               connection.connect();
               InputStreamReader streamReader = new InputStreamReader(connection.getInputStream());
               BufferedReader reader = new BufferedReader(streamReader);
               StringBuilder stringBuilder = new StringBuilder();
               //Check if the line we are reading is not null
               while((inputLine = reader.readLine()) != null){
                   stringBuilder.append(inputLine);
               }
               reader.close();
               streamReader.close();
               result = stringBuilder.toString();
               JSONObject MovieObject= new JSONObject(result);
               JSONArray MovieDetail=MovieObject.getJSONArray("results");
               for(int i=0;i<MovieDetail.length();i++)
               {
                    //GET data  from ResPonse api
                   JSONObject Detail=MovieDetail.getJSONObject(i);
                   String moviePosterPath=Detail.getString("poster_path");
                   String movieOriginalTitle=Detail.getString("original_title");
                   String movieOverView=Detail.getString("overview");
                   Integer movieID=Detail.getInt("id");
                   String movieVoteAverage= Detail.getString("vote_average");
                   String movieReleaseDate=Detail.getString("release_date");
                   movieResponses.add( new MovieResponse(moviePosterPath,
                           movieOriginalTitle,
                           movieOverView,
                           movieID,
                           movieVoteAverage,
                           movieReleaseDate));

               }


            } catch (MalformedURLException e  ) {
               e.printStackTrace();
           } catch (IOException e) {
               e.printStackTrace();
               result = null;
           } catch (JSONException e) {
               e.printStackTrace();
           }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            if(dialog.isShowing())
            { dialog.dismiss();}
            moviesAdapter=new MoviesAdapter(movieResponses,getApplicationContext());
            moviesRecyclerView.setAdapter(moviesAdapter);
            moviesAdapter.notifyDataSetChanged();

        }
    }

}
