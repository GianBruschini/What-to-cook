package WhatToCook.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.getcooked.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.takusemba.multisnaprecyclerview.MultiSnapRecyclerView;

import java.util.ArrayList;
import java.util.List;

import WhatToCook.Interfaces.RecetasApi;
import WhatToCook.Model.AdapterMenu;
import WhatToCook.Model.Comida_detail_cardViewHome;
import WhatToCook.Model.HomeActivity;
import WhatToCook.Model.HomeAdapter;
import WhatToCook.Model.Receipe;
import WhatToCook.Model.Receta;
import WhatToCook.Model.item;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class HomeFragment extends Fragment implements HomeAdapter.OnItemClickListener     {
    View v;
    RecyclerView recyclerView;
    private HomeAdapter adapter;
    private List<Comida_detail_cardViewHome> mlistBreakfast = new ArrayList<>();
    private List<item> mlistLunch = new ArrayList<>();
    private List<item> mlistSnackTime = new ArrayList<>();
    private List<item> mlistDinner = new ArrayList<>();
    private CardView card;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v= inflater.inflate(R.layout.fragment_home,container,false);
        getBreakfastReceipes();
        return v;
    }




    private void getBreakfastReceipes() {


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://test-es.edamam.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RecetasApi recipeService = retrofit.create(RecetasApi.class);
        Call<Receta> call = recipeService.getRecipeByBreakfast("Desayuno",20,"breakfast");

        call.enqueue(new Callback<Receta>() {
            @Override
            public void onResponse(Call<Receta> call, Response<Receta> response) {

                if(response.isSuccessful()){
                    Receta r = response.body();
                    ArrayList<Receipe> recetas =r.getHits();
                    for(int i = 0; i<recetas.size(); i++){
                        Receipe rec = recetas.get(i);
                        mlistBreakfast.add(new Comida_detail_cardViewHome(rec.getRecipe().getLabel(),rec.getRecipe().getImage(),String.valueOf(rec.getRecipe().getCalories())));
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
        adapter = new HomeAdapter(getActivity(),mlistBreakfast);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),1,GridLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(HomeFragment.this);


    }


    @Override
    public void onitemClick(int position) {

    }
}
