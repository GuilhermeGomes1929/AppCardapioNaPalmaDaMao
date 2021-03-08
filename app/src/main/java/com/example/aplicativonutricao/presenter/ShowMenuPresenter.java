package com.example.aplicativonutricao.presenter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aplicativonutricao.model.service.Dir;
import com.example.aplicativonutricao.model.dao.FoodListDAO;
import com.example.aplicativonutricao.model.entity.MenuModel;
import com.example.aplicativonutricao.model.service.MyListView;
import com.example.aplicativonutricao.R;
import com.example.aplicativonutricao.model.service.AlertDialogUsages;
import com.example.aplicativonutricao.model.service.ShowMenuService;

import java.util.ArrayList;

public class ShowMenuPresenter {

    private Context showMenuContext;
    private Activity showMenu;
    private AlertDialogUsages usages;
    private ShowMenuService service;


    public ShowMenuPresenter(Activity showMenu){
        this.showMenuContext = showMenu;
        this.showMenu = showMenu;
        this.service = new ShowMenuService(this.showMenuContext, this);

    }

    public void setupMenu(){
        usages = new AlertDialogUsages(showMenu);

        ArrayList<String> menuList = new Dir().listDirectory();
        if (menuList.isEmpty()){
            usages.emptyMenu();
        }
        else{

            final Spinner spinner = new Spinner(showMenuContext);
            spinner.setPadding(10, 10,10,10);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(showMenuContext,android.R.layout.simple_spinner_item, menuList);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);

            DialogInterface.OnClickListener onClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    FoodListDAO foodListDAO = new FoodListDAO();
                    if (foodListDAO.checkConnection()){
                        updateListViewsMenu(spinner.getSelectedItem().toString());
                    }else {

                        Toast.makeText(showMenuContext, "Erro ao selecionar o cardápio", Toast.LENGTH_SHORT).show();
                    }

                }
            };
            usages.chooseMenu(spinner,onClickListener);
        }


    }

    public void updateListViewsMenu(String menuName){

        MyListView mylistViewBreakFast = showMenu.findViewById(R.id.list_menu_breakfast);
        MyListView mylistViewSnack = showMenu.findViewById(R.id.list_menu_snack);
        MyListView mylistViewLunch = showMenu.findViewById(R.id.list_menu_lunch);
        MyListView mylistViewAftersnack = showMenu.findViewById(R.id.list_menu_aftersnack);
        MyListView mylistViewDinner = showMenu.findViewById(R.id.list_menu_dinner);
        MyListView mylistViewSupper = showMenu.findViewById(R.id.list_menu_supper);

        ArrayList<ListView> listViewArrayList = new ArrayList<>();
        listViewArrayList.add(mylistViewBreakFast);
        listViewArrayList.add(mylistViewSnack);
        listViewArrayList.add(mylistViewLunch);
        listViewArrayList.add(mylistViewAftersnack);
        listViewArrayList.add(mylistViewDinner);
        listViewArrayList.add(mylistViewSupper);

        ArrayList<ArrayAdapter<String>> arrayList = service.setupListViewsMenu(listViewArrayList, menuName);
        for (int i = 0; i < arrayList.size(); i++){

            listViewArrayList.get(i).setAdapter(arrayList.get(i));

        }

    }

    public void getAlertDialogSubs(int position, ArrayList<MenuModel> listMenuModel, String foodName, String homePortion){

        usages = new AlertDialogUsages(showMenu);
        FoodListDAO foodListDAO = new FoodListDAO();
        ArrayList<String> tables = foodListDAO.getAllTables();

        View dialogShowSubs = showMenu.getLayoutInflater().inflate(R.layout.dialog_show_subs, null);
        TextView foodNameTextView = dialogShowSubs.findViewById(R.id.food_name);
        TextView homePortionTextView = dialogShowSubs.findViewById(R.id.food_homeportion);
        ListView listViewSubs = dialogShowSubs.findViewById(R.id.food_subs);
        CardView subsCard = dialogShowSubs.findViewById(R.id.subs_card);

        foodNameTextView.setText(foodName);
        homePortionTextView.setText(homePortion);

        ArrayAdapter<String[]> adapter = service.setupAlertDialogSubs(position, listMenuModel, tables);
        if (adapter == null){
            subsCard.setVisibility(View.GONE);
        }else{
            listViewSubs.setAdapter(adapter);
        }
        usages.showSubs(dialogShowSubs);
    }

}
