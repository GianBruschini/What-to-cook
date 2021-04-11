package WhatToCook.Model;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.example.getcooked.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.yarolegovich.slidingrootnav.SlidingRootNav;
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder;

import java.util.Arrays;

import WhatToCook.Clases.DrawerAdapter;
import WhatToCook.Clases.DrawerItem;
import WhatToCook.Clases.SimpleItem;
import WhatToCook.Clases.SpaceItem;
import WhatToCook.Fragments.HomeFragment;

public class HomeActivity2 extends AppCompatActivity implements DrawerAdapter.OnItemSelectedListener {
    private AdapterView.OnItemSelectedListener listener;
    private static final int POS_HOME = 0;
    private static final int POS_LIKES = 1;
    private static final int POS_MESSAGES = 2;
    private static final int POS_PERFIL= 3;
    private static final int POS_AGREGAR= 4;
    private static final int POS_SETTINGS = 5;
    private static final int POS_LOGOUT = 7;

    private String[] screenTitles;
    private Drawable[] screenIcons;
    private SlidingRootNav slidingRootNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home2);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false); //remuevo el nombre de la app del toolbar

        slidingRootNav = new SlidingRootNavBuilder(this)
                .withToolbarMenuToggle(toolbar)
                .withDragDistance(180)
                .withRootViewScale(0.75f)
                .withRootViewElevation(25)
                .withMenuOpened(false)
                .withContentClickableWhenMenuOpened(false)
                .withSavedState(savedInstanceState)
                .withMenuLayout(R.layout.drawer_menu)
                .inject();




        /*View headerView = slidingRootNav.getHeaderView(0);
        TextView navUsername = (TextView) headerView.findViewById(R.id.navUsername);
        navUsername.setText("Your Text Here");

         */

        screenIcons = loadScreenIcons();
        screenTitles = loadScreenTitles();

        DrawerAdapter adapter = new DrawerAdapter(Arrays.asList(
                createItemFor(POS_HOME).setChecked(true),
                createItemFor(POS_LIKES),
                createItemFor(POS_MESSAGES),
                createItemFor(POS_PERFIL),
                createItemFor(POS_AGREGAR),
                createItemFor(POS_SETTINGS),
                new SpaceItem(90),
                createItemFor(POS_LOGOUT)));
        adapter.setListener(this);

        RecyclerView list = findViewById(R.id.drawer_list);
        list.setNestedScrollingEnabled(false);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(adapter);

        adapter.setSelected(POS_HOME);
    }



    /*private void showFragment(Fragment fragment) {
        System.out.println("ENTRE 2");
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_layout, fragment)
                .commit();
        System.out.println("ENTRE 3");
    }

     */





    @SuppressWarnings("rawtypes")
    private DrawerItem createItemFor(int position) {
        return new SimpleItem(screenIcons[position], screenTitles[position])
                .withIconTint(color(R.color.grayText))
                .withTextTint(color(R.color.grayText))
                .withSelectedIconTint(color(R.color.white))
                .withSelectedTextTint(color(R.color.white));
    }

    private String[] loadScreenTitles() {
        return getResources().getStringArray(R.array.ld_activityScreenTitles);
    }

    private Drawable[] loadScreenIcons() {
        TypedArray ta = getResources().obtainTypedArray(R.array.ld_activityScreenIcons);
        Drawable[] icons = new Drawable[ta.length()];
        for (int i = 0; i < ta.length(); i++) {
            int id = ta.getResourceId(i, 0);
            if (id != 0) {
                icons[i] = ContextCompat.getDrawable(this, id);
            }
        }
        ta.recycle();
        return icons;
    }

    @ColorInt
    private int color(@ColorRes int res) {
        return ContextCompat.getColor(this, res);
    }



    @Override
    public void onItemSelected(int position) {
        if (position == POS_HOME) {

            setFragment(new HomeFragment());
        }

        if (position == POS_LIKES) {

        }

        if (position == POS_MESSAGES) {

        }

        if (position == POS_PERFIL) {

        }

        if (position == POS_AGREGAR) {


        }

        if (position == POS_SETTINGS) {

        }

        if (position == POS_LOGOUT) {

        }
        slidingRootNav.closeMenu();

    }


    public void setFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout,fragment);
        fragmentTransaction.commit();
        System.out.println("Comitio");
    }





    }



