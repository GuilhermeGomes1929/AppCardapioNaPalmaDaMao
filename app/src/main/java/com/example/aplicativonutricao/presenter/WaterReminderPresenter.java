package com.example.aplicativonutricao.presenter;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.text.InputType;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.aplicativonutricao.model.service.AlarmArrayAdapter;
import com.example.aplicativonutricao.model.service.MyListView;
import com.example.aplicativonutricao.R;
import com.example.aplicativonutricao.model.service.AlertDialogUsages;
import com.example.aplicativonutricao.model.service.WaterReminderService;

public class WaterReminderPresenter {

    private Activity waterReminderActivity;
    private Context waterReminderContext;
    private WaterReminderService service;
    public static int ADDWATER = 2;
    public static int LESSWATER = 1;
    public static  int UPDATEWATER = 0;

    public WaterReminderPresenter(Activity waterReminderActivity){
        this.waterReminderActivity = waterReminderActivity;
        this.waterReminderContext = waterReminderActivity;
        service = new WaterReminderService(waterReminderContext);
    }


    public void updateProgressBar(int flag){
        ProgressBar progressBar = waterReminderActivity.findViewById(R.id.waterProgressBar);
        int quantity = service.updateWater(flag);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            progressBar.setProgress(quantity, true);
        }else {
            progressBar.setProgress(quantity);
        }
        updateDifferenceWater();
    }

    public void updateDifferenceWater(){

        TextView textView = waterReminderActivity.findViewById(R.id.difference_water);
        ProgressBar progressBar = waterReminderActivity.findViewById(R.id.waterProgressBar);
        Float number = Float.valueOf((progressBar.getMax() - progressBar.getProgress())) / 1000;
        textView.setText(number + "L");

    }

    public void setupWaterQuantity(){
        if (service.getWaterQuantity() == null){
            setWaterQuantity();
        }
    }

    public void setWaterQuantity(){
        final ProgressBar progressBar = waterReminderActivity.findViewById(R.id.waterProgressBar);
        final Button button =  waterReminderActivity.findViewById(R.id.litros);

        AlertDialogUsages usages = new AlertDialogUsages(waterReminderContext);

        final EditText editText = new EditText(waterReminderContext);
        editText.setInputType(InputType.TYPE_CLASS_NUMBER);
        editText.setPadding(30,10,30,10);


        DialogInterface.OnClickListener onClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int quantityWaterProgressbar = Integer.parseInt(editText.getText().toString());
                service.setWaterQuantity(quantityWaterProgressbar);
                button.setText(Float.valueOf(quantityWaterProgressbar / 1000)+" Litros");
                progressBar.setMax(quantityWaterProgressbar);
                updateDifferenceWater();
            }
        };

        usages.getWaterQuantity(editText, onClickListener);

    }

    public void getTimePicker(){
        TimePickerDialog.OnTimeSetListener  listener= new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                service.createAnAlarm(hourOfDay, minute);
                updateAlarmList();
            }
        };
        TimePickerDialog timePicker = new TimePickerDialog(waterReminderContext, listener, 12, 0, true);
        timePicker.show();

    }

    public void updateAlarmList(){
        try{

            MyListView listView = waterReminderActivity.findViewById(R.id.list_alarms);
            AlarmArrayAdapter alarmArrayAdapter = new AlarmArrayAdapter(waterReminderContext, service.getAllFormatedAlarms());
            listView.setAdapter(alarmArrayAdapter);
            listView.deferNotifyDataSetChanged();

        }catch (Exception e){}
    }

}
