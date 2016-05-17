package in.codingeek.movies.impl;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;

import java.util.List;

import in.codingeek.adapters.ImageAdapter;
import in.codingeek.common.MovieListRenderer;
import in.codingeek.movies.api.MoviesApi;
import in.codingeek.movies.mapper.Movie;
import in.codingeek.movies.mapper.MoviesResult;
import in.codingeek.moviestage.MovieDetails;
import in.codingeek.moviestage.R;
import in.codingeek.util.AppUtility;
import in.codingeek.util.PropertyReader;
import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by root on 13/3/16.
 */
public class MoviesImpl implements retrofit.Callback<MoviesResult> {

    private View view;
    private PropertyReader propertyReader;
    private AppUtility appUtility;
    private Context context;
    private MovieListRenderer movieListRenderer;

    public MoviesImpl(Context context, View view) {
        this.view = view;
        this.context = context;
        propertyReader = new PropertyReader(context);
        appUtility = new AppUtility(propertyReader.getProperties("app.properties"));
    }

    public void fetchMovies(String order) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(appUtility.getPropertyValue("base.url"))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MoviesApi moviesApi = retrofit.create(MoviesApi.class);
        Call<MoviesResult> call = moviesApi.loadMovies(appUtility.getPropertyValue("api.key"), order);

        call.enqueue(this);
    }

    @Override
    public void onResponse(Response<MoviesResult> response, Retrofit retrofit) {

        List<Movie> movieList = response.body().getResults();
        movieListRenderer = new MovieListRenderer(this.context, this.view, movieList);
        movieListRenderer.renderMovieList();
    }

    @Override
    public void onFailure(Throwable t) {

    }
}
