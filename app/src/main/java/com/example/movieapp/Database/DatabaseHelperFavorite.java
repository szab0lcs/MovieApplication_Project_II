package com.example.movieapp.Database;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import com.example.movieapp.Movie.MovieDetails;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelperFavorite extends SQLiteOpenHelper implements BaseColumns {

    private static final String DATABASE_NAME= "favorites.db";

    private static final int DATABASE_VERSION= 1;

    public static final String TABLE_NAME = "favorite";
    public static final String COLUMN_MOVIEID = "movieId";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_USERRATING = "userRating";
    public static final String COLUMN_POSTER_PATH = "posterPath";
    public static final String COLUMN_PLOT_SYNOPSIS = "overview";

    private SQLiteOpenHelper dbHandler;
    private SQLiteDatabase db;

    public DatabaseHelperFavorite(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void close(){
        dbHandler.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        final String SQL_CREATE_FAVORITE_TABLE = "CREATE TABLE " +TABLE_NAME + " (" +
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_MOVIEID + " INTEGER, " +
                COLUMN_TITLE + " TEXT NOT NULL, " +
                COLUMN_USERRATING + " REAL NOT NULL, " +
                COLUMN_POSTER_PATH + " TEXT NOT NULL, " +
                COLUMN_PLOT_SYNOPSIS + " TEXT NOT NULL" +
                "); ";
        db.execSQL(SQL_CREATE_FAVORITE_TABLE);
    }

    public List<MovieDetails> getAllFavorite(){
        String[] columns = {
                _ID,
                COLUMN_MOVIEID,
                COLUMN_TITLE,
                COLUMN_USERRATING,
                COLUMN_POSTER_PATH,
                COLUMN_PLOT_SYNOPSIS

        };
        String sortOrder =
                _ID + " ASC";
        List<MovieDetails> favoriteList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME,
                columns,
                null,
                null,
                null,
                null,
                sortOrder);

        if (cursor.moveToFirst()){
            do {
                MovieDetails movie = new MovieDetails();
                movie.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_MOVIEID))));
                movie.setOriginalTitle(cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)));
                movie.setVoteAverage(Double.parseDouble(cursor.getString(cursor.getColumnIndex(COLUMN_USERRATING))));
                movie.setPosterPath(cursor.getString(cursor.getColumnIndex(COLUMN_POSTER_PATH)));
                movie.setOverview(cursor.getString(cursor.getColumnIndex(COLUMN_PLOT_SYNOPSIS)));

                if(favoriteList.contains(movie)){

                }else{
                    favoriteList.add(movie);
                }


            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return favoriteList;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addFavoriteList(MovieDetails movie){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_MOVIEID, movie.getId());
        values.put(COLUMN_TITLE, movie.getOriginalTitle());
        values.put(COLUMN_USERRATING, movie.getVoteAverage());
        values.put(COLUMN_POSTER_PATH, movie.getPosterPath());
        values.put(COLUMN_PLOT_SYNOPSIS, movie.getOverview());

        db.insert(TABLE_NAME, null, values);
        db.close();
    }
}
