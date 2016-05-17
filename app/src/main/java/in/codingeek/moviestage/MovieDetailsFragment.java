package in.codingeek.moviestage;

import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import in.codingeek.movies.impl.MovieTrailerImpl;
import in.codingeek.movies.mapper.MovieTrailer;
import in.codingeek.movies.realm.config.RealmConfig;
import in.codingeek.movies.realm.contract.MovieObj;
import in.codingeek.movies.impl.MovieReviewImpl;
import in.codingeek.movies.mapper.Movie;
import in.codingeek.movies.realm.service.MovieObjService;
import in.codingeek.util.AppUtility;
import in.codingeek.util.DateUtil;
import in.codingeek.util.PropertyReader;
import io.realm.Realm;

/**
 * A placeholder fragment containing a simple view.
 */
public class MovieDetailsFragment extends Fragment {

    private PropertyReader propertyReader;
    private AppUtility appUtility;
    private RealmConfig realmConfig;
    private Realm realm;
    private MovieObjService movieObjService;
    private CoordinatorLayout coordinatorLayout;

    public MovieDetailsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        propertyReader = new PropertyReader(this.getActivity());
        appUtility = new AppUtility(propertyReader.getProperties("app.properties"));

        if(getResources().getBoolean(R.bool.dual_pane)) {
            coordinatorLayout = (CoordinatorLayout) getActivity().findViewById(R.id.movie_list_coordinator);
        } else {
            coordinatorLayout = (CoordinatorLayout) getActivity().findViewById(R.id.movie_details_coordinator);
        }

        final View rootView = inflater.inflate(R.layout.fragment_movie_details, container, false);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(getResources().getBoolean(R.bool.dual_pane)) {
            coordinatorLayout = (CoordinatorLayout) getActivity().findViewById(R.id.movie_list_coordinator);
        } else {
            coordinatorLayout = (CoordinatorLayout) getActivity().findViewById(R.id.movie_details_coordinator);
        }

        Movie temp = null;
        if(this.getArguments() != null) {
            temp = this.getArguments().getParcelable("movie");
        }
        final Movie movie = getResources().getBoolean(R.bool.dual_pane) ? temp :
                ((MovieDetails)getActivity()).getMovie();
        if(movie != null) {
            renderMovieDetails(this.getView(), movie);
            realmConfig = new RealmConfig();
            initializeFab(movie);
        }
    }

    private void renderMovieDetails(View rootView, Movie movie) {
        TextView movieTitle = (TextView) rootView.findViewById(R.id.movie_title);
        TextView movieRatings = (TextView) rootView.findViewById(R.id.movie_ratings);
        TextView movieReleaseDate = (TextView) rootView.findViewById(R.id.movie_release_date);
        final TextView movieOverview = (TextView) rootView.findViewById(R.id.movie_overview);

        ImageView imageView = (ImageView) rootView.findViewById(R.id.image_detail_view);
        Picasso.with(getActivity()).load(appUtility.getPropertyValue("movie.backdrop.path") + movie.getBackdrop_path()).into(imageView);

        movieTitle.setText(movie.getTitle());
        movieRatings.setText(movie.getVote_average().toString());
        movieReleaseDate.setText(DateUtil.beautifyDate(movie.getRelease_date()));
        movieOverview.setText(movie.getOverview());

        MovieReviewImpl movieReview = new MovieReviewImpl(getContext(), rootView);
        movieReview.fetchMovieReviews(movie.getId());

        MovieTrailerImpl movieTrailer = new MovieTrailerImpl(getContext(), rootView);
        movieTrailer.fetchMovieTrailers(movie.getId());

        final TextView showMoreOverview = (TextView) rootView.findViewById(R.id.movie_overview_show_more);
        showMoreOverview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
            movieOverview.setMaxLines(Integer.MAX_VALUE);
                v.setVisibility(View.GONE);
            }
                      });
    }

    private void initializeFab(final Movie movie) {

        final FloatingActionButton addFab = (FloatingActionButton) getActivity().findViewById(R.id.add_to_favorite);
        final FloatingActionButton removeFab = (FloatingActionButton) getActivity().findViewById(R.id.remove_favorite);

        realm = realmConfig.initializeRealm(getActivity());
        movieObjService = new MovieObjService(realm);
        if (movieObjService.fetchMovie(String.valueOf(movie.getId()))) {
            addFab.setVisibility(View.VISIBLE);
            removeFab.setVisibility(View.INVISIBLE);
        } else {
            addFab.setVisibility(View.INVISIBLE);
            removeFab.setVisibility(View.VISIBLE);
        }

        if (removeFab != null) {
            removeFab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    movieObjService.addMovieToFavorite(movie);
                    Snackbar snackbar = Snackbar.make(coordinatorLayout, "Movie added to favorites", Snackbar.LENGTH_LONG);
                    snackbar.show();
                    removeFab.setVisibility(View.INVISIBLE);
                    addFab.setVisibility(View.VISIBLE);
                }
            });
        }
        if (addFab != null) {
            addFab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    movieObjService.removeMovieFromFavorite(String.valueOf(movie.getId()));
                    Snackbar snackbar = Snackbar.make(coordinatorLayout, "Movie removed from favorites", Snackbar.LENGTH_LONG);
                    snackbar.show();
                    removeFab.setVisibility(View.VISIBLE);
                    addFab.setVisibility(View.INVISIBLE);
                }
            });
        }
    }
}
