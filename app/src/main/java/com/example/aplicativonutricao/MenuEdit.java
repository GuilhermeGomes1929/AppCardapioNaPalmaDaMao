package com.example.aplicativonutricao;


import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;

public class MenuEdit extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private InfoDAO dao;

    private Spinner foodGroup;
    private Spinner foodAmount;
    private Spinner foodChoose;
    private Spinner timeToEat;
    private ArrayAdapter<CharSequence> adapterTimeToEat;

    private ListView listViewBreakFast;
    private ListView listViewSnack;
    private ListView listViewLunch;
    private ListView listViewAftersnack;
    private ListView listViewDinner;
    private ListView listViewSupper;


    private ArrayList<String> alimentosListBreakfast;
    private ArrayList<String> quantidadeListBreakfast;
    private ArrayList<String> alimentosListSnack;
    private ArrayList<String> quantidadeListSnack;
    private ArrayList<String> alimentosListLunch;
    private ArrayList<String> quantidadeListLunch;
    private ArrayList<String> alimentosListAftersnack;
    private ArrayList<String> quantidadeListAftersnack;
    private ArrayList<String> alimentosListDinner;
    private ArrayList<String> quantidadeListDinner;
    private ArrayList<String> alimentosListSupper;
    private ArrayList<String> quantidadeListSupper;

    private Toolbar toolbar;

    private Button buttonDelete1;
    private Button buttonDelete2;
    private Button buttonDelete3;
    private Button buttonDelete4;
    private Button buttonDelete5;
    private Button buttonDelete6;




    private Alimentos alimentos;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_edit);

        //Configura título da activity
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Editar cardápio");

        //Configura o spinner do grupo de alimentos
        foodGroup = findViewById(R.id.spinner_food_group);
        ArrayAdapter<CharSequence> adapterFoodGroup = ArrayAdapter.createFromResource(this, R.array.group_foods, android.R.layout.simple_spinner_item);
        adapterFoodGroup.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        foodGroup.setAdapter(adapterFoodGroup);
        foodGroup.setOnItemSelectedListener(this);

        //Configura o spinner da quantidade de alimentos
        foodAmount = findViewById(R.id.spinner_food_amount);
        ArrayAdapter<CharSequence> adapterFoodAmount = ArrayAdapter.createFromResource(this, R.array.amount, android.R.layout.simple_spinner_item);
        adapterFoodAmount.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        foodAmount.setAdapter(adapterFoodAmount);

        //Configura o spinner da refeição
        timeToEat = findViewById(R.id.spinner_time_to_eat);
        adapterTimeToEat = ArrayAdapter.createFromResource(this, R.array.meal, android.R.layout.simple_spinner_item);
        adapterTimeToEat.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        timeToEat.setAdapter(adapterTimeToEat);


        //Permite fazer alterações no banco de dados
        dao = new InfoDAO(this);

        setupExpand();

        //Configurar a listview para o cardápio aparecer
        listViewBreakFast = findViewById(R.id.list_breakfast);
        alimentosListBreakfast = dao.obterAlimento("breakfast");
        quantidadeListBreakfast = dao.obterPeso("breakfast");
        FoodAdapter adapterBreakfast = new FoodAdapter(this, R.layout.listvew_cardapio, adicionarFood(quantidadeListBreakfast, alimentosListBreakfast));
        listViewBreakFast.setAdapter(adapterBreakfast);

        listViewSnack = findViewById(R.id.list_snack);
        alimentosListSnack = dao.obterAlimento("snack");
        quantidadeListSnack = dao.obterPeso("snack");
        FoodAdapter adapterSnack = new FoodAdapter(this, R.layout.listvew_cardapio, adicionarFood(quantidadeListSnack, alimentosListSnack));
        listViewSnack.setAdapter(adapterSnack);

        listViewLunch = findViewById(R.id.list_lunch);
        alimentosListLunch = dao.obterAlimento("lunch");
        quantidadeListLunch = dao.obterPeso("lunch");
        FoodAdapter adapterLunch = new FoodAdapter(this, R.layout.listvew_cardapio, adicionarFood(quantidadeListLunch, alimentosListLunch));
        listViewLunch.setAdapter(adapterLunch);

        listViewAftersnack = findViewById(R.id.list_aftersnack);
        alimentosListAftersnack = dao.obterAlimento("aftersnack");
        quantidadeListAftersnack = dao.obterPeso("aftersnack");
        FoodAdapter adapterAftersnack = new FoodAdapter(this, R.layout.listvew_cardapio, adicionarFood(quantidadeListAftersnack, alimentosListAftersnack));
        listViewAftersnack.setAdapter(adapterAftersnack);

        listViewDinner = findViewById(R.id.list_dinner);
        alimentosListDinner = dao.obterAlimento("dinner");
        quantidadeListDinner = dao.obterPeso("dinner");
        FoodAdapter adapterDinner = new FoodAdapter(this, R.layout.listvew_cardapio, adicionarFood(quantidadeListDinner, alimentosListDinner));
        listViewDinner.setAdapter(adapterDinner);

        listViewSupper = findViewById(R.id.list_supper);
        alimentosListSupper = dao.obterAlimento("supper");
        quantidadeListSupper = dao.obterPeso("supper");
        FoodAdapter adapterSupper = new FoodAdapter(this, R.layout.listvew_cardapio, adicionarFood(quantidadeListSupper, alimentosListSupper));
        listViewSupper.setAdapter(adapterSupper);

        listViewBreakFast.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                alertDialog("breakfast", position, listViewBreakFast);


            }
        });

        listViewSnack.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                alertDialog("snack", position, listViewSnack);
            }
        });
        listViewLunch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                alertDialog("lunch", position, listViewLunch);
            }
        });
        listViewAftersnack.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                alertDialog("aftersnack", position, listViewAftersnack);
            }
        });
        listViewDinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                alertDialog("dinner", position, listViewDinner);
            }
        });
        listViewSupper.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                alertDialog("supper", position, listViewSupper);
            }
        });

        setupButtonsDelete();

        listViewBreakFast.getLayoutParams().height = 120 * listViewBreakFast.getCount();
        listViewSnack.getLayoutParams().height = 120 * listViewSnack.getCount();
        listViewLunch.getLayoutParams().height = 120 * listViewLunch.getCount();
        listViewAftersnack.getLayoutParams().height = 120 * listViewAftersnack.getCount();
        listViewDinner.getLayoutParams().height = 120 * listViewDinner.getCount();
        listViewSupper.getLayoutParams().height = 120 * listViewSupper.getCount();




    }

    //AlertDialog
    public void alertDialog(final String table, final int position, final ListView listViewAlert){
        AlertDialog.Builder msgBox = new AlertDialog.Builder(this);
        msgBox.setTitle("Excluindo...");
        msgBox.setIcon(R.drawable.ic_baseline_delete_24);
        msgBox.setMessage("Tem certeza que deseja excluir esse alimento?");
        msgBox.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dao.delete(table, position);


                ArrayList<String> alimentoAlert = dao.obterAlimento(table);
                ArrayList<String> quantidadeAlert = dao.obterPeso(table);
                FoodAdapter adapterAlert = new FoodAdapter(getApplicationContext(), R.layout.listvew_cardapio, adicionarFood(quantidadeAlert, alimentoAlert));
                listViewAlert.setAdapter(adapterAlert);
                Toast.makeText(getApplicationContext(), "Item excluído", Toast.LENGTH_SHORT).show();

                listViewAlert.getLayoutParams().height = listViewAlert.getCount() * 120;
            }
        });
        msgBox.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(),"Item não excluído", Toast.LENGTH_SHORT).show();
            }
        });
        msgBox.show();
    }

    //adiciona no arraylist
    public ArrayList<String> adicionarFood(ArrayList<String> quantidade, ArrayList<String> alimento){
        Food e = new Food(quantidade, alimento);
        ArrayList<String> lista = e.listaTratada();
        return lista;
    }

    //Delete o cardapio completo da refeição
    public void deleteDbButton(String msg, final String table, final ListView listViewAlert){
        AlertDialog.Builder msgBox = new AlertDialog.Builder(this);
        msgBox.setTitle("Excluindo...");
        msgBox.setMessage("Tem certeza que deseja excluir todos os alimentos de "+msg);
        msgBox.setIcon(R.drawable.ic_baseline_delete_24);
        msgBox.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dao.deleteDb(table);
                Toast.makeText(getApplicationContext(), "Todos os itens foram excluídos", Toast.LENGTH_SHORT).show();
                ArrayList<String> alimentoAlert = dao.obterAlimento(table);
                ArrayList<String> quantidadeAlert = dao.obterPeso(table);
                FoodAdapter adapterAlert = new FoodAdapter(getApplicationContext(), R.layout.listvew_cardapio, adicionarFood(quantidadeAlert, alimentoAlert));
                listViewAlert.setAdapter(adapterAlert);
                listViewAlert.getLayoutParams().height = 120 * listViewAlert.getCount();
            }
        });
        msgBox.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "Os itens não foram excluídos", Toast.LENGTH_SHORT).show();
            }
        });
        msgBox.show();

    }

    //Setup dos botões de deletar o cardápio
    public void setupButtonsDelete(){
        buttonDelete1 = findViewById(R.id.buttonDelete1);
        buttonDelete2 = findViewById(R.id.buttonDelete2);
        buttonDelete3 = findViewById(R.id.buttonDelete3);
        buttonDelete4 = findViewById(R.id.buttonDelete4);
        buttonDelete5 = findViewById(R.id.buttonDelete5);
        buttonDelete6 = findViewById(R.id.buttonDelete6);

        buttonDelete1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listViewBreakFast = findViewById(R.id.list_breakfast);
                deleteDbButton("café da manhã","breakfast" , listViewBreakFast);
            }
        });
        buttonDelete2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listViewSnack = findViewById(R.id.list_snack);
                deleteDbButton("lanche","snack" , listViewSnack);
            }
        });
        buttonDelete3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listViewLunch = findViewById(R.id.list_lunch);
                deleteDbButton("almoço","lunch" , listViewLunch);
            }
        });
        buttonDelete4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listViewAftersnack = findViewById(R.id.list_aftersnack);
                deleteDbButton("lanche da tarde","aftersnack", listViewAftersnack );
            }
        });
        buttonDelete5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listViewDinner = findViewById(R.id.list_dinner);
                deleteDbButton("jantar","dinner", listViewDinner );
            }
        });
        buttonDelete6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listViewSupper = findViewById(R.id.list_supper);
                deleteDbButton("ceia","supper", listViewSupper );
            }
        });

    }





    //Salva o alimento escolhido no banco de dados
    public void salvar(View view){


        String meal = null;
        String stringFoodChoose = foodChoose.getSelectedItem().toString();
        int stringFoodAmount = Integer.parseInt(foodAmount.getSelectedItem().toString());
        int stringFoodTime = timeToEat.getSelectedItemPosition();
        int stringFoodGroup = foodGroup.getSelectedItemPosition();

        listViewBreakFast = findViewById(R.id.list_breakfast);
        listViewSnack = findViewById(R.id.list_snack);
        listViewLunch = findViewById(R.id.list_lunch);
        listViewAftersnack = findViewById(R.id.list_aftersnack);
        listViewDinner = findViewById(R.id.list_dinner);
        listViewSupper = findViewById(R.id.list_supper);


        alimentos = new Alimentos();
        String peso = String.valueOf(alimentos.getPeso(stringFoodGroup,foodChoose.getSelectedItemPosition()) * stringFoodAmount);


        if (stringFoodTime == 0){


            meal = "breakfast";
            dao.insert(meal, stringFoodAmount, stringFoodChoose, stringFoodGroup, foodChoose.getSelectedItemPosition(), peso );
            alimentosListBreakfast = dao.obterAlimento(meal);
            quantidadeListBreakfast = dao.obterPeso(meal);
            FoodAdapter adapterBreakfast = new FoodAdapter(getApplicationContext(), R.layout.listvew_cardapio, adicionarFood(quantidadeListBreakfast, alimentosListBreakfast));
            listViewBreakFast.setAdapter(adapterBreakfast);
            listViewBreakFast.getLayoutParams().height = 120 * listViewBreakFast.getCount();


            Toast.makeText(getApplicationContext(), "Alimento adicionado", Toast.LENGTH_LONG).show();

        }
        if (stringFoodTime == 1){
            meal = "snack";
            dao.insert(meal, stringFoodAmount, stringFoodChoose, stringFoodGroup, foodChoose.getSelectedItemPosition(),peso );

            alimentosListSnack = dao.obterAlimento(meal);
            quantidadeListSnack = dao.obterPeso(meal);

            FoodAdapter adapterSnack = new FoodAdapter(getApplicationContext(), R.layout.listvew_cardapio, adicionarFood(quantidadeListSnack, alimentosListSnack));
            listViewSnack.setAdapter(adapterSnack);
            listViewSnack.getLayoutParams().height = 120 * listViewSnack.getCount();

            Toast.makeText(getApplicationContext(), "Alimento adicionado", Toast.LENGTH_LONG).show();

        }
        if (stringFoodTime == 2){
            meal = "lunch";
            dao.insert(meal, stringFoodAmount, stringFoodChoose, stringFoodGroup, foodChoose.getSelectedItemPosition(),peso );

            alimentosListLunch = dao.obterAlimento(meal);
            quantidadeListLunch = dao.obterPeso(meal);

            FoodAdapter adapterLunch = new FoodAdapter(getApplicationContext(), R.layout.listvew_cardapio, adicionarFood(quantidadeListLunch, alimentosListLunch));
            listViewLunch.setAdapter(adapterLunch);
            listViewLunch.getLayoutParams().height = 120 * listViewLunch.getCount();


            Toast.makeText(getApplicationContext(), "Alimento adicionado", Toast.LENGTH_LONG).show();
        }
        if (stringFoodTime == 3){
            meal = "aftersnack";
            dao.insert(meal, stringFoodAmount, stringFoodChoose, stringFoodGroup, foodChoose.getSelectedItemPosition(),peso );

            alimentosListAftersnack = dao.obterAlimento(meal);
            quantidadeListAftersnack = dao.obterPeso(meal);

            FoodAdapter adapterAftersnack = new FoodAdapter(getApplicationContext(), R.layout.listvew_cardapio, adicionarFood(quantidadeListAftersnack, alimentosListAftersnack));
            listViewAftersnack.setAdapter(adapterAftersnack);
            listViewAftersnack.getLayoutParams().height = 120 * listViewAftersnack.getCount();


            Toast.makeText(getApplicationContext(), "Alimento adicionado", Toast.LENGTH_LONG).show();
        }
        if (stringFoodTime == 4){
            meal = "dinner";
            dao.insert(meal, stringFoodAmount, stringFoodChoose, stringFoodGroup, foodChoose.getSelectedItemPosition(),peso );

            alimentosListDinner = dao.obterAlimento(meal);
            quantidadeListDinner = dao.obterPeso(meal);

            FoodAdapter adapterDinner = new FoodAdapter(getApplicationContext(), R.layout.listvew_cardapio, adicionarFood(quantidadeListDinner, alimentosListDinner));
            listViewDinner.setAdapter(adapterDinner);
            listViewDinner.getLayoutParams().height = 120 * listViewDinner.getCount();

            Toast.makeText(getApplicationContext(), "Alimento adicionado", Toast.LENGTH_LONG).show();

        }
        if (stringFoodTime == 5){
            meal = "supper";
            dao.insert(meal, stringFoodAmount, stringFoodChoose, stringFoodGroup, foodChoose.getSelectedItemPosition(),peso );

            alimentosListSupper =  dao.obterAlimento(meal);
            quantidadeListSupper = dao.obterPeso(meal);

            FoodAdapter adapterSupper = new FoodAdapter(getApplicationContext(), R.layout.listvew_cardapio, adicionarFood(quantidadeListSupper, alimentosListSupper));
            listViewSupper.setAdapter(adapterSupper);
            listViewSupper.getLayoutParams().height = 120 * listViewSupper.getCount();

            Toast.makeText(getApplicationContext(), "Alimento adicionado", Toast.LENGTH_LONG).show();

        }

    }


    //Configura o spinner de alimento com base no grupo de alimentos escolhidos
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String food = parent.getSelectedItem().toString();

        int[] length;
        int cont;
        ArrayList<String> lista = new ArrayList<String>();

        Alimentos alimentos = new Alimentos();
        length = alimentos.getLength();
        cont = 0;

        if (food == parent.getItemAtPosition(0).toString()){
            lista.add("Alimento");
            foodChoose = findViewById(R.id.spinner_food_choose);
            ArrayAdapter<String> adapterDiff = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, lista);
            adapterDiff.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            foodChoose.setAdapter(adapterDiff);

        }

        if (food == parent.getItemAtPosition(1).toString()){

            while (cont < length[0]){
                lista.add(alimentos.getLegumes(cont)[0]);
                cont = cont + 1;
            }

            foodChoose = findViewById(R.id.spinner_food_choose);
            ArrayAdapter<String> adapterDiff = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, lista);
            adapterDiff.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            foodChoose.setAdapter(adapterDiff);


        }

        if (food == parent.getItemAtPosition(2).toString()){

            while (cont < length[1]){
                lista.add(alimentos.getFrutas(cont)[0]);
                cont = cont + 1;
            }

            foodChoose = findViewById(R.id.spinner_food_choose);
            ArrayAdapter<String> adapterDiff = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, lista);
            adapterDiff.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            foodChoose.setAdapter(adapterDiff);
        }

        if (food == parent.getItemAtPosition(3).toString()){

            while (cont < length[2]){
                lista.add(alimentos.getCereais(cont)[0]);
                cont = cont + 1;
            }

            foodChoose = findViewById(R.id.spinner_food_choose);
            ArrayAdapter<String> adapterDiff = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, lista);
            adapterDiff.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            foodChoose.setAdapter(adapterDiff);
        }

        if (food == parent.getItemAtPosition(4).toString()){

            while (cont < length[3]){
                lista.add(alimentos.getCarnes(cont)[0]);
                cont = cont + 1;
            }

            foodChoose = findViewById(R.id.spinner_food_choose);
            ArrayAdapter<String> adapterDiff = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, lista);
            adapterDiff.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            foodChoose.setAdapter(adapterDiff);
        }

        if (food == parent.getItemAtPosition(5).toString()){

            while (cont < length[4]){
                lista.add(alimentos.getLeite(cont)[0]);
                cont = cont + 1;
            }

            foodChoose = findViewById(R.id.spinner_food_choose);
            ArrayAdapter<String> adapterDiff = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, lista);
            adapterDiff.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            foodChoose.setAdapter(adapterDiff);
        }

        if (food == parent.getItemAtPosition(6).toString()){

            while (cont < length[5]){
                lista.add(alimentos.getGraos(cont)[0]);
                cont = cont + 1;
            }

            foodChoose = findViewById(R.id.spinner_food_choose);
            ArrayAdapter<String> adapterDiff = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, lista);
            adapterDiff.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            foodChoose.setAdapter(adapterDiff);
        }


    }

    //Configura a expansão da listview do cardápio
    public void setupExpand(){
        final LinearLayout contExpand1 = findViewById(R.id.contBreakfast);
        final Button buttonEdit1 = findViewById(R.id.buttonEdit1);
        final LinearLayout contExpand2 = findViewById(R.id.contSnack);
        final Button buttonEdit2 = findViewById(R.id.buttonEdit2);
        final LinearLayout contExpand3 = findViewById(R.id.contLunch);
        final Button buttonEdit3 = findViewById(R.id.buttonEdit3);
        final LinearLayout contExpand4 = findViewById(R.id.contAftersnack);
        final Button buttonEdit4 = findViewById(R.id.buttonEdit4);
        final  LinearLayout contExpand5 = findViewById(R.id.contDinner);
        final Button buttonEdit5 = findViewById(R.id.buttonEdit5);
        final LinearLayout contExpand6 = findViewById(R.id.contSupper);
        final Button buttonEdit6 = findViewById(R.id.buttonEdit6);

        listViewBreakFast = findViewById(R.id.list_breakfast);
        listViewSnack = findViewById(R.id.list_snack);
        listViewLunch = findViewById(R.id.list_lunch);
        listViewAftersnack = findViewById(R.id.list_aftersnack);
        listViewDinner = findViewById(R.id.list_dinner);
        listViewSupper =  findViewById(R.id.list_supper);



        buttonEdit1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expand(v, contExpand1, listViewBreakFast.getCount());

            }
        });
        buttonEdit2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expand(v, contExpand2, listViewSnack.getCount());
            }
        });
        buttonEdit3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expand(v, contExpand3, listViewLunch.getCount());
            }
        });
        buttonEdit4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expand(v, contExpand4, listViewAftersnack.getCount());
            }
        });
        buttonEdit5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expand(v, contExpand5, listViewDinner.getCount());
            }
        });
        buttonEdit6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expand(v, contExpand6, listViewSupper.getCount());
            }
        });

    }

    //Faz a expansão da listview
    public void expand(View view, LinearLayout numberId, int count) {
        if (count == 0){
            if (numberId.getVisibility()==View.GONE) {
                AlertDialog.Builder msgBox = new AlertDialog.Builder(this);
                msgBox.setTitle("Ops.. Cardápio vazio!");
                msgBox.setMessage("Adicione alimentos no cardápio");
                msgBox.show();
            }else{
                numberId.setVisibility(View.GONE);
            }

        }else{
            if (numberId.getVisibility()==View.GONE){
                numberId.setVisibility(View.VISIBLE);
            }else{
                numberId.setVisibility(View.GONE);
            }
        }

    }

    //Não serve ainda
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


}