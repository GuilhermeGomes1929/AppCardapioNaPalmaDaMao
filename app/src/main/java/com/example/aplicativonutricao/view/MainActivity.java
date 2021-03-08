package com.example.aplicativonutricao.view;


import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.aplicativonutricao.R;
import com.example.aplicativonutricao.presenter.MainActivityPresenter;


public class MainActivity extends AppCompatActivity{


    private Toolbar toolbar;
    private MainActivityPresenter presenter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter = new MainActivityPresenter(this);

        //Verifica se já existe as informações no banco de dados
        presenter.verifyFirstData();

        //Verifica se há permissões no aplicativo
        checkPermissions();

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
                saveButtonListener();
            }
        });


    }

    public void checkPermissions(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) !=  PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }
    }



    public void saveButtonListener(){
        EditText newName = findViewById(R.id.new_name);
        EditText newAge = findViewById(R.id.new_age);
        EditText newHeight = findViewById(R.id.new_height);
        Spinner newSex = findViewById(R.id.new_sex);

        presenter.getFirstData(newName.getText().toString(),
                newAge.getText().toString(),
                newSex.getSelectedItem().toString(),
                newHeight.getText().toString());
    }



}