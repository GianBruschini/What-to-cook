package gian.SpoonacularClases;

import java.util.List;

public class SpecificRecipe {
    public boolean vegetarian;
    public boolean vegan;
    public boolean glutenFree;
    public boolean dairyFree;
    public boolean veryHealthy;
    public boolean cheap;
    public boolean veryPopular;
    public boolean sustainable;
    public int weightWatcherSmartPoints;
    public String gaps;
    public boolean lowFodmap;
    public int aggregateLikes;
    public double spoonacularScore;
    public double healthScore;
    public String creditsText;
    public String license;
    public String sourceName;
    public double pricePerServing;
    public List<ExtendedIngredient> extendedIngredients;
    public int id;
    public String title;
    public int readyInMinutes;
    public int servings;
    public String sourceUrl;
    public String image;
    public String imageType;
    public Nutrition nutrition;
    public String summary;
    public List<Object> cuisines;
    public List<Object> dishTypes;
    public List<String> diets;
    public List<Object> occasions;
    public WinePairing winePairing;
    public String instructions;
    public List<AnalyzedInstruction> analyzedInstructions;
    public Object originalId;
    public String spoonacularSourceUrl;


    public boolean isVegetarian() {
        return vegetarian;
    }

    public boolean isVegan() {
        return vegan;
    }

    public boolean isGlutenFree() {
        return glutenFree;
    }

    public boolean isDairyFree() {
        return dairyFree;
    }

    public boolean isVeryHealthy() {
        return veryHealthy;
    }

    public boolean isCheap() {
        return cheap;
    }

    public boolean isVeryPopular() {
        return veryPopular;
    }

    public boolean isSustainable() {
        return sustainable;
    }

    public int getWeightWatcherSmartPoints() {
        return weightWatcherSmartPoints;
    }

    public String getGaps() {
        return gaps;
    }

    public boolean isLowFodmap() {
        return lowFodmap;
    }

    public int getAggregateLikes() {
        return aggregateLikes;
    }

    public double getSpoonacularScore() {
        return spoonacularScore;
    }

    public double getHealthScore() {
        return healthScore;
    }

    public String getCreditsText() {
        return creditsText;
    }

    public String getLicense() {
        return license;
    }

    public String getSourceName() {
        return sourceName;
    }

    public double getPricePerServing() {
        return pricePerServing;
    }

    public List<ExtendedIngredient> getExtendedIngredients() {
        return extendedIngredients;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getReadyInMinutes() {
        return readyInMinutes;
    }

    public int getServings() {
        return servings;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public String getImage() {
        return image;
    }

    public String getImageType() {
        return imageType;
    }

    public Nutrition getNutrition() {
        return nutrition;
    }

    public String getSummary() {
        return summary;
    }

    public List<Object> getCuisines() {
        return cuisines;
    }

    public List<Object> getDishTypes() {
        return dishTypes;
    }

    public List<String> getDiets() {
        return diets;
    }

    public List<Object> getOccasions() {
        return occasions;
    }

    public WinePairing getWinePairing() {
        return winePairing;
    }

    public String getInstructions() {
        return instructions;
    }

    public List<AnalyzedInstruction> getAnalyzedInstructions() {
        return analyzedInstructions;
    }

    public Object getOriginalId() {
        return originalId;
    }

    public String getSpoonacularSourceUrl() {
        return spoonacularSourceUrl;
    }
}
