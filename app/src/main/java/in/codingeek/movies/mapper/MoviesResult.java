package in.codingeek.movies.mapper;

import java.util.List;

/**
 * Created by root on 13/3/16.
 */
public class MoviesResult {
    private List<Movie> results;

    public List<Movie> getResults() {
        return results;
    }

    public void setResults(List<Movie> results) {
        this.results = results;
    }
}
