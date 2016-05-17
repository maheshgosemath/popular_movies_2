package in.codingeek.common;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import in.codingeek.adapters.ImageAdapter;
import in.codingeek.movies.mapper.Movie;
import in.codingeek.moviestage.MainActivity;
import in.codingeek.moviestage.MovieDetails;
import in.codingeek.moviestage.R;

/**
 * Created by root on 16/5/16.
 */
public class MovieListRenderer {

    private Context context;
    private View view;
    List<Movie> movieList;

    public MovieListRenderer(Context context, View view, List<Movie> movieList) {
        this.context = context;
        this.view = view;
        this.movieList = movieList;
    }

    public void renderMovieList() {
        ProgressBar progressBar = (ProgressBar) this.view.findViewById(R.id.progressBar);
        if(progressBar != null) {
            progressBar.setVisibility(View.GONE);
        }
        final GridView gridView = (GridView) this.view.findViewById(R.id.grid_view);
        gridView.setAdapter(new ImageAdapter(this.context, this.movieList));

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Movie movie = (Movie) parent.getAdapter().getItem(position);
            if(!context.getResources().getBoolean(R.bool.dual_pane)) {
                Intent intent = new Intent(context, MovieDetails.class);
                intent.putExtra("movie", movie);
                context.startActivity(intent);
            } else {
                updateMovie(movie);
            }
            }
        });

        TextView textView = (TextView) this.view.findViewById(R.id.movie_list_empty);
        if(movieList != null && movieList.isEmpty()) {
            textView.setVisibility(View.VISIBLE);
        } else {
            textView.setVisibility(View.INVISIBLE);
        }

        if(context.getResources().getBoolean(R.bool.dual_pane)) {
            if(this.movieList != null && this.movieList.size() > 0) {
                gridView.performItemClick(gridView, 0, gridView.getItemIdAtPosition(0));
            }
        }
    }

    public interface OnItemSelectedListener {
        void onMovieSelected(Movie movie);
    }

    public void updateMovie(Movie movie) {
        ((MainActivity) context).onMovieSelected(movie);
    }

}
