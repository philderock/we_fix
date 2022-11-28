package uk.ac.tees.b1498820.wefix;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import uk.ac.tees.b1498820.wefix.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        view = binding.getRoot();
        setContentView(view);

        binding.textViewUserSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent userSignUpIntent = new Intent(LoginActivity.this, UserSignupActivity.class);
                startActivity(userSignUpIntent);
                finish();
            }
        });

        binding.textViewStaffSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent staffSignUpIntent = new Intent(LoginActivity.this, StaffSignupActivity.class);
                startActivity(staffSignUpIntent);
                finish();
            }
        });
    }
}