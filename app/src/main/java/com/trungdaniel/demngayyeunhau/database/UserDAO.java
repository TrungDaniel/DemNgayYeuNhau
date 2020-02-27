package com.trungdaniel.demngayyeunhau.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.trungdaniel.demngayyeunhau.model.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private static SQLiteDatabase db;
    static final String TABLE_NAME = "User";
    static final String SQL_USER = "CREATE TABLE User (uID text primary key, tenBan text, tenNguoiAy text, dateStart date);";
    private static final String TAG = "UserDAO";
    @SuppressLint("SimpleDateFormat")
    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public UserDAO(Context context) {
        DatabaseManager databasemanager = new DatabaseManager(context);
        db = databasemanager.getWritableDatabase();
    }

    public int inserUser(User user) {
        ContentValues values = new ContentValues();
        values.put("uID", user.getuID());
        values.put("tenBan", user.getTenBan());
        values.put("tenNguoiAy", user.getTenNguoiAy());
        values.put("dateStart", sdf.format(user.getDateStart()));
        try {
            if (db.insert(TABLE_NAME, null, values) == -1) {
                return -1;
            }
        } catch (Exception ex) {
            Log.e(TAG, ex.toString());
        }
        return 1;
    }

    public static List<User> getAllUser() {
        List<User> dsHoaDon = new ArrayList<>();
        Cursor c = db.query(TABLE_NAME, null, null, null, null, null, null);
        c.moveToFirst();
        while (!c.isAfterLast()) {
            User ee = new User();
            ee.setuID(Integer.parseInt(c.getString(0)));
            ee.setTenBan(c.getString(1));
            ee.setTenNguoiAy(c.getString(2));
            try {
                ee.setDateStart(sdf.parse(c.getString(3)));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            dsHoaDon.add(ee);
            Log.d("//=====", ee.toString());
            c.moveToNext();
        }
        c.close();
        return dsHoaDon;
    }
}
