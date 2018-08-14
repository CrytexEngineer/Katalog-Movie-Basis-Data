package com.example.aqil.katalogfilmuiux;

import android.content.ContentValues;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aqil.katalogfilmuiux.adapter.FavoriteAdapter;
import com.example.aqil.katalogfilmuiux.adapter.MovieAdapter;
import com.example.aqil.katalogfilmuiux.entity.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.aqil.katalogfilmuiux.db.DatabaseContract.CONTENT_URI;
import static com.example.aqil.katalogfilmuiux.db.DatabaseContract.favoriteColumns.DESCRIPTION;
import static com.example.aqil.katalogfilmuiux.db.DatabaseContract.favoriteColumns.OVERVIEW;
import static com.example.aqil.katalogfilmuiux.db.DatabaseContract.favoriteColumns.POSTER;
import static com.example.aqil.katalogfilmuiux.db.DatabaseContract.favoriteColumns.RELEASE_DATE;
import static com.example.aqil.katalogfilmuiux.db.DatabaseContract.favoriteColumns.THUMBNAIL;
import static com.example.aqil.katalogfilmuiux.db.DatabaseContract.favoriteColumns.TITLE;

public class DetailActivity extends AppCompatActivity {
    ImageView img_detail;
    TextView detaiLDescription;
    TextView titleDetail, movieDateDetail;
    @BindView(R.id.favorites_checkbox)
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        int requestCode = getIntent().getIntExtra(MovieAdapter.EXTRA_REQUEST_CODE, 0);
        ButterKnife.bind(this);
        img_detail = findViewById(R.id.img_detail);
        titleDetail = findViewById(R.id.titleDetail);
        movieDateDetail = findViewById(R.id.movie_date_time_detail);
        detaiLDescription = findViewById(R.id.description);
        final Movie currentMovie = getIntent().getParcelableExtra(MovieAdapter.EXTRA_MOVIE);
        titleDetail.setText(currentMovie.getTitle());
        movieDateDetail.setText(currentMovie.getRelease_date());
        Picasso.get().load(currentMovie.getPosterPath(0)).resize(480, 680).into(img_detail);
        Log.d(getClass().getSimpleName(), "onCreate: " + requestCode);
        if (requestCode != 1) {
            button.setVisibility(View.INVISIBLE);
            detaiLDescription.setText(currentMovie.getDescription(0));
        } else detaiLDescription.setText(currentMovie.getDescription(1));
        button.setOnClickListener(
                new View.OnClickListener() {


                    public void onClick(View v) {
                        Boolean hasAdded = false;
                        ArrayList<Movie> listFavoritedMovie = FavoriteAdapter.listMovie;
                        Toast toast = Toast.makeText(DetailActivity.this, "ITEM ALREADY ADDED", Toast.LENGTH_SHORT);
                        Toast toast1 = Toast.makeText(DetailActivity.this, "Added To Favorites", Toast.LENGTH_LONG);
                        for (int i = 0; i < listFavoritedMovie.size(); i++) {
                            if (currentMovie.getTitle().equals(listFavoritedMovie.get(i).getTitle())) {
                                toast1.cancel();
                                toast.show();
                                hasAdded = true;

                            }
                        }
                        if (hasAdded != true) {

                            ContentValues contentValues = new ContentValues();
                            contentValues.put(TITLE, currentMovie.getTitle());
                            contentValues.put(OVERVIEW, currentMovie.getOverview());
                            contentValues.put(POSTER, currentMovie.getPosterPath(1));
                            contentValues.put(RELEASE_DATE, currentMovie.getRelease_date());
                            contentValues.put(THUMBNAIL, currentMovie.getThumbnailPath(1));
                            contentValues.put(DESCRIPTION, currentMovie.getDescription(1));
                            getContentResolver().insert(CONTENT_URI, contentValues);
                            FavoriteAdapter.listMovie.add(currentMovie);
                            toast.cancel();
                            toast1.show();
                        }

                    }
                }
        );

    }
}
