package com.example.aplicativonutricao;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.RadialGradient;
import android.icu.text.IDNA;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

public class WaterReminder extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_reminder);

        //Título da activity
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Lembrete de água");

        //Setup navigation drawer
        setupNavigationDrawer();
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View navName = navigationView.getHeaderView(0);
        TextView name = navName.findViewById(R.id.nav_name);
        changeName(name);

        updateAlarmList();


        Button increaseWaterButton = findViewById(R.id.increaseWater);
        Button decreaseWaterButton = findViewById(R.id.decreaseWater);
        final Button createAlarm = findViewById(R.id.createAlarm);

        increaseWaterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                increaseWater();
            }
        });

        decreaseWaterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decreaseWater();
            }
        });

        createAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAnAlarm();
            }
        });

    }

    public void updateAlarmList(){

        try{
            InfoDAO  infoDAO = new InfoDAO(this);
            
            MyListView listView = findViewById(R.id.list_alarms);
            AlarmArrayAdapter alarmArrayAdapter = new AlarmArrayAdapter(getApplicationContext(), changeLongListToString(infoDAO.obterTodosAlarmes()));
            listView.setAdapter(alarmArrayAdapter);
            listView.deferNotifyDataSetChanged();

        }catch (Exception e){}

    }

    public void updateDifferenceWater(){

        TextView textView = findViewById(R.id.difference_water);
        ProgressBar progressBar = findViewById(R.id.waterProgressBar);
        Float number = Float.valueOf((progressBar.getMax() - progressBar.getProgress())) / 1000;
        textView.setText(number + "L");

    }


    public void createAnAlarm(){

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                InfoDAO infoDAO = new InfoDAO(getApplicationContext());
                ContentValues values = new ContentValues();

                if (infoDAO.obterUltimoAlarmeId() == null){
                    Intent intent = new Intent(getApplicationContext(), WaterBroadcastReceiver.class);
                    intent.setAction(String.valueOf(1));
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, 0);
                    int hour = hourOfDay;
                    int min = minute;

                    Calendar calendar = Calendar.getInstance();
                    calendar.set(Calendar.HOUR_OF_DAY, hour);
                    calendar.set(Calendar.MINUTE, min);
                    calendar.set(Calendar.SECOND, 0);

                    AlarmManager alarm = (AlarmManager) getSystemService(ALARM_SERVICE);
                    alarm.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY, pendingIntent);

                    values.put("time", calendar.getTimeInMillis());
                    infoDAO.insertValues("alarms", values);

                    updateAlarmList();

                    SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
                    Toast.makeText(getApplicationContext(), "Alarme programado para "
                            + dateFormat.format(calendar.getTime()), Toast.LENGTH_LONG).show();
                }
                else{
                    Intent intent = new Intent(getApplicationContext(), WaterBroadcastReceiver.class);
                    intent.setAction(String.valueOf(infoDAO.obterUltimoAlarmeId()+1));
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, 0);
                    int hour = hourOfDay;
                    int min = minute;

                    Calendar calendar = Calendar.getInstance();
                    calendar.set(Calendar.HOUR_OF_DAY, hour);
                    calendar.set(Calendar.MINUTE, min);
                    calendar.set(Calendar.SECOND, 0);

                    AlarmManager alarm = (AlarmManager) getSystemService(ALARM_SERVICE);
                    alarm.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY, pendingIntent);

                    values.put("time", calendar.getTimeInMillis());
                    infoDAO.insertValues("alarms", values);

                    MyListView listView = findViewById(R.id.list_alarms);
                    AlarmArrayAdapter alarmArrayAdapter = new AlarmArrayAdapter(getApplicationContext(), changeLongListToString(infoDAO.obterTodosAlarmes()));
                    listView.setAdapter(alarmArrayAdapter);
                    listView.deferNotifyDataSetChanged();

                    SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
                    Toast.makeText(getApplicationContext(), "Alarme agendado para "+dateFormat.format(calendar.getTime()), Toast.LENGTH_LONG).show();

                }





            }
        }, 12, 0, true);

        timePickerDialog.show();

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

    public void changeName(View headerLayout){

        TextView name = headerLayout.findViewById(R.id.nav_name);
        InfoDAO infoDAO = new InfoDAO(this);
        name.setText(infoDAO.obterNomeIdadeAltura().get(0));
    }

    public void updateProgressBar(){
        InfoDAO infoDAO = new InfoDAO(this);
        ProgressBar progressBar = findViewById(R.id.waterProgressBar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            progressBar.setProgress(infoDAO.obterQuantidadeDeAgua(), true);
        }else {
            progressBar.setProgress(infoDAO.obterQuantidadeDeAgua());
        }

    }


    public void increaseWater(){

        InfoDAO infoDAO = new InfoDAO(this);
        ContentValues value = new ContentValues();
        ProgressBar waterProgressBar = findViewById(R.id.waterProgressBar);
        int quantity = infoDAO.obterQuantidadeDeAgua();

        if (quantity < 2000){

            infoDAO.delete("water", 0);
            value.put("quantity", quantity + 200);
            infoDAO.insertValues("water", value);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

                waterProgressBar.setProgress(infoDAO.obterQuantidadeDeAgua(), true);

            }else{
                waterProgressBar.setProgress(infoDAO.obterQuantidadeDeAgua());
            }
            updateDifferenceWater();

        }

    }

    public void decreaseWater(){

        InfoDAO infoDAO = new InfoDAO(this);
        ContentValues value = new ContentValues();
        ProgressBar waterProgressBar = findViewById(R.id.waterProgressBar);
        int quantity = infoDAO.obterQuantidadeDeAgua();

        if (quantity > 0){

            infoDAO.delete("water", 0);
            value.put("quantity", quantity - 200);
            infoDAO.insertValues("water", value);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                waterProgressBar.setProgress(infoDAO.obterQuantidadeDeAgua(), true);
            }else{
                waterProgressBar.setProgress(infoDAO.obterQuantidadeDeAgua());
            }
            updateDifferenceWater();

        }


    }


    public void setupNavigationDrawer(){

        toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        navigationView.bringToFront();

        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer);
        drawerLayout.addDrawerListener(toggle);

        toggle.syncState();

    }

    @Override
    protected void onResume() {
        updateProgressBar();
        updateDifferenceWater();
        super.onResume();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()){

            case R.id.nav_waterReminder:
                Toast.makeText(this, "Lembrete de água", Toast.LENGTH_LONG).show();
                break;

            case R.id.nav_graphics:
                Intent intentGraphs = new Intent(this, Graphs.class);
                startActivity(intentGraphs);
                finish();
                break;

            case R.id.nav_menu:
                Intent intentMenu = new Intent(this, ShowMenu.class);
                startActivity(intentMenu);
                finish();
                break;

            case R.id.nav_mybody:
                Intent intentMyBody = new Intent(this, MyBody.class);
                startActivity(intentMyBody);
                finish();
                break;


        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed(){

        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();

        }
    }


}