package com.example.aplicativonutricao.presenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.example.aplicativonutricao.model.entity.FirstDataModel;
import com.example.aplicativonutricao.model.service.AlertDialogUsages;
import com.example.aplicativonutricao.model.service.MainActivityService;

public class MainActivityPresenter {

    private Context mainActivityContext;
    private Activity mainActivity;
    private MainActivityService service;

    public MainActivityPresenter(Activity mainActivity){
        this.mainActivity = mainActivity;
        this.mainActivityContext = mainActivity;
        this.service = new MainActivityService(this.mainActivityContext);
    }

    public void getFirstData(String name, String age, String sex, String height){

        AlertDialogUsages error = new AlertDialogUsages(mainActivityContext);

        try{

            FirstDataModel firstDataModel = new FirstDataModel();
            firstDataModel.setName(name);
            firstDataModel.setAge(age);
            firstDataModel.setSex(sex);
            firstDataModel.setHeight(Float.valueOf(height));

            if (firstDataModel.verifyData()){
                service.saveFirstData(firstDataModel);
                mainActivityContext.startActivity(new Intent(mainActivityContext, com.example.aplicativonutricao.view.ShowMenu.class));
                mainActivity.finish();

            }
            else{
                error.emptyField();
            }

        }catch (Exception e){
            error.emptyField();
        }

    }

    public void verifyFirstData(){
        if (service.firstDataIxists()){
            mainActivityContext.startActivity(new Intent(mainActivityContext, com.example.aplicativonutricao.view.ShowMenu.class));
            mainActivity.finish();
        }
    }
}
