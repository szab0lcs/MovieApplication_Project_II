package com.example.movieapp.Main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.movieapp.Api.ApiAdapter;
import com.example.movieapp.Api.ApiService;
import com.example.movieapp.BuildConfig;
import com.example.movieapp.Movie.DetailsActivity;
import com.example.movieapp.Movie.MovieDetails;
import com.example.movieapp.Movie.MoviesResponse;
import com.example.movieapp.ProfileAndFavoriteActivity.ProfileActivity;
import com.example.movieapp.R;
import com.example.movieapp.User.MyClient;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private ApiAdapter mApiAdapter;
    private List<MovieDetails> movieDetailsList;
    public ProgressDialog mProgressDialog;
    private SwipeRefreshLayout swipeContainer;
    public static final String LOG_TAG = ApiAdapter.class.getName();

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        initViews();

        bottomNavigation();

        swipeContainer.setColorSchemeColors(android.R.color.holo_orange_dark);

        loadJSON();
    }

    private void bottomNavigation() {

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);

        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.nav_home:
                        Intent intent1 = new Intent(MainActivity.this, MainActivity.class);
                        startActivity(intent1);
                        break;
                    case R.id.nav_fav:
                        Intent intent2 = new Intent(MainActivity.this, DetailsActivity.class);
                        startActivity(intent2);
                        break;
                    case R.id.nav_profile:
                        Intent intent3 = new Intent(MainActivity.this,  ProfileActivity.class);
                        Intent intent4 = getIntent();
                        intent3.putExtra("email", intent4.getStringExtra("email"));
                        startActivity(intent3);
                        break;
                }

                return false;
            }
        });
    }

    private void init() {
        swipeContainer = findViewById(R.id.main_content);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){
            @Override
            public void onRefresh(){
                initViews();
                Toast.makeText(MainActivity.this, "Movies Refreshed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public Activity getActivity(){
        Context context = this;
        while (context instanceof ContextWrapper){
            if (context instanceof Activity){
                return (Activity) context;
            }
            context = ((ContextWrapper) context).getBaseContext();
        }
        return null;
    }

    private void initViews(){

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("Fetching movies...");
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();

        mRecyclerView = findViewById(R.id.recycler_view);

        movieDetailsList = new ArrayList<>();
        mApiAdapter = new ApiAdapter(this, movieDetailsList);

        if(getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            mRecyclerView.setLayoutManager((new GridLayoutManager(this, 1)));
        }else{
            mRecyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        }

        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mApiAdapter);
        mApiAdapter.notifyDataSetChanged();


    }

    private void loadJSON(){
        try {
            if (BuildConfig.THE_MOVIE_DB_API_TOKEN.isEmpty()){
                Toast.makeText(getApplicationContext(), "Please obtain API KEY firstly from themoviedb.org", Toast.LENGTH_SHORT).show();
                mProgressDialog.dismiss();
                return;
            }

            MyClient MyClient = new MyClient();
            ApiService apiService =
                    MyClient.getClient().create(ApiService.class);
            Call<MoviesResponse> call = apiService.getPopularMovies(BuildConfig.THE_MOVIE_DB_API_TOKEN);
            call.enqueue(new Callback<MoviesResponse>() {
                @Override
                public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                    List<MovieDetails> movieDetails = response.body().getResults();
                    mRecyclerView.setAdapter(new ApiAdapter(getApplicationContext(), movieDetails));
                    mRecyclerView.smoothScrollToPosition(0);
                    if(swipeContainer.isRefreshing()){
                        swipeContainer.setRefreshing(false);
                    }
                    mProgressDialog.dismiss();
                }

                @Override
                public void onFailure(Call<MoviesResponse> call, Throwable t) {
                    Log.d("Error", t.getMessage());
                    Toast.makeText(MainActivity.this, "Error Fetching data.", Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception e){
            Log.d("error", e.getMessage());
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }




}
