package com.example.aplicativonutricao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Conexao extends SQLiteOpenHelper {

    private static final String name = "banco.db";
    private static final int version = 4;

    public Conexao(Context context){
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE breakfast(id integer primary key autoincrement, "+
                "quantidade int, alimento varchar(50), grupo int, adress int, peso varchar(50))");

        db.execSQL("CREATE TABLE snack(id integer primary key autoincrement, "+
                "quantidade int, alimento varchar(50), grupo int, adress int, peso varchar(50))");

        db.execSQL("CREATE TABLE lunch(id integer primary key autoincrement, "+
                "quantidade int, alimento varchar(50), grupo int, adress int, peso varchar(50))");

        db.execSQL("CREATE TABLE aftersnack(id integer primary key autoincrement, "+
                "quantidade int, alimento varchar(50), grupo int, adress int, peso varchar(50))");

        db.execSQL("CREATE TABLE dinner(id integer primary key autoincrement, "+
                "quantidade int, alimento varchar(50), grupo int, adress int, peso varchar(50))");

        db.execSQL("CREATE TABLE supper(id integer primary key autoincrement, "+
                "quantidade int, alimento varchar(50), grupo int, adress int, peso varchar(50))");

        db.execSQL("CREATE TABLE body(id integer primary key autoincrement, "+
                "data text, peso real,meta real, bf real,ombro int, bracod int, bracoe int," +
                " peitoral int, cintura int, quadril int, pernad int, pernae int, panturrilhad int, panturrilhae int)");

        db.execSQL("CREATE TABLE info(id integer primary key autoincrement, "+
                "nome varchar(50), idade varchar(50), sexo varchar(50), altura real)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table breakfast;");
        db.execSQL("drop table snack;");
        db.execSQL("drop table lunch;");
        db.execSQL("drop table aftersnack;");
        db.execSQL("drop table dinner;");
        db.execSQL("drop table supper;");
        db.execSQL("drop table body;");
        db.execSQL("drop table info;");
        onCreate(db);


    }
}
