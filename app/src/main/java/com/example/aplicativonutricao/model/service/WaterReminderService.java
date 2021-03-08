package com.example.aplicativonutricao.model.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.aplicativonutricao.model.dao.InfoDAO;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class WaterReminderService {

    private Context waterReminderContext;
    private InfoDAO infoDAO;

    public WaterReminderService(Context waterReminderContext){
        this.waterReminderContext = waterReminderContext;
        infoDAO = new InfoDAO(waterReminderContext);

    }

    public Integer updateWater(int flag){
        ContentValues values = new ContentValues();
        int quantity = infoDAO.obterQuantidadeDeAgua();

        if (flag == 2){
            if (quantity < infoDAO.obterLitros()){

                infoDAO.delete("water", 0);
                values.put("quantity", quantity + 200);
                infoDAO.insertValues("water", values);
                return infoDAO.obterQuantidadeDeAgua();
            }
        }else if(flag == 1){
            if (quantity > 0){
                infoDAO.delete("water", 0);
                values.put("quantity", quantity - 200);
                infoDAO.insertValues("water", values);
                return infoDAO.obterQuantidadeDeAgua();
            }
        }else if (flag == 0){
            return infoDAO.obterQuantidadeDeAgua();
        }
        return infoDAO.obterQuantidadeDeAgua();
    }

    public void setWaterQuantity(int quantity){
        ContentValues values = new ContentValues();
        try{
            infoDAO.delete("fullquantity", 0);
            values.put("fullquantity", quantity);
            infoDAO.insertValues("litros", values);
        }catch (Exception e){
            values.put("fullquantity", quantity);
            infoDAO.insertValues("litros", values);
        }
    }

    public Integer getWaterQuantity(){
        return infoDAO.obterLitros();
    }

    public void createAnAlarm(int hourOfDay , int minute){
        ContentValues values = new ContentValues();

        if (infoDAO.obterUltimoAlarmeId() == null){
            Intent intent = new Intent(waterReminderContext, com.example.aplicativonutricao.model.service.WaterBroadcastReceiver.class);
            intent.setAction(String.valueOf(1));
            PendingIntent pendingIntent = PendingIntent.getBroadcast(waterReminderContext, 0, intent, 0);
            int hour = hourOfDay;
            int min = minute;

            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, hour);
            calendar.set(Calendar.MINUTE, min);
            calendar.set(Calendar.SECOND, 0);

            AlarmManager alarm = (AlarmManager) waterReminderContext.getSystemService(Context.ALARM_SERVICE);
            alarm.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY, pendingIntent);

            values.put("time", calendar.getTimeInMillis());
            infoDAO.insertValues("alarms", values);

            //updateAlarmList();

            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
            Toast.makeText(waterReminderContext, "Alarme agendado para "+dateFormat.format(calendar.getTime()), Toast.LENGTH_LONG).show();

        }
        else{
            Intent intent = new Intent(waterReminderContext, com.example.aplicativonutricao.model.service.WaterBroadcastReceiver.class);
            intent.setAction(String.valueOf(infoDAO.obterUltimoAlarmeId()+1));
            PendingIntent pendingIntent = PendingIntent.getBroadcast(waterReminderContext, 0, intent, 0);
            int hour = hourOfDay;
            int min = minute;

            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, hour);
            calendar.set(Calendar.MINUTE, min);
            calendar.set(Calendar.SECOND, 0);

            AlarmManager alarm = (AlarmManager) waterReminderContext.getSystemService(Context.ALARM_SERVICE);
            alarm.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY, pendingIntent);

            values.put("time", calendar.getTimeInMillis());
            infoDAO.insertValues("alarms", values);

            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
            Toast.makeText(waterReminderContext, "Alarme agendado para "+dateFormat.format(calendar.getTime()), Toast.LENGTH_LONG).show();

        }
    }

    public ArrayList<String> changeLongListToString(ArrayList<Long> longArrayList) {
        ArrayList<String> stringArrayList = new ArrayList<>();
        for (int i = 0; i < longArrayList.size(); i++){
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(longArrayList.get(i));

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");

            stringArrayList.add(simpleDateFormat.format(calendar.getTime()));
        }
        return stringArrayList;
    }

    public ArrayList<String> getAllFormatedAlarms(){
        return changeLongListToString(infoDAO.obterTodosAlarmes());
    }
}
