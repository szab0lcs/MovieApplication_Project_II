package com.example.movieapp.Movie;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;
public class MoviesResponse implements Parcelable {
    @SerializedName("page")
    private int page;
    @SerializedName("results")
    private List<MovieDetails> results;
    @SerializedName("total_results")
    private int totalResults;
    @SerializedName("total_pages")
    private int totalPages;

    public List<MovieDetails> getResults() {
        return results;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.page);
        dest.writeTypedList(this.results);
        dest.writeInt(this.totalResults);
        dest.writeInt(this.totalPages);
    }

    protected MoviesResponse(Parcel in) {
        this.page = in.readInt();
        this.results = in.createTypedArrayList(MovieDetails.CREATOR);
        this.totalResults = in.readInt();
        this.totalPages = in.readInt();
    }

    public static final Parcelable.Creator<MoviesResponse> CREATOR = new Parcelable.Creator<MoviesResponse>() {
        @Override
        public MoviesResponse createFromParcel(Parcel source) {
            return new MoviesResponse(source);
        }

        @Override
        public MoviesResponse[] newArray(int size) {
            return new MoviesResponse[size];
        }
    };
}