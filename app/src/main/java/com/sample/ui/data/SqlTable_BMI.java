package com.sample.ui.data;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sample.ui.data.model.BMIData;

import java.util.ArrayList;
import java.util.List;

public class SqlTable_BMI {

    public final static String DATE_FORMAT = "yyyy-MM-dd hh:mm:ss";

    private final static String TABLE_NAME = "BMI";
    private final static String ITEM_ID = "_id";                       //流水號
    private final static String ITEM_NAME = "name";
    private final static String ITEM_HEIGHT = "height";
    private final static String ITEM_WEIGHT = "weight";
    private final static String ITEM_BMI = "bmi";
    private final static String ITEM_DATE = "date";
    private SQLiteDatabase m_DB = null;                        //db

    public SqlTable_BMI(SqlHelper sqlHelper) {
        m_DB = sqlHelper.getReadableDatabase();
    }

    //給sqlhelper呼叫用,其他物件不要呼叫
    public static synchronized void CreateTable(SQLiteDatabase db) {
        try {
            StringBuilder sql1 = new StringBuilder();
            sql1.append("CREATE TABLE " + TABLE_NAME + " ( " +
                    ITEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    ITEM_NAME + " TEXT," +
                    ITEM_HEIGHT + " TEXT," +
                    ITEM_WEIGHT + " TEXT," +
                    ITEM_BMI + " TEXT," +
                    ITEM_DATE + " TEXT);"
            );
            db.execSQL(sql1.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //給sqlhelper呼叫用,其他物件不要呼叫
    public static synchronized void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            String sql1 = String.format("DROP TABLE IF EXISTS %s", TABLE_NAME);
            db.execSQL(sql1);
            CreateTable(db);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void insert(BMIData data) {
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put(ITEM_NAME, data.name);
            contentValues.put(ITEM_HEIGHT, data.height);
            contentValues.put(ITEM_WEIGHT, data.weight);
            contentValues.put(ITEM_BMI, data.bmi);
            contentValues.put(ITEM_DATE, data.date);

            m_DB.insert(TABLE_NAME, null, contentValues);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateById(BMIData data) {

        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put(ITEM_NAME, data.name);
            contentValues.put(ITEM_HEIGHT, data.height);
            contentValues.put(ITEM_WEIGHT, data.weight);
            contentValues.put(ITEM_BMI, data.bmi);
            contentValues.put(ITEM_DATE, data.date);

            m_DB.update(TABLE_NAME, contentValues, ITEM_ID + "=" + data.id, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("Range")
    public List<BMIData> getListData() {

        Cursor cursor = null;
        cursor = m_DB.rawQuery("SELECT * FROM " + TABLE_NAME + " ORDER BY " + ITEM_ID + " DESC", null);

        List<BMIData> bmiList = new ArrayList<BMIData>();
        if (cursor == null || cursor.getCount() == 0) {
        } else if (cursor.moveToFirst()) {
            BMIData data = null;
            do {
                data = new BMIData();
                data.id = cursor.getString(cursor.getColumnIndex(ITEM_ID));
                data.name = cursor.getString(cursor.getColumnIndex(ITEM_NAME));
                data.height = cursor.getString(cursor.getColumnIndex(ITEM_HEIGHT));
                data.weight = cursor.getString(cursor.getColumnIndex(ITEM_WEIGHT));
                data.bmi = cursor.getString(cursor.getColumnIndex(ITEM_BMI));
                data.date = cursor.getString(cursor.getColumnIndex(ITEM_DATE));

                bmiList.add(data);
            }
            while (cursor.moveToNext());
        }
        return bmiList;
    }

    /**
     * 刪除資料
     */
    public void deleteById(String id) {
        try {
            m_DB.delete(TABLE_NAME, ITEM_ID + "=" + id, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void closeDb() {
        if (m_DB != null) {
            try {
                m_DB.close();
                m_DB = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
