package com.example.aplicativonutricao;

import android.view.Menu;

public class MenuModel {
    private int id;
    private int foodGroup;
    private int food;
    private double portion;
    private int subs;

    public MenuModel(int id, int foodGroup, int food, double portion, int subs){
        this.id = id;
        this.foodGroup = foodGroup;
        this.food = food;
        this.portion = portion;
        this.subs = subs;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFoodGroup() {
        return foodGroup;
    }

    public void setFoodGroup(int foodGroup) {
        this.foodGroup = foodGroup;
    }

    public int getFood() {
        return food;
    }

    public void setFood(int food) {
        this.food = food;
    }

    public double getPortion() {
        return portion;
    }

    public void setPortion(double portion) {
        this.portion = portion;
    }

    public int getSubs() {
        return subs;
    }

    public void setSubs(int subs) {
        this.subs = subs;
    }
}
