package com.example.aplicativonutricao.presenter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.aplicativonutricao.R;
import com.example.aplicativonutricao.model.entity.BodyModel;
import com.example.aplicativonutricao.model.service.AlertDialogUsages;
import com.example.aplicativonutricao.model.service.MyBodyService;

import java.text.DecimalFormat;

public class MyBodyPresenter {
    
    private Context myBodyContext;
    private Activity myBodyActivity;
    private Button nextDataBtn;
    private Button previousDataBtn;
    private MyBodyService service;
    private AlertDialogUsages usages;
    private TextView data;
    private TextView currentWeight;
    private TextView goalWeight;
    private TextView diferenceWeight;
    private TextView idealWeight;
    private TextView bodyFat;
    private TextView shoulders;
    private TextView chest;
    private TextView leftArm;
    private TextView rightArm;
    private TextView hip;
    private TextView waist;
    private TextView leftLeg;
    private TextView rightLeg;
    private TextView leftCalf;
    private TextView rightCalf;

    public MyBodyPresenter(Activity myBodyActivity){
        this.myBodyContext = myBodyActivity;
        this.myBodyActivity = myBodyActivity;
        this.nextDataBtn = this.myBodyActivity.findViewById(R.id.dataPosterior);
        this.previousDataBtn = this.myBodyActivity.findViewById(R.id.dataAnterior);
        this.nextDataBtn.setClickable(false);
        this.nextDataBtn.setBackgroundResource(R.drawable.ic_baseline_arrow_forward_ios_24_gray);
        this.service = new MyBodyService(myBodyContext);

        data = myBodyActivity.findViewById(R.id.data_myBody);
        currentWeight = myBodyActivity.findViewById(R.id.pesoAtual);
        goalWeight = myBodyActivity.findViewById(R.id.pesoMeta);
        diferenceWeight = myBodyActivity.findViewById(R.id.diference);
        idealWeight = myBodyActivity.findViewById(R.id.pesoIdeal);
        bodyFat = myBodyActivity.findViewById(R.id.bf);
        shoulders = myBodyActivity.findViewById(R.id.ombros);
        chest = myBodyActivity.findViewById(R.id.peitoral);
        leftArm = myBodyActivity.findViewById(R.id.bracoE);
        rightArm = myBodyActivity.findViewById(R.id.bracoD);
        hip = myBodyActivity.findViewById(R.id.quadril);
        waist = myBodyActivity.findViewById(R.id.cintura);
        leftLeg = myBodyActivity.findViewById(R.id.pernaE);
        rightLeg = myBodyActivity.findViewById(R.id.pernaD);
        leftCalf = myBodyActivity.findViewById(R.id.panturrilhaE);
        rightCalf = myBodyActivity.findViewById(R.id.panturrilhaD);


    }
    
    public void setupData(){
        if (service.isDataEmpty()){

            data.setText("?");
            currentWeight.setText("?");
            goalWeight.setText("?");
            bodyFat.setText("?");
            shoulders.setText("?");
            rightArm.setText("?");
            leftArm.setText("?");
            chest.setText("?");
            waist.setText("?");
            hip.setText("?");
            rightLeg.setText("?");
            leftLeg.setText("?");
            rightCalf.setText("?");
            leftCalf.setText("?");
            diferenceWeight.setText("?");
            idealWeight.setText("?");

            nextDataBtn.setClickable(false);
            previousDataBtn.setClickable(false);
            nextDataBtn.setBackgroundResource(R.drawable.ic_baseline_arrow_forward_ios_24_gray);
            previousDataBtn.setBackgroundResource(R.drawable.ic_baseline_arrow_back_ios_24_gray);
            
            usages = new AlertDialogUsages(myBodyContext);
            usages.emptyDatabase();
        }else {
            final Spinner spinner = new Spinner(myBodyContext);
            final ArrayAdapter<String> adapterSpinner = new ArrayAdapter<String>(myBodyContext,android.R.layout.simple_spinner_item, service.getAllData());
            adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapterSpinner);


            spinner.setSelection(adapterSpinner.getCount() - 1);
            setupInfos(spinner.getSelectedItemPosition());

            nextDataBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    previousDataBtn.setClickable(true);
                    previousDataBtn.setBackgroundResource(R.drawable.ic_baseline_arrow_back_ios_24);
                    if (spinner.getSelectedItemPosition()==adapterSpinner.getCount()-2){
                        nextDataBtn.setClickable(false);
                        nextDataBtn.setBackgroundResource(R.drawable.ic_baseline_arrow_forward_ios_24_gray);
                        nexData(spinner);
                    }else{
                        nexData(spinner);
                    }
                }
            });
            previousDataBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    nextDataBtn.setClickable(true);
                    nextDataBtn.setBackgroundResource(R.drawable.ic_baseline_arrow_forward_ios_24);
                    if(spinner.getSelectedItemPosition()== 1){
                        previousDataBtn.setClickable(false);
                        previousDataBtn.setBackgroundResource(R.drawable.ic_baseline_arrow_back_ios_24_gray);
                        previousData(spinner);
                    }else {

                        previousData(spinner);
                    }
                }
            });
            if(adapterSpinner.getCount()==1){
                previousDataBtn.setClickable(false);
                previousDataBtn.setBackgroundResource(R.drawable.ic_baseline_arrow_back_ios_24_gray);
            }
            nextDataBtn.setClickable(false);
            nextDataBtn.setBackgroundResource(R.drawable.ic_baseline_arrow_forward_ios_24_gray);

        }

    }


    public void nexData(Spinner spinner){
        int i = spinner.getSelectedItemPosition();
        spinner.setSelection(i + 1);
        setupInfos(i + 1);

    }

    public void previousData(Spinner spinner){
        int i = spinner.getSelectedItemPosition();
        spinner.setSelection(i - 1);
        setupInfos(i - 1);
    }

    public void setupInfos(int position){

        BodyModel bodyModel = service.getBodyModel(position);

        data.setText(bodyModel.getData());
        currentWeight.setText(bodyModel.getWeight() +" Kg");
        goalWeight.setText(bodyModel.getGoal() + " Kg");
        idealWeight.setText(new DecimalFormat("0.00").format(bodyModel.getWeight() / (bodyModel.getHight() * bodyModel.getHight())));
        bodyFat.setText(bodyModel.getBodyFat()+"%");
        shoulders.setText(bodyModel.getShoulders()+" cm");
        rightArm.setText(bodyModel.getRightArm()+" cm");
        leftArm.setText(bodyModel.getLeftArm()+" cm");
        chest.setText(bodyModel.getChest()+" cm");
        waist.setText(bodyModel.getWaist()+" cm");
        hip.setText(bodyModel.getHip()+" cm");
        rightLeg.setText(bodyModel.getRightLeg()+" cm");
        leftLeg.setText(bodyModel.getLeftLeg()+" cm");
        rightCalf.setText(bodyModel.getRightCalf()+" cm");
        leftCalf.setText(bodyModel.getLeftCalf()+" cm");
        diferenceWeight.setText(bodyModel.getGoal() - bodyModel.getWeight() +" Kg");


    }

    public void checkIsMaleOrFemale(){

        String sex = service.getSex();
        ImageView silhouette = myBodyActivity.findViewById(R.id.silhouette);

        if (sex.equals("Masculino")){
            silhouette.setImageResource(R.drawable.male_silhouette);

        }else if (sex.equals("Feminino")){
            silhouette.setImageResource(R.drawable.woman_silhouette);
        }

    }

}
