package com.example.aplicativonutricao.model.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Conexao extends SQLiteOpenHelper {

    private static final String name = "banco.db";
    private static final int version = 5;

    public Conexao(Context context){
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE IF NOT EXISTS body(id integer primary key autoincrement, "+
                "data text, peso real,meta real, bf real,ombro int, bracod int, bracoe int," +
                " peitoral int, cintura int, quadril int, pernad int, pernae int, panturrilhad int, panturrilhae int)");

        db.execSQL("CREATE TABLE IF NOT EXISTS info(id integer primary key autoincrement, "+
                "nome varchar(50), idade varchar(50), sexo varchar(50), altura real)");

        db.execSQL("CREATE TABLE IF NOT EXISTS water(id integer primary key autoincrement, " +
                "quantity int)");

        db.execSQL("CREATE TABLE IF NOT EXISTS litros(id integer primary key autoincrement, " +
                "fullquantity int)");

        db.execSQL("CREATE TABLE IF NOT EXISTS alarms(id integer primary key autoincrement, " +
                "time INTEGER)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        try{
            db.execSQL("drop table body;");
            db.execSQL("drop table info;");
            db.execSQL("drop table water;");
            db.execSQL("drop table litros;");
            db.execSQL("drop table alarms;");
            onCreate(db);
        }catch (Exception e){
            onCreate(db);
        }
    }
}
