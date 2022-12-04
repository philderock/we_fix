package uk.ac.tees.b1498820.wefix.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import uk.ac.tees.b1498820.wefix.adapters.BusinessListAdapter;
import uk.ac.tees.b1498820.wefix.databinding.FragmentHomeBinding;
import uk.ac.tees.b1498820.wefix.models.Business;

public class HomeFragment extends Fragment implements BusinessListAdapter.ItemClickListener {

    private FragmentHomeBinding binding;
    ArrayList<Business> businesses = new ArrayList<>();
    BusinessListAdapter businessListAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fetchBusinesses();
        binding = FragmentHomeBinding.inflate(getLayoutInflater());
        binding.list.setLayoutManager(new LinearLayoutManager(getContext()));
        businessListAdapter = new BusinessListAdapter(getContext(), businesses);
        businessListAdapter.setClickListener(this);
        binding.list.setAdapter(businessListAdapter);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    void fetchBusinesses(){
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
        Toast.makeText(getContext(), "clicked"+businesses.get(position).getAddress(), Toast.LENGTH_SHORT).show();
    }
}