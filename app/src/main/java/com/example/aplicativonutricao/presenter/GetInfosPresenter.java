package com.example.aplicativonutricao.presenter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aplicativonutricao.R;
import com.example.aplicativonutricao.model.entity.BodyModel;
import com.example.aplicativonutricao.model.entity.PersonModel;
import com.example.aplicativonutricao.model.service.AlertDialogUsages;
import com.example.aplicativonutricao.model.service.GetInfosService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class GetInfosPresenter {

    private Context getInfosContext;
    private Activity getInfosActivity;
    private GetInfosService service;

    public GetInfosPresenter(Activity getInfosActivity){
        this.getInfosContext = getInfosActivity;
        this.getInfosActivity = getInfosActivity;
        service = new GetInfosService(this.getInfosContext);
    }

    public void setupInfos(){
        TextView nome = getInfosActivity.findViewById(R.id.nome_perfil);
        TextView idade = getInfosActivity.findViewById(R.id.altura_idade_perfil);

        PersonModel personModel = service.getPersonModel();
        if (personModel.getName() == null){
            nome.setText("Nome não identificado");
            idade.setText("Idade e altura não identificadas");
        }else{
            nome.setText(personModel.getName());
            idade.setText(personModel.getHeight()+"m /"+personModel.getAge()+" anos");
        }
    }

    public void editPersonInfos(){
        final AlertDialogUsages usages = new AlertDialogUsages(getInfosContext);
        final PersonModel personModel = new PersonModel();
        final View view = getInfosActivity.getLayoutInflater().inflate(R.layout.dialog_edit_texts, null);
        final EditText name = view.findViewById(R.id.nome_dialog);
        final EditText age = view.findViewById(R.id.idade_dialog);
        final EditText height = view.findViewById(R.id.altura_dialog);
        final Spinner sex = view.findViewById(R.id.spinner_sexo);
        Button cancel = view.findViewById(R.id.cancel_btn);
        Button confirm = view.findViewById(R.id.confirm_btn);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getInfosContext,android.R.layout.simple_spinner_item, new String[]{"Masculino", "Feminino"});
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sex.setAdapter(adapter);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{

                    personModel.setName(name.getText().toString());
                    personModel.setAge(Integer.parseInt(age.getText().toString()));
                    personModel.setHeight(Float.valueOf(height.getText().toString()));
                    personModel.setSex(sex.getSelectedItem().toString());
                    service.savePersonModel(personModel);

                    TextView nome = getInfosActivity.findViewById(R.id.nome_perfil);
                    TextView idadeAltura = getInfosActivity.findViewById(R.id.altura_idade_perfil);
                    nome.setText(personModel.getName());
                    idadeAltura.setText(personModel.getHeight()+"m /"+personModel.getAge()+" anos");
                    usages.alertDialogDismiss();

                }catch (Exception e){
                    Toast.makeText(getInfosContext, "Preencha todos os campos", Toast.LENGTH_LONG);
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usages.alertDialogDismiss();
                Toast.makeText(getInfosContext, "Nenhuma alteração foi feita.", Toast.LENGTH_LONG).show();
            }
        });

        usages.updatePersonInfos(view);
    }

    public void editBodyInfos() {
        AlertDialogUsages usages = new AlertDialogUsages(getInfosContext);
        final Spinner spinner = new Spinner(getInfosContext);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getInfosContext, android.R.layout.simple_spinner_item, service.getAllBodyInfoDatas());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        View.OnClickListener[] listeners = new View.OnClickListener[]{new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (spinner.getCount() > 0) {
                    service.deleteBodyInfoFromData(spinner.getSelectedItemPosition());
                } else {
                    Toast.makeText(getInfosContext, "Não há informações para deletar.", Toast.LENGTH_LONG).show();
                }
            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getInfosContext, "Nenhuma informação foi deletada", Toast.LENGTH_LONG).show();
            }
        }};
        usages.updateBodyInfos(spinner, (DialogInterface.OnClickListener[]) listeners);

    }

    public void saveBodyInfo(){

        BodyModel bodyModel = new BodyModel();
        Date date = new Date();
        EditText currentWeight = getInfosActivity.findViewById(R.id.peso);
        EditText goal = getInfosActivity.findViewById(R.id.meta);
        EditText bodyFat = getInfosActivity.findViewById(R.id.bf);
        EditText shoulders = getInfosActivity.findViewById(R.id.ombros);
        EditText chest = getInfosActivity.findViewById(R.id.peitoral);
        EditText leftArm = getInfosActivity.findViewById(R.id.bracoE);
        EditText rightArm = getInfosActivity.findViewById(R.id.bracoD);
        EditText waist = getInfosActivity.findViewById(R.id.cintura);
        EditText hip = getInfosActivity.findViewById(R.id.quadril);
        EditText leftLeg = getInfosActivity.findViewById(R.id.pernaE);
        EditText rightLeg = getInfosActivity.findViewById(R.id.pernaD);
        EditText leftCalf = getInfosActivity.findViewById(R.id.panturrilhaE);
        EditText rightCalf = getInfosActivity.findViewById(R.id.panturrilhaD);

        bodyModel.setData(new SimpleDateFormat("dd-MM").format(date));
        bodyModel.setWeight(Float.valueOf(currentWeight.getText().toString()));
        bodyModel.setGoal(Float.valueOf(goal.getText().toString()));
        bodyModel.setBodyFat(Float.valueOf(bodyFat.getText().toString()));
        bodyModel.setShoulders(Integer.parseInt(shoulders.getText().toString()));
        bodyModel.setChest(Integer.parseInt(chest.getText().toString()));
        bodyModel.setLeftArm(Integer.parseInt(leftArm.getText().toString()));
        bodyModel.setRightArm(Integer.parseInt(rightArm.getText().toString()));
        bodyModel.setWaist(Integer.parseInt(waist.getText().toString()));
        bodyModel.setHip(Integer.parseInt(hip.getText().toString()));
        bodyModel.setLeftLeg(Integer.parseInt(leftLeg.getText().toString()));
        bodyModel.setRightLeg(Integer.parseInt(rightLeg.getText().toString()));
        bodyModel.setLeftCalf(Integer.parseInt(leftCalf.getText().toString()));
        bodyModel.setRightCalf(Integer.parseInt(rightCalf.getText().toString()));

        try{
            service.saveBodyInfo(bodyModel);
            Toast.makeText(getInfosContext, "Informações salvas com sucesso", Toast.LENGTH_LONG).show();
        }catch (Exception e){
            AlertDialogUsages usages = new AlertDialogUsages(getInfosContext);
            usages.emptyField();
        }

    }
}
