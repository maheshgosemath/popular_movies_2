package in.codingeek.movies.mapper;

import java.util.List;

/**
 * Created by root on 16/5/16.
 */
public class MovieReviewsResult {
    private List<MovieReviews> results;

    public List<MovieReviews> getResults() {
        return results;
    }

    public void setResults(List<MovieReviews> results) {
        this.results = results;
    }
}
