package uk.ac.tees.b1498820.wefix;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // set handler to run task for specific time interval
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //create the intent
                Intent splashScreenIntent = new Intent(MainActivity.this, LoginActivity.class);

                //Start activity
                startActivity(splashScreenIntent);
                finish();
            }
        }, 2000);

    }
}