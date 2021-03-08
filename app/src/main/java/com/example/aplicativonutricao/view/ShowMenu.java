package com.example.aplicativonutricao.view;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aplicativonutricao.Graphs;
import com.example.aplicativonutricao.model.dao.InfoDAO;
import com.example.aplicativonutricao.R;
import com.example.aplicativonutricao.presenter.ShowMenuPresenter;

import java.text.SimpleDateFormat;
import java.util.Date;


public class ShowMenu extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{


    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ListView listViewBreakFast;
    private ListView listViewSnack;
    private ListView listViewLunch;
    private ListView listViewAftersnack;
    private ListView listViewDinner;
    private ListView listViewSupper;
    private ShowMenuPresenter presenter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_menu);

        //Título da activity
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Meu cardápio");

        //Setup da data
        setDate();

        //Verifica as permissões
        checkPermissions();

        //Setup da navigationview
        setupNavigationDrawer();
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View navName = navigationView.getHeaderView(0);
        TextView name = navName.findViewById(R.id.nav_name);
        changeName(name);

        //alertDialog para escolher o cardapio
        presenter = new ShowMenuPresenter(this);
        presenter.setupMenu();

        //setup da expansão de botões
        setupExpand();
    }

    public void changeName(View headerLayout){

        TextView name = headerLayout.findViewById(R.id.nav_name);
        InfoDAO infoDAO = new InfoDAO(this);
        name.setText(infoDAO.obterNomeIdadeAltura().getName());
    }

    public void setDate(){
        TextView dateText = findViewById(R.id.date);
        Date date = new Date();
        String dateString = new SimpleDateFormat("dd/MM/yyyy").format(date);
        dateText.setText(dateString);
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

    public void setupExpand(){

        final LinearLayout contExpand1 = findViewById(R.id.contMenuBreakfast);
        final Button buttonExpand1 = findViewById(R.id.buttonExpand1);
        final LinearLayout contExpand2 = findViewById(R.id.contMenuSnack);
        final Button buttonExpand2 = findViewById(R.id.buttonExpand2);
        final LinearLayout contExpand3 = findViewById(R.id.contMenuLunch);
        final Button buttonExpand3 = findViewById(R.id.buttonExpand3);
        final LinearLayout contExpand4 = findViewById(R.id.contMenuAftersnack);
        final Button buttonExpand4 = findViewById(R.id.buttonExpand4);
        final  LinearLayout contExpand5 = findViewById(R.id.contMenuDinner);
        final Button buttonExpand5 = findViewById(R.id.buttonExpand5);
        final LinearLayout contExpand6 = findViewById(R.id.contMenuSupper);
        final Button buttonExpand6 = findViewById(R.id.buttonExpand6);

        listViewBreakFast = findViewById(R.id.list_menu_breakfast);
        listViewSnack = findViewById(R.id.list_menu_snack);
        listViewLunch = findViewById(R.id.list_menu_lunch);
        listViewAftersnack = findViewById(R.id.list_menu_aftersnack);
        listViewDinner = findViewById(R.id.list_menu_dinner);
        listViewSupper = findViewById(R.id.list_menu_supper);


        buttonExpand1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expand(contExpand1, buttonExpand1, listViewBreakFast.getCount());

            }
        });

        buttonExpand2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expand(contExpand2, buttonExpand2, listViewSnack.getCount());
            }
        });

        buttonExpand3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expand(contExpand3, buttonExpand3, listViewLunch.getCount());
            }
        });

        buttonExpand4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expand(contExpand4, buttonExpand4, listViewAftersnack.getCount());
            }
        });

        buttonExpand5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expand(contExpand5, buttonExpand5, listViewDinner.getCount());
            }
        });

        buttonExpand6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expand(contExpand6, buttonExpand6, listViewSupper.getCount()
                );
            }
        });



    }

    public void expand(LinearLayout numberId, Button changeButton, int count) {
        if (count == 0){
            AlertDialog.Builder msgBox = new AlertDialog.Builder(this);
            msgBox.setTitle("Ops.. Cardápio vazio!");
            msgBox.setMessage("Parece que não há alimentos para essa refeição.");
            msgBox.show();
        }else{
            if (numberId.getVisibility()==View.GONE){
                numberId.setVisibility(View.VISIBLE);
                changeButton.setBackgroundResource(R.drawable.ic_baseline_expand_less_24);
            }else{
                numberId.setVisibility(View.GONE);
                changeButton.setBackgroundResource(R.drawable.ic_baseline_expand_more_24);
            }
        }


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
                break;

            case R.id.nav_graphics:
                Intent intentGraphs = new Intent(this, Graphs.class);
                startActivity(intentGraphs);
                break;

            case R.id.nav_menu:
                Toast.makeText(this,"Cardápio", Toast.LENGTH_LONG).show();
                break;

            case R.id.nav_mybody:
                Intent intentMyBody = new Intent(this, com.example.aplicativonutricao.view.MyBody.class);
                startActivity(intentMyBody);
                break;


        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


    public void checkPermissions(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) !=  PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if (id == R.id.editMenu){
            presenter.setupMenu();
        }
        return true;
    }

}