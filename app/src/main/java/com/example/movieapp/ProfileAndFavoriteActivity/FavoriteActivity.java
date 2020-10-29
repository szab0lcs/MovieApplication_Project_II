package com.example.movieapp.ProfileAndFavoriteActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.movieapp.Api.ApiAdapter;
import com.example.movieapp.Database.DatabaseHelperFavorite;
import com.example.movieapp.Main.MainActivity;
import com.example.movieapp.Movie.MovieDetails;
import com.example.movieapp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class FavoriteActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ApiAdapter adapter;
    private List<MovieDetails> movieList;
    private DatabaseHelperFavorite databaseHelperFavorite;
    private final AppCompatActivity appCompatActivity = FavoriteActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        
        init();
        
        initFavorites();

        getAllFavorite();

        bottomNavigation();

    }

    private void bottomNavigation() {
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);

        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.nav_home:
                        Intent intent1 = new Intent(FavoriteActivity.this, MainActivity.class);
                        Intent intent6 = getIntent();
                        intent1.putExtra("email", intent6.getStringExtra("email"));
                        startActivity(intent1);
                        finish();
                        break;
                    case R.id.nav_fav:
                        Intent intent2 = new Intent(FavoriteActivity.this, FavoriteActivity.class);
                        Intent intent5 = getIntent();
                        intent2.putExtra("email", intent5.getStringExtra("email"));
                        startActivity(intent2);
                        finish();
                        break;
                    case R.id.nav_profile:
                        Intent intent3 = new Intent(FavoriteActivity.this, ProfileActivity.class);
                        Intent intent4 = getIntent();
                        intent3.putExtra("email", intent4.getStringExtra("email"));
                        startActivity(intent3);
                        finish();
                        break;
                }

                return false;
            }
        });
    }

    @SuppressLint("StaticFieldLeak")
    private void getAllFavorite(){
        new AsyncTask<Void, Void, Void>(){

            @Override
            protected Void doInBackground(Void... voids) {
                movieList.clear();

                movieList.addAll(databaseHelperFavorite.getAllFavorite());
                return null;
            }
            @Override
            protected void onPostExecute(Void aVoid){
                super.onPostExecute(aVoid);
                adapter.notifyDataSetChanged();
            }
        }.execute();
    }

    private void initFavorites() {

        if (getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            recyclerView.setLayoutManager(new GridLayoutManager(this,1));
        }
        else{
            recyclerView.setLayoutManager(new GridLayoutManager(this,4));
        }

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        databaseHelperFavorite = new DatabaseHelperFavorite(appCompatActivity);
    }

    private void init() {

        recyclerView = findViewById(R.id.recycler_favorites);
        movieList = new ArrayList<>();
        adapter = new ApiAdapter(this, movieList);
    }

    public Activity getActivity(){
        Context context = this;
        while(context instanceof ContextWrapper){
            if(context instanceof Activity){
                return (Activity) context;
            }
            context = ((ContextWrapper)context).getBaseContext();
        }
        return null;
    }
}
