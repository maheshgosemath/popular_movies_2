package in.codingeek.moviestage;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.List;

import in.codingeek.common.MovieListRenderer;
import in.codingeek.movies.mapper.Movie;
import in.codingeek.movies.realm.config.RealmConfig;
import in.codingeek.movies.impl.MoviesImpl;
import in.codingeek.movies.realm.service.MovieObjService;
import io.realm.Realm;

public class MainActivity extends AppCompatActivity implements MovieListRenderer.OnItemSelectedListener {

    private Realm realm;
    private RealmConfig realmConfig;
    private MovieObjService movieObjService;
    private MovieListRenderer movieListRenderer;
    private SharedPreferences sharedpreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        realmConfig = new RealmConfig();

        sharedpreferences = getSharedPreferences("sort_criteria", Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
        if(!sharedpreferences.contains("movie_sort")) {
            editor.putString("movie_sort", "popularity.desc");
            editor.commit();
        }

        if (getResources().getBoolean(R.bool.dual_pane)) {
            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.detailsfragment, new MovieDetailsFragment())
                        .commit();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        showLoader();
        int id = item.getItemId();
        if (id == R.id.action_high_popularity) {
            MoviesImpl movies = new MoviesImpl(this, findViewById(R.id.fragment));
            editor.putString("movie_sort", "popularity.desc");
            editor.commit();
            movies.fetchMovies("popularity.desc");
            return true;
        }
        if (id == R.id.action_high_rated) {
            MoviesImpl movies = new MoviesImpl(this, findViewById(R.id.fragment));
            editor.putString("movie_sort", "vote_average.desc");
            editor.commit();
            movies.fetchMovies("vote_average.desc");
            return true;
        }
        if (id == R.id.action_favourite) {
            realm = realmConfig.initializeRealm(this);
            movieObjService = new MovieObjService(realm);

            List<Movie> movieList = movieObjService.fetchFavoriteMovies();
            movieListRenderer = new MovieListRenderer(this, findViewById(R.id.fragment), movieList);
            movieListRenderer.renderMovieList();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showLoader() {
        View view = (View) findViewById(R.id.fragment);
        if (view != null) {
            view.findViewById(R.id.progressBar).setVisibility(View.VISIBLE);
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void onMovieSelected(Movie movie) {
        MovieDetailsFragment movieDetailsFragment = new MovieDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("movie", movie);
        movieDetailsFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.detailsfragment, movieDetailsFragment).commit();
    }
}
