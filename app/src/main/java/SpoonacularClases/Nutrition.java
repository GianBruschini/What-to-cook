package SpoonacularClases;

import java.util.List;

public class Nutrition {
    public List<Nutrient> nutrients;
    public List<Property> properties;
    public List<Flavanoid> flavanoids;
    public List<Ingredient> ingredients;
    public CaloricBreakdown caloricBreakdown;
    public WeightPerServing weightPerServing;


    public List<Nutrient> getNutrients() {
        return nutrients;
    }

    public List<Property> getProperties() {
        return properties;
    }

    public List<Flavanoid> getFlavanoids() {
        return flavanoids;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public CaloricBreakdown getCaloricBreakdown() {
        return caloricBreakdown;
    }

    public WeightPerServing getWeightPerServing() {
        return weightPerServing;
    }
}
