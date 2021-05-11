package gian.Interfaces;

import java.util.ArrayList;

import SpoonacularClases.FindByIngredient;
import SpoonacularClases.SpecificRecipe;
import gian.Model.Receta;
import gian.Model.Root;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface SpoonacularApi {

    @GET
    Call<ArrayList<FindByIngredient>> getRecipesByIngredient(@Url String url);

    @GET
    Call<SpecificRecipe> getSpecificRecipe(@Url String url);

    @GET
    Call<Root> getRecipesTimeOfDay(@Url String url);
}
