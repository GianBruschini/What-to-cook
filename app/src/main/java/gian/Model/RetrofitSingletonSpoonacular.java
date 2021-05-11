package gian.Model;

import gian.Interfaces.RecetasApi;
import gian.Interfaces.SpoonacularApi;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitSingletonSpoonacular {
    private static Retrofit retrofit = null;
    private static String BASE_URL = "https://api.spoonacular.com/";

    public static SpoonacularApi getService(){
        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(SpoonacularApi.class);
    }


}
