package com.example.aplicativonutricao.model.entity;

public class FoodListModel {
    private int id;
    private String foodGroup;
    private String foodName;
    private int weight;
    private String homeMeasure;
    private int calories;
    private Double homePortions;

    public FoodListModel(int id, String foodGroup,String foodName, int weight, String homeMeasure, int calories, Double homePortions){
        this.id = id;
        this.foodGroup = foodGroup;
        this.foodName = foodName;
        this.weight = weight;
        this.homeMeasure = homeMeasure;
        this.calories = calories;
        this.homePortions = homePortions;
    }

    public int getIdM() {
        return id;
    }

    public void setIdM(int id) {
        this.id = id;
    }

    public String getFoodGroup() {
        return foodGroup;
    }

    public void setFoodGroup(String foodGroup) {
        this.foodGroup = foodGroup;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getHomeMeasure() {
        return homeMeasure;
    }

    public void setHomeMeasure(String homeMeasure) {
        this.homeMeasure = homeMeasure;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public Double getHomePortions() {
        return homePortions;
    }

    public void setHomePortions(Double homePortions) {
        this.homePortions = homePortions;
    }
}
