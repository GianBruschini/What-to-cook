package gian.Model;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.gian.getcooked.R;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                Intent startLandingPageActivity = new Intent(WelcomeActivity.this, HomeRecetas.class);
                startActivity(startLandingPageActivity);
            }
        }, 1000);
    }
}