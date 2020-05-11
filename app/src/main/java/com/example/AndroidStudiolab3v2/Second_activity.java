package com.example.AndroidStudiolab3v2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.util.Log;

import com.example.AndroidStudiolab3v2.FeedReaderContract.FeedReaderDbHelper;
import com.example.AndroidStudiolab3v2.FeedReaderContract.FeedEntry;

import java.util.ArrayList;
import java.util.List;

public class Second_activity extends AppCompatActivity {

    // Поля для БД
    FeedReaderDbHelper dbHelper;
    SQLiteDatabase db;

    // Поля для списка
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_activity);

        // Создаем список
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // Адаптер создадим после открытия БД

        // Открываем БД
        Log.d("MyTag", "Сейчас попробую открыть БД");
        dbHelper = new FeedReaderDbHelper(getApplicationContext());
        db = dbHelper.getReadableDatabase();

        Log.d("MyTag", "У меня получилось");

        // Чтение из БД
        String[] projection = {
                BaseColumns._ID,
                FeedEntry.COLUMN_NAME_LASTNAME,
                FeedEntry.COLUMN_NAME_NAME,
                FeedEntry.COLUMN_NAME_FATHNAME,
                FeedEntry.COLUMN_NAME_TIME
        };

         String sortOrder =
                FeedEntry.COLUMN_NAME_LASTNAME + " ASC";

        Log.d("MyTag", "Хочу получить курсор");

        Cursor cursor = db.query(
                FeedEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                  sortOrder
        );

        List itemIDs = new ArrayList<>();   // ID
      //  List itemFIOs = new ArrayList<>();  // full name student
        List itemDates = new ArrayList<>(); // date added
        List itemLastNames = new ArrayList(); // Student's last names
        List itemNames = new ArrayList(); //Student's names
        List itemFaths = new ArrayList(); //Student's fath Names
        Log.d("MyTag", "Пробую читать");

        while (cursor.moveToNext()) {
            // Получения ID
            long itemId = cursor.getLong(
                    cursor.getColumnIndexOrThrow(BaseColumns._ID));
            // Получение ФИО студента
            String itemLastName = cursor.getString(
                    cursor.getColumnIndexOrThrow(FeedEntry.COLUMN_NAME_LASTNAME));

            String itemName = cursor.getString(
                    cursor.getColumnIndexOrThrow(FeedEntry.COLUMN_NAME_NAME));

            String itemFath = cursor.getString(
                    cursor.getColumnIndexOrThrow(FeedEntry.COLUMN_NAME_FATHNAME));




            // Получение времени добавления
            String itemDate = cursor.getString(
                    cursor.getColumnIndexOrThrow(FeedEntry.COLUMN_NAME_TIME));

            itemIDs.add(itemId);
            itemLastNames.add(itemLastName);
            itemNames.add(itemName);
            itemFaths.add(itemFath);
            itemDates.add(itemDate);

        }

        cursor.close();

        mAdapter = new MyAdapter(itemIDs, itemLastNames, itemNames, itemFaths, itemDates);
        recyclerView.setAdapter(mAdapter);
    }

}
