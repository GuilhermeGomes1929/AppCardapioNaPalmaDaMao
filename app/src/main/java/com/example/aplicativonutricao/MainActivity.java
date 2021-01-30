package com.example.aplicativonutricao;


import android.content.Intent;


import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.support.v7.widget.Toolbar;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{


    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    private ListView listViewBreakFast;
    private ListView listViewSnack;
    private ListView listViewLunch;
    private ListView listViewAftersnack;
    private ListView listViewDinner;
    private ListView listViewSupper;


    private ArrayList<String> alimentosListBreakfast;
    private ArrayList<String> quantidadeListBreakfast;
    private ArrayList<String> alimentosListSnack;
    private ArrayList<String> quantidadeListSnack;
    private ArrayList<String> alimentosListLunch;
    private ArrayList<String> quantidadeListLunch;
    private ArrayList<String> alimentosListAftersnack;
    private ArrayList<String> quantidadeListAftersnack;
    private ArrayList<String> alimentosListDinner;
    private ArrayList<String> quantidadeListDinner;
    private ArrayList<String> alimentosListSupper;
    private ArrayList<String> quantidadeListSupper;

    private InfoDAO dao;

    private LinearLayout navPerfil;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        //Título da activity
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Meu cardápio");

        //Setup dos botões de expandir e da progressbar
        setupExpand();
        ProgressBar progress = findViewById(R.id.calories_progress_bar);
        progress.setProgress(75);
        // ------------------------------------------------------------------

        //Setup da navigationview
        setupNavigationDrawer();
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);






        //permite fazer alterações no banco de dados
        dao = new InfoDAO(this);

        //configura os itens do cardápio
        /*listViewBreakFast = findViewById(R.id.list_menu_breakfast);
        alimentosListBreakfast = dao.obterAlimento("breakfast");
        quantidadeListBreakfast = dao.obterPeso("breakfast");
        FoodAdapter adapterBreakfast = new FoodAdapter(this, R.layout.listvew_cardapio, adicionarFood(quantidadeListBreakfast, alimentosListBreakfast));
        listViewBreakFast.setAdapter(adapterBreakfast);

        listViewSnack = findViewById(R.id.list_menu_snack);
        alimentosListSnack = dao.obterAlimento("snack");
        quantidadeListSnack = dao.obterPeso("snack");
        FoodAdapter adapterSnack = new FoodAdapter(this, R.layout.listvew_cardapio, adicionarFood(quantidadeListSnack, alimentosListSnack));
        listViewSnack.setAdapter(adapterSnack);

        listViewLunch = findViewById(R.id.list_menu_lunch);
        alimentosListLunch = dao.obterAlimento("lunch");
        quantidadeListLunch = dao.obterPeso("lunch");
        FoodAdapter adapterLunch = new FoodAdapter(this, R.layout.listvew_cardapio, adicionarFood(quantidadeListLunch, alimentosListLunch));
        listViewLunch.setAdapter(adapterLunch);

        listViewAftersnack = findViewById(R.id.list_menu_aftersnack);
        alimentosListAftersnack = dao.obterAlimento("aftersnack");
        quantidadeListAftersnack = dao.obterPeso("aftersnack");
        FoodAdapter adapterAftersnack = new FoodAdapter(this, R.layout.listvew_cardapio, adicionarFood(quantidadeListAftersnack, alimentosListAftersnack));
        listViewAftersnack.setAdapter(adapterAftersnack);

        listViewDinner = findViewById(R.id.list_menu_dinner);
        alimentosListDinner = dao.obterAlimento("dinner");
        quantidadeListDinner = dao.obterPeso("dinner");
        FoodAdapter adapterDinner = new FoodAdapter(this, R.layout.listvew_cardapio, adicionarFood(quantidadeListDinner, alimentosListDinner));
        listViewDinner.setAdapter(adapterDinner);

        listViewSupper = findViewById(R.id.list_menu_supper);
        alimentosListSupper = dao.obterAlimento("supper");
        quantidadeListSupper = dao.obterPeso("supper");
        FoodAdapter adapterSupper = new FoodAdapter(this, R.layout.listvew_cardapio, adicionarFood(quantidadeListSupper, alimentosListSupper));
        listViewSupper.setAdapter(adapterSupper);


        //Configura o tamanho das listas
        listViewBreakFast.getLayoutParams().height = 120 * listViewBreakFast.getCount();
        listViewSnack.getLayoutParams().height = 120 * listViewSnack.getCount();
        listViewLunch.getLayoutParams().height = 120 * listViewLunch.getCount();
        listViewAftersnack.getLayoutParams().height = 120 * listViewAftersnack.getCount();
        listViewDinner.getLayoutParams().height = 120 * listViewDinner.getCount();
        listViewSupper.getLayoutParams().height = 120 * listViewSupper.getCount();*/




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
                expand(v, contExpand1, buttonExpand1, listViewBreakFast.getCount());

            }
        });

        buttonExpand2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expand(v, contExpand2, buttonExpand2, listViewSnack.getCount());
            }
        });

        buttonExpand3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expand(v, contExpand3, buttonExpand3, listViewLunch.getCount());
            }
        });

        buttonExpand4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expand(v, contExpand4, buttonExpand4, listViewAftersnack.getCount());
            }
        });

        buttonExpand5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expand(v, contExpand5, buttonExpand5, listViewDinner.getCount());
            }
        });

        buttonExpand6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expand(v, contExpand6, buttonExpand6, listViewSupper.getCount()
                );
            }
        });



    }

    public void expand(View view, LinearLayout numberId, Button changeButton, int count) {
        if (count == 0){
            AlertDialog.Builder msgBox = new AlertDialog.Builder(this);
            msgBox.setTitle("Ops.. Cardápio vazio!");
            msgBox.setMessage("Adicione alimentos no cardápio");
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

            case R.id.nav_graphics:
                Intent intentGraphs = new Intent(this, Graphs.class);
                startActivity(intentGraphs);
                break;

            case R.id.nav_menu:
                Intent intentTest = new Intent(this, ShowMenu.class);
                startActivity(intentTest);
                break;

            case R.id.nav_water:
                Intent intentWater = new Intent(this, Water.class);
                startActivity(intentWater);
                break;

            case R.id.nav_metas:
                Intent intentMetas = new Intent(this, Metas.class);
                startActivity(intentMetas);
                break;

            case R.id.nav_mybody:
                Intent intentMyBody = new Intent(this, MyBody.class);
                startActivity(intentMyBody);
                break;


        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
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
            Toast.makeText(getApplicationContext(),"Não tem nada aqui.", Toast.LENGTH_SHORT).show();
        }
        return true;
    }



}