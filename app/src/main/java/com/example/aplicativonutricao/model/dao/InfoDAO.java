package com.example.aplicativonutricao.model.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.aplicativonutricao.model.entity.BodyModel;
import com.example.aplicativonutricao.model.entity.PersonModel;

import java.util.ArrayList;


public class InfoDAO {
    private Conexao conexao;
    private SQLiteDatabase banco;

    public InfoDAO(Context context){
        conexao = new Conexao(context);

    }



    public long insertValues(String table, ContentValues values){
        banco = conexao.getWritableDatabase();
        long valuesReturn = banco.insert(table, null, values);
        banco.close();
        return valuesReturn;

    }



    public void delete(String table, int position){
        banco = conexao.getWritableDatabase();
        Cursor cursor = banco.query(table,new String[]{"id"},null, null, null, null, null);
        cursor.moveToPosition(position);
        try {
            banco.delete(table, "id = ?", new String[]{String.valueOf(cursor.getInt(0))} );
            banco.close();
        }catch (Exception e){
            banco.close();
        }


    }



    public ArrayList<String> obterData(){
        banco = conexao.getWritableDatabase();
        ArrayList<String> infos = new ArrayList<>();
        Cursor cursor = banco.query("body", new String[]{"data"}, null, null, null, null, null);
        while (cursor.moveToNext()){
            infos.add(cursor.getString(0));
        }
        banco.close();
        return infos;
    }

    public BodyModel obterInfosData(String table, int position){
        banco = conexao.getWritableDatabase();
        BodyModel bodyModel = new BodyModel();
        Cursor cursor = banco.query(table, new String[]{"data","peso","meta","bf","ombro","bracod","bracoe","peitoral",
                        "cintura","quadril", "pernad", "pernae", "panturrilhad", "panturrilhae"},
                null,null,null,null,null);
        cursor.moveToPosition(position);
        bodyModel.setData(cursor.getString(0));
        bodyModel.setWeight(cursor.getFloat(1));
        bodyModel.setGoal(cursor.getFloat(2));
        bodyModel.setBodyFat(cursor.getFloat(3));
        bodyModel.setShoulders(cursor.getInt(4));
        bodyModel.setRightArm(cursor.getInt(5));
        bodyModel.setLeftArm(cursor.getInt(6));
        bodyModel.setChest(cursor.getInt(7));
        bodyModel.setWaist(cursor.getInt(8));
        bodyModel.setHip(cursor.getInt(9));
        bodyModel.setRightLeg(cursor.getInt(10));
        bodyModel.setLeftLeg(cursor.getInt(11));
        bodyModel.setRightCalf(cursor.getInt(12));
        bodyModel.setLeftCalf(cursor.getInt(13));

        return bodyModel;
    }

    public ArrayList<String> obterPesoGrafico(){
        banco = conexao.getWritableDatabase();
        ArrayList<String> infos = new ArrayList<>();
        Cursor cursor = banco.query("body",new String[]{"peso"}, null, null, null, null,null);
        while (cursor.moveToNext()){
            infos.add(String.valueOf(cursor.getFloat(0)));
        }
        return infos;
    }

    public ArrayList<String> obterBfGrafico(){
        banco = conexao.getWritableDatabase();
        ArrayList<String> infos = new ArrayList<>();
        Cursor cursor = banco.query("body",new String[]{"bf"}, null, null, null, null,null);
        while (cursor.moveToNext()){
            infos.add(String.valueOf(cursor.getFloat(0)));
        }
        return infos;
    }

    public ArrayList<String> obterOmbrosGrafico(){
        banco = conexao.getWritableDatabase();
        ArrayList<String> infos = new ArrayList<>();
        Cursor cursor = banco.query("body",new String[]{"ombro"}, null, null, null, null,null);
        while (cursor.moveToNext()){
            infos.add(String.valueOf(cursor.getInt(0)));
        }
        return infos;
    }

    public ArrayList<String> obterPeitoralGrafico(){
        banco = conexao.getWritableDatabase();
        ArrayList<String> infos = new ArrayList<>();
        Cursor cursor = banco.query("body",new String[]{"peitoral"}, null, null, null, null,null);
        while (cursor.moveToNext()){
            infos.add(String.valueOf(cursor.getInt(0)));
        }
        return infos;
    }

