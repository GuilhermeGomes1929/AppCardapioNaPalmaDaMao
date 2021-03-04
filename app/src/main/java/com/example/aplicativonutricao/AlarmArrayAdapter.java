package com.example.aplicativonutricao;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AlarmArrayAdapter extends ArrayAdapter<String> {

    private final Context context;
    private final ArrayList<String> objects;

    public AlarmArrayAdapter(Context context, ArrayList<String> objects) {
        super(context, R.layout.alarm_listview, objects);
        this.context = context;
        this.objects = objects;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.alarm_listview, parent, false);

        TextView time = rowView.findViewById(R.id.text_time);
        Button deleteAlarm = rowView.findViewById(R.id.delete_alarm);

        time.setText(objects.get(position));

        deleteAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InfoDAO infoDAO = new InfoDAO(context);
                infoDAO.delete("alarms", position);
                Intent intent = new Intent(context, WaterBroadcastReceiver.class);
                intent.setAction(String.valueOf(infoDAO.obterAlarmIdNaPosicao(position)));
                PendingIntent pendingIntent = PendingIntent.getBroadcast(context,0,intent, 0);
                AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

                alarmManager.cancel(pendingIntent);
                pendingIntent.cancel();

                remove(objects.get(position));
                notifyDataSetChanged();

                Toast.makeText(context, "Alarme deletado", Toast.LENGTH_LONG).show();

            }
        });

        return rowView;
    }

}
