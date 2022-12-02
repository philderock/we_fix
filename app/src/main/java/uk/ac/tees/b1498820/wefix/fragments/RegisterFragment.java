package uk.ac.tees.b1498820.wefix.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Locale;

import uk.ac.tees.b1498820.wefix.R;
import uk.ac.tees.b1498820.wefix.activities.NavigationActivity;
import uk.ac.tees.b1498820.wefix.databinding.FragmentRegisterBinding;

public class RegisterFragment extends Fragment {
    FragmentRegisterBinding binding;
    FirebaseAuth firebaseAuth;
    View view;
    String email = "", password = "";
    ProgressDialog progressDialog;

    public RegisterFragment() {
        // Required empty public constructor
    }

    public static RegisterFragment newInstance() {
        RegisterFragment fragment = new RegisterFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //initialize firebase
        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Please wait");
        progressDialog.setMessage("Creating your account...");
        progressDialog.setCanceledOnTouchOutside(false);

        //initialize view binding
        binding = FragmentRegisterBinding.inflate(getLayoutInflater());

        //set register button onclick event
        binding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateData();
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = binding.getRoot();
        return view;
    }

    void validateData(){
        email = binding.etEmail.getText().toString().trim();
        password = binding.etPassword.getText().toString().trim();
        //validate
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.etEmail.setError("Invalid email address format");
        }else if (TextUtils.isEmpty(password)){
            binding.etPassword.setError("Enter password");
        }else if (password.length() < 6){
            binding.etPassword.setError("Password must be at least 6 characters");
        }else{
            SignUp();
        }

    }

    private void SignUp() {
        progressDialog.show();
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        //success
                        progressDialog.dismiss();
                        //get user info
                        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                        String email = firebaseUser.getEmail();
                        Toast.makeText(getContext(), "Account created\n"+email, Toast.LENGTH_SHORT).show();

                        startActivity(new Intent(getActivity(), NavigationActivity.class));
                        getActivity().finish();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //failure
                        Toast.makeText(getContext(), "Error : "+e.getMessage(), Toast.LENGTH_LONG).show();

                    }
                });
    }
}