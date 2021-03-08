package com.example.aplicativonutricao.model.service;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;

import com.example.aplicativonutricao.model.dao.InfoDAO;

public class DrinkBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        InfoDAO infoDAO = new InfoDAO(context);
        ContentValues values = new ContentValues();
        int quantity = infoDAO.obterQuantidadeDeAgua();

        values.put("quantity", quantity + 200);
        infoDAO.delete("water", 0);
        infoDAO.insertValues("water", values);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        notificationManager.cancel(22062003);
    }
}
