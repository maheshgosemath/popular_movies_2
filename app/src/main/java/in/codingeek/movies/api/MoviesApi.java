package in.codingeek.movies.api;

import in.codingeek.movies.mapper.MovieReviewsResult;
import in.codingeek.movies.mapper.MovieTrailersResult;
import in.codingeek.movies.mapper.MoviesResult;
import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by root on 13/3/16.
 */
public interface MoviesApi {
    @GET("/3/discover/movie?page=1")
    Call<MoviesResult> loadMovies(@Query("api_key") String apiKey, @Query("sort_by") String sortBy);

    @GET("/3/movie/{movie_id}/videos")
    Call<MovieTrailersResult> movieTrailers(@Path("movie_id") Long movieId, @Query("api_key") String apiKey);

    @GET("/3/movie/{movie_id}/reviews")
    Call<MovieReviewsResult> movieReviews(@Path("movie_id") Long movieId, @Query("api_key") String apiKey);
}
