package edu.fjnu.fujiantravel.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import edu.fjnu.fujiantravel.city.City;

/**
 * Created by ZhouShiqiao on 2017/4/6 0006.
 */

public class DBManager {
    private  final static DBManager instance = new DBManager();

    private DBHelper helper;
    private SQLiteDatabase db;

    private DBManager() {
        helper = DBHelper.getInstance();
        db = helper.getWritableDatabase();
    }

    public static DBManager getInstance(){
        return instance;
    }

    public void initcity(List<City> citys) {
        for (int i = 0; i < citys.size(); i++) {
            db.execSQL("INSERT INTO city VALUES('" + citys.get(i).getCid() + "','" + citys.get(i).getName() + "','" + citys.get(i).getParent() + "')");
        }
    }

    public List<City> getcitylist() {
        List<City> list = new ArrayList<City>();
        Cursor c = db.rawQuery("SELECT * FROM city", null);
        while (c.moveToNext()) {
            City city = new City();
            city.setCid(c.getString(c.getColumnIndex("no")));
            city.setName(c.getString(c.getColumnIndex("name")));
            city.setParent(c.getString(c.getColumnIndex("parent")));
            list.add(city);
        }
        c.close();
        return list;
    }
}
