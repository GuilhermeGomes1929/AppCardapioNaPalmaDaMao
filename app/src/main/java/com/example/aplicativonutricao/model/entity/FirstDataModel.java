package com.example.aplicativonutricao.model.entity;

public class FirstDataModel {
    private String name;
    private String age;
    private String sex;
    private Float height;

    public FirstDataModel(String name, String age, String sex, Float height) {
        this.name = name;
        this.age = age;
        this.sex = sex;
        this.height = height;
    }

    public FirstDataModel() {
    }

    public boolean verifyData(){
        if (name != null && age != null && sex != null && height != null){
            return true;
        }else{
            return false;
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setHeight(Float height) {
        this.height = height;
    }

    public String getName() {
        return name;
    }

    public String getAge() {
        return age;
    }

    public String getSex() {
        return sex;
    }

    public Float getHeight() {
        return height;
    }
}
