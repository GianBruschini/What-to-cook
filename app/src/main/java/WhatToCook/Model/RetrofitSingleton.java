package WhatToCook.Model;

import WhatToCook.Interfaces.RecetasApi;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitSingleton {
    private static Retrofit retrofit = null;
    private static String BASE_URL = "https://test-es.edamam.com/";

    public static RecetasApi getService(){
        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

        }
        return retrofit.create(RecetasApi.class);
    }


}
