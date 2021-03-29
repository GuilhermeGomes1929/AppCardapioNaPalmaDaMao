package com.example.aplicativonutricao.model.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.Toast;

import com.example.aplicativonutricao.model.dao.InfoDAO;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;


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

    public void createAnAlarm(int hourOfDay, int minute){

        ContentValues values = new ContentValues();
        Calendar calendar = Calendar.getInstance();
        String action = "";

        if (infoDAO.obterUltimoAlarmeId() == null){
            action = "1";
        }else{
            action = String.valueOf(infoDAO.obterUltimoAlarmeId() + 1);
        }


        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
        long timeInMillis = calendar.getTimeInMillis();


        if (Calendar.getInstance().get(Calendar.HOUR_OF_DAY) > hourOfDay){
            timeInMillis = timeInMillis + 1000 * 60 * 60 * 24;

        }else if (Calendar.getInstance().get(Calendar.HOUR_OF_DAY) == hourOfDay){
            if (Calendar.getInstance().get(Calendar.MINUTE) >= minute){
                timeInMillis = timeInMillis + 1000 * 60 * 60 * 24;
            }
        }

        AlarmManager alarmManager = (AlarmManager) waterReminderContext.getSystemService(Context.ALARM_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Intent intent = new Intent(waterReminderContext, RepeatingWaterBroadcastReceiver.class);
            intent.setAction(action);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(waterReminderContext, 1, intent, 0);

            alarmManager.setExact(AlarmManager.RTC_WAKEUP, timeInMillis, pendingIntent);
        }else{
            Intent intent = new Intent(waterReminderContext, WaterBroadcastReceiver.class);
            intent.setAction(action);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(waterReminderContext, 1, intent, 0);
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, timeInMillis,AlarmManager.INTERVAL_DAY, pendingIntent);
        }

        values.put("time", calendar.getTimeInMillis());
        infoDAO.insertValues("alarms", values);
        Toast.makeText(waterReminderContext, "Alarme agendado para "+ new SimpleDateFormat("EEE HH:mm", new Locale("pt","BR")).format(timeInMillis), Toast.LENGTH_LONG).show();

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
