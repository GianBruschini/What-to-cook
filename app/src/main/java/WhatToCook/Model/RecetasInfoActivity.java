package WhatToCook.Model;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;

import com.example.getcooked.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import WhatToCook.Interfaces.RecetasApi;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RecetasInfoActivity extends AppCompatActivity implements Adapter.OnItemClickListener {
    private List<item> mlist = new ArrayList<>();
    private List<ImageView> mImageView = new ArrayList<>();
    private BottomNavigationView menu;
    private BottomNavigationView menuItems;
    private LoadingDialog loading;
    private Intent i;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recetas_info);
        settingNavigationView();
        String ingredientes = getIntent().getStringExtra("Ingredientes");
        menuItems = findViewById(R.id.bottom_navigation);


        loading = new LoadingDialog(this);

        loading.startLoadingAlertDialog();
        getRecipes(ingredientes);
        removeImages();
    }


    private void settingNavigationView() {
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
    }


    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    switch (item.getItemId()) {
                        case R.id.nav_home:
                            i = new Intent (RecetasInfoActivity.this, HomeActivity.class);
                            startActivity(i);
                            RecetasInfoActivity.this.finish();
                            break;

                        case R.id.nav_search:
                            i = new Intent (RecetasInfoActivity.this, BuscarRecetas.class);
                            startActivity(i);
                            RecetasInfoActivity.this.finish();
                            break;
                    }

                    return true;
                }
            };



    private void removeImages() {

    }


    private void getRecipes(String s) {


        /*Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://test-es.edamam.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RecetasApi recipeService = retrofit.create(RecetasApi.class);
        Call<Receta> call = recipeService.getRecipe(s,40);

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
                                rec.getRecipe().getUrl(),rec.getRecipe().getIngredientLines()));

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
        item itemClicked = mlist.get(position);
        detailedIntent.putExtra("ImageURL", itemClicked.getBackground());
        detailedIntent.putExtra("ReceipeLabel",itemClicked.getTitle());
        detailedIntent.putExtra("Calories",itemClicked.getCalories());
        detailedIntent.putExtra("Uri",itemClicked.getUrl());
        detailedIntent.putExtra("Ingredients",itemClicked.getIngredientLines());
        startActivity(detailedIntent);
    }
}