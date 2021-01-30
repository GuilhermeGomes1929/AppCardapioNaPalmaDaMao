package com.example.aplicativonutricao;

import android.widget.ArrayAdapter;

import java.util.ArrayList;

public class Food {
    private ArrayList<String> quantidade;
    private ArrayList<String> alimento;
    private Alimentos alimentos;

    public Food(ArrayList<String> quantidade, ArrayList<String> alimento){
        this.quantidade = quantidade;
        this.alimento = alimento;
        alimentos = new Alimentos();
    }

    public ArrayList<String> listaTratada(){
        ArrayList<String> lista = new ArrayList<>();
        int length = alimento.size();
        int cont = 0;

        while (cont<length){

            if(alimentos.getLeiteMl().contains(alimento.get(cont))){
                lista.add(quantidade.get(cont)+ "ml " +alimento.get(cont));

            }else{
                lista.add(quantidade.get(cont) + "g " + alimento.get(cont));
            }

            cont = cont + 1;
        }

        return  lista;
    }


}
