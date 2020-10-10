package WhatToCook.Model;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.getcooked.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

public class RecetasDetalle extends AppCompatActivity {
    private TextView labelReceta;
    private ImageView imageView;
    private TextView calorias;
    private TextView ingredients;
    private ArrayList<String>ingr = new ArrayList<>();
    private String content;
    private String uri;
    private Intent i;
    private Button preparacion_uri;
    private String urlImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recetas_detalle);

        labelReceta = findViewById(R.id.recetaLabel);
        imageView = findViewById(R.id.imagenReceta);
        calorias = findViewById(R.id.calories);
        ingredients = findViewById(R.id.ingredients);
        preparacion_uri = findViewById(R.id.Preparacion_Uri);





        String label= getIntent().getStringExtra("ReceipeLabel");
        urlImage = getIntent().getStringExtra("ImageURL");
        uri = getIntent().getStringExtra("Uri");
        Double calories = getIntent().getDoubleExtra("Calories",0);
        ingr = getIntent().getStringArrayListExtra("Ingredients");

        Picasso.get().load(urlImage).fit().centerInside().into(imageView);
        labelReceta.setText(label);
        calorias.setText(calories.intValue() + " " + "calorias");
        for (int i = 0; i < ingr.size(); i++){
            System.out.println("Ingredientes " + " " +  ingr.get(i));
            content = ingr.get(i) + "\n";
            ingredients.append(content);
        }
        calorias.setVisibility(View.INVISIBLE);
        preparacion_uri.setVisibility(View.INVISIBLE);

        settingNavigationViewInside();
        settingNavigationView();

    }



    private void settingNavigationViewInside() {
        BottomNavigationView buttonNavInside = findViewById(R.id.inside_menu);
        buttonNavInside.setOnNavigationItemSelectedListener(buttonNavInside2);
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
                             i = new Intent (RecetasDetalle.this, HomeActivity.class);
                            startActivity(i);
                            RecetasDetalle.this.finish();
                            break;

                        case R.id.nav_search:
                            i = new Intent (RecetasDetalle.this, RecetasInfoActivity.class);
                            startActivity(i);
                            RecetasDetalle.this.finish();
                            break;
                    }

                    return true;
                }
            };



    private BottomNavigationView.OnNavigationItemSelectedListener buttonNavInside2 =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    if(item.getItemId() == R.id.nav_ingredients){
                        ingredients.setVisibility(View.VISIBLE);
                        calorias.setVisibility(View.INVISIBLE);
                        preparacion_uri.setVisibility(View.INVISIBLE);

                    }

                    if(item.getItemId() == R.id.nav_preparacion){
                        preparacion_uri.setVisibility(View.VISIBLE);
                        ingredients.setVisibility(View.INVISIBLE);
                        calorias.setVisibility(View.INVISIBLE);

                    }

                    if(item.getItemId() == R.id.nav_nutrition){
                        ingredients.setVisibility(View.INVISIBLE);
                        preparacion_uri.setVisibility(View.INVISIBLE);
                        calorias.setVisibility(View.VISIBLE);

                    }
                    return true;
                }
            };

    public void AbrirLinkPreparacion(View view) {
        Intent i = new Intent(Intent.ACTION_VIEW,Uri.parse(uri));
        startActivity(i);
    }

    public void shareReceipe(View view) {
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("text/plain");
        i.putExtra(Intent.EXTRA_SUBJECT, "Compartiendo");
        i.putExtra(Intent.EXTRA_TEXT, uri);
        i.putExtra(Intent.EXTRA_STREAM, urlImage);
        startActivity(Intent.createChooser(i, "Enviar a"));
    }
}