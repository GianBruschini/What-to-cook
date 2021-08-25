package gian.Model;

import java.util.ArrayList;

public class item {
    String background;
    String title;
    double calories;
    String url;
    ArrayList<String> ingredientLines;
    double totalTime;
    ENERCKCAL ENERC_KCAL;
    FAT FAT;
    FASAT fASAT;
    CHOLE cHOLE;
    NA nA;


    public item(String background, String title, double calories, String url, ArrayList<String> ingredientLines,
                double totalTime, ENERCKCAL ENERCKCAL,FAT FAT, FASAT fASAT, CHOLE cHOLE, NA nA) {
        this.background = background;
        this.title = title;
        this.calories = calories;
        this.url = url;
        this.ingredientLines = ingredientLines;
        this.totalTime = totalTime;
        this.ENERC_KCAL = ENERCKCAL;
        this.FAT = FAT;
        this.fASAT = fASAT;
        this.cHOLE = cHOLE;
        this.nA = nA;
    }

    public ENERCKCAL getENERC_KCAL() {
        return ENERC_KCAL;
    }

    public FAT getFAT() {
        return FAT;
    }

    public FASAT getfASAT() {
        return fASAT;
    }

    public CHOLE getcHOLE() {
        return cHOLE;
    }

    public NA getnA() {
        return nA;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public double getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(double totalTime) {
        this.totalTime = totalTime;
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
