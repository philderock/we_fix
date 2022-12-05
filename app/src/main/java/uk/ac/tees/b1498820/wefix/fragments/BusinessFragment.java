package uk.ac.tees.b1498820.wefix.fragments;

import static com.sucho.placepicker.Constants.*;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import android.location.Location;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sucho.placepicker.AddressData;
import com.sucho.placepicker.Constants;
import com.sucho.placepicker.PlacePicker;

import java.util.ArrayList;

import uk.ac.tees.b1498820.wefix.activities.NavigationActivity;
import uk.ac.tees.b1498820.wefix.databinding.FragmentBusinessBinding;
import uk.ac.tees.b1498820.wefix.models.Business;
import uk.ac.tees.b1498820.wefix.models.User;

public class BusinessFragment extends Fragment {
    FragmentBusinessBinding binding;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseUser currentUser = firebaseAuth.getCurrentUser();
    View view;
    private FusedLocationProviderClient fusedLocationClient;
    private Double myLatitude = Double.valueOf(0);
    private Double myLongitude = Double.valueOf(0);
    private Business myBusiness = new Business();
    ProgressDialog progressDialog;

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

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(getContext());
        getCurrentLocation();
        //initialize firebase
//        firebaseAuth = FirebaseAuth.getInstance();
        //initialize view binding
        binding = FragmentBusinessBinding.inflate(getLayoutInflater());
        fetchUserBusiness();

        binding.etBAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ContextCompat.checkSelfPermission(
                        getActivity(),
                        Manifest.permission
                                .ACCESS_FINE_LOCATION)
                        == PackageManager
                        .PERMISSION_GRANTED
                        && ContextCompat.checkSelfPermission(
                        getActivity(),
                        Manifest.permission
                                .ACCESS_COARSE_LOCATION)
                        == PackageManager
                        .PERMISSION_GRANTED) {
                    // When permission is granted
                    // Call method
                    getCurrentLocation();
                }
                else {
                    // When permission is not granted
                    // Call method
                    requestPermissions(
                            new String[] {
                                    Manifest.permission
                                            .ACCESS_FINE_LOCATION,
                                    Manifest.permission
                                            .ACCESS_COARSE_LOCATION },
                            100);
                }
                Intent intent = new PlacePicker.IntentBuilder().setLatLong(myLatitude, myLongitude) .build(getActivity());

                startActivityForResult(intent, PLACE_PICKER_REQUEST);
            }
        });

        binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog = new ProgressDialog(getContext());
                progressDialog.setTitle("Please wait");
                progressDialog.setMessage("Saving your business info...");
                progressDialog.setCanceledOnTouchOutside(false);

                saveBusiness();
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == Constants.PLACE_PICKER_REQUEST) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                AddressData addressData = data.getParcelableExtra(Constants.ADDRESS_INTENT);
            binding.etBAddress.setText(addressData.getAddressList().get(0).getAddressLine(0));
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @SuppressLint("MissingPermission")
    private void getCurrentLocation()
    {
        // Initialize Location manager
        LocationManager locationManager
                = (LocationManager)getActivity()
                .getSystemService(
                        Context.LOCATION_SERVICE);
        // Check condition
        if (locationManager.isProviderEnabled(
                LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(
                LocationManager.NETWORK_PROVIDER)) {
            // When location service is enabled
            // Get last location
            fusedLocationClient.getLastLocation().addOnCompleteListener(
                    new OnCompleteListener<Location>() {
                        @Override
                        public void onComplete(
                                @NonNull Task<Location> task)
                        {

                            // Initialize location
                            Location location
                                    = task.getResult();
                            // Check condition
                            if (location != null) {
                                // When location result is not
                                // null set latitude
                                myLatitude = location.getLatitude();
                                myLongitude = location.getLongitude();

                            }
                            else {
                                // When location result is null
                                // initialize location request
                                LocationRequest locationRequest
                                        = new LocationRequest()
                                        .setPriority(
                                                LocationRequest
                                                        .PRIORITY_HIGH_ACCURACY)
                                        .setInterval(10000)
                                        .setFastestInterval(
                                                1000)
                                        .setNumUpdates(1);

                                // Initialize location call back
                                LocationCallback
                                        locationCallback
                                        = new LocationCallback() {
                                    @Override
                                    public void
                                    onLocationResult(
                                            LocationResult
                                                    locationResult)
                                    {
                                        // Initialize
                                        // location
                                        Location location1
                                                = locationResult
                                                .getLastLocation();
                                        // Set latitude
                                        myLatitude = location1.getLatitude();
                                        myLongitude = location1.getLongitude();
                                    }
                                };

                                // Request location updates
                                fusedLocationClient.requestLocationUpdates(
                                        locationRequest,
                                        locationCallback,
                                        Looper.myLooper());
                            }
                        }
                    });
        }
        else {
            // When location service is not enabled
            // open location setting
        }
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode, @NonNull String[] permissions,
            @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(
                requestCode, permissions, grantResults);
        // Check condition
        if (requestCode == 100 && (grantResults.length > 0)
                && (grantResults[0] + grantResults[1]
                == PackageManager.PERMISSION_GRANTED)) {
            // When permission are granted
            // Call  method
            getCurrentLocation();
        }
        else {
            // When permission are denied
            // Display toast
            Toast
                    .makeText(getActivity(),
                            "Permission denied",
                            Toast.LENGTH_SHORT)
                    .show();
        }
    }

    void fetchUserBusiness(){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("businesses/"+currentUser.getUid());
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Business business = (Business) snapshot.getValue(Business.class);
                if (business != null && binding != null){
                    myBusiness = business;
                    binding.etBName.setText(business.getBusinessName());
                    binding.etBAddress.setText(business.getAddress());
                    binding.etBDescription.setText(business.getDescription());
                    binding.etBTag.setText(business.getServicesTag());
                    binding.etBPhone.setText(business.getPhoneNumber());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void saveBusiness() {
        progressDialog.show();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        Business business = new Business(
                currentUser.getUid(),
                binding.etBName.getText().toString(),
                binding.etBDescription.getText().toString(),
                binding.etBAddress.getText().toString(),
                binding.etBTag.getText().toString(),
                "Others",
                myLatitude,
                myLongitude,
                binding.etBPhone.getText().toString()
                );

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                databaseReference.child("businesses").child(currentUser.getUid()).setValue(business);
                progressDialog.setMessage("Successfully saved profile");
                new Handler().postDelayed(() -> progressDialog.dismiss(), 5000);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressDialog.setMessage("Error : "+error.getMessage());
                new Handler().postDelayed(() -> progressDialog.dismiss(), 5000);
            }
        });
    }

}