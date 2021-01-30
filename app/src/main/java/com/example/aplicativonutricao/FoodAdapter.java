package com.example.aplicativonutricao;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class FoodAdapter extends ArrayAdapter<String> {
    private final Context context;


    private final ArrayList<String> elementos;

    public FoodAdapter(Context context,int r, ArrayList<String> elementos){
        super(context, r, elementos);
        this.context = context;
        this.elementos = elementos;



    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.listvew_cardapio, parent, false);


        TextView alimentoListView = rowView.findViewById(R.id.alimento);

        alimentoListView.setText(elementos.get(position));


        return rowView;
    }

}
