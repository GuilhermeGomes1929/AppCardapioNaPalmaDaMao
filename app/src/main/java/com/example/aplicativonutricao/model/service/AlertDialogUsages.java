package com.example.aplicativonutricao.model.service;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

public class AlertDialogUsages {

    private AlertDialog.Builder msgBox;

    public AlertDialogUsages(Context context){
        msgBox = new AlertDialog.Builder(context);
    }

    public void emptyField(){
        msgBox.setTitle("Ops... Algum campo se encontra vazio.");
        msgBox.setMessage("Preencha todos os campos para continuar.");
        msgBox.show();
    }

    public void emptyMenu(){
        msgBox.setTitle("Não existem cardápios no celular.");
        msgBox.setMessage("Peça para o seu nutricionista enviar um cardápio válido ou atualize a página.");
        msgBox.show();
    }

    public void chooseMenu(final Spinner spinner, DialogInterface.OnClickListener onClickListener){

        msgBox.setTitle("Escolha o cardápio:");
        msgBox.setView(spinner);
        msgBox.setPositiveButton("Escolher", onClickListener);
        msgBox.show();
    }

    public void showSubs(View view){
        msgBox.setView(view);
        msgBox.show();
    }

    public void getWaterQuantity(EditText editText,  DialogInterface.OnClickListener onClickListener){
        msgBox.setTitle("Quantos mL de água você bebe por dia?");
        msgBox.setView(editText);
        msgBox.setPositiveButton("Confirmar", onClickListener);
        msgBox.show();
    }

    public void emptyDatabase(){
        msgBox.setTitle("Ops... O banco de dados se encontra vazio!");
        msgBox.setMessage("Não foi possível encontrar suas informações no banco de dados. Por favor, adicone-as e tente novamente!");
        msgBox.show();
    }
}
