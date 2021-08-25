package gian.SpoonacularClases;

import java.util.List;

public class FindByIngredient {

    public int id;
    public String title;
    public String image;
    public String imageType;
    public int usedIngredientCount;
    public int missedIngredientCount;
    public List<MissedIngredient> missedIngredients;
    public List<UsedIngredient> usedIngredients;
    public List<Object> unusedIngredients;
    public int likes;

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getImage() {
        return image;
    }

    public String getImageType() {
        return imageType;
    }

    public int getUsedIngredientCount() {
        return usedIngredientCount;
    }

    public int getMissedIngredientCount() {
        return missedIngredientCount;
    }

    public List<MissedIngredient> getMissedIngredients() {
        return missedIngredients;
    }

    public List<UsedIngredient> getUsedIngredients() {
        return usedIngredients;
    }

    public List<Object> getUnusedIngredients() {
        return unusedIngredients;
    }

    public int getLikes() {
        return likes;
    }
}
