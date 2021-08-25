package gian.SpoonacularClases;

import java.util.List;

public class Ingredient {
    public int id;
    public String name;
    public double amount;
    public String unit;
    public List<Nutrient> nutrients;
    public String localizedName;
    public String image;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getAmount() {
        return amount;
    }

    public String getUnit() {
        return unit;
    }

    public List<Nutrient> getNutrients() {
        return nutrients;
    }

    public String getLocalizedName() {
        return localizedName;
    }

    public String getImage() {
        return image;
    }
}