    public ArrayList<String> obterbracosGrafico(){
        banco = conexao.getWritableDatabase();
        ArrayList<String> infos = new ArrayList<>();
        Cursor cursor = banco.query("body",new String[]{"bracod","bracoe"}, null, null, null, null,null);
        while (cursor.moveToNext()){
            infos.add(String.valueOf((cursor.getInt(0) + cursor.getInt(1)/2)));
        }
        return infos;
    }

    public ArrayList<String> obterCinturaGrafico(){
        banco = conexao.getWritableDatabase();
        ArrayList<String> infos = new ArrayList<>();
        Cursor cursor = banco.query("body",new String[]{"cintura"}, null, null, null, null,null);
        while (cursor.moveToNext()){
            infos.add(String.valueOf(cursor.getInt(0)));
        }
        return infos;
    }

    public ArrayList<String> obterQuadrilGrafico(){
        banco = conexao.getWritableDatabase();
        ArrayList<String> infos = new ArrayList<>();
        Cursor cursor = banco.query("body",new String[]{"quadril"}, null, null, null, null,null);
        while (cursor.moveToNext()){
            infos.add(String.valueOf(cursor.getInt(0)));
        }
        return infos;
    }

    public ArrayList<String> obterPernasGrafico(){
        banco = conexao.getWritableDatabase();
        ArrayList<String> infos = new ArrayList<>();
        Cursor cursor = banco.query("body",new String[]{"pernad", "pernae"}, null, null, null, null,null);
        while (cursor.moveToNext()){
            infos.add(String.valueOf((cursor.getInt(0) + cursor.getInt(1)/2)));
        }
        return infos;
    }
    public ArrayList<String> obterPanturrilhasGrafico(){
        banco = conexao.getWritableDatabase();
        ArrayList<String> infos = new ArrayList<>();
        Cursor cursor = banco.query("body",new String[]{"panturrilhad", "panturrilhae"}, null, null, null, null,null);
        while (cursor.moveToNext()){
            infos.add(String.valueOf((cursor.getInt(0) + cursor.getInt(1)/2)));
        }
        return infos;
    }


    public PersonModel obterNomeIdadeAltura(){
        banco = conexao.getWritableDatabase();
        PersonModel personModel = new PersonModel();
        Cursor cursor = banco.query("info", new String[]{"nome","idade","sexo","altura"},null,null,null,null,null);
        if (cursor.moveToLast()){
            personModel.setName(cursor.getString(0));
            personModel.setAge(Integer.parseInt(cursor.getString(1)));
            personModel.setSex(cursor.getString(2));
            personModel.setHeight(cursor.getFloat(3));
        }

        banco.close();
        return personModel;
    }

    public int obterQuantidadeDeAgua(){
        banco = conexao.getWritableDatabase();
        int quantidade = 0;
        Cursor cursor = banco.query("water", new String[]{"quantity"}, null, null,null,null,null);
        if (cursor.moveToLast()){
            quantidade = cursor.getInt(0);
        }

        banco.close();
        return quantidade;
    }

    public Integer obterAlarmIdNaPosicao(int position){
        banco = conexao.getWritableDatabase();
        Cursor cursor = banco.query("alarms",new String[]{"id"},null,null,null,null,null);

        if (cursor.moveToPosition(position)){
            int id = cursor.getInt(0);
            banco.close();
            return id;

        }else{
            return null;
        }
    }

    public Integer obterUltimoAlarmeId(){
        banco = conexao.getWritableDatabase();
        Cursor cursor = banco.query("alarms", new String[]{"id"}, null, null, null, null, null);

        if (cursor.moveToLast()){

            int id = cursor.getInt(0);
            banco.close();
            return id;
        }else{
            return null;
        }
    }

    public ArrayList<Long> obterTodosAlarmes(){
        banco = conexao.getWritableDatabase();
        ArrayList<Long> alarmes = new ArrayList<>();
        Cursor cursor = banco.query("alarms", new String[]{"time"}, null, null, null, null, null);
        while (cursor.moveToNext()){
            alarmes.add(cursor.getLong(0));
        }

        return alarmes;
    }

    public Integer obterLitros(){
        banco = conexao.getWritableDatabase();
        Cursor cursor = banco.query("litros", new String[]{"fullquantity"}, null, null, null, null, null);
        if (cursor.moveToLast()){
            int litros = cursor.getInt(0);

            return litros;
        }else {
            return null;
        }

    }

}
