package gian.Model;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;


import com.gian.getcooked.R;


public class HomeActivity2 extends AppCompatActivity  {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home2);
        //setTextAccordingHourOfDay();

    }

    /*private void setTextAccordingHourOfDay() {

            Calendar c = Calendar.getInstance();
            int timeOfDay = c.get(Calendar.HOUR_OF_DAY);

            if(timeOfDay >= 0 && timeOfDay < 12){

                textoBienvenida.setText("Hi! \nIt's breakfast time!");
            }else{
                if(timeOfDay>=12 && timeOfDay < 16){

                    textoBienvenida.setText("Hi! \nIt's lunch time!");
                }else{
                    if(timeOfDay >= 16 && timeOfDay < 21){

                        textoBienvenida.setText("Hi! \nIt's snack time!");
                    }else{
                        if(timeOfDay >= 21 && timeOfDay < 24){
                            textoBienvenida.setText("Hi! \nIt's dinner time!");
                        }
                    }
                }
            }


    }

     */







    /*public void onItemSelected(int position) {

        switch(position){
            case POS_HOME:
                setTextAccordingHourOfDay();
                setFragment(new HomeFragment());
                break;
            case POS_SEARCH:
                textoBienvenida.setText("Search your recipes\n by ingredient");
                setFragment(new SearchRecipesFragment());
                break;

            case POS_FAVORITES:
                textoBienvenida.setText("My favourite\nlist ");
                setFragment(new FavouritesFragment());
                break;


        }
        slidingRootNav.closeMenu();
    }

     */


    public void setFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout,fragment);
        fragmentTransaction.commit();
        System.out.println("Comitio");
    }





    }



