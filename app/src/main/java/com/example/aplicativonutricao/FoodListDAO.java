package com.example.aplicativonutricao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import java.util.ArrayList;

public class FoodListDAO {

    private SQLiteDatabase foodDb;



    public void getConnection(){

        foodDb = SQLiteDatabase.openDatabase(Environment.getExternalStorageDirectory().getAbsolutePath() +"/WhatsApp/Media/WhatsApp Documents/foodlist.db",
                null, SQLiteDatabase.OPEN_READONLY);
    }

    public boolean checkConnection(){
        try{
            getConnection();
            foodDb.close();
            return true;
        }catch (Exception e){
            return false;
        }

    }

    public ArrayList<String> getAllTables(){
        getConnection();
        ArrayList<String> tables = new ArrayList<>();
        Cursor cursor = foodDb.rawQuery("SELECT name FROM sqlite_master WHERE type='table' AND name NOT LIKE 'sqlite_%' ORDER BY name ASC", null);
        while (cursor.moveToNext()){
            tables.add(cursor.getString(cursor.getColumnIndex("name")));
        }

        foodDb.close();
        return tables;
    }

    public ArrayList<String> getFoodName(String table, ArrayList<Integer> id){
        ArrayList<String> foodNames = new ArrayList<>();
        Cursor cursor = foodDb.query(table, new String[]{"foodname"}, null, null, null, null, null);
        for (int i = 0; i < id.size(); i++){
            cursor.moveToPosition(id.get(i));
            foodNames.add(cursor.getString(0));

        }

        return foodNames;
    }

    public ArrayList<Integer> getFoodWeight(String table, ArrayList<Integer> id){
        ArrayList<Integer> weight = new ArrayList<>();
        Cursor cursor = foodDb.query(table, new String[]{"weight"}, null, null, null, null, null);

        for (int i = 0; i < id.size(); i++){
            cursor.moveToPosition(id.get(i));
            weight.add(cursor.getInt(0));

        }
        return weight;
    }

    public ArrayList<String> getHomeMeasures(String table, ArrayList<Integer> id){
        ArrayList<String> homeMeasure = new ArrayList<>();
        Cursor cursor = foodDb.query(table, new String[]{"homemeasure"}, null, null, null, null, null);

        for (int i = 0; i < id.size(); i++){
            cursor.moveToPosition(id.get(i));
            homeMeasure.add(cursor.getString(0));
        }
        return homeMeasure;
    }

    public FoodListModel getItemMenu(String table, int index){
        getConnection();

        Cursor cursor = foodDb.query(table, new String[]{"id","foodname","weight","homemeasure","calories","homeportions"}, null, null, null, null, null);

        cursor.moveToPosition(index);
        FoodListModel foodListModel = new FoodListModel(cursor.getInt(0),
                table,
                cursor.getString(1),
                cursor.getInt(2),
                cursor.getString(3),
                cursor.getInt(4),
                cursor.getDouble(5));



        foodDb.close();
        return foodListModel;
    }


}
