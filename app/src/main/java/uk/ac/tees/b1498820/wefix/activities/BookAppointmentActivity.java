package uk.ac.tees.b1498820.wefix.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import uk.ac.tees.b1498820.wefix.R;
import uk.ac.tees.b1498820.wefix.databinding.ActivityBookAppointmentBinding;
import uk.ac.tees.b1498820.wefix.models.Business;

public class BookAppointmentActivity extends AppCompatActivity {

    private ActivityBookAppointmentBinding binding;
    private final String BUSINESS_INFO = "business_info";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityBookAppointmentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Business businessinfo = (Business) getIntent().getParcelableExtra(BUSINESS_INFO);

        Toolbar toolbar = binding.toolbar;
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolBarLayout = binding.toolbarLayout;
        if (businessinfo != null){
            toolBarLayout.setTitle(businessinfo.getBusinessName());
            binding.scrollBody.tvContent.setText(
                    businessinfo.getBusinessName()+"\n\n\n"
                            +"Description:\n"
                    +businessinfo.getDescription()+"\n\n"
                            +"Address:\n"
                            +businessinfo.getAddress()+"\n\n"
                            +"Contact:\n"
                            +businessinfo.getCategory()+"\n\n"
                            +"Kindly give us a call to provide you with awesome handy services\n\n"
                    +"Hit the star button to make us your favorite"

            );
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FloatingActionButton fab = binding.fab;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(businessinfo.getPhoneNumber())){
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:"+businessinfo.getPhoneNumber().trim() ));
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(), "No phone number found", Toast.LENGTH_LONG).show();
                }

            }
        });
    }
}