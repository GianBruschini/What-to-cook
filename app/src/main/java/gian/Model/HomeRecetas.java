package gian.Model;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.gian.getcooked.R;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import gian.Fragments.FavouritesFragment;
import gian.Fragments.HomeFragment;
import gian.Fragments.HomeFragmentSpoon;
import gian.Fragments.SearchRecipesFragment;

public class HomeRecetas extends AppCompatActivity {
    ChipNavigationBar bottomNav;
    private LoadingDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_recetas);
        bottomNav = findViewById(R.id.bottomNav);


        if(savedInstanceState==null){
            bottomNav.setItemSelected(R.id.nav_home,true);

            setFragment(new HomeFragmentSpoon());
        }

        bottomNav.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int id) {

                switch(id){
                    case R.id.nav_home:
                        setFragment(new HomeFragmentSpoon());
                        break;
                    case R.id.nav_buscar:
                        setFragment(new SearchRecipesFragment());
                        break;
                    case R.id.nav_favourites:
                        setFragment(new FavouritesFragment());
                        break;

                }
            }
        });

    }

    public void setFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout,fragment);
        fragmentTransaction.commit();

    }
}