package in.codingeek.adapters;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import in.codingeek.movies.mapper.MovieReviews;
import in.codingeek.moviestage.R;

/**
 * Created by root on 16/5/16.
 */
public class MovieReviewArrayAdapter extends ArrayAdapter<MovieReviews> {

    private Context context;

    public MovieReviewArrayAdapter(Context context, List<MovieReviews> movieReviewsList) {
        super(context, 0, movieReviewsList);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final MovieReviews movieReviews = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.movie_review, parent, false);
        }
        TextView author = (TextView) convertView.findViewById(R.id.movie_review_author);
        TextView content = (TextView) convertView.findViewById(R.id.movie_review);
        TextView readMore = (TextView) convertView.findViewById(R.id.movie_review_show_more);

        author.setText(movieReviews.getAuthor());
        content.setText(movieReviews.getContent());

        readMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent i = new Intent("android.intent.action.MAIN");
                    i.setComponent(ComponentName.unflattenFromString("com.android.chrome/com.android.chrome.Main"));
                    i.addCategory("android.intent.category.LAUNCHER");
                    i.setData(Uri.parse(movieReviews.getUrl()));
                    context.startActivity(i);
                }
                catch(ActivityNotFoundException e) {
                    Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(movieReviews.getUrl()));
                    context.startActivity(i);
                }
            }
        });

        return convertView;
    }
}
