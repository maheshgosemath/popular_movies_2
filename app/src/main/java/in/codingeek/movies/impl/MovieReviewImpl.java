package in.codingeek.movies.impl;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import in.codingeek.adapters.MovieReviewArrayAdapter;
import in.codingeek.movies.api.MoviesApi;
import in.codingeek.movies.mapper.MovieReviews;
import in.codingeek.movies.mapper.MovieReviewsResult;
import in.codingeek.movies.mapper.MoviesResult;
import in.codingeek.moviestage.R;
import in.codingeek.util.AppUtility;
import in.codingeek.util.PropertyReader;

import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by root on 13/5/16.
 */
public class MovieReviewImpl implements retrofit.Callback<MovieReviewsResult> {

    private View view;
    private PropertyReader propertyReader;
    private AppUtility appUtility;
    private Context context;

    public MovieReviewImpl(Context context, View view) {
        this.view = view;
        this.context = context;
        propertyReader = new PropertyReader(context);
        appUtility = new AppUtility(propertyReader.getProperties("app.properties"));
    }

    public void fetchMovieReviews(Long movieId) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(appUtility.getPropertyValue("base.url"))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MoviesApi moviesApi = retrofit.create(MoviesApi.class);
        Call<MovieReviewsResult> call = moviesApi.movieReviews(movieId, appUtility.getPropertyValue("api.key"));

        call.enqueue(this);
    }

    @Override
    public void onResponse(Response<MovieReviewsResult> response, Retrofit retrofit) {

        MovieReviewArrayAdapter adapter = new MovieReviewArrayAdapter(context, response.body().getResults());
        LinearLayout linearLayout = (LinearLayout) this.view.findViewById(R.id.movie_review_layout);

        for (int i = 0; i < adapter.getCount(); i++) {
            View view = adapter.getView(i, null, linearLayout);
            linearLayout.addView(view);
        }
        if(adapter.getCount() == 0) {
            TextView textView = (TextView) this.view.findViewById(R.id.movie_review_empty);
            textView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onFailure(Throwable t) {

    }
}
