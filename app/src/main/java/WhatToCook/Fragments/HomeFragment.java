package WhatToCook.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.getcooked.R;

import org.joda.time.DateTime;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.TimeZone;

import WhatToCook.Interfaces.RecetasApi;
import WhatToCook.Model.Comida_detail_cardViewHome;
import WhatToCook.Model.HomeAdapter;
import WhatToCook.Model.Receipe;
import WhatToCook.Model.Receta;
import WhatToCook.Model.RetrofitSingleton;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class HomeFragment extends Fragment implements HomeAdapter.OnItemClickListener,View.OnClickListener     {
    View v;
    RecyclerView recyclerView;
    private HomeAdapter adapter;
    private List<Comida_detail_cardViewHome> mListFood = new ArrayList<>();
    private ImageView lunchPicture;
    private ImageView breakfastPicture;
    private ImageView snackTimePicture;
    private ImageView dinnerPicture;
    private ToggleButton favouriteButtom;
    private  static final RecetasApi recipeService = RetrofitSingleton.getService();;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v= inflater.inflate(R.layout.fragment_home,container,false);
        getValues();
        insertDataAccordingHour();
        return v;

    }

    @SuppressLint("SetTextI18n")
    private void insertDataAccordingHour() {
       Calendar c = Calendar.getInstance();
       int timeOfDay = c.get(Calendar.HOUR_OF_DAY);

       if(timeOfDay >= 0 && timeOfDay < 12){

           setRecyclerViewWithBreakfastFood();
       }else{
           if(timeOfDay>=12 && timeOfDay < 16){

               setRecyclerViewWithLunchFood();
           }else{
                if(timeOfDay >= 16 && timeOfDay < 21){

                    setRecyclerViewWithSnackTimeFood();
                }else{
                    if(timeOfDay >= 21 && timeOfDay < 24){
                        setRecyclerViewWithDinnerTimeFood();
                    }
                }
           }
       }







    }



    private void getValues() {

        lunchPicture = v.findViewById(R.id.lunch_picture);
        breakfastPicture = v.findViewById(R.id.breakfast_picture);
        snackTimePicture = v.findViewById(R.id.snackTime_picture);
        dinnerPicture = v.findViewById(R.id.dinner_picture);
        breakfastPicture.setOnClickListener(this);
        lunchPicture.setOnClickListener(this);
        snackTimePicture.setOnClickListener(this);
        dinnerPicture.setOnClickListener(this);


    }


    private void setRecyclerViewWithBreakfastFood() {

        
        Call<Receta> call = recipeService.getRecipeByBreakfast("https://api.edamam.com/"+"search"+"?"+"q"+"="+"breakfast"
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
                        mListFood.add(new Comida_detail_cardViewHome(rec.getRecipe().getLabel(),rec.getRecipe().getImage(),String.valueOf(rec.getRecipe().getCalories())));
                    }

                }
                recyclerView.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<Receta> call, Throwable t) {
                Toast.makeText(getActivity(),"Error, vuelva a intentarlo pronto",Toast.LENGTH_LONG).show();

            }
        });


    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        recyclerView = v.findViewById(R.id.recycler_view);
        adapter = new HomeAdapter(getActivity(), mListFood);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),1,GridLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(HomeFragment.this);


    }

    @Override
    public void onitemClick(int position) {

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
                recyclerView.getAdapter().notifyDataSetChanged();
                break;

            case R.id.lunch_picture:
                clear();
                setRecyclerViewWithLunchFood();
                recyclerView.getAdapter().notifyDataSetChanged();
                break;

            case R.id.snackTime_picture:
                clear();
                setRecyclerViewWithSnackTimeFood();
                recyclerView.getAdapter().notifyDataSetChanged();
                break;

            case R.id.dinner_picture:
                clear();
                setRecyclerViewWithDinnerTimeFood();
                recyclerView.getAdapter().notifyDataSetChanged();
                break;

        }




    }

    private void setRecyclerViewWithDinnerTimeFood() {
            
        Call<Receta> call = recipeService.getRecipeByBreakfast("https://api.edamam.com/"+"search"+"?"+"q"+"="+"dinner"
                +"&"+"app_id"+"="+"25d5c2a6"+"&"+"app_key"+"="+"41b4d59bd6ed74ebf43f69c96b5f1761"+"&"+"mealType"+"="
                +"dinner"+"&"+"to"+"="+"20");

        call.enqueue(new Callback<Receta>() {
            @Override
            public void onResponse(Call<Receta> call, Response<Receta> response) {

                if(response.isSuccessful()){
                    Receta r = response.body();
                    ArrayList<Receipe> recetas =r.getHits();
                    for(int i = 0; i<recetas.size(); i++){
                        Receipe rec = recetas.get(i);
                        mListFood.add(new Comida_detail_cardViewHome(rec.getRecipe().getLabel(),rec.getRecipe().getImage(),String.valueOf(rec.getRecipe().getCalories())));

                    }

                    recyclerView.getAdapter().notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<Receta> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
    }

    private void setRecyclerViewWithSnackTimeFood() {


        Call<Receta> call = recipeService.getRecipeByBreakfast("https://api.edamam.com/"+"search"+"?"+"q"+"="+"snack"
                +"&"+"app_id"+"="+"25d5c2a6"+"&"+"app_key"+"="+"41b4d59bd6ed74ebf43f69c96b5f1761"+"&"+"mealType"+"="
                +"snack"+"&"+"to"+"="+"20");

        call.enqueue(new Callback<Receta>() {
            @Override
            public void onResponse(Call<Receta> call, Response<Receta> response) {

                if(response.isSuccessful()){
                    Receta r = response.body();
                    ArrayList<Receipe> recetas =r.getHits();
                    for(int i = 0; i<recetas.size(); i++){
                        Receipe rec = recetas.get(i);
                        mListFood.add(new Comida_detail_cardViewHome(rec.getRecipe().getLabel(),rec.getRecipe().getImage(),String.valueOf(rec.getRecipe().getCalories())));

                    }

                    recyclerView.getAdapter().notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<Receta> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
    }

    private void setRecyclerViewWithLunchFood() {

        Call<Receta> call = recipeService.getRecipeByBreakfast("https://api.edamam.com/"+"search"+"?"+"q"+"="+"lunch"
                +"&"+"app_id"+"="+"25d5c2a6"+"&"+"app_key"+"="+"41b4d59bd6ed74ebf43f69c96b5f1761"+"&"+"mealType"+"="
                +"lunch"+"&"+"to"+"="+"20");

        call.enqueue(new Callback<Receta>() {
            @Override
            public void onResponse(Call<Receta> call, Response<Receta> response) {

                if(response.isSuccessful()){
                    Receta r = response.body();
                    ArrayList<Receipe> recetas =r.getHits();
                    for(int i = 0; i<recetas.size(); i++){
                        Receipe rec = recetas.get(i);
                        mListFood.add(new Comida_detail_cardViewHome(rec.getRecipe().getLabel(),rec.getRecipe().getImage(),String.valueOf(rec.getRecipe().getCalories())));
                    }

                }
                recyclerView.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<Receta> call, Throwable t) {
                Toast.makeText(getActivity(),"Error, vuelva a intentarlo pronto",Toast.LENGTH_LONG).show();

            }
        });
    }
}
