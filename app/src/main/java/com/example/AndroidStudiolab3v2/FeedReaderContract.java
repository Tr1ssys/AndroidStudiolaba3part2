package com.example.AndroidStudiolab3v2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

public class FeedReaderContract {
    private FeedReaderContract(){}

    public static class FeedEntry implements BaseColumns {
        public static final String TABLE_NAME = "Students";
     //   public static final String COLUMN_NAME_FIO = "FIO";
        public static final String COLUMN_NAME_TIME = "TIME";
        public static final String COLUMN_NAME_FATHNAME = "FATHNAME";
        public static final String COLUMN_NAME_LASTNAME = "LASTNAME";
        public static final String COLUMN_NAME_NAME = "NAME";

    }

    private static final String SQL_CREATE_ENTRIES = "CREATE TABLE " + FeedEntry.TABLE_NAME + " (" +
                    FeedEntry._ID + " INTEGER PRIMARY KEY," +
                    FeedEntry.COLUMN_NAME_LASTNAME + " TEXT," +
                    FeedEntry.COLUMN_NAME_NAME + " TEXT," +
                    FeedEntry.COLUMN_NAME_FATHNAME + " TEXT," +
                    FeedEntry.COLUMN_NAME_TIME +" TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + FeedEntry.TABLE_NAME;


    public static class FeedReaderDbHelper extends SQLiteOpenHelper {
        public static final int DATABASE_VERSION = 1;
        public static final String DATABASE_NAME = "FeedReader.db";

        public FeedReaderDbHelper(Context context){
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.d("MyTag", "Создание БД");
            db.execSQL(SQL_CREATE_ENTRIES);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.d("MyTag", "Upgrade БД");
            db.execSQL(SQL_DELETE_ENTRIES);
            onCreate(db);
        }

        @Override
        public void onOpen(SQLiteDatabase db) {
            super.onOpen(db);
            Log.d("MyTag", "Открыл БД");
        }

        public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.d("MyTag", "Upgrade с повышением версии БД");
            onUpgrade(db, oldVersion, newVersion);
        }

        public void deleteData(SQLiteDatabase db){
            Log.d("MyTag", "Удалил данные из БД");
            db.execSQL(SQL_DELETE_ENTRIES);
            onCreate(db);
        }

    }

}
