package uk.ac.tees.b1498820.wefix.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import uk.ac.tees.b1498820.wefix.R;
import uk.ac.tees.b1498820.wefix.adapters.AuthenticationPagerAdapter;
import uk.ac.tees.b1498820.wefix.databinding.ActivityMainBinding;
import uk.ac.tees.b1498820.wefix.fragments.LoginFragment;
import uk.ac.tees.b1498820.wefix.fragments.RegisterFragment;
import uk.ac.tees.b1498820.wefix.fragments.SplashFragment;

public class MainActivity extends AppCompatActivity {
ViewPager2 viewPager;
ActivityMainBinding binding;
View view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        view = binding.getRoot();
        setContentView(view);

        //derive auth information on launch

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();

        //if logged in, display main application part
        //else display splash and auth pages
        if (currentUser != null){
            startActivity(new Intent(MainActivity.this, NavigationActivity.class));
            finish();
        }else{
            viewPager = binding.viewPager;

            AuthenticationPagerAdapter pagerAdapter = new AuthenticationPagerAdapter(getSupportFragmentManager(), getLifecycle());
            pagerAdapter.addFragment(new SplashFragment());
            pagerAdapter.addFragment(new LoginFragment());
            pagerAdapter.addFragment(new RegisterFragment());
            viewPager.setAdapter(pagerAdapter);

//      set handler to run task for specific time interval
            new Handler().postDelayed(() -> {
                pagerAdapter.removeFragment(0);
                viewPager.setAdapter(pagerAdapter);
            }, 2000);
        }
    }
}