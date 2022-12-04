package uk.ac.tees.b1498820.wefix.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import uk.ac.tees.b1498820.wefix.R;
import uk.ac.tees.b1498820.wefix.models.Business;

public class BusinessListAdapter extends RecyclerView.Adapter<BusinessListAdapter.BusinessViewHolder> {
    private ArrayList<Business> businesses;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    public BusinessListAdapter(Context context, ArrayList<Business> businesses) {
        this.businesses = businesses;
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public BusinessViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.business_item, parent, false);
        return new BusinessViewHolder(view);
    }

    @NonNull

    @Override
    public void onBindViewHolder(@NonNull BusinessViewHolder holder, int position) {
        holder.tvBusinessName.setText(businesses.get(position).getBusinessName());
        holder.tvBusinessAddress.setText(businesses.get(position).getAddress());
        holder.tvBusinessDescription.setText(businesses.get(position).getDescription());
    }


    @Override
    public int getItemCount() {
        return businesses.size();
    }

    public class BusinessViewHolder extends  RecyclerView.ViewHolder implements View.OnClickListener{
        TextView tvBusinessName, tvBusinessDescription, tvBusinessAddress;
        public BusinessViewHolder(@NonNull View itemView) {
            super(itemView);
            tvBusinessName = itemView.findViewById(R.id.businessName);
            tvBusinessDescription = itemView.findViewById(R.id.businessDescription);
            tvBusinessAddress = itemView.findViewById(R.id.businessAddress);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    public Business getItem(int id) {
        return businesses.get(id);
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
