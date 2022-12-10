package uk.ac.tees.b1498820.wefix.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import uk.ac.tees.b1498820.wefix.R;
import uk.ac.tees.b1498820.wefix.activities.BookAppointmentActivity;
import uk.ac.tees.b1498820.wefix.adapters.BusinessListAdapter;
import uk.ac.tees.b1498820.wefix.databinding.AppointmentItemListBinding;
import uk.ac.tees.b1498820.wefix.databinding.FragmentHomeBinding;
import uk.ac.tees.b1498820.wefix.fragments.placeholder.PlaceholderContent;
import uk.ac.tees.b1498820.wefix.models.Business;

/**
 * A fragment representing a list of Items.
 */
public class AppointmentFragment extends Fragment implements BusinessListAdapter.ItemClickListener {

    private AppointmentItemListBinding binding;
    ArrayList<Business> businesses = new ArrayList<>();
    BusinessListAdapter businessListAdapter;
    private final String BUSINESS_INFO = "business_info";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = AppointmentItemListBinding.inflate(getLayoutInflater());
        fetchBusinesses();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        if (binding == null){
            binding = AppointmentItemListBinding.inflate(getLayoutInflater());
        }
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        fetchBusinesses();
    }

    void fetchBusinesses(){
        binding.list.setLayoutManager(new LinearLayoutManager(getContext()));
        businessListAdapter = new BusinessListAdapter(getContext(), businesses);
        businessListAdapter.setClickListener(this);
        binding.list.setAdapter(businessListAdapter);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("businesses");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                businesses.clear();
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    Business business = postSnapshot.getValue(Business.class);
                    businesses.add(business);
                }
                businessListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent i=new Intent(getContext(), BookAppointmentActivity.class);
        i.putExtra(BUSINESS_INFO, businesses.get(position));
        startActivity(i);

    }
}