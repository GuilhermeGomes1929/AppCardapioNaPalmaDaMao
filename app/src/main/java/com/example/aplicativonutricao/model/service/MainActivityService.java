package com.example.aplicativonutricao.model.service;

import android.content.ContentValues;
import android.content.Context;

import com.example.aplicativonutricao.model.dao.InfoDAO;
import com.example.aplicativonutricao.model.entity.FirstDataModel;
import com.example.aplicativonutricao.model.entity.PersonModel;

import java.util.ArrayList;

public class MainActivityService {

    private Context mainActivityContext;
    private InfoDAO infoDAO;

    public MainActivityService(Context context){
        this.mainActivityContext = context;
        infoDAO = new InfoDAO(mainActivityContext);
    }

    public void saveFirstData(FirstDataModel firstDataModel){

        ContentValues values = new ContentValues();

        values.put("nome", firstDataModel.getName());
        values.put("idade", firstDataModel.getAge());
        values.put("sexo", firstDataModel.getSex());
        values.put("altura", firstDataModel.getHeight());

        infoDAO.insertValues("info", values);

    }

    public boolean firstDataIxists(){
        try{

            PersonModel firstData = infoDAO.obterNomeIdadeAltura();
            if (firstData.getName() != null){
                return true;
            }else{
                return false;
            }

        }catch (Exception e){
            return true;
        }

    }
}
