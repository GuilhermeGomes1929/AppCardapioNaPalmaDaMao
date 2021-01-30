package com.example.aplicativonutricao;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class Water extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private TextView activityTitle;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water);

        //Título da activity
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Lembretes de água");

        //Setup dos botões de expandir e da progressbar
        ProgressBar progress = findViewById(R.id.water_progress_bar);
        progress.setProgress(75);
        // ------------------------------------------------------------------

        //Setup da navigationview
        setupNavigationDrawer();
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);




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


            case R.id.nav_menu:
                Intent intentMenu= new Intent(this, MainActivity.class);
                startActivity(intentMenu);
                break;

            case R.id.nav_water:
                Toast.makeText(this,"Lembretes de água", Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_metas:
                Intent intentMetas = new Intent(this, Metas.class);
                startActivity(intentMetas);
                break;

            case R.id.nav_mybody:
                Intent intentMyBody = new Intent(this, MyBody.class);
                startActivity(intentMyBody);
                break;

            case R.id.nav_graphics:
                Intent intentGraphs = new Intent(this, Graphs.class);
                startActivity(intentGraphs);
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}