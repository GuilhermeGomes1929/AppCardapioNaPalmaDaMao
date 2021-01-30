package com.example.aplicativonutricao;

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

import java.util.ArrayList;

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


    private Button editPeso;

    private Button editPerfil;





    private InfoDAO dao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_infos);

        final Context context = this;

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Informações");

        salvarPeso = findViewById(R.id.salvar_infos);
        salvarPeso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveWeight();
            }
        });

        TextView nome = findViewById(R.id.nome_perfil);
        TextView idade = findViewById(R.id.altura_idade_perfil);
        dao = new InfoDAO(this);
        ArrayList<String> infos = dao.obterNomeIdadeAltura();
        if (infos.isEmpty()){
            nome.setText("Nome não identificado");
            idade.setText("Idade e altura não identificadas");
        }else {
            nome.setText(infos.get(0));
            idade.setText(infos.get(3)+"m /"+infos.get(1)+" anos");
        }



        editPeso = findViewById(R.id.edit_infos);
        editPeso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dao = new InfoDAO(getApplicationContext());
                AlertDialog.Builder msgBox = new AlertDialog.Builder(context);
                View mView = getLayoutInflater().inflate(R.layout.dialog_spinner, null);
                msgBox.setTitle("Apagar informações da data:");
                final Spinner spinner = mView.findViewById(R.id.spinner_edit_peso);
                ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_spinner_item,dao.obterData());
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter);

                msgBox.setPositiveButton("Deletar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dao.delete("body", spinner.getSelectedItemPosition());
                        Toast.makeText(getApplicationContext(),"Informações da data "+ spinner.getSelectedItem().toString()+ " foram deletadas." ,Toast.LENGTH_LONG).show();

                    }
                });
                msgBox.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "Nenhuma informação foi deletada.", Toast.LENGTH_LONG).show();
                    }
                });

                msgBox.setView(mView);
                msgBox.show();

            }
        });

        editPerfil = findViewById(R.id.edit_perfil);
        editPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dao = new InfoDAO(getApplicationContext());
                AlertDialog.Builder msgBox = new AlertDialog.Builder(context);
                View view = getLayoutInflater().inflate(R.layout.dialog_edit_texts, null);
                msgBox.setTitle("Editar informações do perfil.");
                final EditText nome = view.findViewById(R.id.nome_dialog);
                final EditText idade = view.findViewById(R.id.idade_dialog);
                final EditText altura = view.findViewById(R.id.altura_dialog);
                final Spinner sexo = view.findViewById(R.id.spinner_sexo);
                ArrayList<String> escolhas = new ArrayList<>();
                escolhas.add("Masculino");
                escolhas.add("Feminino");

                ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_spinner_item,escolhas);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                sexo.setAdapter(adapter);


                msgBox.setPositiveButton("Salvar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            ContentValues values = new ContentValues();
                            values.put("nome", nome.getText().toString());
                            values.put("idade", idade.getText().toString());
                            values.put("sexo", sexo.getSelectedItem().toString());
                            values.put("altura", Float.valueOf(String.valueOf(altura.getText())));
                            dao.delete("info", 0);
                            dao.insertValues("info", values);
                            ArrayList<String> infos = dao.obterNomeIdadeAltura();
                            TextView nome = findViewById(R.id.nome_perfil);
                            TextView idadeAltura = findViewById(R.id.altura_idade_perfil);
                            nome.setText(infos.get(0));
                            idadeAltura.setText(infos.get(3)+"m /"+infos.get(1)+" anos");
                        }catch (Exception e){

                            Toast.makeText(context,"Algum campo se encontra vazio... Tente novamente!",Toast.LENGTH_LONG).show();
                        }



                    }
                });
                msgBox.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(context,"As alterações não foram salvas.",Toast.LENGTH_LONG).show();
                    }
                });
                msgBox.setView(view);
                msgBox.show();
            }
        });







    }

    public void saveWeight(){
        diaOne = findViewById(R.id.dia_one);
        mesOne = findViewById(R.id.mes_one);
        anoOne = findViewById(R.id.ano_one);

        pesoAtual= findViewById(R.id.peso);
        meta = findViewById(R.id.meta);
        bf = findViewById(R.id.bf);

        ombros = findViewById(R.id.ombros);
        peitoral = findViewById(R.id.peitoral);
        bracoE = findViewById(R.id.bracoE);
        bracoD = findViewById(R.id.bracoD);
        cintura = findViewById(R.id.cintura);
        quadril = findViewById(R.id.quadril);
        pernaE = findViewById(R.id.pernaE);
        pernaD = findViewById(R.id.pernaD);
        panturrilhaE = findViewById(R.id.panturrilhaE);
        panturrilhaD = findViewById(R.id.panturrilhaD);

        dao = new InfoDAO(getApplicationContext());

        ContentValues values = new ContentValues();
        try{
            values.put("data", diaOne.getText().toString()+"-"+mesOne.getText().toString()+"-"+anoOne.getText().toString());
            values.put("peso", Float.valueOf(String.valueOf(pesoAtual.getText())).floatValue());
            values.put("meta", Float.valueOf(String.valueOf(meta.getText())).floatValue());
            values.put("bf", Float.valueOf(String.valueOf(bf.getText())).floatValue());
            values.put("ombro", Integer.parseInt(String.valueOf(ombros.getText())));
            values.put("bracod", Integer.parseInt(String.valueOf(bracoD.getText())));
            values.put("bracoe", Integer.parseInt(String.valueOf(bracoE.getText())));
            values.put("peitoral", Integer.parseInt(String.valueOf(peitoral.getText())));
            values.put("cintura", Integer.parseInt(String.valueOf(cintura.getText())));
            values.put("quadril", Integer.parseInt(String.valueOf(quadril.getText())));
            values.put("pernad", Integer.parseInt(String.valueOf(pernaD.getText())));
            values.put("pernae", Integer.parseInt(String.valueOf(pernaE.getText())));
            values.put("panturrilhad", Integer.parseInt(String.valueOf(panturrilhaD.getText())));
            values.put("panturrilhae", Integer.parseInt(String.valueOf(panturrilhaE.getText())));
            dao.insertValues("body", values);
            Toast.makeText(getApplicationContext(),"Informações salvas.", Toast.LENGTH_SHORT).show();

        }catch (Exception ex){
            AlertDialog.Builder msgBox = new AlertDialog.Builder(this);
            msgBox.setTitle("Ops... Algum campo se encontra vazio!");
            msgBox.setMessage("Preencha todos os campos para salvar os dados.");
            msgBox.show();


        }



    }
    @Override
    public void onBackPressed(){
        Intent intent = new Intent(this, MyBody.class);
        startActivity(intent);
    }


}