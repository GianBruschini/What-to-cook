package WhatToCook.Model;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.getcooked.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;
import com.takusemba.multisnaprecyclerview.MultiSnapRecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import WhatToCook.Interfaces.RecetasApi;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeActivity extends AppCompatActivity implements AdapterMenu.OnItemClickListener {
    private FirebaseAuth auth;
    private DatabaseReference myRef;
    private FirebaseDatabase database;
    private TextView user;
    private String email;
    private LoadingDialog loading;
    private String app_id = "25d5c2a6";
    private String app_key = "41b4d59bd6ed74ebf43f69c96b5f1761";
    private BottomNavigationView menuItems;
    private Intent i ;
    private String comida;
    private List<item> mlistComidaFav = new ArrayList<>();
    private List<item> mlistBreakfast = new ArrayList<>();
    private List<item> mlistLunch = new ArrayList<>();
    private List<item> mlistSnackTime = new ArrayList<>();
    private List<item> mlistDinner = new ArrayList<>();
    private ImageView imagenTest;






    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        settingNavigationView();

        capturarTextoSearch();

        imagenTest = findViewById(R.id.imagenTest);


        loading = new LoadingDialog(this);
        loading.startLoadingAlertDialog();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("username");
        auth = FirebaseAuth.getInstance();
        menuItems = findViewById(R.id.bottom_navigation);
        getFavoritesRecipes();
        getBreakfastReceipes();
        getLunchReceipes();
        getSnackReceipes();
        getDinnerReceipes();
        loading.dismissDialog();
        //settingNavigationView();
        //getUsername();
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
                            i = new Intent (HomeActivity.this, HomeActivity.class);
                            startActivity(i);
                            HomeActivity.this.finish();
                            break;

                        case R.id.nav_search:
                            i = new Intent (HomeActivity.this, BuscarRecetas.class);
                            startActivity(i);
                            HomeActivity.this.finish();
                            break;
                    }

                    return true;
                }
            };



    private void getFavoritesRecipes() {


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.edamam.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RecetasApi recipeService = retrofit.create(RecetasApi.class);
        Call<Receta> call = recipeService.getRecipe("https://api.edamam.com/"+"search"+"?"+"q"+"="+"desayuno"
                +"&"+"app_id"+"="+"25d5c2a6"+"&"+"app_key"+"="+"41b4d59bd6ed74ebf43f69c96b5f1761"+"&"+"mealType"+"="
                +"breakfast"+"&"+"to"+"="+"20");

        call.enqueue(new Callback<Receta>() {
            @Override
            public void onResponse(Call<Receta> call, Response<Receta> response) {
                if(response.isSuccessful()){
                    Receta r = response.body();
                    ArrayList<Receipe> recetas =r.getHits();
                    for(int i = 0; i<recetas.size(); i++){
                        Receipe rec = recetas.get(i);
                        Picasso.get().load(rec.getRecipe().getUrl()).into(imagenTest);

                        mlistComidaFav.add
                                    (new item
                                            (rec.getRecipe().getImage(),
                                                    rec.getRecipe().getLabel(),
                                                    rec.getRecipe().getCalories() ,
                                                    rec.getRecipe().getUrl(),
                                                    rec.getRecipe().getIngredientLines()));



                    }

                    AdapterMenu firstAdapter = new AdapterMenu(getApplicationContext(),mlistComidaFav);
                    MultiSnapRecyclerView firstRecyclerView =  (MultiSnapRecyclerView) findViewById(R.id.first_recycler_view);
                    LinearLayoutManager firstManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
                    firstRecyclerView.setLayoutManager(firstManager);
                    firstRecyclerView.setAdapter(firstAdapter);
                    firstAdapter.setOnItemClickListener(HomeActivity.this,mlistComidaFav);

                }
            }

            @Override
            public void onFailure(Call<Receta> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });


    }


    private void getBreakfastReceipes() {


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.edamam.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RecetasApi recipeService = retrofit.create(RecetasApi.class);
        Call<Receta> call = recipeService.getRecipeByBreakfast("https://api.edamam.com/"+
        "app_id"+"="+"25d5c2a6"+"&"+"app_key"+"="+"41b4d59bd6ed74ebf43f69c96b5f1761"+"&"+"mealType"
                +"="+"breakfast"+"&"+"to"+"="+"20");

        call.enqueue(new Callback<Receta>() {
            @Override
            public void onResponse(Call<Receta> call, Response<Receta> response) {

                if(response.isSuccessful()){
                    Receta r = response.body();
                    ArrayList<Receipe> recetas =r.getHits();
                    for(int i = 0; i<recetas.size(); i++){
                        Receipe rec = recetas.get(i);
                        mlistBreakfast.add(new item(rec.getRecipe().getImage(),rec.getRecipe().getLabel(),
                                rec.getRecipe().getCalories() ,
                                rec.getRecipe().getUrl(),rec.getRecipe().getIngredientLines()));

                    }

                    AdapterMenu secondAdapter = new AdapterMenu(getApplicationContext(),mlistBreakfast);
                    MultiSnapRecyclerView secondRecyclerView =  (MultiSnapRecyclerView) findViewById(R.id.second_recycler_view);
                    LinearLayoutManager secondManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
                    secondRecyclerView.setLayoutManager(secondManager);
                    secondRecyclerView.setAdapter(secondAdapter);
                    secondAdapter.setOnItemClickListener(HomeActivity.this,mlistBreakfast);
                }
            }

            @Override
            public void onFailure(Call<Receta> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });


    }


    private void getLunchReceipes() {


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.edamam.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RecetasApi recipeService = retrofit.create(RecetasApi.class);
        Call<Receta> call = recipeService.getRecipeByBreakfast("https://api.edamam.com/"+"search"+"?"+"q"+"="+"pollo"
                +"&"+"app_id"+"="+"25d5c2a6"+"&"+"app_key"+"="+"41b4d59bd6ed74ebf43f69c96b5f1761");

        call.enqueue(new Callback<Receta>() {
            @Override
            public void onResponse(Call<Receta> call, Response<Receta> response) {

                if(response.isSuccessful()){
                    Receta r = response.body();
                    ArrayList<Receipe> recetas =r.getHits();
                    for(int i = 0; i<recetas.size(); i++){
                        Receipe rec = recetas.get(i);
                        mlistLunch.add(new item(rec.getRecipe().getImage(),rec.getRecipe().getLabel(),
                                rec.getRecipe().getCalories() ,
                                rec.getRecipe().getUrl(),rec.getRecipe().getIngredientLines()));

                    }

                    AdapterMenu thirdAdapter = new AdapterMenu(getApplicationContext(),mlistLunch);
                    MultiSnapRecyclerView thirdRecyclerView =  (MultiSnapRecyclerView) findViewById(R.id.third_recycler_view);
                    LinearLayoutManager thirdManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
                    thirdRecyclerView.setLayoutManager(thirdManager);
                    thirdRecyclerView.setAdapter(thirdAdapter);
                    thirdAdapter.setOnItemClickListener(HomeActivity.this,mlistLunch);
                }
            }

            @Override
            public void onFailure(Call<Receta> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });


    }



    private void getSnackReceipes() {


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.edamam.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RecetasApi recipeService = retrofit.create(RecetasApi.class);
        Call<Receta> call = recipeService.getRecipeByBreakfast("https://api.edamam.com/"+"search"+"?"+"q"+"="+"pollo"
                +"&"+"app_id"+"="+"25d5c2a6"+"&"+"app_key"+"="+"41b4d59bd6ed74ebf43f69c96b5f1761");

        call.enqueue(new Callback<Receta>() {
            @Override
            public void onResponse(Call<Receta> call, Response<Receta> response) {

                if(response.isSuccessful()){
                    Receta r = response.body();
                    ArrayList<Receipe> recetas =r.getHits();
                    for(int i = 0; i<recetas.size(); i++){
                        Receipe rec = recetas.get(i);
                        mlistSnackTime.add(new item(rec.getRecipe().getImage(),rec.getRecipe().getLabel(),
                                rec.getRecipe().getCalories() ,
                                rec.getRecipe().getUrl(),rec.getRecipe().getIngredientLines()));

                    }

                    AdapterMenu fourthAdapter = new AdapterMenu(getApplicationContext(),mlistSnackTime);
                    MultiSnapRecyclerView fourthRecyclerView =  (MultiSnapRecyclerView) findViewById(R.id.fourth_recycler_view);
                    LinearLayoutManager fourthManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
                    fourthRecyclerView.setLayoutManager(fourthManager);
                    fourthRecyclerView.setAdapter(fourthAdapter);
                    fourthAdapter.setOnItemClickListener(HomeActivity.this,mlistSnackTime);
                }
            }

            @Override
            public void onFailure(Call<Receta> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });


    }



    private void getDinnerReceipes() {


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.edamam.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RecetasApi recipeService = retrofit.create(RecetasApi.class);
        Call<Receta> call = recipeService.getRecipeByBreakfast("https://api.edamam.com/"+"search"+"?"+"q"+"="+"pollo"
                +"&"+"app_id"+"="+"25d5c2a6"+"&"+"app_key"+"="+"41b4d59bd6ed74ebf43f69c96b5f1761");

        call.enqueue(new Callback<Receta>() {
            @Override
            public void onResponse(Call<Receta> call, Response<Receta> response) {

                if(response.isSuccessful()){
                    Receta r = response.body();
                    ArrayList<Receipe> recetas =r.getHits();
                    for(int i = 0; i<recetas.size(); i++){
                        Receipe rec = recetas.get(i);
                        mlistDinner.add(new item(rec.getRecipe().getImage(),rec.getRecipe().getLabel(),
                                rec.getRecipe().getCalories() ,
                                rec.getRecipe().getUrl(),rec.getRecipe().getIngredientLines()));

                    }

                    AdapterMenu fifthAdapter = new AdapterMenu(getApplicationContext(),mlistDinner);
                    MultiSnapRecyclerView fifthRecyclerView =  (MultiSnapRecyclerView) findViewById(R.id.fifth_recycler_view);
                    LinearLayoutManager fifthManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
                    fifthRecyclerView.setLayoutManager(fifthManager);
                    fifthRecyclerView.setAdapter(fifthAdapter);

                    fifthAdapter.setOnItemClickListener(HomeActivity.this,mlistDinner);
                }
            }

            @Override
            public void onFailure(Call<Receta> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });


    }

    @Override
    public void onitemClick(int position,List<item> actualList) {
        System.out.println("La posicion es " + " " + position);
        Intent detailedIntent = new Intent(this,RecetasDetalle.class);
        item itemClicked = actualList.get(position);
        detailedIntent.putExtra("ImageURL", itemClicked.getBackground());
        detailedIntent.putExtra("ReceipeLabel",itemClicked.getTitle());
        detailedIntent.putExtra("Calories",itemClicked.getCalories());
        detailedIntent.putExtra("Uri",itemClicked.getUrl());
        detailedIntent.putExtra("Ingredients",itemClicked.getIngredientLines());
        startActivity(detailedIntent);
    }

    private void capturarTextoSearch(){
        EditText textSearch = findViewById(R.id.receipeSearchInfo);
        textSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                         i = new Intent(HomeActivity.this,RecetasInfoActivity.class);
                         i.putExtra("Ingredientes", v.getText().toString());
                         startActivity(i);
                         return true;
                }
                return false;

            }


        });

    }










   /* private void getUsername() {
        email = getIntent().getStringExtra("Email");
        Query checkUser = FirebaseDatabase.getInstance().getReference("username").orderByChild("email").equalTo(email);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    String nombre = snapshot.child("nombre").getValue().toString();
                    if(!nombre.isEmpty()){
                        user = findViewById(R.id.usuario);
                        user.setText(nombre);
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        loading.dismissDialog();
    }


    public void buscarRecetas(View view) {
        Intent i = new Intent (this,BuscarRecetas.class);
        startActivity(i);
        this.finish();
    }

    */
}