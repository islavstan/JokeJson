package com.islavdroid.jokejson.database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import com.islavdroid.jokejson.Content;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION =1;
    public static final String DATABASE_NAME = "joke_database";
    public static final String TABLE ="joke_table";
    public static final String TEXT_COLUMN ="text";
    public static final String CHK_VALUES = "checkbox_value";

    public static final String TABLE_CREATE_SCRIPT ="CREATE TABLE "+TABLE + " ("+ BaseColumns._ID+
            " INTEGER PRIMARY KEY AUTOINCREMENT, "+ TEXT_COLUMN+ " TEXT NOT NULL, "+CHK_VALUES+" NUMERIC );";

    public DBHelper(Context context) {
        super(context,DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //создаём таблицу
        db.execSQL(TABLE_CREATE_SCRIPT);
    }


    public void saveContent(Content content) {
        ContentValues newValues = new ContentValues();
        newValues.put(TEXT_COLUMN,content.getText());
        newValues.put(CHK_VALUES,content.isSelected());
        getWritableDatabase().insert(TABLE,null,newValues);
    }

        public List<Content> getContentFromDB(){
        List<Content> contentList = new ArrayList<Content>();
        String query = "select * from "+TABLE;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        if (cursor.moveToFirst()){
            do {
                Content content = new Content();
                content.setText(cursor.getString(1));

                //Получить значение столбца CHK_VALUES. Оно хранится
                // в базе данных в числовом виде:1 — да, 0 — нет
                boolean selected = (cursor.getInt(2) == 1);
                content.setSelected(selected);
                contentList.add(content);}
            while (cursor.moveToNext());}
            return  contentList;
    }





    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
      //удаляем таблицу
        db.execSQL("DROP TABLE "+TABLE);
        //пересоздаём
        onCreate(db);
    }

    public void delete(String text){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE, "TEXT" + " = ?", new String[]{text});
        db.close();
    }
}
