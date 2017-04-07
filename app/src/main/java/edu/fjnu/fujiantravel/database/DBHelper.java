package edu.fjnu.fujiantravel.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import edu.fjnu.fujiantravel.R;
import edu.fjnu.fujiantravel.SharedPreferences.InformationStorage;
import edu.fjnu.fujiantravel.city.City;
import edu.fjnu.fujiantravel.city.CityThread;

/**
 * Created by ZhouShiqiao on 2017/4/6 0006.
 */

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "travel.db";
    private static final int DATABASE_VERSION = 1;

    private Context context;

    private static DBHelper instance;

    public static int flag =0;

    private DBHelper(Context context) {
        //CursorFactory设置为null,使用默认值
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }
    public static void setInstance(Context context){
        instance=new DBHelper(context);
    }
    public static DBHelper getInstance(){
        return instance;
    }

    //数据库第一次被创建时onCreate会被调用
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table city(\n" +
                "no varchar(10) primary key,\n" +
                "name varchar(20),\n" +
                "parent varchar(10)\n" +
                ")");
        db.execSQL("create table scenic(\n" +
                "id varchar(20) primary key,\n" +
                "name varchar(50)\n" +
                ")");
        InformationStorage.getInstance().databaseinfo(context);
    }

    //如果DATABASE_VERSION值被改为2,系统发现现有数据库版本不同,即会调用onUpgrade
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("ALTER TABLE person ADD COLUMN other STRING");
    }
}
