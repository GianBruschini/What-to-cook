package gian.Model;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.gian.getcooked.R;
import com.google.android.gms.ads.interstitial.InterstitialAd;

import java.util.ArrayList;
import java.util.List;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import gian.SpoonacularClases.FindByIngredient;
import gian.SpoonacularClases.SpoonacularItemToShow;
import gian.Interfaces.RecetasApi;
import gian.Interfaces.SpoonacularApi;
import gian.adapters.Adapter;
import gian.adapters.AdapterSpoonacularInfo;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RecetasInfoActivity extends AppCompatActivity implements Adapter.OnItemClickListener {

    private List<item> mlist = new ArrayList<>();
    private List<SpoonacularItemToShow> mlistSpoonacular = new ArrayList<>();
    private LoadingDialog loading;
    private Intent i;
    private  static final RecetasApi recipeService = RetrofitSingleton.getService();
    private InterstitialAd mInterstitialAd;
    private static final SpoonacularApi recipeServiceSpoonacular= RetrofitSingletonSpoonacular.getService();
    private String missingString;
    private ArrayList<String> arrayMissing = new ArrayList<>();
    private String content;
    private ArrayList<String>mListImagesMissing = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recetas_info);
        getValues();
        String ingredientes = getIntent().getStringExtra("Ingredientes");
        getRecipes(ingredientes);

    }

    private void getValues() {
        loading = new LoadingDialog(this);
        loading.startLoadingAlertDialog();
    }


    private void getRecipes(String s) {

        Call<ArrayList<FindByIngredient>>callSpoonacula = recipeServiceSpoonacular
                .getRecipesByIngredient("https://api.spoonacular.com/" +
                        "recipes"+"/"+"findByIngredients"+"?"+"apiKey"+"="+"4b033cbb7a934b438a783df2edd8969f"+
                        "&"+"ingredients"+"="+s+"&"+"number"+"="+"40");

        callSpoonacula.enqueue(new Callback<ArrayList<FindByIngredient>>() {
            @Override
            public void onResponse(Call<ArrayList<FindByIngredient>> call, Response<ArrayList<FindByIngredient>> response) {
                    if(response.isSuccessful()){
                        ArrayList<FindByIngredient> findByIngredient = response.body();
                        for(int i = 0 ; i<findByIngredient.size(); i++){

                            mlistSpoonacular.add(new SpoonacularItemToShow(
                                    findByIngredient.get(i).getImage(),
                                    findByIngredient.get(i).getTitle(),
                                    findByIngredient.get(i).getId(),
                                    findByIngredient.get(i).getMissedIngredients()
                            ));
                        }
                        RecyclerView recyclerView = findViewById(R.id.rv_list);
                        recyclerView.setHasFixedSize(true);
                        AdapterSpoonacularInfo adapter = new AdapterSpoonacularInfo(RecetasInfoActivity.this,mlistSpoonacular);
                        recyclerView.setAdapter(adapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(RecetasInfoActivity.this));
                        adapter.setOnItemClickListener(RecetasInfoActivity.this);
                        loading.dismissDialog();
                    }else{
                        Toast.makeText(RecetasInfoActivity.this, "We have not found any matches :(, try again later", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(RecetasInfoActivity.this,RecetasDetalle.class);
                        startActivity(intent);
                    }

            }

            @Override
            public void onFailure(Call<ArrayList<FindByIngredient>> call, Throwable t) {

            }
        });

        /*Call<Receta> call = recipeService.getRecipe("https://api.edamam.com/"+"search"+"?"+"q"+"="+s
                +"&"+"app_id"+"="+"25d5c2a6"+"&"+"app_key"+"="+"41b4d59bd6ed74ebf43f69c96b5f1761"+"&"+"to"+"="+"100");

        call.enqueue(new Callback<Receta>() {
            @Override
            public void onResponse(Call<Receta> call, Response<Receta> response) {

                if(response.isSuccessful()){
                    Receta r = response.body();
                    ArrayList<Receipe> recetas =r.getHits();
                    loading.dismissDialog();
                    for(int i = 0; i<recetas.size(); i++){
                        Receipe rec = recetas.get(i);
                        mlist.add(new item(rec.getRecipe().getImage(),rec.getRecipe().getLabel(),
                                rec.getRecipe().getCalories() ,
                                rec.getRecipe().getUrl(),
                                rec.getRecipe().getIngredientLines(),
                                rec.getRecipe().getTotalTime(),
                                rec.getRecipe().totalNutrients.geteNERC_KCAL(),
                                rec.getRecipe().totalNutrients.getfAT(),
                                rec.getRecipe().totalNutrients.getfASAT(),
                                rec.getRecipe().totalNutrients.getcHOLE(),
                                rec.getRecipe().totalNutrients.getnA()));
                    }

                    RecyclerView recyclerView = findViewById(R.id.rv_list);
                    recyclerView.setHasFixedSize(true);
                    Adapter adapter = new Adapter(RecetasInfoActivity.this,mlist);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(RecetasInfoActivity.this));
                    adapter.setOnItemClickListener(RecetasInfoActivity.this);
                }
            }

            @Override
            public void onFailure(Call<Receta> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });

         */

    }



    @Override
    public void onitemClick(int position) {

        Intent detailedIntent = new Intent(this,RecetasDetalle.class);
        SpoonacularItemToShow item=mlistSpoonacular.get(position);

        for(int i = 0; i< item.getMissedIngredients().size(); i++){
            content = item.getMissedIngredients().get(i).getAmount() +
                    " " + item.getMissedIngredients().get(i).getUnit()+
                    " " + item.getMissedIngredients().get(i).getName()+ "\n";
            arrayMissing.add(content);
            mListImagesMissing.add(item.getMissedIngredients().get(i).getImage());

        }
        detailedIntent.putExtra("recipeID",item.getId());
        detailedIntent.putExtra("missingIngredients",arrayMissing);
        detailedIntent.putExtra("missinIngredientsStringUris",mListImagesMissing);
        startActivity(detailedIntent);
            /*Intent detailedIntent = new Intent(this,RecetasDetalle.class);
            item itemClicked = mlist.get(position);
            detailedIntent.putExtra("ImageURL", itemClicked.getBackground());
            detailedIntent.putExtra("ReceipeLabel",itemClicked.getTitle());
            detailedIntent.putExtra("Calories",itemClicked.getCalories());
            detailedIntent.putExtra("Uri",itemClicked.getUrl());
            detailedIntent.putExtra("Ingredients",itemClicked.getIngredientLines());
            detailedIntent.putExtra("totalTime",itemClicked.getTotalTime());
            detailedIntent.putExtra("energy",itemClicked.getENERC_KCAL().getQuantity());
            detailedIntent.putExtra("fat",itemClicked.getFAT().getQuantity());
            detailedIntent.putExtra("fasat",itemClicked.getfASAT().getQuantity());
            detailedIntent.putExtra("chole",itemClicked.getcHOLE().getQuantity());
            detailedIntent.putExtra("na",itemClicked.getnA().getQuantity());
            startActivity(detailedIntent);
             */

    }
}