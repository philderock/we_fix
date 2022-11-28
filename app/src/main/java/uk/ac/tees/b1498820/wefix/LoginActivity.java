package uk.ac.tees.b1498820.wefix;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {
    EditText editTextUserLoginUsername, editTextUserLoginPassword;
    Button buttonUserLogin;
    TextView textViewUserSignup, textViewStaffSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextUserLoginUsername = findViewById(R.id.edit_text_user_login_username);
        editTextUserLoginPassword = findViewById(R.id.edit_text_user_login_username);
        buttonUserLogin = findViewById(R.id.button_user_login);
        textViewUserSignup = findViewById(R.id.text_view_user_signup);
        textViewStaffSignup = findViewById(R.id.text_view_staff_signup);

        textViewUserSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent userSignUpIntent = new Intent(LoginActivity.this, UserSignupActivity.class);
                startActivity(userSignUpIntent);
                finish();
            }
        });

        textViewStaffSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent staffSignUpIntent = new Intent(LoginActivity.this, StaffSignupActivity.class);
                startActivity(staffSignUpIntent);
                finish();
            }
        });
    }
}