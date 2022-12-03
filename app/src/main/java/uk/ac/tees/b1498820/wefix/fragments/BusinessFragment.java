package uk.ac.tees.b1498820.wefix.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;

import uk.ac.tees.b1498820.wefix.R;
import uk.ac.tees.b1498820.wefix.databinding.FragmentBusinessBinding;
import uk.ac.tees.b1498820.wefix.databinding.FragmentLoginBinding;

public class BusinessFragment extends Fragment {
    FragmentBusinessBinding binding;
    FirebaseAuth firebaseAuth;
    View view;
    public BusinessFragment() {
        // Required empty public constructor
    }

    public static BusinessFragment newInstance() {
        BusinessFragment fragment = new BusinessFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //initialize firebase
        firebaseAuth = FirebaseAuth.getInstance();
        //initialize view binding
        binding = FragmentBusinessBinding.inflate(getLayoutInflater());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = binding.getRoot();
        return view;
    }
}