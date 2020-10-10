package WhatToCook.Model;

import java.util.ArrayList;

public class item {
    String background;
    String title;
    double calories;
    String url;
    ArrayList<String> ingredientLines;



    public item(String background, String title, double calories, String url, ArrayList<String> ingredientLines) {
        this.background = background;
        this.title = title;
        this.calories = calories;
        this.url = url;
        this.ingredientLines = ingredientLines;
    }

    public ArrayList<String> getIngredientLines() {
        return ingredientLines;
    }

    public void setIngredientLines(ArrayList<String> ingredientLines) {
        this.ingredientLines = ingredientLines;
    }

    public item(){

    }

    public double getCalories() {
        return calories;
    }

    public void setCalories(double calories) {
        this.calories = calories;
    }

    public String getUrl() {
        return url;
    }

    public void setUri(String uri) {
        this.url = url;
    }

    public String getBackground() {
        return background;
    }

    public String getTitle() {
        return title;
    }


    public void setBackground(String background) {
        this.background = background;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
