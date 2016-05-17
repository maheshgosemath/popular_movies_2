package in.codingeek.adapters;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import in.codingeek.movies.mapper.MovieTrailer;
import in.codingeek.moviestage.R;
import in.codingeek.util.AppUtility;
import in.codingeek.util.PropertyReader;

/**
 * Created by root on 16/5/16.
 */
public class MovieTrailerArrayAdapter extends BaseAdapter {

    List<MovieTrailer> movieTrailerList;
    private Context context;
    private PropertyReader propertyReader;
    private AppUtility appUtility;

    public MovieTrailerArrayAdapter(Context context, List<MovieTrailer> movieTrailerList) {
        propertyReader = new PropertyReader(context);
        appUtility = new AppUtility(propertyReader.getProperties("app.properties"));
        this.context=context;
        this.movieTrailerList = movieTrailerList;
    }

    @Override
    public int getCount() {
        return movieTrailerList.size();
    }

    @Override
    public Object getItem(int position) {
        return  movieTrailerList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final MovieTrailer movieTrailer = (MovieTrailer) getItem(position);

        if(convertView == null) {
            convertView = LayoutInflater.from(this.context).inflate(R.layout.movie_trailer_images_layout, parent, false);
        }

        ImageView imageView = (ImageView) convertView.findViewById(R.id.movie_trailer_image);
        TextView textView = (TextView) convertView.findViewById(R.id.movie_trailer_name);
        textView.setText(movieTrailer.getName());

        imageView.setAdjustViewBounds(true);
        Picasso.with(context).load(appUtility.getPropertyValue("img.youtube.path") + movieTrailer.getKey() + appUtility.getPropertyValue("img.youtube.imagesize")).resize(185, 278).into(imageView);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + movieTrailer.getKey()));
                    context.startActivity(intent);
                } catch (ActivityNotFoundException ex) {
                    Intent intent = new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://www.youtube.com/watch?v=" + movieTrailer.getKey()));
                    context.startActivity(intent);
                }
            }
        });
        return convertView;

    }
}

