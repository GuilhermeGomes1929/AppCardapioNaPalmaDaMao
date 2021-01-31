package com.example.aplicativonutricao;


import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;


import android.icu.text.IDNA;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.support.v7.widget.Toolbar;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity{


    private Toolbar toolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Verifica se já existe as informações no banco de dados
        verifyFirstData();


        //Título da activity
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Aplicativo nutrição");

        //Configurar o spinner da escolha de sexo
        Spinner newSex = findViewById(R.id.new_sex);
        ArrayAdapter<String> adapterSex = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item,
                new String[]{"Masculino", "Feminino"});
        adapterSex.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        newSex.setAdapter(adapterSex);

        //Botão que salva as informações
        FloatingActionButton saveBuuton = findViewById(R.id.save_first_data);
        saveBuuton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveFirstData();
            }
        });


    }

    public void verifyFirstData(){

        InfoDAO infoDAO = new InfoDAO(this);

        try{

            ArrayList<String> firstData = infoDAO.obterNomeIdadeAltura();
            if (!firstData.isEmpty()){
                startActivity(new Intent(this, ShowMenu.class));
            }

        }catch (Exception e){

            startActivity(new Intent(this, ShowMenu.class));
        }

    }


    public void saveFirstData(){

        Context context = this;

        InfoDAO infoDAO = new InfoDAO(this);

        EditText newName = findViewById(R.id.new_name);
        EditText newAge = findViewById(R.id.new_age);
        EditText newHeight = findViewById(R.id.new_height);
        Spinner newSex = findViewById(R.id.new_sex);

        ContentValues values = new ContentValues();

        try{

            values.put("nome", newName.getText().toString());
            values.put("idade", newAge.getText().toString());
            values.put("sexo", newSex.getSelectedItem().toString());
            values.put("altura", Float.valueOf(newHeight.getText().toString()));

            infoDAO.insertValues("info", values);

            startActivity(new Intent(context, ShowMenu.class));

        }catch (Exception e){

            AlertDialog.Builder msgBox = new AlertDialog.Builder(context);
            msgBox.setTitle("Ops... Algum campo se encontra vazio.");
            msgBox.setMessage("Preencha todos os campos para continuar.");
            msgBox.show();

        }

    }

}