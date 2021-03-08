package com.example.aplicativonutricao.model.service;

import android.content.Context;

import com.example.aplicativonutricao.model.dao.InfoDAO;
import com.example.aplicativonutricao.model.entity.BodyModel;

import java.util.ArrayList;

public class MyBodyService {

    private Context myBodyContext;
    private InfoDAO infoDAO;

    public MyBodyService(Context myBodyContext){
        this.myBodyContext = myBodyContext;
        infoDAO = new InfoDAO(myBodyContext);
    }

    public boolean isDataEmpty(){
        return infoDAO.obterData().isEmpty();
    }

    public ArrayList<String> getAllData(){
        return infoDAO.obterData();
    }

    public BodyModel getBodyModel(int position){
        BodyModel bodyModel = infoDAO.obterInfosData("body", position);
        bodyModel.setHight(Float.valueOf(infoDAO.obterNomeIdadeAltura().getHeight()));
        return bodyModel;
    }

    public String getSex(){
        return infoDAO.obterNomeIdadeAltura().getSex();
    }
}
