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
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Map;

public class Graphs extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    private InfoDAO dao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphs);

        //Setup do título da activity
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Gráficos");

        //Setup da navigation drawer
        setupNavigationDrawer();
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View navName = navigationView.getHeaderView(0);
        TextView name = navName.findViewById(R.id.nav_name);
        changeName(name);



        //Possibilita a leitura e modificação do banco de dados
        dao = new InfoDAO(this);

        ArrayList<String> allDates = dao.obterData();


        setupGraphs(R.id.graph_weight, dao.obterPesoGrafico(), allDates, "Peso");
        setupGraphs(R.id.graph_bf, dao.obterBfGrafico(), allDates, "Gordura corporal");
        setupGraphs(R.id.graph_ombros, dao.obterOmbrosGrafico(), allDates, "Ombros");
        setupGraphs(R.id.graph_peitoral, dao.obterPeitoralGrafico(),allDates,"Peitoral");
        setupGraphs(R.id.graph_bracos,dao.obterbracosGrafico(), allDates,"Braços");
        setupGraphs(R.id.graph_cintura, dao.obterCinturaGrafico(), allDates, "Cintura");
        setupGraphs(R.id.graph_quadril, dao.obterQuadrilGrafico(), allDates, "Quadril");
        setupGraphs(R.id.graph_pernas, dao.obterPernasGrafico(), allDates, "Pernas");
        setupGraphs(R.id.graph_panturrilhas, dao.obterPanturrilhasGrafico(), allDates, "Panturrilhas");









    }

    public void changeName(View headerLayout){

        TextView name = headerLayout.findViewById(R.id.nav_name);
        InfoDAO infoDAO = new InfoDAO(this);
        name.setText(infoDAO.obterNomeIdadeAltura().get(0));
    }

    public void setupGraphs(int chartId, ArrayList<String> data, final ArrayList<String> date , String label){

        LineChart lineChart = findViewById(chartId);

        if (data.isEmpty()){
            lineChart.setNoDataText("Não há informações salvas.");
        }else {

            ArrayList<Entry> entries = new ArrayList<>();
            XAxis xAxis = lineChart.getXAxis();

            for (int i = 0; i < data.size(); i++){
                entries.add( new Entry(i , Float.valueOf(data.get(i))));

            }

            Description description = new Description();
            description.setText(" ");

            LineDataSet lineDataSet = new LineDataSet(entries,label);
            lineDataSet.setCircleColor(R.color.colorPrimaryDark);
            lineDataSet.setCircleColorHole(R.color.colorPrimaryDark);


            LineData lineData = new LineData(lineDataSet);
            lineChart.setData(lineData);
            lineChart.invalidate();
            lineChart.setDrawBorders(true);
            lineChart.setBorderColor(R.color.colorPrimary);
            lineChart.setDescription(description);


            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

            xAxis.setValueFormatter(new IAxisValueFormatter() {
                @Override
                public String getFormattedValue(float value, AxisBase axis) {
                    for (int n = 0; n < date.size(); n++){
                        if (n == value){
                            return date.get(n);
                        }
                    }
                    return "";
                }

            });



        }


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


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){

            case R.id.nav_waterReminder:
                Intent intentWater = new Intent(this, WaterReminder.class);
                startActivity(intentWater);
                finish();
                break;

            case R.id.nav_graphics:
                Toast.makeText(this,"Gráficos", Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_menu:
                Intent intentMenu = new Intent(this, ShowMenu.class);
                startActivity(intentMenu);
                finish();
                break;

            case R.id.nav_mybody:
                Intent intentMyBody = new Intent(this, MyBody.class);
                startActivity(intentMyBody);
                finish();
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