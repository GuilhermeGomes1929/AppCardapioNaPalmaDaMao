package com.example.aplicativonutricao.view;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aplicativonutricao.R;
import com.example.aplicativonutricao.model.dao.InfoDAO;
import com.example.aplicativonutricao.model.entity.PersonModel;
import com.example.aplicativonutricao.presenter.GetInfosPresenter;
import com.example.aplicativonutricao.view.MyBody;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class GetInfos extends AppCompatActivity {

    private Toolbar toolbar;

    private EditText diaOne;
    private EditText mesOne;
    private EditText anoOne;

    private EditText pesoAtual;
    private EditText meta;
    private EditText bf;
    private EditText ombros;
    private EditText peitoral;
    private EditText bracoE;
    private EditText bracoD;
    private EditText cintura;
    private EditText quadril;
    private EditText pernaE;
    private EditText pernaD;
    private EditText panturrilhaE;
    private EditText panturrilhaD;

    private Button salvarPeso;


    private Button editBodyInfos;

    private Button editPersonInfos;





    private InfoDAO dao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_infos);

        final Context context = this;
        final GetInfosPresenter presenter = new GetInfosPresenter(this);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Informações");

        salvarPeso = findViewById(R.id.salvar_infos);
        salvarPeso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.saveBodyInfo();;
            }
        });

        presenter.setupInfos();

        editBodyInfos = findViewById(R.id.edit_infos);
        editBodyInfos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.editBodyInfos();
            }
        });

        editPersonInfos = findViewById(R.id.edit_perfil);
        editPersonInfos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.editPersonInfos();
            }
        });

    }


    @Override
    public void onBackPressed(){
        finish();
        Intent intent = new Intent(this, MyBody.class);
        startActivity(intent);
    }

}