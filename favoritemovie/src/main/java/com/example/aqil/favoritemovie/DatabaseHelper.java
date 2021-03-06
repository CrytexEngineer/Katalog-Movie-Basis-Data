package com.example.aqil.favoritemovie;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.example.aqil.favoritemovie.DatabaseContract.TABLE_FAVORITE;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static String DATABASE_NAME = "dbFavorite";

private  static final int DATABASE_VERSION = 1;
private  static final String SQL_CREATE_TABLE_FAVORITE = String.format("CREATE TABLE %s"
                + " (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                " %s TEXT NOT NULL," +
                " %s TEXT NOT NULL," +
                " %s TEXT NOT NULL," +
                " %s TEXT NOT NULL," +
                " %s TEXT NOT NULL," +
                " %s TEXT NOT NULL)",
        TABLE_FAVORITE,
        DatabaseContract.favoriteColumns._ID,
        DatabaseContract.favoriteColumns.TITLE,
        DatabaseContract.favoriteColumns.DESCRIPTION,
        DatabaseContract.favoriteColumns.POSTER,
        DatabaseContract.favoriteColumns.RELEASE_DATE,
        DatabaseContract.favoriteColumns.OVERVIEW,
        DatabaseContract.favoriteColumns.THUMBNAIL

);

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {db.execSQL(SQL_CREATE_TABLE_FAVORITE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_FAVORITE );
onCreate(db);

    }
}
