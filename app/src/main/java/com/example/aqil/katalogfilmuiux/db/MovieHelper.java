package com.example.aqil.katalogfilmuiux.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.aqil.katalogfilmuiux.entity.Movie;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.example.aqil.katalogfilmuiux.db.DatabaseContract.TABLE_FAVORITE;
import static com.example.aqil.katalogfilmuiux.db.DatabaseContract.favoriteColumns.DESCRIPTION;
import static com.example.aqil.katalogfilmuiux.db.DatabaseContract.favoriteColumns.OVERVIEW;
import static com.example.aqil.katalogfilmuiux.db.DatabaseContract.favoriteColumns.POSTER;
import static com.example.aqil.katalogfilmuiux.db.DatabaseContract.favoriteColumns.RELEASE_DATE;
import static com.example.aqil.katalogfilmuiux.db.DatabaseContract.favoriteColumns.THUMBNAIL;
import static com.example.aqil.katalogfilmuiux.db.DatabaseContract.favoriteColumns.TITLE;

public class MovieHelper {
    private static String DATABASE_TABLE = TABLE_FAVORITE;
    DatabaseHelper databaseHelper;
    private SQLiteDatabase database;

    public MovieHelper(DatabaseHelper databaseHelper) {
        this.databaseHelper = databaseHelper;

    }

    public MovieHelper open() throws SQLException {
        database = databaseHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        databaseHelper.close();
    }

    public ArrayList<Movie> query(ArrayList<Movie> list, Movie movie) {
        Cursor cursor = database.query(DATABASE_TABLE, null
                , null
                , null
                , null
                , null
                , _ID + " DESC"
                , null);
        if (cursor.moveToFirst()) {
            do {
                movie.setId(cursor.getColumnIndexOrThrow(_ID));
                movie.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(TITLE)));
                movie.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(DESCRIPTION)));
                movie.setOverview(cursor.getString(cursor.getColumnIndexOrThrow(OVERVIEW)));
                movie.setPosterPath(cursor.getString(cursor.getColumnIndexOrThrow(POSTER)));
                movie.setThumbnailPath(cursor.getString(cursor.getColumnIndexOrThrow(THUMBNAIL)));
                movie.setRelease_date(cursor.getString(cursor.getColumnIndexOrThrow(RELEASE_DATE)));

                list.add(movie);
            }
            while (!cursor.isAfterLast());
        }

        cursor.close();
        return list;
    }

    public long insert(Movie movie, ContentValues initialValues) {
        initialValues.put(TITLE, movie.getTitle());
        initialValues.put(POSTER, movie.getPosterPath(1));
        initialValues.put(THUMBNAIL, movie.getThumbnailPath(1));
        initialValues.put(RELEASE_DATE, movie.getRelease_date());
        initialValues.put(DESCRIPTION, movie.getDescription(1));
        initialValues.put(OVERVIEW, movie.getOverview());
        return database.insert(DATABASE_TABLE, null, initialValues);

    }

    public int update(Movie movie, ContentValues args) {
        args.put(TITLE, movie.getTitle());
        args.put(POSTER, movie.getPosterPath(1));
        args.put(THUMBNAIL, movie.getThumbnailPath(1));
        args.put(RELEASE_DATE, movie.getRelease_date());
        args.put(DESCRIPTION, movie.getDescription(1));
        args.put(OVERVIEW, movie.getOverview());
        return database.update(DATABASE_TABLE, args, _ID + "= '" + movie.getId() + "'", null);
    }

    public int delete(int id) {
        return database.delete(TABLE_FAVORITE, _ID + " = '" + id + "'", null);
    }


    public Cursor queryProvider() {
        return database.query(DATABASE_TABLE
                , null
                , null
                , null

                , null
                , null
                , _ID + " DESC");
    }

    public long insertProvider(ContentValues values) {
        return database.insert(DATABASE_TABLE, null, values);
    }

    public int updateProvider(String id, ContentValues values) {
        return database.update(DATABASE_TABLE, values, _ID + " = ?", new String[]{id});
    }

    public int deleteProvider(String id) {
        return database.delete(DATABASE_TABLE, _ID + " = ?", new String[]{id});
    }
}

