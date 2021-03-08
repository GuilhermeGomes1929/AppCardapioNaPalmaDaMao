package com.example.aplicativonutricao.model.entity;

public class BodyModel {

    //"data","peso","meta","bf","ombro","bracod","bracoe","peitoral",
    // "cintura","quadril", "pernad", "pernae", "panturrilhad", "panturrilhae"

    private String data;
    private Float weight;
    private Float goal;
    private Float bodyFat;
    private int shoulders;
    private int rightArm;
    private int leftArm;
    private int chest;
    private int waist;
    private int hip;
    private int rightLeg;
    private int leftLeg;
    private int rightCalf;
    private int leftCalf;
    private Float hight;

    public Float getHight() {
        return hight;
    }

    public void setHight(Float hight) {
        this.hight = hight;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public Float getGoal() {
        return goal;
    }

    public void setGoal(Float goal) {
        this.goal = goal;
    }

    public Float getBodyFat() {
        return bodyFat;
    }

    public void setBodyFat(Float bodyFat) {
        this.bodyFat = bodyFat;
    }

    public int getShoulders() {
        return shoulders;
    }

    public void setShoulders(int shoulders) {
        this.shoulders = shoulders;
    }

    public int getRightArm() {
        return rightArm;
    }

    public void setRightArm(int rightArm) {
        this.rightArm = rightArm;
    }

    public int getLeftArm() {
        return leftArm;
    }

    public void setLeftArm(int leftArm) {
        this.leftArm = leftArm;
    }

    public int getChest() {
        return chest;
    }

    public void setChest(int chest) {
        this.chest = chest;
    }

    public int getWaist() {
        return waist;
    }

    public void setWaist(int waist) {
        this.waist = waist;
    }

    public int getHip() {
        return hip;
    }

    public void setHip(int hip) {
        this.hip = hip;
    }

    public int getRightLeg() {
        return rightLeg;
    }

    public void setRightLeg(int rightLeg) {
        this.rightLeg = rightLeg;
    }

    public int getLeftLeg() {
        return leftLeg;
    }

    public void setLeftLeg(int leftLeg) {
        this.leftLeg = leftLeg;
    }

    public int getRightCalf() {
        return rightCalf;
    }

    public void setRightCalf(int rightCalf) {
        this.rightCalf = rightCalf;
    }

    public int getLeftCalf() {
        return leftCalf;
    }

    public void setLeftCalf(int leftCalf) {
        this.leftCalf = leftCalf;
    }
}
