package ru.solandme.remindmeabout;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;

public class HolidayDBHelper extends SQLiteAssetHelper {

    private static final String DATABASE_NAME = "holidays.db";
    private static final int DATABASE_VERSION = 1;


    public static final String TABLE = "holidays";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_IMAGE_URI = "imageUri";
    public static final String COLUMN_CATEGORY = "category";
    public static final String COLUMN_DATA = "date";
    public static final String COLUMN_CODE = "code";


    public HolidayDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public List<Holiday> getHolidaysByCategory(String category) {
        List<Holiday> holidays = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();

        Cursor cursor = db.rawQuery("select * from " + HolidayDBHelper.TABLE + " where " +
                HolidayDBHelper.COLUMN_CATEGORY + "=? ", new String[]{category});

        while (cursor.moveToNext()) {
            Holiday holiday = new Holiday();
            holiday.setId(cursor.getString(0));
            holiday.setName(cursor.getString(1));
            holiday.setDescription(cursor.getString(2));
            holiday.setImageUri(cursor.getString(3));
            holiday.setCategory(cursor.getString(4));
            holiday.setDate(cursor.getLong(5));
            holiday.setCode(cursor.getString(6));
            holidays.add(holiday);
        }
        cursor.close();
        db.close();
        return holidays;
    }

    public void addHolidayToDB(Holiday holiday) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(HolidayDBHelper.COLUMN_NAME, holiday.getName());
        cv.put(HolidayDBHelper.COLUMN_DESCRIPTION, holiday.getDescription());
        cv.put(HolidayDBHelper.COLUMN_IMAGE_URI, holiday.getImageUri());
        cv.put(HolidayDBHelper.COLUMN_CATEGORY, holiday.getCategory());
        cv.put(HolidayDBHelper.COLUMN_DATA, holiday.getDate());
        cv.put(HolidayDBHelper.COLUMN_CODE, holiday.getCode());

        db.insert(TABLE, null, cv);
        db.close();
        Log.e("DB_add", cv.toString());
    }

    public void replaceHolidayOnDB(Holiday holiday) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(HolidayDBHelper.COLUMN_NAME, holiday.getName());
        cv.put(HolidayDBHelper.COLUMN_DESCRIPTION, holiday.getDescription());
        cv.put(HolidayDBHelper.COLUMN_IMAGE_URI, holiday.getImageUri());
        cv.put(HolidayDBHelper.COLUMN_CATEGORY, holiday.getCategory());
        cv.put(HolidayDBHelper.COLUMN_DATA, holiday.getDate());
        cv.put(HolidayDBHelper.COLUMN_CODE, holiday.getCode());

        db.update(TABLE, cv, COLUMN_ID + "=" + holiday.getId(), null);
        db.close();
        Log.e("DB_edit", holiday.getId() + "  " + cv.toString());
    }

    public boolean deleteHolidayFromDB(Holiday holiday) {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete(TABLE, COLUMN_ID + "=" + holiday.getId(), null) > 0;
    }

}