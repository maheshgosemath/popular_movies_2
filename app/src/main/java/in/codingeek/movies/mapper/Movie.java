package in.codingeek.movies.mapper;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

/**
 * Created by root on 13/3/16.
 */
public class Movie implements Parcelable {
    private String poster_path;
    private boolean adult;
    private String overview;
    private String release_date;
    private List<Integer> genre_ids;
    private Long id;
    private String original_title;
    private String original_language;
    private String title;
    private String backdrop_path;
    private Double popularity;
    private Long vote_count;
    private boolean video;
    private Double vote_average;

    public Movie() {
    }

    protected Movie(Parcel in) {
        String strArry[] = new String[7];
        in.readStringArray(strArry);
        poster_path = strArry[0];
        overview = strArry[1];
        release_date = strArry[2];
        original_title = strArry[3];
        original_language = strArry[4];
        title = strArry[5];
        backdrop_path = strArry[6];

        boolean boolArry[] = new boolean[2];
        in.readBooleanArray(boolArry);
        adult = boolArry[0];
        video = boolArry[1];

        long longArry[] = new long[2];
        in.readLongArray(longArry);
        id = longArry[0];
        vote_count = longArry[1];

        double doubleArry[] = new double[2];
        in.readDoubleArray(doubleArry);
        popularity = doubleArry[0];
        vote_average = doubleArry[1];
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public List<Integer> getGenre_ids() {
        return genre_ids;
    }

    public void setGenre_ids(List<Integer> genre_ids) {
        this.genre_ids = genre_ids;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public Double getPopularity() {
        return popularity;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    public Long getVote_count() {
        return vote_count;
    }

    public void setVote_count(Long vote_count) {
        this.vote_count = vote_count;
    }

    public boolean isVideo() {
        return video;
    }

    public void setVideo(boolean video) {
        this.video = video;
    }

    public Double getVote_average() {
        return vote_average;
    }

    public void setVote_average(Double vote_average) {
        this.vote_average = vote_average;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[]{poster_path, overview, release_date,
                original_language, original_title, title, backdrop_path});
        dest.writeBooleanArray(new boolean[]{adult, video});
        dest.writeLongArray(new long[]{id, vote_count});
        dest.writeDoubleArray(new double[]{popularity, vote_average});
        dest.writeList(genre_ids);
    }
}
