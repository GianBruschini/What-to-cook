package gian.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import java.util.ArrayList;
import java.util.List;


import gian.Model.Ingredient;
import gian.Model.RecetasInfoActivity;
import gian.Model.RecyclerViewAdapterIngredient;

public class SearchRecipesFragment extends Fragment implements View.OnClickListener {
    View view;
    private String s;
    private List<Ingredient> lstIngredient = new ArrayList<>();
    private RecyclerView myrv;
    private EditText editTextSearch;
    private RecyclerViewAdapterIngredient myAdapter;
    private InterstitialAd mInterstitialAd;
    private List<String> tmp;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeIngredients();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_search_recipes,container,false);
        MobileAds.initialize(getActivity(), new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
                createPersonalizedAd();
            }
        });
        setRecyclerView();
        makeActionEditText();
        setButtonSearchAction();
        return view;
    }

    private void createPersonalizedAd() {
        AdRequest adRequest = new AdRequest.Builder().build();
        createInterstisialAd(adRequest);
    }

    private void createInterstisialAd(AdRequest adRequest){
        InterstitialAd.load(getActivity(),"ca-app-pub-4185358034958198/8187278255", adRequest, new InterstitialAdLoadCallback() {
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
                        Log.d("TAG", "The ad was dismissed.");
                        createPersonalizedAd();
                        if (mInterstitialAd != null) {
                            s = tmp.toString();
                            s = s.substring(1, s.length()-1);
                            Intent i = new Intent(getContext(), RecetasInfoActivity.class);
                            i.putExtra("Ingredientes", s);
                            startActivity(i);
                        } else {
                            Log.d("TAG", "The interstitial ad wasn't ready yet.");

                        }

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


    private void makeActionEditText() {
        editTextSearch = view.findViewById(R.id.search);
        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                filter(editable.toString());
            }
        });
    }



    private void filter(String text) {
        ArrayList<Ingredient>filteredList = new ArrayList<>();
        for(Ingredient item: lstIngredient){
            if(item.getName().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(item);
            }
        }
        myAdapter.filterList(filteredList);

    }

    private void setButtonSearchAction() {
        Button buscarRecetas = view.findViewById(R.id.buscarRecetasButton);
        buscarRecetas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tmp = RecyclerViewAdapterIngredient.ingredientsList;
                if(tmp.isEmpty()){
                    Toast.makeText(getActivity(), "You must select at least one ingredient", Toast.LENGTH_LONG).show();
                }
                else{
                    if(mInterstitialAd!=null){
                        s = tmp.toString();
                        s = s.substring(1, s.length()-1);
                        Intent i = new Intent(getContext(), RecetasInfoActivity.class);
                        i.putExtra("Ingredientes", s);
                        startActivity(i);
                        //verCodigoResponse();
                        mInterstitialAd.show(getActivity());
                    }else{
                        Log.d("TAG", "The interstitial ad wasn't ready yet.");
                    }

                }
            }
        });
    }

    private void setRecyclerView() {
        myrv = view.findViewById(R.id.recycleview_ingredients);
        myrv.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        myAdapter = new RecyclerViewAdapterIngredient(getContext(), lstIngredient);
        myrv.setAdapter(myAdapter);
    }


    private void initializeIngredients() {
        lstIngredient.add(new Ingredient("Beef", "beef-cubes-raw.png"));
        lstIngredient.add(new Ingredient("Fish", "fish-fillet.jpg"));
        lstIngredient.add(new Ingredient("Chicken", "chicken-breasts.png"));
        lstIngredient.add(new Ingredient("Tuna", "canned-tuna.png"));
        lstIngredient.add(new Ingredient("Flour", "flour.png"));
        lstIngredient.add(new Ingredient("Rice", "uncooked-white-rice.png"));
        lstIngredient.add(new Ingredient("Pasta", "fusilli.jpg"));
        lstIngredient.add(new Ingredient("Cheese", "cheddar-cheese.png"));
        lstIngredient.add(new Ingredient("Butter", "butter.png"));
        lstIngredient.add(new Ingredient("Bread", "white-bread.jpg"));
        lstIngredient.add(new Ingredient("Onion", "brown-onion.png"));
        lstIngredient.add(new Ingredient("Garlic", "garlic.jpg"));
        lstIngredient.add(new Ingredient("Milk", "milk.png"));
        lstIngredient.add(new Ingredient("Eggs", "egg.png"));
        lstIngredient.add(new Ingredient("Oil", "vegetable-oil.jpg"));
        lstIngredient.add(new Ingredient("Yogurt", "plain-yogurt.jpg"));
        lstIngredient.add(new Ingredient("Salt", "salt.jpg"));
        lstIngredient.add(new Ingredient("Sugar", "sugar-in-bowl.png"));
        lstIngredient.add(new Ingredient("Pepper", "pepper.jpg"));
        lstIngredient.add(new Ingredient("Water", "water.jpg"));
        lstIngredient.add(new Ingredient("Parsley", "parsley.jpg"));
        lstIngredient.add(new Ingredient("Basil", "basil.jpg"));
        lstIngredient.add(new Ingredient("Chocolate", "milk-chocolate.jpg"));
        lstIngredient.add(new Ingredient("Nuts", "hazelnuts.png"));
        lstIngredient.add(new Ingredient("Tomato", "tomato.png"));
        lstIngredient.add(new Ingredient("Cucumber", "cucumber.jpg"));
        lstIngredient.add(new Ingredient("Bell pepper", "red-bell-pepper.jpg"));
        lstIngredient.add(new Ingredient("Mushrooms", "portabello-mushrooms.jpg"));
        lstIngredient.add(new Ingredient("Lemon", "lemon.jpg"));
        lstIngredient.add(new Ingredient("Orange", "orange.jpg"));
        lstIngredient.add(new Ingredient("Banana", "bananas.jpg"));
        lstIngredient.add(new Ingredient("Wine", "red-wine.jpg"));
        lstIngredient.add(new Ingredient("Apple", "apple.jpg"));
        lstIngredient.add(new Ingredient("Berries", "berries-mixed.jpg"));
        lstIngredient.add(new Ingredient("Biscuits", "buttermilk-biscuits.jpg"));
        lstIngredient.add(new Ingredient("Pineapple", "pineapple.jpg"));
        lstIngredient.add(new Ingredient("Bacon", "bacon.jpg"));
        lstIngredient.add(new Ingredient("Baguette", "baguette.jpg"));
        lstIngredient.add(new Ingredient("Chocolate milk", "chocolate-milk.jpg"));
        lstIngredient.add(new Ingredient("Coconut", "coconut.jpg"));
        lstIngredient.add(new Ingredient("Coffee", "coffee.jpg"));
        lstIngredient.add(new Ingredient("Cognac", "cognac.jpg"));
        lstIngredient.add(new Ingredient("Corn", "corn.jpg"));
    }


    @Override
    public void onClick(View view) {

    }
}
