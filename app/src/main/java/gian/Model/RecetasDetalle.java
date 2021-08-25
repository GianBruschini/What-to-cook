package gian.Model;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.gian.getcooked.R;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

import gian.SpoonacularClases.SpecificRecipe;
import gian.Interfaces.SpoonacularApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecetasDetalle extends AppCompatActivity {
    private TextView labelReceta;
    private ArrayList<String>missingIngr = new ArrayList<>();
    private ArrayList<String>missingIngr_urls = new ArrayList<>();
    private Button button_viewRecipe;
    private TextView instructions_text;
    private String tituloAcomparar;
    private String content;
    private String urlImagen;
    private TextView time_text;
    private TextView calories_text;
    private TextView serving_text;
    private TextView ingr_description;
    private String myKey;
    private String keyComida;
    private LoadingDialog loadingDialog;
    private RoundedImageView imagenRecetaFondo;
    private String url;
    private TextView caloriesTxt;
    private TextView proteinsTxt;
    private TextView fatTxt;
    private TextView carbTxt;
    private ToggleButton button_favourite;
    private InterstitialAd mInterstitialAd;
    private int idRecipe;
    private ImageView button_share;
    private static final SpoonacularApi recipeServiceSpoonacular= RetrofitSingletonSpoonacular.getService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recetas_detalle);
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
                createPersonalizedAd();
            }
        });
        if (mInterstitialAd != null) {
            mInterstitialAd.show(RecetasDetalle.this);
        } else {
            Log.d("TAG", "The interstitial ad wasn't ready yet.");
        }
        getValues();
        settingValuesAndViews();
        addLogicToButtonFavouriteAndShare();

    }

    private void checkearSiSeAgregoAFavs(String title) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        if(myKey!=null){
            Query applesQuery = reference.child("RecetasFavoritas").child(myKey).orderByChild("nombreReceta").equalTo(title);
            applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                        if(appleSnapshot.exists()){
                           button_favourite.setChecked(true);
                        }
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }else{
            System.out.println("Es null");
        }
    }

    private void addLogicToButtonFavouriteAndShare() {
        button_favourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(button_favourite.isChecked()){
                    keyComida = FirebaseDatabase.getInstance().getReference("key").push().getKey();
                    DatabaseReference nombreDeLaReceta = FirebaseDatabase.getInstance().getReference().child("RecetasFavoritas").child(myKey).child(keyComida).child("nombreReceta");
                    DatabaseReference idDeLaReceta= FirebaseDatabase.getInstance().getReference().child("RecetasFavoritas").child(myKey).child(keyComida).child("id");
                    DatabaseReference urlDeLaImagenDeComida= FirebaseDatabase.getInstance().getReference().child("RecetasFavoritas").child(myKey).child(keyComida).child("imagenUrl");
                    DatabaseReference timeReady= FirebaseDatabase.getInstance().getReference().child("RecetasFavoritas").child(myKey).child(keyComida).child("timeReady");
                    nombreDeLaReceta.setValue(labelReceta.getText().toString());
                    idDeLaReceta.setValue(String.valueOf(idRecipe));
                    urlDeLaImagenDeComida.setValue(urlImagen);
                    timeReady.setValue(String.valueOf(time_text.getText().toString()));
                }else{
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                    Query applesQuery = ref.child("RecetasFavoritas").child(myKey).orderByChild("nombreReceta").equalTo(labelReceta.getText().toString());
                    applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                                appleSnapshot.getRef().removeValue();
                            }
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }
        });
    }

    private void getValues() {
        loadingDialog = new LoadingDialog(this);
        loadingDialog.startLoadingAlertDialog();
        labelReceta = findViewById(R.id.recetaLabel);
        ingr_description = findViewById(R.id.ingrTxt);
        instructions_text = findViewById(R.id.instructions_description);
        time_text = findViewById(R.id.time);
        serving_text = findViewById(R.id.serving);
        calories_text = findViewById(R.id.calories);
        imagenRecetaFondo = findViewById(R.id.imagenRecetaFondo);
        caloriesTxt = findViewById(R.id.calorias_text);
        proteinsTxt = findViewById(R.id.proteinas_text);
        fatTxt = findViewById(R.id.grasas_text);
        carbTxt = findViewById(R.id.carb_text);
        button_viewRecipe = findViewById(R.id.buttonViewRecipe);
        button_favourite = findViewById(R.id.add_to_favourite);
        myKey = getIntent().getStringExtra("myKey");
    }

    private void settingValuesAndViews() {
        idRecipe = getIntent().getIntExtra("recipeID",0);
        missingIngr = getIntent().getStringArrayListExtra("missingIngredients");
        missingIngr_urls = getIntent().getStringArrayListExtra("missinIngredientsStringUris");

        Call<SpecificRecipe> callSpoonacula = recipeServiceSpoonacular
                .getSpecificRecipe("https://api.spoonacular.com/" +
                        "recipes"+"/"+idRecipe+"/"+"information"+"?"+"apiKey"+"="+"4b033cbb7a934b438a783df2edd8969f"+
                        "&"+"includeNutrition"+"="+"true");

        callSpoonacula.enqueue(new Callback<SpecificRecipe>() {
            @Override
            public void onResponse(Call<SpecificRecipe> call, Response<SpecificRecipe> response) {
                if(response.isSuccessful()){
                    final SpecificRecipe specificRecipe = response.body();
                    urlImagen = specificRecipe.getImage();
                    button_viewRecipe.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent i = new Intent(
                                    Intent.ACTION_VIEW,
                                    Uri.parse(specificRecipe.getSourceUrl()));
                            startActivity(i);
                        }
                    });
                    url = specificRecipe.sourceUrl;
                    checkearSiSeAgregoAFavs(specificRecipe.getTitle());
                    labelReceta.setText(specificRecipe.getTitle());
                    tituloAcomparar=specificRecipe.getTitle();
                    Picasso.get().load(specificRecipe.getImage()).fit().centerCrop().into(imagenRecetaFondo);
                    String min= specificRecipe.getReadyInMinutes()+" "+"min";
                    time_text.setText(min);
                    serving_text.setText(String.valueOf(Integer.valueOf(specificRecipe.getServings())));
                    for(int i = 0; i< specificRecipe.getNutrition().getNutrients().size();i++){
                        if(specificRecipe.getNutrition().getNutrients().get(i).getName().equals("Calories")){
                            String calorias= specificRecipe.getNutrition().getNutrients().get(i).getAmount()+" "+"kcal";
                            calories_text.setText(calorias);
                            caloriesTxt.setText(calorias);
                        }
                        if(specificRecipe.getNutrition().getNutrients().get(i).getName().equals("Protein")){
                            String protein = (int)specificRecipe.getNutrition().getNutrients().get(i).getAmount()+" "+"g";
                            proteinsTxt.setText(protein);
                        }
                        if(specificRecipe.getNutrition().getNutrients().get(i).getName().equals("Fat")){
                            String fat = (int)specificRecipe.getNutrition().getNutrients().get(i).getAmount()+" "+"g";
                            fatTxt.setText(fat);
                        }

                        if(specificRecipe.getNutrition().getNutrients().get(i).getName().equals("Carbohydrates")){
                            String carb = (int)specificRecipe.getNutrition().getNutrients().get(i).getAmount()+" "+"g";
                            carbTxt.setText(carb);
                        }

                    }

                    if(!missingIngr.isEmpty()){
                        StringBuilder sb = new StringBuilder();
                        for (String s: missingIngr){
                            sb.append(s).append(",");
                        }
                    }
                    if(specificRecipe.getInstructions()!=null){
                        String instructionsWithoutHtmlTags = specificRecipe.getInstructions().replaceAll("\\<.*?\\>", "");
                        instructions_text.setText(instructionsWithoutHtmlTags);
                    }

                    for (int i = 0; i < specificRecipe.getNutrition().getIngredients().size(); i++){
                        if((int) specificRecipe.getNutrition().getIngredients().get(i).getAmount()==0){
                            content =  specificRecipe.getNutrition().getIngredients().get(i).getUnit() + " "
                                    + specificRecipe.getNutrition().getIngredients().get(i).getName() + "\n";
                            ingr_description.append(content);
                        }else{
                            content = (int) specificRecipe.getNutrition().getIngredients().get(i).getAmount() +" "
                                    + specificRecipe.getNutrition().getIngredients().get(i).getUnit() + " "
                                    + specificRecipe.getNutrition().getIngredients().get(i).getName() + "\n";
                            ingr_description.append(content);
                        }

                    }

                }
                loadingDialog.dismissDialog();

            }

            @Override
            public void onFailure(Call<SpecificRecipe> call, Throwable t) {

            }
        });
    }

    private void shareButtonLogic() {
        button_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("El source es " + " " + url);
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "What to cook");
                String shareMessage= "\nLet me recommend you this recipe\n\n";
                shareMessage = shareMessage + url;
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                startActivity(Intent.createChooser(shareIntent, "Share on..."));
            }
        });
    }

    private void createPersonalizedAd() {

        AdRequest adRequest = new AdRequest.Builder().build();
        createInterstisialAd(adRequest);

    }


    private void createInterstisialAd(AdRequest adRequest){
        InterstitialAd.load(this,"ca-app-pub-4185358034958198/8187278255", adRequest, new InterstitialAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                // The mInterstitialAd reference will be null until
                // an ad is loaded.
                mInterstitialAd = interstitialAd;
                Log.i("---AdMob", "onAdLoaded");
                mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback(){
                    @Override
                    public void onAdDismissedFullScreenContent() {
                        // Called when fullscreen content is dismissed.
                        RecetasDetalle.this.finish();
                        Log.d("TAG", "The ad was dismissed.");

                    }
                    @Override
                    public void onAdFailedToShowFullScreenContent(AdError adError) {
                        // Called when fullscreen content failed to show.
                        Log.d("TAG", "The ad failed to show.");
                    }
                    @Override
                    public void onAdShowedFullScreenContent() {
                        // Called when fullscreen content is shown.
                        // Make sure to set your reference to null so you don't
                        // show it a second time.
                        mInterstitialAd = null;
                        Log.d("TAG", "The ad was shown.");
                    }
                });
            }
            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                // Handle the error
                Log.i("---AdMob", loadAdError.getMessage());
                mInterstitialAd = null;
            }
        });
    }




    public void returnToListOfFood(View view) {
        if (mInterstitialAd != null) {
            mInterstitialAd.show(RecetasDetalle.this);
        } else {
            Log.d("TAG", "The interstitial ad wasn't ready yet.");
        }
        this.finish();
    }

}