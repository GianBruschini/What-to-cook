package WhatToCook.Interfaces;

import java.util.List;

import WhatToCook.Model.Receta;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface RecetasApi {
    String app_id = "25d5c2a6";
    String app_key = "41b4d59bd6ed74ebf43f69c96b5f1761";


    @GET
    Call<Receta> getRecipe(@Url String url);
    @GET
    Call<Receta> getRecipeByBreakfast(@Url String url);

    //@GET("search")
    //Call<Receta> getRecipe(@Query("q") String q,@Query("to") int to);
    //@GET("search")
    //Call<Receta> getRecipeByBreakfast(@Query("q")String q,@Query("to") int to,@Query("mealType")String mealType);

}
