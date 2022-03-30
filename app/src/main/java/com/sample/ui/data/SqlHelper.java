package com.sample.ui.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SqlHelper extends SQLiteOpenHelper {
    public static String DB_NAME = "BMI.db";                        //db檔案名稱
    public static int DB_VERSION = 1;                               //控制db版本如果table schema 變動時要+1且要去對onUpgrade做調整

    public SqlHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    //每次打開資料庫時最新執行此function
    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }

    //建立資料表
    @Override
    public void onCreate(SQLiteDatabase db) {
        SqlTable_BMI.CreateTable(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        SqlTable_BMI.onUpgrade(db, oldVersion, newVersion);
    }

    public void closeDB()    //關閉DB
    {
        getReadableDatabase().close();
    }
}
