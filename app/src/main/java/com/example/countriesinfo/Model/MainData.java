package com.example.countriesinfo.Model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity (tableName = "table_name")
public class MainData implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int Id;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "capital")
    private String capital;
    @ColumnInfo(name = "flag")
    private String flag;
    @ColumnInfo(name = "region")
    private String region;
    @ColumnInfo(name = "subregion")
    private String subregion;
    @ColumnInfo(name = "population")
    private int population;
    @ColumnInfo(name = "borders")
    private String borders;
    @ColumnInfo(name = "languages")
    private String languages;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getSubregion() {
        return subregion;
    }

    public void setSubregion(String subregion) {
        this.subregion = subregion;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public String getLanguages() {
        return languages;
    }

    public void setLanguages(String languages) {
        this.languages = languages;
    }

    public String getBorders() {
        return borders;
    }

    public void setBorders(String borders) {
        this.borders = borders;
    }
}
