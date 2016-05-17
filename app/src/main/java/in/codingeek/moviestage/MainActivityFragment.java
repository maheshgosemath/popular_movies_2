package in.codingeek.moviestage;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import in.codingeek.movies.impl.MoviesImpl;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private View rootView;
    private SharedPreferences sharedPreferences;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_main, container, false);
        ProgressBar progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);
        if(progressBar != null) {
            progressBar.setVisibility(View.VISIBLE);
        }

        sharedPreferences = getActivity().getSharedPreferences("sort_criteria", getActivity().MODE_PRIVATE);
        MoviesImpl movies = new MoviesImpl(getContext(), rootView);
        movies.fetchMovies(sharedPreferences.getString("movie_sort", ""));
        return rootView;
    }

}
