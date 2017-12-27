package com.example.andriginting.myapplication.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.andriginting.myapplication.model.Movie;

import java.util.ArrayList;

/**
 * Created by Andri Ginting on 12/26/2017.
 */

public class MovieHelper  {
    private static String DATABASE_TABLE = DatabaseHelper.TABLE_NAME;
    private Context context;
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase sqLiteDatabase;

    public MovieHelper(Context context) {
        this.context = context;
    }

    public MovieHelper open() throws SQLException{
        databaseHelper = new DatabaseHelper(context);
        sqLiteDatabase = databaseHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        databaseHelper.close();
    }

    public Cursor searchQuery(int judul){
        return sqLiteDatabase.rawQuery("SELECT * FROM "+DATABASE_TABLE+" WHERE "+
        DatabaseHelper.FIELD_MOVIE_ID+" LIKE '%"+judul+"%'",null);
    }

    public ArrayList<Movie> getSearchResult(int keyword){
        ArrayList<Movie> arrayList = new ArrayList<>();
        Cursor cursor = searchQuery(keyword);
        cursor.moveToFirst();
        Movie movie;

        if (cursor.getCount()>0){
            do{
                movie = new Movie();
                movie.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.FIELD_ID)));
                movie.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.FIELD_TITLE)));
                movie.setPosterPath(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.FIELD_POSTER)));
                movie.setOverview(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.FIELD_OVERVIEW)));
                movie.setReleaseDate(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.FIELD_RELEASE)));

                arrayList.add(movie);
                cursor.moveToNext();
            }while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public int getData(int id){
        int result =0;
        Cursor cursor = searchQuery(id);
        cursor.moveToFirst();

        if (cursor.getCount()>0){
            result=cursor.getInt(1);

            for (;!cursor.isAfterLast(); cursor.moveToNext()){
                result = cursor.getInt(1);
                Log.d("TAG", "getData: for "+result);
            }
        }
        return result;
    }

    public Cursor queryAllData(){
        return sqLiteDatabase.rawQuery("SELECT * FROM "+DATABASE_TABLE+" ORDER BY "+DatabaseHelper.FIELD_MOVIE_ID
                +" DESC",null);
    }

    public ArrayList<Movie> getAllData(){
        ArrayList<Movie> arrayList = new ArrayList<>();
        Cursor cursor = queryAllData();
        cursor.moveToFirst();
        Movie movie;

        if (cursor.getCount()>0){
            do{
                movie = new Movie();
                movie.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.FIELD_ID)));
                movie.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.FIELD_TITLE)));
                movie.setPosterPath(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.FIELD_POSTER)));
                movie.setOverview(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.FIELD_OVERVIEW)));
                movie.setReleaseDate(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.FIELD_RELEASE)));

                arrayList.add(movie);
                cursor.moveToNext();
            }while (!cursor.isAfterLast());
        }
        cursor.close();
        return  arrayList;
    }

    public long insert(Movie movieItem){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.FIELD_TITLE,movieItem.getTitle());;
        contentValues.put(DatabaseHelper.FIELD_POSTER,movieItem.getPosterPath());
        contentValues.put(DatabaseHelper.FIELD_OVERVIEW,movieItem.getOverview());
        contentValues.put(DatabaseHelper.FIELD_RELEASE,movieItem.getReleaseDate());
        return sqLiteDatabase.update(DATABASE_TABLE
                ,contentValues
                ,DatabaseHelper.FIELD_ID+" = '"+movieItem.getId()+"'"
                ,null);
    }

    public void delete(int id){
        sqLiteDatabase.delete(DATABASE_TABLE
                ,DatabaseHelper.FIELD_ID+" = '"+id+"'"
                ,null);
    }
}
