package edu.ucsb.boning.jsontest;

/**
 * Created by boning on 4/22/17.
 */

public class CityInfo {
    private String name;
    private int number;
    private int rate;
    private int index;
    private int cityRank;
    private int resRank;

    //Getters and Setters:
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getCityRank() {
        return cityRank;
    }

    public void setCityRank(int cityRank) {
        this.cityRank = cityRank;
    }

    public int getResRank() {
        return resRank;
    }

    public void setResRank(int resRank) {
        this.resRank = resRank;
    }
}
