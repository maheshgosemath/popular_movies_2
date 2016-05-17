package in.codingeek.movies.realm.service;

import java.util.ArrayList;
import java.util.List;

import in.codingeek.movies.mapper.Movie;
import in.codingeek.movies.realm.contract.MovieObj;
import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.exceptions.RealmException;

/**
 * Created by root on 16/5/16.
 */
public class MovieObjService {

    private Realm realm;

    public MovieObjService(Realm realm) {
        this.realm = realm;
    }

    public List<Movie> fetchFavoriteMovies() {
        RealmQuery<MovieObj> query = realm.where(MovieObj.class);
        RealmResults<MovieObj> realmResults = query.findAll();
        return convertToMovieList(realmResults);
    }

    public Boolean fetchMovie(String movieId) {
        RealmQuery<MovieObj> query = realm.where(MovieObj.class)
                .equalTo("movieId", movieId);
        RealmResults<MovieObj> realmResults = query.findAll();
        if(realmResults != null && realmResults.size() > 0) {
            return true;
        }
        return false;
    }

    public void addMovieToFavorite(Movie movie) {
        try {
            realm.beginTransaction();
            MovieObj movieDO = realm.createObject(MovieObj.class);
            movieDO.setMovieName(movie.getOriginal_title());
            movieDO.setPosterPath(movie.getPoster_path());
            movieDO.setBackdropPath(movie.getBackdrop_path());
            movieDO.setMovieId(String.valueOf(movie.getId()));
            movieDO.setReleaseDate(movie.getRelease_date());
            movieDO.setVoteAverage(String.valueOf(movie.getVote_average()));
            movieDO.setOverview(movie.getOverview());
            realm.commitTransaction();
        }catch(RealmException e) {
            realm.close();
        }
    }

    public void removeMovieFromFavorite(String movieId) {
        RealmQuery<MovieObj> query = realm.where(MovieObj.class)
                .equalTo("movieId", movieId);
        realm.beginTransaction();
        RealmResults<MovieObj> realmResults = query.findAll();
        realmResults.deleteAllFromRealm();
        realm.commitTransaction();
    }

    private List<Movie> convertToMovieList(RealmResults<MovieObj> realmResults) {
        List<Movie> movieList = new ArrayList<Movie>();

        Movie movie = null;
        if(realmResults != null) {
            for (MovieObj movieObj : realmResults) {
                movie = new Movie();
                movie.setTitle(movieObj.getMovieName());
                movie.setBackdrop_path(movieObj.getBackdropPath());
                movie.setPoster_path(movieObj.getPosterPath());
                movie.setId(Long.parseLong(movieObj.getMovieId()));
                movie.setRelease_date(movieObj.getReleaseDate());
                movie.setVote_average(Double.parseDouble(movieObj.getVoteAverage()));
                movie.setOverview(movieObj.getOverview());
                if(movieObj.getVoteAverage() != null) {
                    movie.setVote_average(Double.parseDouble(movieObj.getVoteAverage()));
                } else {
                    movie.setVote_average(0.0);
                }
                movie.setPopularity(0.0);
                movie.setVote_count(0l);

                movieList.add(movie);
            }
        }
        return movieList;
    }
}

