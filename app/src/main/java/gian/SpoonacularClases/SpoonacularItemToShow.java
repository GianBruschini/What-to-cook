package gian.SpoonacularClases;

import java.util.List;

public class SpoonacularItemToShow {
    String background;
    String title;
    int id;
    List<MissedIngredient> missedIngredients;



    public SpoonacularItemToShow(String background, String title, int id, List<MissedIngredient> missedIngredients) {
        this.background = background;
        this.title = title;
        this.id = id;
        this.missedIngredients = missedIngredients;



    }

    public String getBackground() {
        return background;
    }

    public String getTitle() {
        return title;
    }

    public int getId() {
        return id;
    }

    public List<MissedIngredient> getMissedIngredients() {
        return missedIngredients;
    }


}

