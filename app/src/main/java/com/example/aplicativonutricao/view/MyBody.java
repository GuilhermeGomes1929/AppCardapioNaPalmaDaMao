package com.example.aplicativonutricao.view;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aplicativonutricao.Graphs;
import com.example.aplicativonutricao.R;
import com.example.aplicativonutricao.model.dao.InfoDAO;
import com.example.aplicativonutricao.presenter.MyBodyPresenter;

public class MyBody extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    private TextView teste;

    private InfoDAO dao;

    private Context context;

    private Button dataPosteriorBtn;
    private Button dataAnteriorBtn;

    private TextView data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_body);


        context = this;

        //Título da activity
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Meu corpo");

        //setup navigation drawer
        setupNavigationDrawer();
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View navName = navigationView.getHeaderView(0);
        TextView name = navName.findViewById(R.id.nav_name);
        changeName(name);

        //setup dos textViews das informações
        MyBodyPresenter presenter = new MyBodyPresenter(this);
        presenter.setupData();

        //Muda a imagem se for homem ou mulher
        presenter.checkIsMaleOrFemale();
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

        setSupportActionBar(toolbar);

        navigationView.bringToFront();

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer);
        drawerLayout.addDrawerListener(toggle);

        toggle.syncState();

    }

    //configurar o botão back
    @Override
    public void onBackPressed(){

        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();

        }
    }


    //ouve os eventos de clique
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()){

            case R.id.nav_waterReminder:
                Intent intentWater = new Intent(this, com.example.aplicativonutricao.view.WaterReminder.class);
                startActivity(intentWater);
                finish();
                break;

            case R.id.nav_menu:
                Intent intentMenu= new Intent(this,com.example.aplicativonutricao.view.ShowMenu.class);
                startActivity(intentMenu);
                finish();
                break;


            case R.id.nav_mybody:
                Toast.makeText(this,"Meu corpo", Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_graphics:
                Intent intentGraphs = new Intent(this, Graphs.class);
                startActivity(intentGraphs);
                finish();
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_create_mybody, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if (id == R.id.createMenu){
            Intent intent = new Intent(this, GetInfos.class);
            startActivity(intent);
        }
        return true;
    }


}