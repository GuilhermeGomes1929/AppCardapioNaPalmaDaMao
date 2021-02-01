package com.example.aplicativonutricao;

import android.Manifest;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;



public class ShowMenu extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{


    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    private String menuName;


    private ListView listViewBreakFast;
    private ListView listViewSnack;
    private ListView listViewLunch;
    private ListView listViewAftersnack;
    private ListView listViewDinner;
    private ListView listViewSupper;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_menu);




        //Título da activity
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Meu cardápio");

        //Setup da data
        setDate();

        //Verifica as permissões
        checkPermissions();

        //Setup da navigationview
        setupNavigationDrawer();
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        changeName();

        //alertDialog para escolher o cardapio
        chooseMenu();

        //setup da expansão de botões
        setupExpand();
    }

    public void changeName(){

        View headerLayout = getLayoutInflater().inflate(R.layout.nav_header, null);
        TextView name = headerLayout.findViewById(R.id.nav_name);

        InfoDAO infoDAO = new InfoDAO(this);


        name.setText(infoDAO.obterNomeIdadeAltura().get(0));
    }

    public void setDate(){
        TextView dateText = findViewById(R.id.date);
        Date date = new Date();
        String dateString = new SimpleDateFormat("dd/MM/yyyy").format(date);
        dateText.setText(dateString);
    }


    public void chooseMenu(){

        AlertDialog.Builder msgBox = new AlertDialog.Builder(this);
        ArrayList<String> menuList = new Dir().listDirectory();

        if (menuList.isEmpty()){
            msgBox.setTitle("Não existem cardápios no celular.");
            msgBox.setMessage("Peça para o seu nutricionista enviar um cardápio válido ou atualize a página.");
            msgBox.show();
        }else{

            View view = getLayoutInflater().inflate(R.layout.dialog_spinner, null);
            final Spinner spinner = view.findViewById(R.id.spinner_edit_peso);
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item, menuList);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);

            msgBox.setTitle("Escolha o cardápio:");
            msgBox.setView(view);
            msgBox.setPositiveButton("Escolher", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    menuName = spinner.getSelectedItem().toString();
                    FoodListDAO foodListDAO = new FoodListDAO();
                    if (foodListDAO.checkConnection()){


                        setupListViewsMenu();
                    }else {
                        Toast.makeText(getApplicationContext(), "Erro ao selecionar o cardápio", Toast.LENGTH_SHORT).show();
                    }

                }
            });

            msgBox.show();

        }
    }



    public void setupListViewsMenu(){
        try{

            final FoodListDAO foodListDAO = new FoodListDAO();
            MenuDAO menuDAO = new MenuDAO(menuName);

            MyListView mylistViewBreakFast = findViewById(R.id.list_menu_breakfast);
            MyListView mylistViewSnack = findViewById(R.id.list_menu_snack);
            MyListView mylistViewLunch = findViewById(R.id.list_menu_lunch);
            MyListView mylistViewAftersnack = findViewById(R.id.list_menu_aftersnack);
            MyListView mylistViewDinner = findViewById(R.id.list_menu_dinner);
            MyListView mylistViewSupper = findViewById(R.id.list_menu_supper);


            ArrayList<ListView> listViewArrayList = new ArrayList<>();
            listViewArrayList.add(mylistViewBreakFast);
            listViewArrayList.add(mylistViewSnack);
            listViewArrayList.add(mylistViewLunch);
            listViewArrayList.add(mylistViewAftersnack);
            listViewArrayList.add(mylistViewDinner);
            listViewArrayList.add(mylistViewSupper);


            final String[] table = new String[]{"[café da manhã]","[lanche]","[almoço]","[lanche da tarde]","[jantar]","[ceia]"};


            ArrayList<String> tables = foodListDAO.getAllTables();


            for (int x = 0; x < tables.size(); x++){

                ArrayList<String> foods = new ArrayList<>();
                final ArrayList<String> foodNames = new ArrayList<>();
                final ArrayList<String> homePortions = new ArrayList<>();


                final ArrayList<MenuModel> index = menuDAO.getData(table[x]);


                for (int i = 0; i < index.size(); i++){

                    String tableIndex = tables.get(index.get(i).getFoodGroup());
                    int indexFoodName = index.get(i).getFood();
                    double portion = index.get(i).getPortion();

                    FoodListModel foodListModel = foodListDAO.getItemMenu(tableIndex, indexFoodName);



                    int weight = foodListModel.getWeight();

                    foods.add(weight *  portion + "g " +foodListModel.getFoodName());
                    foodNames.add(foodListModel.getFoodName());
                    homePortions.add(foodListModel.getHomePortions() * portion + " "+ foodListModel.getHomeMeasure());


                }

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, foods);
                listViewArrayList.get(x).setAdapter(arrayAdapter);

                listViewArrayList.get(x).setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        alertDialogSubs(position, index, foodNames.get(position), homePortions.get(position));
                    }
                });

            }


        }catch(Exception e){
            Toast.makeText(getApplicationContext(), "Erro ao conectar no banco de dados", Toast.LENGTH_SHORT).show();

        }





    }

    public void alertDialogSubs(int position, ArrayList<MenuModel> listMenuModel, String foodName, String homePortion){

        FoodListDAO foodListDAO = new FoodListDAO();
        ArrayList<String> tables = foodListDAO.getAllTables();

        AlertDialog.Builder subsBox = new AlertDialog.Builder(this);
        View dialogShowSubs = getLayoutInflater().inflate(R.layout.dialog_show_subs, null);
        TextView foodNameTextView = dialogShowSubs.findViewById(R.id.food_name);
        TextView homePortionTextView = dialogShowSubs.findViewById(R.id.food_homeportion);
        ListView listViewSubs = dialogShowSubs.findViewById(R.id.food_subs);
        CardView subsCard = dialogShowSubs.findViewById(R.id.subs_card);

        foodNameTextView.setText(foodName);
        homePortionTextView.setText(homePortion);

        if (listMenuModel.get(position).getSubs() == 1){

            int foodgroup = listMenuModel.get(position).getFoodGroup();
            Double portion = listMenuModel.get(position).getPortion();

            final ArrayList<String[]> subs = new ArrayList<>();


            ArrayList<FoodListModel> foodListModels = foodListDAO.getAllFood(tables.get(foodgroup));

            for (int i = 0; i < foodListModels.size(); i++){


                subs.add(new String[]{(double) foodListModels.get(i).getWeight() * portion +"g " + foodListModels.get(i).getFoodName(),
                        "ou "+ foodListModels.get(i).getHomePortions() * portion + " " + foodListModels.get(i).getHomeMeasure()});

            }

            ArrayAdapter<String[]> arrayAdapter = new ArrayAdapter<String[]>(getApplicationContext(), android.R.layout.simple_list_item_2, android.R.id.text1, subs){
                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    View viewAdapter = super.getView(position, convertView, parent);
                    String[] entry = subs.get(position);
                    TextView text1 = viewAdapter.findViewById(android.R.id.text1);
                    TextView text2 = viewAdapter.findViewById(android.R.id.text2);
                    text1.setText(entry[0]);
                    text2.setText(entry[1]);

                    return viewAdapter;
                }
            };

            listViewSubs.setAdapter(arrayAdapter);

        }else{

            subsCard.setVisibility(View.GONE);

        }




        subsBox.setView(dialogShowSubs);
        subsBox.show();


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
                expand(contExpand1, buttonExpand1, listViewBreakFast.getCount());

            }
        });

        buttonExpand2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expand(contExpand2, buttonExpand2, listViewSnack.getCount());
            }
        });

        buttonExpand3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expand(contExpand3, buttonExpand3, listViewLunch.getCount());
            }
        });

        buttonExpand4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expand(contExpand4, buttonExpand4, listViewAftersnack.getCount());
            }
        });

        buttonExpand5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expand(contExpand5, buttonExpand5, listViewDinner.getCount());
            }
        });

        buttonExpand6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expand(contExpand6, buttonExpand6, listViewSupper.getCount()
                );
            }
        });



    }

    public void expand(LinearLayout numberId, Button changeButton, int count) {
        if (count == 0){
            AlertDialog.Builder msgBox = new AlertDialog.Builder(this);
            msgBox.setTitle("Ops.. Cardápio vazio!");
            msgBox.setMessage("Selecione um cardápio válido.");
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
                Toast.makeText(this,"Cardápio", Toast.LENGTH_LONG).show();
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


    public void checkPermissions(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) !=  PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }
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
            chooseMenu();
        }
        return true;
    }

}