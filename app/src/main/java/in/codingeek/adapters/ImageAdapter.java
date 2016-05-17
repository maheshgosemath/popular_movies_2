package in.codingeek.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import in.codingeek.movies.mapper.Movie;
import in.codingeek.moviestage.R;
import in.codingeek.util.AppUtility;
import in.codingeek.util.PropertyReader;

/**
 * Created by root on 13/3/16.
 */
public class ImageAdapter extends BaseAdapter{
    List<Movie> movieList;
    private Context context;
    private PropertyReader propertyReader;
    private AppUtility appUtility;

    public ImageAdapter(Context context,List<Movie> movieList){
        propertyReader = new PropertyReader(context);
        appUtility = new AppUtility(propertyReader.getProperties("app.properties"));
        this.context=context;
        this.movieList = movieList;
    }

    @Override
    public int getCount() {
        return movieList.size();
    }

    @Override
    public Object getItem(int position) {
        return movieList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ImageView imageView;
        if(convertView != null && convertView instanceof  ImageView) {
            imageView = (ImageView) convertView;
        } else {
            imageView = new ImageView(context);
        }

        imageView.setAdjustViewBounds(true);
        Picasso.with(context).load(appUtility.getPropertyValue("movie.poster.path") + movieList.get(position).getPoster_path()).resize(185, 278).into(imageView);
        return imageView;
    }
}