package gian.Model;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.gian.getcooked.R;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ElegirComidaFavoritaActivity extends AppCompatActivity {
    private EditText comidaInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elegir_comida_favorita);
        comidaInput = findViewById(R.id.comidaInput);
        SharedPreferences prefs = getSharedPreferences("shared", MODE_PRIVATE);
        int number = prefs.getInt("number", 0);
        if(number == 1){
            Intent i = new Intent(this,HomeActivity.class);
            startActivity(i);
            this.finish();
        }






    }

    public void continuar(View view) {
        if(!comidaInput.getText().toString().trim().isEmpty()){
            verCodigoResponse();
        }


    }

    private void verCodigoResponse() {
        /*Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://test-es.edamam.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RecetasApi recipeService = retrofit.create(RecetasApi.class);
        Call<Receta> call = recipeService.getRecipe(comidaInput.getText().toString().trim(),20);

        call.enqueue(new Callback<Receta>() {
            @Override
            public void onResponse(Call<Receta> call, Response<Receta> response) {
               if(!response.isSuccessful()){
                   Toast.makeText(ElegirComidaFavoritaActivity.this, "No hemos encontrado coincidencias :(", Toast.LENGTH_SHORT).show();

               }else{
                       Receta r = response.body();
                       ArrayList<Receipe> recetas =r.getHits();
                       if(!recetas.isEmpty()){
                           Intent i = new Intent(ElegirComidaFavoritaActivity.this,HomeActivity.class);
                           i.putExtra("comida",comidaInput.getText().toString().trim());
                           SharedPreferences.Editor editor = getSharedPreferences("shared", MODE_PRIVATE).edit();
                           editor.putInt("number", 1);
                           editor.putString("comida",comidaInput.getText().toString().trim() );
                           editor.apply();
                           startActivity(i);
                       }else{
                           Toast.makeText(ElegirComidaFavoritaActivity.this, "No hemos encontrado coincidencias :(", Toast.LENGTH_SHORT).show();
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