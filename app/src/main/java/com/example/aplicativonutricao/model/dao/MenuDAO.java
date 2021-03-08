package com.example.aplicativonutricao.model.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import com.example.aplicativonutricao.model.entity.MenuModel;

import java.util.ArrayList;

public class MenuDAO {

    private SQLiteDatabase menuDb;
    private String db;

    public MenuDAO(String db){
        this.db = db;

    }
    public void getConnection(){
        menuDb = SQLiteDatabase.openDatabase(Environment.getExternalStorageDirectory().getAbsolutePath() +"/WhatsApp/Media/WhatsApp Documents/" +db, null, SQLiteDatabase.OPEN_READONLY);
    }

    public ArrayList<MenuModel> getData(String table){
        getConnection();
        ArrayList<MenuModel> data = new ArrayList<>();
        Cursor cursor = menuDb.query(table, new String[]{"id","foodgroup","food","portion","subs"}, null, null, null, null, null);
        while (cursor.moveToNext()){
            MenuModel menuModel = new MenuModel(
                    cursor.getInt(0),
                    cursor.getInt(1),
                    cursor.getInt(2),
                    cursor.getDouble(3),
                    cursor.getInt(4)
            );
            data.add(menuModel);

        }

        menuDb.close();
        return data;
    }

}
