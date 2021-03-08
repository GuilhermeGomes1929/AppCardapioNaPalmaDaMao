package com.example.aplicativonutricao.model.service;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aplicativonutricao.model.dao.FoodListDAO;
import com.example.aplicativonutricao.model.entity.FoodListModel;
import com.example.aplicativonutricao.model.dao.MenuDAO;
import com.example.aplicativonutricao.model.entity.MenuModel;
import com.example.aplicativonutricao.presenter.ShowMenuPresenter;

import java.util.ArrayList;

public class ShowMenuService {

    private Context showMenuContext;
    private FoodListDAO foodListDAO;
    private MenuDAO menuDAO;
    private  AlertDialogUsages usages;
    private ShowMenuPresenter presenterContext;

    public ShowMenuService(Context showMenuContext, ShowMenuPresenter presenterContext){
        this.showMenuContext = showMenuContext;
        usages = new AlertDialogUsages(showMenuContext);
        this.presenterContext = presenterContext;
    }

    public ArrayList<ArrayAdapter<String>> setupListViewsMenu(ArrayList<ListView> listViewArrayList, String menuName){
        try{

            foodListDAO = new FoodListDAO();
            menuDAO = new MenuDAO(menuName);

            final String[] table = new String[]{"[café da manhã]","[lanche]","[almoço]","[lanche da tarde]","[jantar]","[ceia]"};

            ArrayList<String> tables = foodListDAO.getAllTables();
            ArrayList<ArrayAdapter<String>> arrayAdapterArrayList = new ArrayList<>();


            for (int x = 0; x < table.length; x++){

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

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(showMenuContext, android.R.layout.simple_list_item_1, foods);
                arrayAdapterArrayList.add(arrayAdapter);

                listViewArrayList.get(x).setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        presenterContext.getAlertDialogSubs(position, index, foodNames.get(position), homePortions.get(position));
                    }
                });

            }
            return arrayAdapterArrayList;

        }catch(Exception e){
            Toast.makeText(showMenuContext, "Erro ao conectar no banco de dados", Toast.LENGTH_SHORT).show();
            return null;
        }
    }

    public ArrayAdapter<String[]> setupAlertDialogSubs(int position, ArrayList<MenuModel> listMenuModel, ArrayList<String> tables ){

        if (listMenuModel.get(position).getSubs() == 1){

            int foodgroup = listMenuModel.get(position).getFoodGroup();
            Double portion = listMenuModel.get(position).getPortion();

            final ArrayList<String[]> subs = new ArrayList<>();


            ArrayList<FoodListModel> foodListModels = foodListDAO.getAllFood(tables.get(foodgroup));

            for (int i = 0; i < foodListModels.size(); i++){


                subs.add(new String[]{(double) foodListModels.get(i).getWeight() * portion +"g " + foodListModels.get(i).getFoodName(),
                        "ou "+ foodListModels.get(i).getHomePortions() * portion + " " + foodListModels.get(i).getHomeMeasure()});

            }

            ArrayAdapter<String[]> arrayAdapter = new ArrayAdapter<String[]>(showMenuContext, android.R.layout.simple_list_item_2, android.R.id.text1, subs){
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

            return  arrayAdapter;

        }else{

            return null;

        }

    }

}
