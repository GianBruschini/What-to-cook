
package gian.Fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

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
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


import gian.Interfaces.RecetasApi;
import gian.Interfaces.SpoonacularApi;
import gian.adapters.HomeAdapter;
import gian.Model.HomeAdapterSpoon;
import gian.Model.LoadingDialog;
import gian.Model.RecetasDetalle;
import gian.Model.RetrofitSingleton;
import gian.Model.RetrofitSingletonSpoonacular;
import gian.Model.Root;
import gian.Model.SlideWelcome;
import gian.Model.SliderPagerAdapter;
import gian.Model.SpoonItem;
import gian.Model.item;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;


public class HomeFragment extends Fragment implements HomeAdapter.OnItemClickListener,View.OnClickListener     {
    View v;
    RecyclerView recyclerView;
    private HomeAdapterSpoon adapter;
    private List<item> mListFood = new ArrayList<>();
    private List<SpoonItem> mListFoodSpoon = new ArrayList<>();
    private ImageView lunchPicture;
    private ImageView breakfastPicture;
    private ImageView snackTimePicture;
    private ImageView dinnerPicture;
    private ToggleButton favouriteButtom;
    private  static final RecetasApi recipeService = RetrofitSingleton.getService();
    private String key;
    private LoadingDialog loading;
    private int actualPosition;
    private InterstitialAd mInterstitialAd;
    private ViewPager sliderPager;
    private List<SlideWelcome> lstSlides;
    private LinearLayout mDotLinearLayout;
    private TextView[] mDots;
    private TabLayout indicator;
    private CardView card_desayuno;
    private CardView card_lunch;
    private CardView card_snack;
    private CardView card_dinner;
    private static final SpoonacularApi recipeServiceSpoonacular= RetrofitSingletonSpoonacular.getService();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v= inflater.inflate(R.layout.fragment_home,container,false);
        loading = new LoadingDialog(getActivity());
        loading.startLoadingAlertDialog();
        setearViewPager();
        MobileAds.initialize(getContext(), new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
                createPersonalizedAd();
            }
        });
        setearKey();
        getValues();
        insertDataAccordingHour();
        return v;
    }


    private void setearViewPager() {
        sliderPager = v.findViewById(R.id.viewPager);
        indicator = v.findViewById(R.id.indicator);
        lstSlides = new ArrayList<>();
        lstSlides.add(new SlideWelcome(R.drawable.ic_logardo,null));
        lstSlides.add(new SlideWelcome(R.drawable.ic_logardo,null));
        lstSlides.add(new SlideWelcome(R.drawable.ic_logardo,null));
        lstSlides.add(new SlideWelcome(R.drawable.ic_logardo,null));
        lstSlides.add(new SlideWelcome(R.drawable.ic_logardo,null));
        lstSlides.add(new SlideWelcome(R.drawable.ic_logardo,null));
        SliderPagerAdapter adapter = new SliderPagerAdapter(getContext(),lstSlides);
        sliderPager.setAdapter(adapter);
        indicator.setupWithViewPager(sliderPager,true);
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new HomeFragment.sliderTimer(),4000,6000);
    }

    private void createPersonalizedAd() {
        AdRequest adRequest = new AdRequest.Builder().build();
        createInterstisialAd(adRequest);
    }

    private void createInterstisialAd(AdRequest adRequest){
        //Prueba ca-app-pub-3940256099942544/1033173712
        //Oficial ca-app-pub-4185358034958198/8187278255
        InterstitialAd.load(getActivity(),"ca-app-pub-3940256099942544/1033173712", adRequest, new InterstitialAdLoadCallback() {
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
                        if (mInterstitialAd != null) {
                            mInterstitialAd.show(getActivity());
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


    private void setearKey() {

        SharedPreferences prefs = getContext().getSharedPreferences("shared", MODE_PRIVATE);
        if(!prefs.contains("key")) {
            key = FirebaseDatabase.getInstance().getReference("RecetasFavoritas").push().getKey();
            SharedPreferences.Editor editor = getContext().getSharedPreferences("shared", MODE_PRIVATE).edit();
            editor.putString("key", key);
            editor.apply();
        }
    }

    @SuppressLint("SetTextI18n")
    private void insertDataAccordingHour() {
       Calendar c = Calendar.getInstance();
       int timeOfDay = c.get(Calendar.HOUR_OF_DAY);

       if(timeOfDay >= 0 && timeOfDay < 12){
           setCardSelectedBreakfast();
           setRecyclerViewWithBreakfastFood();
       }else{
           if(timeOfDay>=12 && timeOfDay < 15){
               setCardSelectedLunch();
               setRecyclerViewWithLunchFood();

           }else{
                if(timeOfDay >= 15 && timeOfDay < 19){
                    setCardSelectedSnack();
                    setRecyclerViewWithSnackTimeFood();
                }else{
                    if(timeOfDay >= 19 && timeOfDay < 24){
                        setCardSelectedDinner();
                        setRecyclerViewWithDinnerTimeFood();
                    }
                }
           }
       }
       loading.dismissDialog();
    }

    private void getValues() {

        lunchPicture = v.findViewById(R.id.lunch_picture);
        breakfastPicture = v.findViewById(R.id.breakfast_picture);
        snackTimePicture = v.findViewById(R.id.snackTime_picture);
        dinnerPicture = v.findViewById(R.id.dinner_picture);
        card_desayuno = v.findViewById(R.id.card_desayuno);
        card_lunch = v.findViewById(R.id.card_almuerzo);
        card_snack = v.findViewById(R.id.card_merienda);
        card_dinner = v.findViewById(R.id.card_cena);
        breakfastPicture.setOnClickListener(this);
        lunchPicture.setOnClickListener(this);
        snackTimePicture.setOnClickListener(this);
        dinnerPicture.setOnClickListener(this);


    }

    private void setRecyclerViewWithBreakfastFood() {
        Call<Root> callSpoonacula = recipeServiceSpoonacular
                .getRecipesTimeOfDay("https://api.spoonacular.com/recipes/search?query="
                        + "breakfast" +
                        "&number=20&instructionsRequired=true&apiKey=c957b6816ba048139fbc25a67d2cff33");

        callSpoonacula.enqueue(new Callback<Root>() {
            @Override
            public void onResponse(Call<Root> call, Response<Root> response) {
                Root root = response.body();
                for(int i = 0; i < root.getResults().size(); i++){
                    mListFoodSpoon.add(new SpoonItem(root.getResults().get(i).getId()
                    ,root.getResults().get(i).getTitle(),root.getResults().get(i).getReadyInMinutes()
                    ,root.getResults().get(i).getSourceUrl()));

                }
                recyclerView.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<Root> call, Throwable t) {

            }
        });


    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        recyclerView = v.findViewById(R.id.recycler_view);
        adapter = new HomeAdapterSpoon(getActivity(), mListFoodSpoon);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),1,GridLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        //adapter.setOnItemClickListener(HomeFragment.this);
    }

    @Override
    public void onitemClick(int position) {
        actualPosition = position;
        if (mInterstitialAd != null) {
            mInterstitialAd.show(getActivity());
        } else {
            Log.d("TAG", "The interstitial ad wasn't ready yet.");
            Intent detailedIntent = new Intent(getContext(),RecetasDetalle.class);
            item itemClicked = mListFood.get(position);
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
        }

    }

    public void clear() {
        int size = mListFood.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                mListFood.remove(0);
            }

            recyclerView.getAdapter().notifyItemRangeRemoved(0, size);
        }
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.breakfast_picture:
                clear();
                setRecyclerViewWithBreakfastFood();
                setCardSelectedBreakfast();
                recyclerView.getAdapter().notifyDataSetChanged();
                break;

            case R.id.lunch_picture:
                clear();
                setRecyclerViewWithLunchFood();
                setCardSelectedLunch();
                recyclerView.getAdapter().notifyDataSetChanged();
                break;

            case R.id.snackTime_picture:
                clear();
                setRecyclerViewWithSnackTimeFood();
                setCardSelectedSnack();
                recyclerView.getAdapter().notifyDataSetChanged();
                break;

            case R.id.dinner_picture:
                clear();
                setRecyclerViewWithDinnerTimeFood();
                setCardSelectedDinner();
                recyclerView.getAdapter().notifyDataSetChanged();
                break;

        }
    }

    private void setCardSelectedDinner() {
        card_desayuno.setCardBackgroundColor(0xffffffff);
        card_snack.setCardBackgroundColor(0xffffffff);
        card_dinner.setCardBackgroundColor(0xffff785b);
        card_lunch.setCardBackgroundColor(0xffffffff);
    }

    private void setCardSelectedSnack() {
        card_desayuno.setCardBackgroundColor(0xffffffff);
        card_snack.setCardBackgroundColor(0xffff785b);
        card_dinner.setCardBackgroundColor(0xffffffff);
        card_lunch.setCardBackgroundColor(0xffffffff);
    }

    private void setCardSelectedLunch() {
        card_desayuno.setCardBackgroundColor(0xffffffff);
        card_snack.setCardBackgroundColor(0xffffffff);
        card_dinner.setCardBackgroundColor(0xffffffff);
        card_lunch.setCardBackgroundColor(0xffff785b);
    }

    private void setCardSelectedBreakfast() {
        card_desayuno.setCardBackgroundColor(0xffff785b);
        card_lunch.setCardBackgroundColor(0xffffffff);
        card_snack.setCardBackgroundColor(0xffffffff);
        card_dinner.setCardBackgroundColor(0xffffffff);
    }

    private void setRecyclerViewWithDinnerTimeFood() {

        Call<Root> callSpoonacula = recipeServiceSpoonacular
                .getRecipesTimeOfDay("https://api.spoonacular.com/recipes/search?query="
                        + "dinner" +
                        "&number=20&instructionsRequired=true&apiKey=c957b6816ba048139fbc25a67d2cff33");

        callSpoonacula.enqueue(new Callback<Root>() {
            @Override
            public void onResponse(Call<Root> call, Response<Root> response) {
                Root root = response.body();
                for(int i = 0; i < root.getResults().size(); i++){
                    mListFoodSpoon.add(new SpoonItem(root.getResults().get(i).getId()
                            ,root.getResults().get(i).getTitle(),root.getResults().get(i).getReadyInMinutes()
                    ,root.getResults().get(i).getSourceUrl()));

                }
                recyclerView.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<Root> call, Throwable t) {

            }
        });
    }

    private void setRecyclerViewWithSnackTimeFood() {


        Call<Root> callSpoonacula = recipeServiceSpoonacular
                .getRecipesTimeOfDay("https://api.spoonacular.com/recipes/search?query="
                        + "snack" +
                        "&number=20&instructionsRequired=true&apiKey=c957b6816ba048139fbc25a67d2cff33");

        callSpoonacula.enqueue(new Callback<Root>() {
            @Override
            public void onResponse(Call<Root> call, Response<Root> response) {

                Root root = response.body();
                for(int i = 0; i < root.getResults().size(); i++){
                    System.out.println("El nombre es " + " " + root.getResults().get(i).getTitle());
                    mListFoodSpoon.add(new SpoonItem(root.getResults().get(i).getId()
                            ,root.getResults().get(i).getTitle(),root.getResults().get(i).getReadyInMinutes()
                    ,root.getResults().get(i).getSourceUrl()));

                }
                recyclerView.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<Root> call, Throwable t) {

            }
        });
    }

    private void setRecyclerViewWithLunchFood() {

        Call<Root> callSpoonacula = recipeServiceSpoonacular
                .getRecipesTimeOfDay("https://api.spoonacular.com/recipes/search?query="
                        + "lunch" +
                        "&number=20&instructionsRequired=true&apiKey=c957b6816ba048139fbc25a67d2cff33");

        callSpoonacula.enqueue(new Callback<Root>() {
            @Override
            public void onResponse(Call<Root> call, Response<Root> response) {
                Root root = response.body();
                for(int i = 0; i < root.getResults().size(); i++){
                    mListFoodSpoon.add(new SpoonItem(root.getResults().get(i).getId()
                            ,root.getResults().get(i).getTitle(),root.getResults().get(i).getReadyInMinutes()
                    ,root.getResults().get(i).getSourceUrl()));

                }
                recyclerView.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<Root> call, Throwable t) {

            }
        });
    }


    class sliderTimer extends TimerTask {

        @Override
        public void run() {
            if(getActivity()!=null){
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (sliderPager.getCurrentItem() < lstSlides.size() - 1) {
                            sliderPager.setCurrentItem(sliderPager.getCurrentItem() + 1);
                        } else {
                            sliderPager.setCurrentItem(0);
                        }


                    }
                });
            }

        }


    }
}
