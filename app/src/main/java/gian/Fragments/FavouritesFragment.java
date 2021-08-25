package gian.Fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


import gian.Model.HomeAdapterSpoon;
import gian.Model.LoadingDialog;
import gian.Model.RecetasDetalle;
import gian.Model.SpoonItem;
import gian.Model.SpoonItemFirebase;

import static android.content.Context.MODE_PRIVATE;


public class FavouritesFragment extends Fragment implements HomeAdapterSpoon.OnItemClickListener,View.OnClickListener {
    private View view;
    private DatabaseReference databaseReference;
    private String mykey;
    private List<SpoonItem> mListFood = new ArrayList<>();
    private List<SpoonItem> mListToAdapter = new ArrayList<>();
    private HomeAdapterSpoon homeAdapterSpoon;
    private RecyclerView recyclerView;
    private LoadingDialog loading;
    private ProgressBar progressBar;
    private TextView text_empty_view;
    private InterstitialAd mInterstitialAd;
    private int globalPosition;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_favourites,container,false);
        MobileAds.initialize(getActivity(), new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
                createPersonalizedAd();
            }
        });
        loading = new LoadingDialog(getActivity());
        loading.startLoadingAlertDialog();
        getValues();
        setRecyclerViewWithMyFavouritesRecipes();
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
                            ArrayList<String> arrayNull = new ArrayList<>();
                            SpoonItem currentItemClicked = mListFood.get(globalPosition);
                            Intent intent = new Intent(getActivity(), RecetasDetalle.class);
                            intent.putExtra("recipeID",currentItemClicked.getId());
                            intent.putExtra("missingIngredients",arrayNull);
                            intent.putExtra("missinIngredientsStringUris",arrayNull);
                            startActivity(intent);

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



    private void setRecyclerViewWithMyFavouritesRecipes() {
        databaseReference = FirebaseDatabase.
                            getInstance().
                            getReference().
                            child("RecetasFavoritas").
                            child(mykey);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data: dataSnapshot.getChildren()){

                          SpoonItemFirebase comidaFavorita = data.getValue(SpoonItemFirebase.class);
                          try{
                              int id = Integer.parseInt(comidaFavorita.getTimeReady());
                              mListFood.add(new SpoonItem(Integer.parseInt(comidaFavorita.getId()),
                                      comidaFavorita.getNombreReceta()
                                      ,id
                                      ,comidaFavorita.getImagenUrl()
                              ));
                          }catch(NumberFormatException ex){

                          }
                }
                progressBar.setVisibility(View.GONE);
                if(mListFood.isEmpty()){
                    text_empty_view.setVisibility(View.VISIBLE);
                }
                recyclerView.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        homeAdapterSpoon = new HomeAdapterSpoon(getActivity(), mListFood);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),1,GridLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(homeAdapterSpoon);
        homeAdapterSpoon.setOnItemClickListener(FavouritesFragment.this);
        loading.dismissDialog();

    }

    private void getValues() {
        recyclerView = view.findViewById(R.id.recycler_view_favoritos);
        progressBar = view.findViewById(R.id.progressBarFavourites);
        text_empty_view = view.findViewById(R.id.text_empty_view);
        progressBar.setVisibility(View.VISIBLE);
        SharedPreferences prefs = getContext().getSharedPreferences("shared", MODE_PRIVATE);
        mykey = prefs.getString("key","");
    }

    @Override
    public void onitemClick(int position) {

        if (mInterstitialAd != null) {
            globalPosition = position;
            ArrayList<String> arrayNull = new ArrayList<>();
            SpoonItem currentItemClicked = mListFood.get(position);
            Intent intent = new Intent(getActivity(), RecetasDetalle.class);
            intent.putExtra("recipeID",currentItemClicked.getId());
            intent.putExtra("missingIngredients",arrayNull);
            intent.putExtra("missinIngredientsStringUris",arrayNull);
            startActivity(intent);
            mInterstitialAd.show(getActivity());
        } else {
            Log.d("TAG", "The interstitial ad wasn't ready yet.");
        }



    }

    @Override
    public void onClick(View view) {

    }
}
