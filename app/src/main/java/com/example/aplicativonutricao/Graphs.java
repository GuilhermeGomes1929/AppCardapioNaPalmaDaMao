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
import android.widget.Toast;

import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;

public class Graphs extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    private InfoDAO dao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphs);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Gráficos");

        setupNavigationDrawer();
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        dao = new InfoDAO(this);



        setupGraphs(R.id.graph_weight, dao.obterPesoGrafico());
        setupGraphs(R.id.graph_bf, dao.obterBfGrafico());
        setupGraphs(R.id.graph_ombros, dao.obterOmbrosGrafico());
        setupGraphs(R.id.graph_bracos, dao.obterbracosGrafico());
        setupGraphs(R.id.graph_peitoral, dao.obterPeitoralGrafico());
        setupGraphs(R.id.graph_cintura, dao.obterCinturaGrafico());
        setupGraphs(R.id.graph_quadril, dao.obterQuadrilGrafico());
        setupGraphs(R.id.graph_pernas, dao.obterPernasGrafico());
        setupGraphs(R.id.graph_panturrilhas, dao.obterPanturrilhasGrafico());






    }

    public void setupGraphs(int i, ArrayList<String> list){
        GraphView graphView = findViewById(i);
        LineGraphSeries<DataPoint> lineGraphSeries = new LineGraphSeries<>(getData(list));
        lineGraphSeries.setColor(getResources().getColor(R.color.colorPrimary));
        lineGraphSeries.setThickness(8);
        lineGraphSeries.setDrawDataPoints(true);


        graphView.addSeries(lineGraphSeries);

        graphView.getViewport().setYAxisBoundsManual(true);
        graphView.getViewport().setXAxisBoundsManual(true);


        graphView.getViewport().setMinY(getMinValueOfList(list) - 2);
        graphView.getViewport().setMaxY(getMaxValueOfList(list) + 2);

        graphView.getViewport().setMinX(0);
        graphView.getViewport().setMaxX(2);

        graphView.getViewport().setScrollable(true);


        final String[] strings = getString(dao.obterData());

        graphView.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter(){
            @Override
            public String formatLabel(double value, boolean isValueX){
                if (isValueX){


                    String[] splitValue = Double.toString(value).split("\\.");
                    if (Integer.parseInt(splitValue[1]) > 0){
                        return " ";
                    }else{
                        int valor = (int) value;
                        return strings[valor];
                    }

                }else {
                    return super.formatLabel(value,isValueX);
                }

            }
        });


        graphView.getGridLabelRenderer().setGridColor(getResources().getColor(R.color.colorPrimaryDark));
        graphView.getGridLabelRenderer().setLabelsSpace(10);
        
        graphView.getGridLabelRenderer().setHumanRounding(false);

    }

    public Float getMaxValueOfList(ArrayList<String> list){

        Float maxValue = Float.valueOf(0);

        for (int i=0; i < list.size(); i++){
            if (Float.valueOf(list.get(i)) > maxValue){
                maxValue = Float.valueOf(list.get(i));
            }

        }

        return maxValue;
    }

    public Float getMinValueOfList(ArrayList<String> list){
        Float minValue = Float.valueOf(100 *1000);

        for (int i=0; i<list.size(); i++){
            if (Float.valueOf(list.get(i)) < minValue){
                minValue = Float.valueOf(list.get(i));

            }
        }
        return minValue;
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
    public String[] getString(ArrayList<String> datas){
        String[] list = new String[datas.size()];
        for (int i = 0; i < datas.size(); i++){
            list[i] = datas.get(i);
        }

        return list;
    }

    public DataPoint[] getData( ArrayList<String> list){

        DataPoint[] dataPoints = new DataPoint[list.size()];
        for (int i = 0; i<list.size(); i++){
            dataPoints[i] = new DataPoint(i , Float.valueOf(list.get(i)));

        }
        return dataPoints;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){

            case R.id.nav_graphics:
                Toast.makeText(this,"Gráficos", Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_menu:
                Intent intentMenu = new Intent(this, MainActivity.class);
                startActivity(intentMenu);
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

    //configurar o botão back
    @Override
    public void onBackPressed(){

        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();

        }
    }
}