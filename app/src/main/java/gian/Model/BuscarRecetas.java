package gian.Model;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.gian.getcooked.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import gian.adapters.AdapterBuscarRecetas;

public class BuscarRecetas extends AppCompatActivity implements AdapterBuscarRecetas.OnItemClickListener {

    private EditText ingrediente_input;
    private List<String>ingres=new ArrayList<>();
    private List<Ingrediente> mlist = new ArrayList<>();
    private RecyclerView recyclerView;
    private AdapterBuscarRecetas adapter;
    private Intent i;
    private String s;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_recetas);
        ingrediente_input =  findViewById(R.id.editTextTextIngrediente);
        recyclerView = findViewById(R.id.recyclerView_ingredientes);
        settingNavigationView();

    }

    public void AgregarIngrediente(View view) {
        hideKeybaord(view);
        if(!ingrediente_input.getText().toString().isEmpty()){
            setAdapter();
            llenarArrayDeIngredientesYsetearTexto();
        }
       // callInputDialog();
    }

    private void hideKeybaord(View v) {
        InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(),0);
    }


    private void llenarArrayDeIngredientesYsetearTexto() {
        if(!ingres.contains(ingrediente_input.getText().toString())){
            System.out.println("No lo tiene");
            ingres.add(ingrediente_input.getText().toString());
            ingrediente_input.setHint("Agregue otro ingrediente");
            ingrediente_input.setText("");
        }
    }

    public void removeItem(int position){
        mlist.remove(position);
        ingres.remove(position);
        adapter.notifyItemRemoved(position);

    }

    private void setAdapter() {

        if(!ingres.contains(ingrediente_input.getText().toString())){
            System.out.println("No lo tiene");
            mlist.add(new Ingrediente(ingrediente_input.getText().toString()));
            recyclerView.setHasFixedSize(true);
            adapter = new AdapterBuscarRecetas(BuscarRecetas.this,mlist);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(BuscarRecetas.this));
            adapter.setOnItemClickListener(new AdapterBuscarRecetas.OnItemClickListener() {
                @Override
                public void onitemClick(int position) {

                }

                @Override
                public void onDelateClick(int position) {
                    removeItem(position);

                }
            });
        }


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
                            i = new Intent (BuscarRecetas.this, HomeActivity.class);
                            startActivity(i);
                            BuscarRecetas.this.finish();
                            break;

                        case R.id.nav_search:
                            i = new Intent (BuscarRecetas.this, BuscarRecetas.class);
                            startActivity(i);
                            BuscarRecetas.this.finish();
                            break;
                    }

                    return true;
                }
            };




    /*private void callInputDialog() {
        DialogAddIngredient dialog = new  DialogAddIngredient ();
        dialog.show(getSupportFragmentManager(), "Ingrediente");
    }
    */


    @Override
    public void onitemClick(int position) {

    }

    @Override
    public void onDelateClick(int position) {

    }





    /* private void getRecipes(String s) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://test-es.edamam.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RecetasApi recipeService = retrofit.create(RecetasApi.class);
        Call<Receta> call = recipeService.getRecipe(s);


        call.enqueue(new Callback<Receta>() {
            @Override
            public void onResponse(Call<Receta> call, Response<Receta> response) {

                if(response.isSuccessful()){
                    System.out.println("Is code " + response.code());
                    Receta r = response.body();
                    ArrayList<Receipe> recetas =r.getHits();
                    if(r.getHits()!=null){
                        for(int i = 0; i<recetas.size(); i++){
                            Receipe rec = recetas.get(i);
                            String content = "Comida " + rec.getRecipe().getLabel() + "\n";
                            content += "Uri" + rec.getRecipe().getUrl() + "\n";
                            content += "Ingrediente" + rec.getRecipe().getIngredientLines() + "\n";
                            System.out.println(content);
                            texto.append(content);
                        }
                    }

                }
            }

            @Override
            public void onFailure(Call<Receta> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
    }

    */




   /* public void AgregarIngrediente(View view) {
        if(!ingrediente.getEditText().getText().toString().trim().isEmpty()){
            ingres.add(ingrediente.getEditText().getText().toString());
            String content = "-" +""+ingrediente.getEditText().getText().toString().trim() + "\n";
            texto.append(content);
            ingrediente.setHint("Agregue otro ingrediente");
            textoEditText.setText("");
        }

    }

    */



    public void buscarRecetas(View view) {
        if(!ingres.isEmpty()){
            s = ingres.toString();
            s = s.substring(1, s.length()-1);
            verCodigoResponse();
        }

    }

    private void verCodigoResponse() {
        /*Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://test-es.edamam.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RecetasApi recipeService = retrofit.create(RecetasApi.class);
        Call<Receta> call = recipeService.getRecipe(s,20);

        call.enqueue(new Callback<Receta>() {
            @Override
            public void onResponse(Call<Receta> call, Response<Receta> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(BuscarRecetas.this, "No hemos encontrado coincidencias :(", Toast.LENGTH_SHORT).show();

                }else{
                    Receta r = response.body();
                    ArrayList<Receipe> recetas =r.getHits();
                    if(!recetas.isEmpty()){
                        Intent i = new Intent(BuscarRecetas.this,RecetasInfoActivity.class);
                        i.putExtra("Ingredientes", s);
                        startActivity(i);
                    }else{
                        Toast.makeText(BuscarRecetas.this, "No hemos encontrado coincidencias :(, vuelva a intentarlo", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Receta> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });

         */

    }










}

