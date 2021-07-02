package com.example.aplicativonutricao.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aplicativonutricao.model.dao.InfoDAO;
import com.example.aplicativonutricao.R;
import com.example.aplicativonutricao.presenter.WaterReminderPresenter;

public class WaterReminder extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private int quantityWaterProgressbar;
    private WaterReminderPresenter presenter;

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

        presenter = new WaterReminderPresenter(this);
        presenter.updateAlarmList();
        presenter.setupWaterQuantity();


        Button increaseWaterButton = findViewById(R.id.increaseWater);
        Button decreaseWaterButton = findViewById(R.id.decreaseWater);
        Button createAlarm = findViewById(R.id.createAlarm);
        Button waterQuantity = findViewById(R.id.litros);

        increaseWaterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.updateProgressBar(presenter.ADDWATER);
            }
        });

        decreaseWaterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.updateProgressBar(presenter.LESSWATER);
            }
        });

        createAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.getTimePicker();
            }
        });

        waterQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.setWaterQuantity();
            }
        });

    }


    public void changeName(View headerLayout){

        TextView name = headerLayout.findViewById(R.id.nav_name);
        InfoDAO infoDAO = new InfoDAO(this);
        name.setText(infoDAO.obterNomeIdadeAltura().getName());
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
        presenter.updateProgressBar(presenter.UPDATEWATER);
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
                Intent intentMenu = new Intent(this, com.example.aplicativonutricao.view.ShowMenu.class);
                startActivity(intentMenu);
                finish();
                break;

            case R.id.nav_mybody:
                Intent intentMyBody = new Intent(this, com.example.aplicativonutricao.view.MyBody.class);
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