package com.example.aplicativonutricao.model.service;

import android.content.ContentValues;
import android.content.Context;

import com.example.aplicativonutricao.model.dao.InfoDAO;
import com.example.aplicativonutricao.model.entity.BodyModel;
import com.example.aplicativonutricao.model.entity.PersonModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class GetInfosService {

    private Context getInfosContext;
    private InfoDAO infoDAO;

    public GetInfosService(Context getInfosContext) {
        this.getInfosContext = getInfosContext;
        infoDAO = new InfoDAO(this.getInfosContext);
    }

    public PersonModel getPersonModel(){
        return infoDAO.obterNomeIdadeAltura();
    }

    public void savePersonModel(PersonModel personModel){
        ContentValues values = new ContentValues();

        values.put("nome", personModel.getName());
        values.put("idade", personModel.getAge());
        values.put("sexo", personModel.getSex());
        values.put("altura",personModel.getHeight());
        infoDAO.delete("info", 0);
        infoDAO.insertValues("info", values);
    }

    public ArrayList<String> getAllBodyInfoDatas(){
        return infoDAO.obterData();
    }

    public void deleteBodyInfoFromData(int position){
        infoDAO.delete("body", position);
    }

    public void saveBodyInfo(BodyModel bodyModel){
        ContentValues values = new ContentValues();

        values.put("data", bodyModel.getData());
        values.put("peso", bodyModel.getWeight());
        values.put("meta", bodyModel.getGoal());
        values.put("bf", bodyModel.getBodyFat());
        values.put("ombro", bodyModel.getShoulders());
        values.put("bracod", bodyModel.getRightArm());
        values.put("bracoe", bodyModel.getLeftArm());
        values.put("peitoral", bodyModel.getChest());
        values.put("cintura", bodyModel.getWaist());
        values.put("quadril", bodyModel.getHip());
        values.put("pernad", bodyModel.getRightLeg());
        values.put("pernae", bodyModel.getLeftLeg());
        values.put("panturrilhad", bodyModel.getRightCalf());
        values.put("panturrilhae", bodyModel.getLeftCalf());
        infoDAO.insertValues("body", values);
    }
}
