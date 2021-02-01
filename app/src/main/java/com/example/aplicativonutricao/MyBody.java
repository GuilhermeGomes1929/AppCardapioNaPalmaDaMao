package com.example.aplicativonutricao;

import android.app.AlertDialog;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MyBody extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    private TextView generic;

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


        //Muda a imagem se for homem ou mulher
        checkIsMaleOrFemale();


        //setup dos textViews das informações
        dao = new InfoDAO(this);


        //setup dos botões que passam as datas
        dataPosteriorBtn = findViewById(R.id.dataPosterior);
        dataAnteriorBtn = findViewById(R.id.dataAnterior);
        dataPosteriorBtn.setClickable(false);
        dataPosteriorBtn.setBackgroundResource(R.drawable.ic_baseline_arrow_forward_ios_24_gray);




        //setup das datas
        data = findViewById(R.id.data_myBody);
        if (dao.obterData().isEmpty()){
            TextView data = findViewById(R.id.data_myBody);
            TextView pesoAtual = findViewById(R.id.pesoAtual);
            TextView pesoMeta = findViewById(R.id.pesoMeta);
            TextView pesoDiference = findViewById(R.id.diference);
            TextView pesoIdeal = findViewById(R.id.pesoIdeal);
            TextView bf = findViewById(R.id.bf);
            TextView ombros = findViewById(R.id.ombros);
            TextView peitoral = findViewById(R.id.peitoral);
            TextView bracoE = findViewById(R.id.bracoE);
            TextView bracoD = findViewById(R.id.bracoD);
            TextView quadril = findViewById(R.id.quadril);
            TextView cintura = findViewById(R.id.cintura);
            TextView pernaE = findViewById(R.id.pernaE);
            TextView pernaD = findViewById(R.id.pernaD);
            TextView panturrilhaE = findViewById(R.id.panturrilhaE);
            TextView panturrilhaD = findViewById(R.id.panturrilhaD);

            data.setText("?");
            pesoAtual.setText("?");
            pesoMeta.setText("?");
            bf.setText("?");
            ombros.setText("?");
            bracoD.setText("?");
            bracoE.setText("?");
            peitoral.setText("?");
            cintura.setText("?");
            quadril.setText("?");
            pernaD.setText("?");
            pernaE.setText("?");
            panturrilhaD.setText("?");
            panturrilhaE.setText("?");
            pesoDiference.setText("?");
            pesoIdeal.setText("?");

            dataPosteriorBtn.setClickable(false);
            dataAnteriorBtn.setClickable(false);
            dataPosteriorBtn.setBackgroundResource(R.drawable.ic_baseline_arrow_forward_ios_24_gray);
            dataAnteriorBtn.setBackgroundResource(R.drawable.ic_baseline_arrow_back_ios_24_gray);

            AlertDialog.Builder msgBox = new AlertDialog.Builder(context);
            msgBox.setTitle("Ops... O banco de dados se encontra vazio!");
            msgBox.setMessage("Não foi possível encontrar suas informações no banco de dados. Por favor, adicone-as e tente novamente!");
            msgBox.show();

        }else {
            final Spinner spinner = new Spinner(this);
            final ArrayAdapter<String> adapterSpinner = new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_spinner_item, dao.obterData());
            adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapterSpinner);


            spinner.setSelection(adapterSpinner.getCount() - 1);
            setupInfos(spinner.getSelectedItemPosition());

            dataPosteriorBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dataAnteriorBtn.setClickable(true);
                    dataAnteriorBtn.setBackgroundResource(R.drawable.ic_baseline_arrow_back_ios_24);
                    if (spinner.getSelectedItemPosition()==adapterSpinner.getCount()-2){
                        dataPosteriorBtn.setClickable(false);
                        dataPosteriorBtn.setBackgroundResource(R.drawable.ic_baseline_arrow_forward_ios_24_gray);
                        dataPosterior(spinner);
                    }else{
                        dataPosterior(spinner);
                    }
                }
            });
            dataAnteriorBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dataPosteriorBtn.setClickable(true);
                    dataPosteriorBtn.setBackgroundResource(R.drawable.ic_baseline_arrow_forward_ios_24);
                    if(spinner.getSelectedItemPosition()== 1){
                        dataAnteriorBtn.setClickable(false);
                        dataAnteriorBtn.setBackgroundResource(R.drawable.ic_baseline_arrow_back_ios_24_gray);
                        dataAnterior(spinner);
                    }else {

                        dataAnterior(spinner);
                    }
                }
            });
            if(adapterSpinner.getCount()==1){
                dataAnteriorBtn.setClickable(false);
                dataAnteriorBtn.setBackgroundResource(R.drawable.ic_baseline_arrow_back_ios_24_gray);
            }
            dataPosteriorBtn.setClickable(false);
            dataPosteriorBtn.setBackgroundResource(R.drawable.ic_baseline_arrow_forward_ios_24_gray);

        }








    }

    public void changeName(View headerLayout){

        TextView name = headerLayout.findViewById(R.id.nav_name);
        InfoDAO infoDAO = new InfoDAO(this);
        name.setText(infoDAO.obterNomeIdadeAltura().get(0));
    }

    public void checkIsMaleOrFemale(){
        InfoDAO infoDAO = new InfoDAO(this);
        String sex = infoDAO.obterNomeIdadeAltura().get(2);

        String[] sexType = new String[]{"Masculino", "Feminino"};

        ImageView silhouette = findViewById(R.id.silhouette);

        if (sex.equals(sexType[0])){
            silhouette.setImageResource(R.drawable.male_silhouette);

        }if (sex.equals(sexType[1])){
            silhouette.setImageResource(R.drawable.woman_silhouette);
        }

    }

    public void dataPosterior(Spinner spinner){
        int i = spinner.getSelectedItemPosition();
        spinner.setSelection(i + 1);
        setupInfos(i + 1);

    }

    public void dataAnterior(Spinner spinner){
        int i = spinner.getSelectedItemPosition();
        spinner.setSelection(i - 1);
        setupInfos(i - 1);
    }


    public void setupInfos(int position){
        dao = new InfoDAO(getApplicationContext());

        TextView data = findViewById(R.id.data_myBody);
        TextView pesoAtual = findViewById(R.id.pesoAtual);
        TextView pesoMeta = findViewById(R.id.pesoMeta);
        TextView pesoDiference = findViewById(R.id.diference);
        TextView pesoIdeal = findViewById(R.id.pesoIdeal);
        TextView bf = findViewById(R.id.bf);
        TextView ombros = findViewById(R.id.ombros);
        TextView peitoral = findViewById(R.id.peitoral);
        TextView bracoE = findViewById(R.id.bracoE);
        TextView bracoD = findViewById(R.id.bracoD);
        TextView quadril = findViewById(R.id.quadril);
        TextView cintura = findViewById(R.id.cintura);
        TextView pernaE = findViewById(R.id.pernaE);
        TextView pernaD = findViewById(R.id.pernaD);
        TextView panturrilhaE = findViewById(R.id.panturrilhaE);
        TextView panturrilhaD = findViewById(R.id.panturrilhaD);

        ArrayList<String> infos = dao.obterInfosData("body", position);
        ArrayList<String> altura = dao.obterNomeIdadeAltura();

        data.setText(infos.get(0));
        pesoAtual.setText(infos.get(1) +" Kg");
        pesoMeta.setText(infos.get(2)+" Kg");
        pesoIdeal.setText(String.valueOf(Float.valueOf(infos.get(1)) /(Float.valueOf(altura.get(3)) * Float.valueOf(altura.get(3))) ));
        bf.setText(infos.get(3)+"%");
        ombros.setText(infos.get(4)+" cm");
        bracoD.setText(infos.get(5)+" cm");
        bracoE.setText(infos.get(6)+" cm");
        peitoral.setText(infos.get(7)+" cm");
        cintura.setText(infos.get(8)+" cm");
        quadril.setText(infos.get(9)+" cm");
        pernaD.setText(infos.get(10)+" cm");
        pernaE.setText(infos.get(11)+" cm");
        panturrilhaD.setText(infos.get(12)+" cm");
        panturrilhaE.setText(infos.get(13)+" cm");
        pesoDiference.setText(String.valueOf(Float.valueOf(infos.get(2)) - Float.valueOf(infos.get(1))) +" Kg");


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
                Intent intentMenu= new Intent(this,ShowMenu.class);
                startActivity(intentMenu);
                break;


            case R.id.nav_mybody:
                Toast.makeText(this,"Meu corpo", Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_graphics:
                Intent intentGraphs = new Intent(this, Graphs.class);
                startActivity(intentGraphs);
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